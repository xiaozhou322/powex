package com.gt.service.front;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.CapitalOperationOutStatus;
import com.gt.Enum.CapitalOperationTypeEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.dao.FcapitaloperationDAO;
import com.gt.dao.FscoreDAO;
import com.gt.dao.FsystemargsDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.FvirtualcaptualoperationDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.FwithdrawfeesDAO;
import com.gt.entity.FbankinfoWithdraw;
import com.gt.entity.Fcapitaloperation;
import com.gt.entity.Fscore;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwithdrawfees;
import com.gt.service.front.FrontAccountService;
import com.gt.util.PasswordHelper;
import com.gt.util.Utils;

@Service("frontAccountService")
public class FrontAccountServiceImpl implements FrontAccountService {
	private static final Logger log = LoggerFactory.getLogger(FrontAccountServiceImpl.class);
	@Autowired
	private FcapitaloperationDAO fcapitaloperationDAO ;
	@Autowired
	private FscoreDAO fscoreDAO ;
	@Autowired
	private FuserDAO fuserDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FvirtualcaptualoperationDAO fvirtualcaptualoperationDAO ;
	@Autowired
	private FsystemargsDAO fsystemargsDAO ;
	@Autowired
	private FwithdrawfeesDAO withdrawfeesDAO;
	
	public Integer addFcapitaloperation(Fcapitaloperation fcapitaloperation){
		return this.fcapitaloperationDAO.save(fcapitaloperation) ;
	}
	
	public List<Fcapitaloperation> findCapitalList(int firstResult, int maxResults,String filter,boolean isFY){
		return this.fcapitaloperationDAO.findByParam(firstResult, maxResults, filter,isFY) ;
	}
	
	public int findCapitalCount(Map<String, Object> param){
		return this.fcapitaloperationDAO.findByParamCount(param) ;
	}
	
	public Fcapitaloperation findCapitalOperationById(int id){
		Fcapitaloperation fcapitaloperation = this.fcapitaloperationDAO.findById(id) ;
		return fcapitaloperation ;
	}
	
	public Fcapitaloperation findCapitalOperationByRemark(String remark){
		List<Fcapitaloperation> fcapitaloperations= this.fcapitaloperationDAO.findByFremark(remark) ;
		Fcapitaloperation fcapitaloperation = null;
		if (fcapitaloperations.size()>0){
			fcapitaloperation=fcapitaloperations.get(0);
		}
		return fcapitaloperation ;
	}
	
	public void updateCapitalOperation(Fcapitaloperation fcapitaloperation){
		this.fcapitaloperationDAO.attachDirty(fcapitaloperation) ;
	}
	
	public void updateSaveCapitalOperation(Fcapitaloperation fcapitaloperation){
		this.fcapitaloperationDAO.save(fcapitaloperation) ;
	}
	
	public boolean updateWithdrawCNY(double withdrawBanlance,Fuser fuser,FbankinfoWithdraw fbankinfoWithdraw) {
		boolean flag = false ;
		try {
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findWallet(fuser.getFid());
			if(fvirtualwallet.getFtotal()<withdrawBanlance){
				return false ;
			}
			
			Fwithdrawfees ffees = this.withdrawfeesDAO.findFfee(fvirtualwallet.getFvirtualcointype().getFid(), fuser.getFscore().getFlevel());
			double feesRate = ffees.getFfee();
			
			Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
			fcapitaloperation.setfBank(fbankinfoWithdraw.getFname()) ;
			fcapitaloperation.setfAccount(fbankinfoWithdraw.getFbankNumber()) ;
			fcapitaloperation.setFaddress(fbankinfoWithdraw.getFaddress()+fbankinfoWithdraw.getFothers());
			
			double fees = Utils.getDouble(withdrawBanlance*feesRate,2);
			
			//手续费低于2元按照2元计费
			if (fees<2){
				fees=2;
			}
			
			double amt = Double.parseDouble(Utils.getDoubleS(withdrawBanlance-fees,2));
			fcapitaloperation.setFamount(amt) ;
			fcapitaloperation.setFfees(fees) ;
			fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
			fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_OUT) ;
			fcapitaloperation.setFuser(fuser) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			fcapitaloperation.setfPhone(fuser.getFtelephone()) ;
			fcapitaloperation.setfPayee(fuser.getFrealName()) ;
			fcapitaloperation.setFremittanceType(null) ;
			fcapitaloperation.setFstatus(CapitalOperationOutStatus.WaitForOperation) ;
			this.fcapitaloperationDAO.save(fcapitaloperation) ;
				
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-amt-fees);
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+amt+fees);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			flag = true ;
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return flag ;
	}
	
	public int updateWithdrawUSDT(double withdrawBanlance,Fuser fuser,FbankinfoWithdraw fbankinfoWithdraw) {
		int flag = -1 ;
		try {
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findUSDTWallet(fuser.getFid());
			if(fvirtualwallet.getFtotal()<withdrawBanlance){
				return -1 ;
			}
			
			Fwithdrawfees ffees = this.withdrawfeesDAO.findFfee(fvirtualwallet.getFvirtualcointype().getFid(), fuser.getFscore().getFlevel());
			double feesRate = ffees.getFfee();
			
			Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
			fcapitaloperation.setfBank(fbankinfoWithdraw.getFname()) ;
			fcapitaloperation.setfAccount(fbankinfoWithdraw.getFbankNumber()) ;
			fcapitaloperation.setFaddress(fbankinfoWithdraw.getFaddress()+fbankinfoWithdraw.getFothers());
			
			double fees = Utils.getDouble(withdrawBanlance*feesRate,2);
			
			//手续费低于0.5USDT按照0.5USDT计费
			if (fees<0.5){
				fees=0.5;
			}
			
			double amt = Double.parseDouble(Utils.getDoubleS(withdrawBanlance-fees,2));
			fcapitaloperation.setFamount(amt) ;
			fcapitaloperation.setFfees(fees) ;
			fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
			fcapitaloperation.setFtype(CapitalOperationTypeEnum.USDT_OUT) ;
			fcapitaloperation.setFuser(fuser) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			fcapitaloperation.setfPhone(fuser.getFtelephone()) ;
			fcapitaloperation.setfPayee(fuser.getFrealName()) ;
			fcapitaloperation.setFremittanceType(null) ;
			fcapitaloperation.setFstatus(CapitalOperationOutStatus.WaitForOperation) ;
			this.fcapitaloperationDAO.save(fcapitaloperation) ;
				
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-amt-fees);
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+amt+fees);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			flag = fcapitaloperation.getFid() ;
			//提币码
			String getCoinCode = PasswordHelper.encryString(fcapitaloperation.getFid().toString(), fuser.getSalt());
			fcapitaloperation.setFremark("UT"+getCoinCode.substring(8, 24));
			this.fcapitaloperationDAO.save(fcapitaloperation);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return flag ;
	}
	
	public Fscore findFscoreById(int id){
		return this.fscoreDAO.findById(id) ;
	}
	
	public void updateCancelWithdrawCny(Fcapitaloperation fcapitaloperation,Fuser fuser){
		try {
			fcapitaloperation.setFstatus(CapitalOperationOutStatus.Cancel) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findWallet(fuser.getFid());
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-fcapitaloperation.getFfees()-fcapitaloperation.getFamount());
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+fcapitaloperation.getFfees()+fcapitaloperation.getFamount());
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.fcapitaloperationDAO.attachDirty(fcapitaloperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCancelWithdrawUsdt(Fcapitaloperation fcapitaloperation,Fuser fuser){
		try {
			fcapitaloperation.setFstatus(CapitalOperationOutStatus.Cancel) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findUSDTWallet(fuser.getFid());
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-fcapitaloperation.getFfees()-fcapitaloperation.getFamount());
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+fcapitaloperation.getFfees()+fcapitaloperation.getFamount());
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.fcapitaloperationDAO.attachDirty(fcapitaloperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCancelWithdrawBtc(Fvirtualcaptualoperation fvirtualcaptualoperation,Fuser fuser){
		try {
			fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.Cancel) ;
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
			
			double amount = fvirtualcaptualoperation.getFamount()+fvirtualcaptualoperation.getFfees() ;
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), fvirtualcaptualoperation.getFvirtualcointype().getFid()) ;
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+amount) ;
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-amount) ;
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
			
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			this.fvirtualcaptualoperationDAO.attachDirty(fvirtualcaptualoperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public int getTodayCnyWithdrawTimes(Fuser fuser){
		return this.fcapitaloperationDAO.getTodayCnyWithdrawTimes(fuser) ;
	}
	
	public int getTodayVirtualCoinWithdrawTimes(Fuser fuser){
		return this.fvirtualcaptualoperationDAO.getTodayVirtualCoinWithdrawTimes(fuser) ;
	}
	
	
}
