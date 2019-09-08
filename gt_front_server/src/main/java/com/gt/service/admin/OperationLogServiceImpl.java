package com.gt.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.MessageStatusEnum;
import com.gt.Enum.OperationlogEnum;
import com.gt.Enum.RemittanceTypeEnum;
import com.gt.dao.FmessageDAO;
import com.gt.dao.FoperationlogDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fadmin;
import com.gt.entity.Fmessage;
import com.gt.entity.Foperationlog;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.Utils;

@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {
	@Autowired
	private FoperationlogDAO operationlogDAO;
	@Autowired
	private FmessageDAO messageDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;

	public Foperationlog findById(int id) {
		Foperationlog operationLog = this.operationlogDAO.findById(id);;
		return operationLog;
	}

	public void saveObj(Foperationlog obj) {
		this.operationlogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Foperationlog obj = this.operationlogDAO.findById(id);
		this.operationlogDAO.delete(obj);
	}

	public void updateObj(Foperationlog obj) {
		this.operationlogDAO.attachDirty(obj);
	}

	public List<Foperationlog> findByProperty(String name, Object value) {
		return this.operationlogDAO.findByProperty(name, value);
	}

	public List<Foperationlog> findAll() {
		return this.operationlogDAO.findAll();
	}

	public List<Foperationlog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		List<Foperationlog> all = this.operationlogDAO.list(firstResult, maxResults, filter,isFY);
		for (Foperationlog foperationlog : all) {
			foperationlog.getFuser().getFemail();
		}
		return all;
	}
	
	public boolean updateOperationLog(int operationId,Fadmin auditor) throws RuntimeException{
		//判断能否找到记录
		try {
			Foperationlog operationLog = findById(operationId);
			if(operationLog == null){
				return false;
			}else if(operationLog.getFstatus() != OperationlogEnum.SAVE){
				return false;
			}
			double amount = operationLog.getFamount();
			Fvirtualwallet wallet = this.virtualwalletDAO.findWallet(operationLog.getFuser().getFid());
			if(operationLog.getFtype()==RemittanceTypeEnum.Type4){
				//如果是USDT，则调用USDT钱包
				wallet = this.virtualwalletDAO.findUSDTWallet(operationLog.getFuser().getFid());
			}
			wallet.setFtotal(wallet.getFtotal()+amount);
			wallet.setFlastUpdateTime(Utils.getTimestamp());
			this.virtualwalletDAO.attachDirty(wallet);
			
			operationLog.setFlastUpdateTime(Utils.getTimestamp());
			operationLog.setFstatus(OperationlogEnum.AUDIT);
			operationLog.setFkey1(auditor.getFname());
			this.operationlogDAO.attachDirty(operationLog);
			
			String title = "管理员向您充值"+amount+"人民币,请注意查收";
			if(operationLog.getFtype()==RemittanceTypeEnum.Type4){
				//如果是USDT，则调用USDT钱包
				title = "管理员向您充值"+amount+"USDT,请注意查收";
			}
			Fmessage msg = new Fmessage();
			msg.setFcreateTime(Utils.getTimestamp());
			msg.setFcontent(title);
			msg.setFreceiver(operationLog.getFuser());
			msg.setFcreator(auditor);
			msg.setFtitle(title);
			msg.setFstatus(MessageStatusEnum.NOREAD_VALUE);
			this.messageDAO.save(msg);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return true;
	}
}
