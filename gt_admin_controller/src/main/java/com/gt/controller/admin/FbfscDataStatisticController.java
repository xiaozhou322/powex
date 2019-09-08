package com.gt.controller.admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gt.controller.BaseAdminController;
import com.gt.entity.FbfscDataStatistics;
import com.gt.service.admin.AdminService;
import com.gt.service.front.FrontOthersService;
import com.gt.util.Utils;

/**
 * BFSC统计数据  admin--controller
 * @author zhouyong
 *
 */
@Controller
public class FbfscDataStatisticController extends BaseAdminController {
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontOthersService frontOthersService ;
	
	private int numPerPage = Utils.getNumPerPage();
	
	
	@RequestMapping("/buluo718admin/bfscDataStatisticList")
	public ModelAndView bfscDataStatisticList(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/bfscDataStatisticList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1  \n");
		
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append(" and  DATE_FORMAT(statistical_date,'%Y-%m-%d') >= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append(" and  DATE_FORMAT(statistical_date,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}


		if (orderField != null && orderField.trim().length() > 0) {
			filter.append(" order by " + orderField + "\n");
		}else{
			filter.append(" order by id \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		}else{
			filter.append( " desc \n");
		}
		

		List<FbfscDataStatistics> list = this.frontOthersService.queryFbfscDataStatisticsList((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("bfscDataStatisticsList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "bfscDataStatisticsList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FbfscDataStatistics", filter+""));
//		modelAndView.addObject("totalAmount",this.adminService.getAllSum("Fdrawaccounts", "famount",filter+""));
		return modelAndView;
	}
	
	
	/**
	 * 
	* @Title: findBfscDataStatisticById  
	* @Description: 根据id获取BFSC统计数据详情 
	* @author Ryan
	* @param @param id
	* @param @return
	* @param @throws Exception  
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/buluo718admin/bfscDataStatisticDetail")
	public ModelAndView findBfscDataStatisticById(@RequestParam(required=true)int pid) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		FbfscDataStatistics bfscDataStatistics = frontOthersService.findFbfscDataStatisticsById(pid);
		
		String filterapp = "where ftype=1 and fcointype.fid =";
		String filterjys = "where ftype=2 and fcointype.fid = 20";
		
		double appbfsc = this.adminService.getAllSum("Fdrawaccounts", "famount",filterapp+"20");
		double appusdt = this.adminService.getAllSum("Fdrawaccounts", "famount",filterapp+"4");
		double jysbfs = this.adminService.getAllSum("Fdrawaccounts", "famount",filterjys);
		
		modelAndView.addObject("appbfsc", appbfsc);
		modelAndView.addObject("appusdt", appusdt);
		modelAndView.addObject("jysbfs",jysbfs);
		
		modelAndView.addObject("bfscDataStatistics", bfscDataStatistics);
		modelAndView.setViewName("ssadmin/bfscDataStatisticsDetail");
		return modelAndView;
	}
	
	@RequestMapping("/buluo718admin/exportfscDataStatistic")
	public String exportfscDataStatistic(HttpServletRequest request, HttpServletResponse response,
										@RequestParam(required=true)int pid) throws Exception{
		
		
		FbfscDataStatistics bfscDataStatistics = frontOthersService.findFbfscDataStatisticsById(pid);
		
		String filterapp = "where ftype=1 and fcointype.fid =";
		String filterjys = "where ftype=2 and fcointype.fid = 20";
		
		double appbfsc = this.adminService.getAllSum("Fdrawaccounts", "famount",filterapp+"20");
		double appusdt = this.adminService.getAllSum("Fdrawaccounts", "famount",filterapp+"4");
		double jysbfs = this.adminService.getAllSum("Fdrawaccounts", "famount",filterjys);
		
		
		HSSFWorkbook	workbook = null;
		HSSFSheet		sheet = null;
		HSSFRow			row = null;
		try {
			String tempFileName = request.getSession().getServletContext().getRealPath("/excelTemplate");
			InputStream input = new FileInputStream(tempFileName+"/bfscTemplate.xls");
			POIFSFileSystem fs = new POIFSFileSystem(input);
			workbook = new HSSFWorkbook(fs);
			sheet = workbook.getSheet("report");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//填入第一行的数据项，分别是：
		//充币数量($),提币数量($),买入数量(B),卖出数量(B)
		row = sheet.getRow(3);
		row.getCell(3).setCellValue(bfscDataStatistics.getCharge_amount());
		row.getCell(5).setCellValue(bfscDataStatistics.getWithdraw_amount());
		row.getCell(9).setCellValue(bfscDataStatistics.getMarket_buy_qty());
		row.getCell(11).setCellValue(bfscDataStatistics.getMarket_sell_qty());
		
		//填入第二行的数据项，分别是：
		//充币笔数,提币笔数,买入金额($),卖出金额($)
		row = sheet.getRow(4);
		row.getCell(3).setCellValue(bfscDataStatistics.getCharge_num());
		row.getCell(5).setCellValue(bfscDataStatistics.getWithdraw_num());
		row.getCell(9).setCellValue(bfscDataStatistics.getMarket_buy_amount());
		row.getCell(11).setCellValue(bfscDataStatistics.getMarket_sell_amount());
		
		//填入第三行的数据项，分别是：
		//购买数量($),售出数量($),买入手续费(B),卖出手续费($)
		row = sheet.getRow(5);
		row.getCell(3).setCellValue(bfscDataStatistics.getOtc_buy_usdt_amount());
		row.getCell(5).setCellValue(bfscDataStatistics.getOtc_sell_usdt_amount());
		row.getCell(9).setCellValue(bfscDataStatistics.getMarket_buy_fees());
		row.getCell(11).setCellValue(bfscDataStatistics.getMarket_sell_fees());
		
		//填入第四行的数据项，分别是：
		//购买金额(￥),售出金额(￥),总买入数量,总卖出数量
		row = sheet.getRow(6);
		row.getCell(3).setCellValue(bfscDataStatistics.getOtc_buy_amount());
		row.getCell(5).setCellValue(bfscDataStatistics.getOtc_sell_amount());
		row.getCell(9).setCellValue(bfscDataStatistics.getMarket_buy_total_qty());
		row.getCell(11).setCellValue(bfscDataStatistics.getMarket_sell_total_qty());
		
		//填入第五行的数据项，分别是：
		//购买笔数,售出笔数,总买入金额,总卖出金额
		row = sheet.getRow(7);
		row.getCell(3).setCellValue(bfscDataStatistics.getOtc_buy_num());
		row.getCell(5).setCellValue(bfscDataStatistics.getOtc_sell_num());
		row.getCell(9).setCellValue(bfscDataStatistics.getMarket_buy_total_amount());
		row.getCell(11).setCellValue(bfscDataStatistics.getMarket_sell_total_amount());
		
		//填入第六行的数据项，分别是：
		//USDT转入数($),BFSC转入数(B),BFSC数量(B)
		row = sheet.getRow(8);
		row.getCell(3).setCellValue(bfscDataStatistics.getTransfer_usdt_in_amount());
		row.getCell(5).setCellValue(bfscDataStatistics.getTransfer_bfsc_in_amount());
		row.getCell(9).setCellValue(bfscDataStatistics.getBfsc_fees_amount());
		
		//填入第七行的数据项，分别是：
		//USDT转入总数($),BFSC转入总数(B),BFSC均价($)
		row = sheet.getRow(9);
		row.getCell(3).setCellValue(appusdt);
		row.getCell(5).setCellValue(appbfsc-49576.959);
		row.getCell(9).setCellValue(bfscDataStatistics.getBfsc_avg_price());
				
		//填入第八行的数据项，分别是：
		//BFSC转出数(B),待交割USDT($)
		row = sheet.getRow(10);
		row.getCell(5).setCellValue(bfscDataStatistics.getTransfer_bfsc_out_amount());
		row.getCell(9).setCellValue(bfscDataStatistics.getUsdt_fees_amount());
				
		//填入第九行的数据项，分别是：
		//BFSC存量数(B),BFSC转出总数(B),总注册人数,新增注册数量
		row = sheet.getRow(11);
		row.getCell(3).setCellValue(bfscDataStatistics.getBfsc_stock_amount());
		row.getCell(5).setCellValue(jysbfs-49521.65);
		row.getCell(9).setCellValue(bfscDataStatistics.getRegister_total_num());
		row.getCell(11).setCellValue(bfscDataStatistics.getRegister_add_num());
		
		//填入统计日期
		row = sheet.getRow(1);
		row.getCell(11).setCellValue(bfscDataStatistics.getStatistical_date());
		
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=bfscDataStatistic"+bfscDataStatistics.getStatistical_date()+".xls");
		
		
		ServletOutputStream os = null;
		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("生成导出Excel文件出错!", e);
		} catch (IOException e) {
			throw new RuntimeException("写入Excel文件出错!", e);
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
