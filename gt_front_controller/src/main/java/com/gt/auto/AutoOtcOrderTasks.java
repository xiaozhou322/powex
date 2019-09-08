package com.gt.auto;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gt.Enum.OtcOrderLogsTypeEnum;
import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.Enum.OtcPendAppealEnum;
import com.gt.entity.FotcOrder;
import com.gt.service.front.FotcOrderService;
import com.gt.service.front.FrontConstantMapService;
import com.gt.util.DateHelper;
import com.gt.util.Utils;
import com.gt.utils.ApplicationContextUtil;

@Component
public class AutoOtcOrderTasks{
	
	private static final Logger logger = LoggerFactory.getLogger(AutoOtcOrderTasks.class);
	
	private FotcOrderService otcorderService = (FotcOrderService)ApplicationContextUtil.getBean("fotcOrderService");
	
	private FrontConstantMapService constantMap = (FrontConstantMapService)ApplicationContextUtil.getBean("frontConstantMapService");;
	
	private Integer countdown1;//等待接单倒计时
	private Integer countdown2;//等待付款倒计时
	private Integer countdown3;//付款超时申诉倒计时
	private Integer countdown4;//等待确认倒计时
	private Integer countdown5;//确认超时申诉倒计时
	private Integer countdown6;//等待二次确认倒计时
	/**
	 * 初始化
	 */
	public void init() {
		logger.info("执行订单扫描任务<<<<<<<<<<<<<<<");
		
		
		synchronized (this) {
			new Thread(new Runnable() {
				public void run() {
					while(true){
						try {
							scanJob();
							
							//休眠2秒
							Thread.sleep(2000L);
						} catch (Exception e1) {
							e1.printStackTrace();
							logger.info("执行订单扫描任务异常：<<<<<<<<<<<<<<<"+e1);
						}
					}
				}
			}).start();
			}
		}

	private void initTime(){
		countdown1 = 300;
		countdown2 = 1200;
		countdown3 = 1800;
		countdown4 = 1200;
		countdown5 = 600;
		countdown6 = 300;
		try {
			if (constantMap.get("countdown1")!=null){
				countdown1 = Integer.valueOf(constantMap.getString("countdown1"));
			}
			if (constantMap.get("countdown2")!=null){
				countdown2 = Integer.valueOf(constantMap.getString("countdown2"));
			}
			if (constantMap.get("countdown3")!=null){
				countdown3 = Integer.valueOf(constantMap.getString("countdown3"));
			}
			if (constantMap.get("countdown4")!=null){
				countdown4 = Integer.valueOf(constantMap.getString("countdown4"));
			}
			if (constantMap.get("countdown5")!=null){
				countdown5 = Integer.valueOf(constantMap.getString("countdown5"));
			}
			if (constantMap.get("countdown6")!=null){
				countdown6 = Integer.valueOf(constantMap.getString("countdown6"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			//配置异常默认等待时间1200秒
			countdown1 = 300;
			countdown2 = 1200;
			countdown3 = 1800;
			countdown4 = 1200;
			countdown5 = 600;
			countdown6 = 300;
		}
	}
	
	public void scanJob() {
		
		//每次扫描前重新读取时间
		initTime();
		
		try {
			String filter = "where orderStatus in ("+OtcOrderStatusEnum.notreceived+","+OtcOrderStatusEnum.Payable+","
					+OtcOrderStatusEnum.Paid +","+OtcOrderStatusEnum.Recognitionreceipt+") order by createTime  ";
			List<FotcOrder> otcOrderList = otcorderService.list(0, 0, filter, false) ;
			if(null == otcOrderList || otcOrderList.size() == 0) {
				return;
			} else {

				for (FotcOrder fotcorder : otcOrderList) {
					Date lastTime = new Date(fotcorder.getUpdateTime().getTime());
					if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.notreceived){						
							systemCancel (fotcorder,lastTime, countdown1);						
						
					}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.Payable){
						if(countdown2>0){
							//等待付款时间大于0，才做自动倒计时，否则一直等待用户操作
							if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.yes){
								//未付款待申诉阶段
	                        	 systemCancel (fotcorder,lastTime, countdown3);
							}else if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.no){
								 //未付款等待阶段
	                        	 if(countdown3>0){
	                        		 //如果未付款等待申诉时间大于0，才进行申诉等待状态修改，否则直接取消订单
	 								PendAppeal (fotcorder, lastTime, countdown2);
	 							}else{
	 								systemCancel (fotcorder,lastTime, countdown2);
	 							}
								
							}
						}
               
						
					}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.Paid){
						if(countdown4>0){
							//等待确认时间大于0，才做自动倒计时，否则一直等待用户操作
					        if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.yes){
					        	//未确认收款待申诉阶段
	                        	systemCancel (fotcorder,lastTime, countdown5);
							}else if(fotcorder.getOvertime()!=null&&fotcorder.getOvertime()==OtcPendAppealEnum.no){
								 //未确认收款等待阶段
								if(countdown5>0){
									 //如果未确认收款等待申诉时间大于0，才进行申诉等待状态修改，否则直接取消订单
									PendAppeal (fotcorder, lastTime, countdown4);
								}else{
									systemCancel (fotcorder,lastTime, countdown4);
								}
								
							}
						}
              
						
					}else if(fotcorder.getOrderStatus()!=null&&fotcorder.getOrderStatus()==OtcOrderStatusEnum.Recognitionreceipt){
						
						systemConfirm (fotcorder, lastTime, countdown6);
					}
															
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	/**
	 * 正常系统取消
	 * @param fotcorder
	 * @param lastTime
	 * @throws Exception
	 */
	private void systemCancel (FotcOrder fotcorder, Date lastTime, Integer waitTime) throws Exception {
		if(DateHelper.getDifferSecond(new Date(), lastTime)/1000 > waitTime) {
			//查询订单
			FotcOrder order = otcorderService.queryById(fotcorder.getId());
			if(OtcOrderStatusEnum.failOrder == order.getOrderStatus() 
					|| OtcOrderStatusEnum.ExceptionOrder == order.getOrderStatus()					
					|| OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 return ;
			 }			 
			 //系统取消订单
			 otcorderService.updateFailedOrder(fotcorder.getId(), OtcOrderLogsTypeEnum.auto_cancel);
			 //第三方回调
			 if(fotcorder.getFuser().isFisprojecter() && fotcorder.getIs_third() == 1) {
				 //状态变更回调
				 otcorderService.updateOrderStatusCallback(fotcorder.getId());
			 }
		}
	}
	
	/**
	 * 系统自动确认收款
	 * @param fotcorder
	 * @param lastTime
	 * @throws Exception
	 */
	private void systemConfirm (FotcOrder fotcorder, Date lastTime, Integer waitTime) throws Exception {
		if(DateHelper.getDifferSecond(new Date(), lastTime)/1000 > waitTime) {
			//查询订单
			FotcOrder order = otcorderService.queryById(fotcorder.getId());
			if(OtcOrderStatusEnum.failOrder == order.getOrderStatus() 
					|| OtcOrderStatusEnum.ExceptionOrder == order.getOrderStatus()					
					|| OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 return ;
			}
			
			//修改otc钱包
			otcorderService.updateSucess(fotcorder.getId(), 0);
			//第三方回调
			if(fotcorder.getFuser().isFisprojecter() && fotcorder.getIs_third() == 1) {
				 //状态变更回调
				 otcorderService.updateOrderStatusCallback(fotcorder.getId());
			}
		}
	}
	
	
	
	/**
	 * 修改订单为等待申诉状态
	 * @param fotcorder
	 * @param lastTime
	 * @throws Exception
	 */
	private void PendAppeal (FotcOrder fotcorder, Date lastTime, Integer waitTime) throws Exception {
		if(DateHelper.getDifferSecond(new Date(), lastTime)/1000 > waitTime) {
			//查询订单
			FotcOrder order = otcorderService.queryById(fotcorder.getId());
			if(OtcOrderStatusEnum.failOrder == order.getOrderStatus() 
					|| OtcOrderStatusEnum.ExceptionOrder == order.getOrderStatus()					
					|| OtcOrderStatusEnum.success == order.getOrderStatus()) {
				 return ;
			}
			
			order.setUpdateTime(Utils.getTimestamp());
            if(order!=null&&order.getOvertime()!=OtcPendAppealEnum.yes){
            	order.setOvertime(OtcPendAppealEnum.yes);
            	otcorderService.updateObj(order);
            }
		}
	}
	
	
}
