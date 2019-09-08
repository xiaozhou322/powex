package com.gt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.LotteryDAO;
import com.gt.entity.Lottery;
import com.gt.entity.Nper;
import com.gt.service.front.LotteryService;
import com.gt.util.Utils;

@Service
public class LotteryServiceImpl implements LotteryService {

	@Autowired
	private LotteryDAO lotteryDAO;
	
	@Override
	public void createTable(String tableName) {
		lotteryDAO.createTable(tableName);
	}
	
	@Override
	public Lottery findById(int id, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.findById(id);
	}

	@Override
	public void saveObj(Lottery obj, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		lotteryDAO.save(obj);

	}

	@Override
	public void deleteObj(Lottery obj, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		lotteryDAO.delete(obj);

	}

	@Override
	public void updateObj(Lottery obj, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		lotteryDAO.attachDirty(obj);

	}

	@Override
	public List<Lottery> findByProperty(String name, Object value, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.findByProperty(name, value);
	}
	
	@Override
	public List<Lottery> findByTwoProperty(String propertyName1, Object value1,String propertyName2, Object value2, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.findByTwoProperty(propertyName1, value1, propertyName2, value2);
	}

	@Override
	public List<Lottery> list(int firstResult, int maxResults, String filter,boolean isFY, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.list(firstResult, maxResults, filter, isFY);
	}
	@Override
	public int getAllCount(String filter, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.getAllCount(filter);
	}
	
	@Override
	public List<String> queryList(int firstResult, int maxResults, String hql,boolean isFY, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.queryList(firstResult, maxResults, hql, isFY);
	}

	@Override
	public Long currentLotteryNumber(String nper, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.currentLotteryNumber(nper);
	}
	
	@Override
	public Long maxLotteryNo(String nper, String tableName) {
		lotteryDAO.replaceTableName(tableName);
		return lotteryDAO.maxLotteryNo(nper);
	}

	private static Map<Integer,Integer> nperCount = new HashMap<Integer, Integer>();
	
	@Override
	public String saveNewNper(Nper nper, int uid) {
		synchronized (nperCount) {
			if(nperCount.containsKey(nper.getId())){
				int no = nperCount.get(nper.getId());
				if(no == nper.getLottery_max()){
					return "";
				}
				no++;
				nperCount.put(nper.getId(), no);
			}else{
				nperCount = new HashMap<Integer, Integer>();
				int no = nper.getLottery_min();
				Long currentNo = maxLotteryNo(nper.getNper(),Utils.getLotteryTable(nper.getNper()));
				if(currentNo >= no){
					no = currentNo.intValue() + 1;
				}
				nperCount.put(nper.getId(), no);
			}
			Lottery lottery = new Lottery();
			lottery.setUid(uid);
			lottery.setNper(nper.getNper());
			lottery.setLottery_no(nperCount.get(nper.getId()).toString());
			lottery.setSerial_no(Utils.getMD5_32_xx(lottery.getUid()+lottery.getLottery_no()));
			try {
				saveObj(lottery,Utils.getLotteryTable(nper.getNper()));
			} catch (Exception e) {
				if(nperCount.containsKey(nper.getId())){
					int no = nperCount.get(nper.getId());
					nperCount.put(nper.getId(), --no);
				}
				throw e;
			}
			return String.valueOf(nperCount.get(nper.getId()));
		}
	}
	
}
