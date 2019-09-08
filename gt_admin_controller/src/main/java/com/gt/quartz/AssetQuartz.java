package com.gt.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.service.front.AssetService;

//每日资产记录
public class AssetQuartz {

	@Autowired
	private AssetService assetService ;
	public void work(){
		try {
			assetService.updateAllAssets() ;
			//assetService.updateGTAssets();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			
			public void run() {
				while(true ){
					boolean flag = true ;
					try {
						flag = assetService.updateAllAssetsDetail() ;
					} catch (Exception e) {
						e.printStackTrace();
						flag = true ;
					}
					if(flag == false ){
						break;
					}
				}
				
			}
		}).start() ;
	}
}
