package com.gt.util.activity;

import java.lang.reflect.Constructor;

import com.gt.entity.FactivityModel;

public abstract class FactivitytUtil {
	
	
	
	
	
	//运行定时处理
	public abstract void luckyDrawTask(FactivityModel factivityModel)  throws Exception ;

	
	//运行定时处理
	public abstract void close(FactivityModel factivityModel)  throws Exception ;
	public static FactivitytUtil createWalletByClass(String calsspath) throws Exception{
		Class c=Class.forName(calsspath);
		Constructor c1=c.getDeclaredConstructor();
		c1.setAccessible(true);
		FactivitytUtil Factivityt =  (FactivitytUtil)c1.newInstance();
		return Factivityt;
	}
	
}
