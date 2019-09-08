package com.gt.auto;

import java.util.List;
import java.util.TimerTask;

import com.gt.Enum.OtcOrderStatusEnum;
import com.gt.entity.FotcOrder;
import com.gt.service.front.FotcOrderService;
import com.gt.utils.ApplicationContextUtil;

public class AutoServiceCallbackOrgin extends TimerTask{

	private FotcOrderService otcorderService = (FotcOrderService)ApplicationContextUtil.getBean("fotcOrderService");
	
	@Override
	public void run() {
		try {
			String filter = "where is_third = 1 and is_callback_success = 0 and orderStatus in ("+OtcOrderStatusEnum.success+","+ OtcOrderStatusEnum.failOrder+") order by createTime asc  ";
			List<FotcOrder> otcOrderList = otcorderService.list(0, 0, filter, false) ;
			if(null == otcOrderList || otcOrderList.size() == 0) {
				return;
			} else {
				for (FotcOrder fotcorder : otcOrderList) {
					if(fotcorder.getFuser().isFisprojecter() && fotcorder.getIs_third() == 1) {
						 //状态变更回调
						 otcorderService.updateOrderStatusCallback(fotcorder.getId());
					 }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
