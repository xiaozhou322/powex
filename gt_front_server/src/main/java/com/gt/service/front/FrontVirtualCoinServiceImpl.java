package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.Enum.CoinTypeEnum;
import com.gt.Enum.VirtualCapitalOperationInStatusEnum;
import com.gt.Enum.VirtualCapitalOperationOutStatusEnum;
import com.gt.Enum.VirtualCapitalOperationTypeEnum;
import com.gt.Enum.VirtualCoinTypeStatusEnum;
import com.gt.dao.FsystemargsDAO;
import com.gt.dao.FvirtualaddressDAO;
import com.gt.dao.FvirtualaddressWithdrawDAO;
import com.gt.dao.FvirtualcaptualoperationDAO;
import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.dao.FwithdrawfeesDAO;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualaddress;
import com.gt.entity.FvirtualaddressWithdraw;
import com.gt.entity.Fvirtualcaptualoperation;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.entity.Fwithdrawfees;
import com.gt.service.front.FrontVirtualCoinService;
import com.gt.util.Utils;

@Service("frontVirtualCoinService")
public class FrontVirtualCoinServiceImpl implements FrontVirtualCoinService {
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;
	@Autowired
	private FwithdrawfeesDAO withdrawfeesDAO;
	@Autowired
	private FvirtualaddressDAO fvirtualaddressDAO ;
	@Autowired
	private FvirtualaddressWithdrawDAO fvirtualaddressWithdrawDAO ;
	@Autowired
	private FvirtualcaptualoperationDAO fvirtualcaptualoperationDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FsystemargsDAO systemargsDAO;
	
	public List<Fvirtualcointype> findFvirtualCoinType(int status,int coinType){
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.findByParam(0, 0, " where fstatus="+status+" and ftype ="+coinType+" order by fid asc ", false, Fvirtualcointype.class) ;
		return list ;
	}
	
	public List<Fvirtualcointype> findByProperty(String propertyName, Object value){
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.findByProperty(propertyName,value) ;
		return list ;
	}
	public Fvirtualcointype findFvirtualCoinById(int id){
		Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(id) ;
		return fvirtualcointype ;
	}
	
	public Fvirtualcointype findFirstFirtualCoin(){
		Fvirtualcointype fvirtualcointype = null ;
		String filter ="where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and ftype="+CoinTypeEnum.COIN_VALUE;
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.list(0, 0, filter, false);
		if(list.size()>0){
			fvirtualcointype = list.get(0) ;
		}else{
			fvirtualcointype = (Fvirtualcointype)this.fvirtualcointypeDAO.findAll(CoinTypeEnum.COIN_VALUE,0).get(0);
		}
		return fvirtualcointype ;
	}
	
	public Fvirtualcointype findFirstFirtualCoin_Wallet(){
		Fvirtualcointype fvirtualcointype = null ;
		String filter = "where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and FIsWithDraw=1";
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.list(0, 0, filter, false);
		if(list.size()>0){
			fvirtualcointype = list.get(0) ;
		}
		return fvirtualcointype ;
	}
	
	public Fvirtualaddress findFvirtualaddress(Fuser fuser,Fvirtualcointype fvirtualcointype){
		return this.fvirtualaddressDAO.findFvirtualaddress(fuser, fvirtualcointype) ;
	}
	
	public List<Fvirtualaddress> findFvirtualaddress(Fvirtualcointype fvirtualcointype,String address){
		return this.fvirtualaddressDAO.findFvirtualaddress(fvirtualcointype, address) ;
	}
	
	public FvirtualaddressWithdraw findFvirtualaddressWithdraw(int fid){
		return this.fvirtualaddressWithdrawDAO.findById(fid);
	}
	
	public List<FvirtualaddressWithdraw> findFvirtualaddressWithdraws(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fvirtualaddressWithdrawDAO.list(firstResult, maxResults, filter,isFY);
	}
	
	public int findFvirtualcaptualoperationCount(
			Fuser fuser,int type[],int status[],Fvirtualcointype[] fvirtualcointypes,String order){
		return this.fvirtualcaptualoperationDAO.findFvirtualcaptualoperationCount(fuser, type, status, fvirtualcointypes, order) ;
	}
	
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperations(int firstResult, int maxResults,String filter, boolean isFY){
		List<Fvirtualcaptualoperation> list =  this.fvirtualcaptualoperationDAO.findByParam(firstResult, maxResults, filter, isFY, Fvirtualcaptualoperation.class) ;
		for (Fvirtualcaptualoperation fvirtualcaptualoperation : list) {
			fvirtualcaptualoperation.getFvirtualcointype().getFname() ;
		}
		return list ;
	}
	public int findFvirtualcaptualoperationsCount(String filter){
		return this.fvirtualcaptualoperationDAO.findByParamCount(filter, Fvirtualcaptualoperation.class) ;
	}
	
	public void updateFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw){
		this.fvirtualaddressWithdrawDAO.save(fvirtualaddressWithdraw) ;
	}
	
	public void updateDelFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw){
		this.fvirtualaddressWithdrawDAO.delete(fvirtualaddressWithdraw) ;
	}
	
	public Fwithdrawfees findFfees(int virtualCoinTypeId,int level){
		return this.withdrawfeesDAO.findFfee(virtualCoinTypeId, level) ;
	}
	
	public void updateWithdrawBtc(FvirtualaddressWithdraw fvirtualaddressWithdraw,Fvirtualcointype fvirtualcointype,Fvirtualwallet fvirtualwallet ,double withdrawAmount,double ffees,Fuser fuser){
		try {
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-withdrawAmount) ;
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+withdrawAmount) ;
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			
			Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
			fvirtualcaptualoperation.setFamount(withdrawAmount-ffees) ;
			fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFfees(ffees) ;
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation) ;
			fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_OUT) ;
			fvirtualcaptualoperation.setFuser(fuser) ;
			fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype) ;
			fvirtualcaptualoperation.setWithdraw_virtual_address(fvirtualaddressWithdraw.getFadderess()) ;
			fvirtualcaptualoperation.setFischarge(fvirtualaddressWithdraw.getFremark()); // 借用字段存储DCT类的MEMO信息
			this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateTransfer(Fvirtualcointype fvirtualcointype,Fvirtualwallet fvirtualwallet ,double withdrawAmount,double ffees,Fuser fuser,Fuser fuskr){
		try {
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-withdrawAmount) ;
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+withdrawAmount) ;
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			
			Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
			fvirtualcaptualoperation.setFamount(withdrawAmount-ffees) ;
			fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFfees(ffees) ;
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation) ;
			fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_TRANSFER) ;
			fvirtualcaptualoperation.setFuser(fuser) ;
			fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype) ;
			fvirtualcaptualoperation.setWithdraw_virtual_address(String.valueOf(fuskr.getFid())) ; //存放转账收款人的ID
			fvirtualcaptualoperation.setFischarge(fuskr.getFloginName()); // 借用字段存储转账收款人账号
			this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void addFvirtualcaptualoperation(Fvirtualcaptualoperation fvirtualcaptualoperation){
		this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
	}
	
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperationByProperty(String key,Object value){
		return this.fvirtualcaptualoperationDAO.findByProperty(key, value) ;
	}
	
	public Fvirtualcaptualoperation findFvirtualcaptualoperationById(int id){
		return this.fvirtualcaptualoperationDAO.findById(id) ;
	}
	
	//比特币自动充值并加币
	public void updateFvirtualcaptualoperationCoinIn(Fvirtualcaptualoperation fvirtualcaptualoperation) {
		try {
			Fvirtualcaptualoperation real = this.fvirtualcaptualoperationDAO.findById(fvirtualcaptualoperation.getFid()) ;
			if(real!=null && real.getFstatus()!=VirtualCapitalOperationInStatusEnum.SUCCESS){
				real.setFstatus(fvirtualcaptualoperation.getFstatus()) ;
				real.setFconfirmations(fvirtualcaptualoperation.getFconfirmations()) ;
				real.setFlastUpdateTime(Utils.getTimestamp()) ;
				real.setFamount(fvirtualcaptualoperation.getFamount());
				this.fvirtualcaptualoperationDAO.attachDirty(real) ;
				
				if(real.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS && real.isFhasOwner()){
					Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(real.getFvirtualcointype().getFid()) ;
					Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(real.getFuser().getFid(), fvirtualcointype.getFid()) ;
					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+ real.getFamount()) ;
					fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
					this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	//比特币自动充值并加币
	public void updateFvirtualcaptualoperationCoinIn_ETP(Fvirtualcaptualoperation fvirtualcaptualoperation) {
		try {
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation);
			if(fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS && fvirtualcaptualoperation.isFhasOwner()){
				Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(fvirtualcaptualoperation.getFvirtualcointype().getFid()) ;
				Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fvirtualcaptualoperation.getFuser().getFid(), fvirtualcointype.getFid()) ;
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+ fvirtualcaptualoperation.getFamount()) ;
				fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public List<Fvirtualaddress> findFvirtualaddressByProperty(String key,Object value){
		List<Fvirtualaddress> fvirtualaddresses = this.fvirtualaddressDAO.findByProperty(key, value) ;
		for (Fvirtualaddress fvirtualaddress : fvirtualaddresses) {
			fvirtualaddress.getFuser().getFnickName() ;
		}
		return fvirtualaddresses ;
	}
	
	public boolean isExistsCanWithdrawCoinType(){
		List<Fvirtualcointype> fvirtualcointypes = this.fvirtualcointypeDAO.findByParam(0, 0, " where FIsWithDraw=1 and fstatus=1 ", false, Fvirtualcointype.class) ;
		return fvirtualcointypes.size()>0 ;
	}

	public Fvirtualcointype findById(int frenchCurrencyType) {
		return fvirtualcointypeDAO.findById(frenchCurrencyType);
	}

	public List<Fvirtualcointype> list(int firstResult, int maxResults,
			String filter, boolean isFY) {
		return fvirtualcointypeDAO.list(firstResult, maxResults, filter, isFY);
	}
	
}
