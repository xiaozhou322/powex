package com.gt.auto;

import org.springframework.beans.factory.annotation.Autowired;

public class AutoCalculateDepth {

	@Autowired
	private RealTimeData realTimeData ;
	
	public void init() {
		new Thread(new Work()).start() ;
	}
	
	class Work implements Runnable{
		public void run() {
			while(true){
				try {
					realTimeData.generateDepthData() ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try{
					Thread.sleep(20L) ;
				}catch(Exception e){
					e.printStackTrace() ;
				}
				
			}
		}
	}

}
