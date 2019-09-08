package com.gt.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.FaboutDAO;
import com.gt.dao.FarticleDAO;
import com.gt.dao.FarticletypeDAO;
import com.gt.dao.FbankinDAO;
import com.gt.dao.FbannerDAO;
import com.gt.dao.FbfscDataStatisticsDAO;
import com.gt.dao.FcapitaloperationDAO;
import com.gt.dao.FentrustlogDAO;
import com.gt.dao.FfeesConvertDAO;
import com.gt.dao.FfriendlinkDAO;
import com.gt.dao.FintrolinfoDAO;
import com.gt.dao.FmessageDAO;
import com.gt.dao.FperiodDAO;
import com.gt.dao.FscoreDAO;
import com.gt.dao.FuserDAO;
import com.gt.dao.FvirtualcointypeDAO;
import com.gt.dao.FvirtualwalletDAO;
import com.gt.entity.Fabout;
import com.gt.entity.Farticle;
import com.gt.entity.Farticletype;
import com.gt.entity.Fbankin;
import com.gt.entity.Fbanner;
import com.gt.entity.FbfscDataStatistics;
import com.gt.entity.Fentrustlog;
import com.gt.entity.FfeesConvert;
import com.gt.entity.Ffriendlink;
import com.gt.entity.Fperiod;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.util.Utils;

@Service("frontOthersService")
public class FrontOthersServiceImpl implements FrontOthersService {
	@Autowired
	private FfriendlinkDAO ffriendlinkDAO ;
	@Autowired
	private FarticleDAO farticleDAO ;
	@Autowired
	private FbannerDAO fbannerDAO ;
	@Autowired
	private FaboutDAO faboutDAO ;
	@Autowired
	private FarticletypeDAO farticletypeDAO ;
	@Autowired
	private FperiodDAO fperiodDAO ;
	@Autowired
	private FentrustlogDAO fentrustlogDAO ;
	@Autowired
	private FbankinDAO fbankinDAO ;
	@Autowired
	private FuserDAO fuserDao;
	@Autowired
	private FscoreDAO fscoreDAO;
	@Autowired
	private FintrolinfoDAO fintrolinfoDAO;
	@Autowired
	private FcapitaloperationDAO fcapitaloperationDAO ;
	@Autowired
	private FvirtualcointypeDAO virtualcointypeDAO;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FmessageDAO fmessageDAO;
	@Autowired
	private FfeesConvertDAO ffeesConvertDAO;
	@Autowired
	private FbfscDataStatisticsDAO fbfscDataStatisticsDAO;
	
	public List<Ffriendlink> findFfriendLink(int type){
		return this.ffriendlinkDAO.findByProperty("ftype",type) ;
	}
	
	public Fabout findFabout(int fid){
		return this.faboutDAO.findById(fid) ;
	}
	
	public List<Fabout> findFaboutAll(){
		return this.faboutDAO.findAll() ;
	}
	
	public List<Farticle> findFarticle(int farticletype,int firstResult,int maxResult){
		List<Farticle> farticles = this.farticleDAO.findFarticle(farticletype, firstResult, maxResult);
		return farticles ;
	}
	
	public List<Fbanner> findFbanner(String fbannertype,int firstResult,int maxResult){
		List<Fbanner> fbanners = this.fbannerDAO.findFbanner(fbannertype, firstResult, maxResult);
		return fbanners ;
	}
	
	public int findFarticleCount(int farticletype){
		return this.farticleDAO.findFarticleCount(farticletype) ;
	}
	public int findProjectFarticleCount(int projectId, int farticletype){
		return this.farticleDAO.findProjectFarticleCount(projectId,farticletype) ;
	}
	public int findFarticle(int farticletype){
		return this.farticleDAO.findFarticleCount(farticletype) ;
	}
	
	public List<Ffriendlink> findFriendLink(int type,int firstResult,int maxResult){
		return this.ffriendlinkDAO.findFriendLink(type, firstResult, maxResult) ;
	}
	
	public Farticle findFarticleById(int id){
		Farticle farticle = this.farticleDAO.findById(id) ;
		return farticle ;
	}
	
	public Farticletype findFarticleTypeById(int id){
		return this.farticletypeDAO.findById(id) ;
	}
	
	public List<Farticletype> findFarticleTypeAll(){
		return this.farticletypeDAO.findAll() ;
	}
	
	public List<Fperiod> findAllFperiod(long fromTime,int fvirtualCoinType){
		return this.fperiodDAO.findAllFperiod(fromTime, fvirtualCoinType) ;
	}
	
	public void addFperiod(Fperiod fperiod){
		this.fperiodDAO.save(fperiod) ;
	}
	public void addFperiods(List<Fperiod> fperiods){
		for (Fperiod fperiod : fperiods) {
			this.fperiodDAO.save(fperiod) ;
		}
	}
	
	public Fperiod getLastFperiod(Fvirtualcointype fvirtualcointype){
		return this.fperiodDAO.getLastFperiod(fvirtualcointype) ;
	}
	
	public Fentrustlog getLastFpeFentrustlog(int fvirtualcointype){
		return this.fentrustlogDAO.getLastFpeFentrustlog(fvirtualcointype) ;
	}
	
	
	public List<Fbankin> findFbankin(){
		return this.fbankinDAO.findByProperty("ok", 0) ;
	}

	@Override
	public List<Farticle> findProjectFarticle(int projectId, int farticletype, int firstResult,
			int maxResult) {
		List<Farticle> farticles = this.farticleDAO.findProjectFarticle(projectId, farticletype, firstResult, maxResult);
		return farticles ;
	}

	
	public List<FfeesConvert> queryFfeesConvertList(int firstResult, int maxResults, String filter, boolean isFY) {
		List<FfeesConvert> ffeesConvertList = ffeesConvertDAO.list(firstResult, maxResults, filter, isFY);
		for (FfeesConvert ffeesConvert : ffeesConvertList) {
			if(null != ffeesConvert.getProjectId()) {
				ffeesConvert.getProjectId().getFrealName();
			}
		}
		return ffeesConvertList;
	}

	public FfeesConvert findFeesConvertById(Integer id) {
		return ffeesConvertDAO.findById(id);
	}
	
	public void saveFfeesConvert(FfeesConvert instance) {
		ffeesConvertDAO.save(instance);
	}

	
	public void updateFfeesConvert(FfeesConvert instance) {
		ffeesConvertDAO.attachDirty(instance);
	}

	
	public void updateFfeesConvertAndWallet(Fvirtualwallet usdtWallet, Fvirtualwallet bfscWallet,
			FfeesConvert feesConvert) {
		usdtWallet.setFtotal(usdtWallet.getFtotal() - feesConvert.getUsdtAmount());
		usdtWallet.setFlastUpdateTime(Utils.getTimestamp());
		fvirtualwalletDAO.attachDirty(usdtWallet);
		
		bfscWallet.setFtotal(bfscWallet.getFtotal() + feesConvert.getBfscAmount());
		bfscWallet.setFlastUpdateTime(Utils.getTimestamp());
		fvirtualwalletDAO.attachDirty(bfscWallet);
		
		feesConvert.setStatus(2);   //处理状态  1：未处理    2：已处理
		feesConvert.setUpdateTime(Utils.getTimestamp());
		ffeesConvertDAO.attachDirty(feesConvert);
	}

	@Override
	public void deleteFfeesConvert(FfeesConvert instance) {
		ffeesConvertDAO.delete(instance);
	}

	
	public void saveFbfscDataStatistics(FbfscDataStatistics bfscData, FfeesConvert ffeesConvert) {
		if(null != bfscData) {
			fbfscDataStatisticsDAO.save(bfscData);
		}
		
		if(null != ffeesConvert) {
			ffeesConvertDAO.save(ffeesConvert);
		}
	}
	

	public FbfscDataStatistics findFbfscDataStatisticsByParam(String filter) {
		return fbfscDataStatisticsDAO.findFbfscDataStatisticsByParam(filter);
	}

	
	public List<FbfscDataStatistics> queryFbfscDataStatisticsList(int firstResult, int maxResults, String filter,
			boolean isFY) {
		List<FbfscDataStatistics> bfscDataStatisticsList = fbfscDataStatisticsDAO.list(firstResult, maxResults, filter, isFY);
		for (FbfscDataStatistics bfscDataStatistics : bfscDataStatisticsList) {
			if(null != bfscDataStatistics.getFuser()) {
				bfscDataStatistics.getFuser().getFrealName();
			}
		}
		return bfscDataStatisticsList;
	}

	
	public FbfscDataStatistics findFbfscDataStatisticsById(Integer id) {
		return fbfscDataStatisticsDAO.findById(id);
	}
	
}
 