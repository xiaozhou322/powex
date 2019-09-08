package com.gt.service.front;

import java.util.List;

import com.gt.entity.Ftrademapping;
import com.gt.entity.Fvirtualcointype;

public interface FtradeMappingService {


	public Ftrademapping findFtrademapping(int fid);

	public Ftrademapping findFtrademapping2(int fid);

	public List<Ftrademapping> list1(int firstResult, int maxResults,
			String filter, boolean isFY, Class c, Object... param);

	// 可以交易的
	public List<Ftrademapping> findActiveTradeMapping();

	// 按法币查询
	public List<Ftrademapping> findActiveTradeMappingByFB(
			Fvirtualcointype fvirtualcointype);
	
	public List<Ftrademapping> list(int firstResult, int maxResults,
			String filter,boolean isFY);
	
}
