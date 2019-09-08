package com.gt.quartz;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.EntrustTypeEnum;
import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.Enum.OtcOrderTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.entity.FbfscDataStatistics;
import com.gt.entity.FfeesConvert;
import com.gt.entity.Ftrademapping;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.AdminService;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontOthersService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;


/**
 * BFSC数据统计定时任务
 * @author zhouyong
 *
 */
public class AutoFbfscDataStatistic {
	private static final Logger log = LoggerFactory.getLogger(AutoFbfscDataStatistic.class);
	
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private TradeMappingService tradeMappingService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FrontOthersService frontOthersService ;
	
	/**
	 * 数据统计定时任务入口
	 */
	public void work() {
		synchronized (this) {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>开始进行BFSC数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
			try{
				String yesterdayStr = Utils.getYesterday();
				
				Integer userId = 366665;
				
				this.bfscDataStatistic(yesterdayStr, userId);
				
			}catch (Exception e) {
				e.printStackTrace() ;
				log.error("BFSC数据统计异常", e.getMessage());
			}
			log.info(">>>>>>>>>>>>>>>>>>>>>>>结束BFSC数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
	
	
	
	
	/**
	 * BFSC数据统计
	 */
	private void bfscDataStatistic(String yesterdayStr, Integer userId) {
		try{
			/*//获USDT币种信息
			List<Fvirtualcointype> usdtCoinTypeList = virtualCoinService.findByProperty("fShortName", "USDT");
			Fvirtualcointype usdtCoinType = usdtCoinTypeList.get(0);
			
			//获BFSC币种信息
			List<Fvirtualcointype> bfscCoinTypeList = virtualCoinService.findByProperty("fShortName", "BFSC");
			Fvirtualcointype bfscCoinType = null;
			if(null != bfscCoinTypeList && bfscCoinTypeList.size() > 0) {
				bfscCoinType = bfscCoinTypeList.get(0);
			}
			
			//查询BFSC兑USDT的交易市场
			Ftrademapping bfscUsdtMapping = tradeMappingService.findById(19);
			*/
			
			
			//>>>>>>>>>>>>>>>>>>>>>>>充提数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<
			//USDT冲币数量
			String sqlfilter = "select SUM(a.fAmount + a.ffees) FROM fvirtualcaptualoperation a"
					+ "  left join fuser t on a.FUs_fId2 = t.fId"
					+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_IN 
					+"   and a.fStatus = "+ VirtualCapitalOperationInStatusEnum.SUCCESS
					+"   and a.fVi_fId2 = 4"
					+"   and t.fregfrom = '366665'"
					+"   and DATE_FORMAT(a.fcreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double usdtChargeAmount = adminService.getSQLValue(sqlfilter);
			//USDT提币数量
			sqlfilter = "select SUM(a.fAmount + a.ffees) from fvirtualcaptualoperation a"
					+ "  left join fuser t on a.FUs_fId2 = t.fId"
					+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_OUT
					+ "  and a.fStatus = "+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
					+"   and a.fVi_fId2 = 4"
					+"   and t.fregfrom = '366665'"
					+"   and DATE_FORMAT(a.fcreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double usdtWithdrawAmount = adminService.getSQLValue(sqlfilter);
			
			
			//冲币笔数
			sqlfilter = "select COUNT(a.fId) FROM fvirtualcaptualoperation a"
					+ "  left join fuser t on a.FUs_fId2 = t.fId"
					+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_IN 
					+"   and a.fStatus = "+ VirtualCapitalOperationInStatusEnum.SUCCESS
					+ "  and a.fVi_fId2 = 4"
					+ "  and t.fregfrom = '366665'"
					+"   and DATE_FORMAT(a.fcreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double chargeNum = adminService.getSQLValue(sqlfilter);
			//提币笔数
			sqlfilter = "select COUNT(a.fId) FROM fvirtualcaptualoperation a"
					+ "  left join fuser t on a.FUs_fId2 = t.fId"
					+ "  where a.fType = "+ VirtualCapitalOperationTypeEnum.COIN_OUT
					+ "  and a.fStatus = "+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
					+ "  and a.fVi_fId2 = 4"
					+ "  and t.fregfrom = '366665'"
					+"   and DATE_FORMAT(a.fcreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double withdrawNum = adminService.getSQLValue(sqlfilter);
			
			
			//>>>>>>>>>>>>>>>>>>>>>>>OTC数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<
			//OTC购买数量（USDT）
			sqlfilter = "select sum(t.amount) from f_otcorder t left join f_otcadvertisement a on t.ad_id = a.id"
					+ "  where a.user_id = 370712"
					+ "  and t.order_status = " + OtcOrderStatusEnum.success
					+ "  and t.order_type = " + OtcOrderTypeEnum.sell_order
					+ "  and t.coin_type = 4"
					+"   and DATE_FORMAT(t.create_time,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double otcBuyUsdtAmount = adminService.getSQLValue(sqlfilter);
			
			
			//OTC出售数量
			sqlfilter = "select sum(t.amount) from f_otcorder t left join f_otcadvertisement a on t.ad_id = a.id"
					+ "  where a.user_id = 370712"
					+ "  and t.order_status = " + OtcOrderStatusEnum.success
					+ "  and t.order_type = " + OtcOrderTypeEnum.buy_order
					+ "  and t.coin_type = 4"
					+"   and DATE_FORMAT(t.create_time,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double otcSellUsdtAmount = adminService.getSQLValue(sqlfilter);
			
			
			//OTC购买金额（CNY）
			sqlfilter = "select sum(t.total_price) from f_otcorder t left join f_otcadvertisement a on t.ad_id = a.id"
					+ "  where a.user_id = 370712"
					+ "  and t.order_status = " + OtcOrderStatusEnum.success
					+ "  and t.order_type = " + OtcOrderTypeEnum.sell_order
					+ "  and t.coin_type = 4"
					+"   and DATE_FORMAT(t.update_time,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double otcBuyAmount = adminService.getSQLValue(sqlfilter);
			
			//OTC出售金额（CNY）
			sqlfilter = "select sum(t.total_price) from f_otcorder t left join f_otcadvertisement a on t.ad_id = a.id"
					+ "  where a.user_id = 370712"
					+ "  and t.order_status = " + OtcOrderStatusEnum.success
					+ "  and t.order_type = " + OtcOrderTypeEnum.buy_order
					+ "  and t.coin_type = 4"
					+"   and DATE_FORMAT(t.create_time,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double otcSellAmount = adminService.getSQLValue(sqlfilter);
			
			
			//OTC购买笔数（USDT）
			sqlfilter = "select count(t.id) from f_otcorder t left join f_otcadvertisement a on t.ad_id = a.id"
					+ "  where a.user_id = 370712"
					+ "  and t.order_status = " + OtcOrderStatusEnum.success
					+ "  and t.order_type = " + OtcOrderTypeEnum.sell_order
					+ "  and t.coin_type = 4"
					+"   and DATE_FORMAT(t.create_time,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double otcBuyNum = adminService.getSQLValue(sqlfilter);
			
			
			//OTC出售笔数（USDT）
			sqlfilter = "select count(t.id) from f_otcorder t left join f_otcadvertisement a on t.ad_id = a.id"
					+ "  where a.user_id = 370712"
					+ "  and t.order_status = " + OtcOrderStatusEnum.success
					+ "  and t.order_type = " + OtcOrderTypeEnum.buy_order
					+ "  and t.coin_type = 4"
					+"   and DATE_FORMAT(t.create_time,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double otcSellNum = adminService.getSQLValue(sqlfilter);
			
			
			
			//>>>>>>>>>>>>>>>>>>>>>>>划转数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<
			//USDT转入数量
			sqlfilter = "select sum(t.fAmount) from fdrawaccounts t"
					+ "  where t.fCointype = 4"
					+ "  and t.fType = 1"
					+"   and DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double transferUsdtInAmount = adminService.getSQLValue(sqlfilter);
			
			//USDT转入笔数
			sqlfilter = "select count(t.fId) from fdrawaccounts t"
					+ "  where t.fCointype = 4"
					+ "  and t.fType = 1"
					+"   and DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double transferUsdtInNum = adminService.getSQLValue(sqlfilter);
			
			//BFSC转入数量
			sqlfilter = "select sum(t.fAmount) from fdrawaccounts t"
					+ "  where t.fCointype = 20"
					+ "  and t.fType = 1"
					+"   and DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double transferBfscInAmount = adminService.getSQLValue(sqlfilter);
			
			//BFSC转出数量
			sqlfilter = "select sum(t.fAmount) from fdrawaccounts t"
					+ "  where t.fCointype = 20"
					+ "  and t.fType = 2"
					+"   and DATE_FORMAT(t.fCreateTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			double transferBfscOutAmount = adminService.getSQLValue(sqlfilter);
			
			//BFSC存量数   TODO(除去项目方自己的BFSC数量)
			//获取项目方BFSC钱包
			Fvirtualwallet bfscWallet = this.frontUserService.findVirtualWalletByUser(366665, 20) ;
			
			sqlfilter = " where fvirtualcointype.fid = 20";
			double totalBfscWallet = adminService.getAllSum("Fvirtualwallet", "ftotal+ffrozen", sqlfilter);
			double bfscStockAmount = totalBfscWallet-(bfscWallet.getFtotal()+bfscWallet.getFfrozen());
			
			
			
			//>>>>>>>>>>>>>>>>>>>>>>>BFSC兑USDT市场数据统计<<<<<<<<<<<<<<<<<<<<<<<<<<
			
			//总买入数量
			sqlfilter = "select sum(a.fCount-a.fleftCount) from fentrust a"
					+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY
					+"   and a.ftrademapping = 19";
			double marketBuyTotalQty = adminService.getSQLValue(sqlfilter);
			
			
			//总买入金额
			sqlfilter = "select sum(a.fsuccessAmount) from fentrust a"
					+ "  where a.fEntrustType = "+ EntrustTypeEnum.BUY 
					+"   and a.ftrademapping = 19";
			double marketBuyTotalAmount = adminService.getSQLValue(sqlfilter);
			
			
			//总卖出数量
			sqlfilter = "select sum(a.fCount-a.fleftCount) from fentrust a"
					+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL
					+"   and a.ftrademapping = 19";
			double marketSellTotalQty = adminService.getSQLValue(sqlfilter);
			
			
			//总卖出金额
			sqlfilter = "select sum(a.fsuccessAmount) from fentrust a"
					+ "  where a.fEntrustType = "+ EntrustTypeEnum.SELL 
					+"   and a.ftrademapping = 19";
			double marketSellTotalAmount = adminService.getSQLValue(sqlfilter);
			
			//查询前一天的数据
			sqlfilter = " order by id desc";
			List<FbfscDataStatistics> bfscDataStatistics = frontOthersService.queryFbfscDataStatisticsList(0, 1, sqlfilter, true);
			double marketBuyQty = 0;         //BFSC兑USDT买入数量
			double marketSellQty = 0;        //BFSC兑USDT卖出数量
			double marketBuyAmount = 0;      //BFSC兑USDT买入金额
			double marketSellAmount = 0;     //BFSC兑USDT卖出金额
			double marketBuyFees = 0;        //BFSC兑USDT买入手续费
			double marketSellFees = 0;       //BFSC兑USDT卖出手续费
			if(null != bfscDataStatistics && bfscDataStatistics.size()>0) {
				//前天的总买入数量
				double marketBuyTotalQtyBefore = bfscDataStatistics.get(0).getMarket_buy_total_qty();
				//前天的总卖出数量
				double marketSellTotalQtyBefore = bfscDataStatistics.get(0).getMarket_sell_total_qty();
				//前天的总买入金额
				double marketBuyTotalAmountBefore = bfscDataStatistics.get(0).getMarket_buy_total_amount();
				//前天的总卖出金额
				double marketSellTotalAmountBefore = bfscDataStatistics.get(0).getMarket_sell_total_amount();
				
				marketBuyQty = marketBuyTotalQty-marketBuyTotalQtyBefore;
				
				marketSellQty = marketSellTotalQty-marketSellTotalQtyBefore;
				
				marketBuyAmount = marketBuyTotalAmount-marketBuyTotalAmountBefore;
				
				marketSellAmount = marketSellTotalAmount-marketSellTotalAmountBefore;
				
				marketBuyFees = marketBuyQty * 0.01;
				
				marketSellFees =marketSellAmount * 0.01;
			}
			
			//手续费交割BFSC数量
			double bfscFeesAmount = marketBuyFees;
			//BFSC均价
			double bfscAvgPrice = 0;
			if(0 != marketBuyQty) {
				bfscAvgPrice = Utils.getDouble(marketBuyAmount/marketBuyQty, 4);
			}
			//待交割USDT数量
			double usdtFeesAmount = Utils.getDouble(bfscFeesAmount*bfscAvgPrice, 4);
			
			//注册总人数
			int userTotal = adminService.getAllCount("Fuser","where fregfrom='366665'");
			//新增注册人数
			sqlfilter = " where 1=1 and  fregfrom='366665' and  DATE_FORMAT(fregisterTime,'%Y-%m-%d') = '" + yesterdayStr +"' ";
			int userAddNum = adminService.getAllCount("Fuser",sqlfilter+"");
			
			
			Fuser fuser = frontUserService.findById(366665);
			//保存统计记录
			FbfscDataStatistics bfscData = new FbfscDataStatistics();
			bfscData.setFuser(fuser);
			bfscData.setStatistical_date(yesterdayStr);
			bfscData.setCharge_amount(usdtChargeAmount);
			bfscData.setWithdraw_amount(usdtWithdrawAmount);
			bfscData.setCharge_num(chargeNum);
			bfscData.setWithdraw_num(withdrawNum);
			bfscData.setOtc_buy_usdt_amount(otcBuyUsdtAmount);
			bfscData.setOtc_sell_usdt_amount(otcSellUsdtAmount);
			bfscData.setOtc_buy_amount(otcBuyAmount);
			bfscData.setOtc_buy_num(otcBuyNum);
			bfscData.setOtc_sell_amount(otcSellAmount);
			bfscData.setOtc_sell_num(otcSellNum);
			bfscData.setTransfer_usdt_in_amount(transferUsdtInAmount);
			bfscData.setTransfer_usdt_in_num(transferUsdtInNum);
			bfscData.setTransfer_bfsc_in_amount(transferBfscInAmount);
			bfscData.setTransfer_bfsc_out_amount(transferBfscOutAmount);
			bfscData.setBfsc_stock_amount(bfscStockAmount);
			bfscData.setMarket_buy_total_amount(marketBuyTotalAmount);
			bfscData.setMarket_buy_total_qty(marketBuyTotalQty);
			bfscData.setMarket_sell_total_amount(marketSellTotalAmount);
			bfscData.setMarket_sell_total_qty(marketSellTotalQty);
			bfscData.setMarket_buy_amount(marketBuyAmount);
			bfscData.setMarket_buy_qty(marketBuyQty);
			bfscData.setMarket_buy_fees(marketBuyFees);
			bfscData.setMarket_sell_amount(marketSellAmount);
			bfscData.setMarket_sell_qty(marketSellQty);
			bfscData.setMarket_sell_fees(marketSellFees);
			bfscData.setBfsc_fees_amount(bfscFeesAmount);
			bfscData.setBfsc_avg_price(bfscAvgPrice);
			bfscData.setUsdt_fees_amount(usdtFeesAmount);
			bfscData.setRegister_total_num(userTotal);
			bfscData.setRegister_add_num(userAddNum);
			bfscData.setCreateTime(Utils.getTimestamp());
			
			FfeesConvert ffeesConvert = null;
			//保存手续费兑换记录
			if(0 != marketBuyQty) {
				ffeesConvert = new FfeesConvert();
				ffeesConvert.setProjectId(fuser);
				ffeesConvert.setBfscAmount(bfscFeesAmount);
				ffeesConvert.setBfscPrice(bfscAvgPrice);
				ffeesConvert.setUsdtAmount(usdtFeesAmount);
				ffeesConvert.setStatus(1);     //处理状态  1：未处理    2：已处理
				Date calcDay = new Date();
				try {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					calcDay = df.parse(yesterdayStr);
				}catch(Exception e) {
					calcDay = new Date();
				}
				ffeesConvert.setCreateTime(new Timestamp(calcDay.getTime()));
				ffeesConvert.setUpdateTime(Utils.getTimestamp());
			}
			
			frontOthersService.saveFbfscDataStatistics(bfscData, ffeesConvert);
			
		}catch (Exception e) {
			e.printStackTrace() ;
			log.error("项目方成交单记录统计异常", e.getMessage());
		}
		log.info(">>>>>>>>>>>>>>>>>>>>>>>结束项目方成交单记录统计<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
	
	
}
