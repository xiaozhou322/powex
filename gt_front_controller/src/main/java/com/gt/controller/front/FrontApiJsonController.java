package com.gt.controller.front;

import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gt.comm.MultipleValues;
import com.gt.controller.BaseController;
import com.gt.entity.Fapi;
import com.gt.entity.Fuser;
import com.gt.entity.Fvirtualcointype;
import com.gt.entity.Fvirtualwallet;
import com.gt.service.admin.TradeMappingService;
import com.gt.service.admin.VirtualCoinService;
import com.gt.service.front.ApiService;
import com.gt.service.front.FrontCacheService;
import com.gt.service.front.FrontUserService;
import com.gt.util.Utils;

@Controller
public class FrontApiJsonController extends BaseController {
	
	private static final String ERROR_CODE = "code" ;
	
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private ApiService apiService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private FrontCacheService frontCacheService ;
	@Autowired
	private TradeMappingService tradeMappingService;
	
	private MultipleValues login(HttpServletRequest request){
		
		boolean flag = false ;
		String api_key = request.getParameter("api_key") ;
		String sign = request.getParameter("sign") ;
		Fapi fapi = this.apiService.findFapi(api_key) ;
		
		try{
			StringBuffer encode = new StringBuffer(0) ;
			
			TreeSet<String> names = new TreeSet<String>(new Comparator<String>() {

				public int compare(String o1, String o2) {
					return o1.compareTo(o2) ;
				}
			}) ;
			Enumeration<String> enumeration = request.getParameterNames() ;
			while(enumeration.hasMoreElements()==true){
				String name = enumeration.nextElement() ;
				if(!name.equals("sign")){
					names.add(name) ;
				}
			}
			
			boolean first = true ;
			for (String string : names) {
				if(first==false){
					encode.append("&") ;
				}else{
					first = false ;
				}
				encode.append(string+"="+request.getParameter(string)) ;
			}
			
			if(fapi!=null){
				encode.append("&secret_key="+fapi.getFsecret()) ;	
				System.out.println(Utils.getMD5_32_xx(encode.toString()).toUpperCase());
				if(sign.equals(Utils.getMD5_32_xx(encode.toString()).toUpperCase())==true){
					flag = true ;
				}
			}
			
		}catch(Exception e){
//			//e.printStackTrace() ;
		}
		
		MultipleValues ret = new MultipleValues() ;
		if(flag==true){
			Fuser fuser = this.frontUserService.findById(fapi.getFuser().getFid()) ;
			ret.setValue1(true) ;
			ret.setValue2(fuser) ;
		}else{
			ret.setValue1(false) ;
		}
		
		return ret ;
	}


	//用户信息
	@ResponseBody
	@RequestMapping(value="/api/v1/userinfo",produces=JsonEncode)
	public String userInfo(HttpServletRequest request) throws Exception {
		MultipleValues ret = this.login(request) ;
		boolean isLogin = (Boolean)ret.getValue1() ;
		if(isLogin==false){
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(ERROR_CODE, 10001) ;//签名不正确
			return jsonObject.toString() ;
		}
		
		Fuser fuser = (Fuser)ret.getValue2() ;
		try {
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(ERROR_CODE, 10000) ;//成功
			
			JSONObject fuserObj = new JSONObject() ;
			fuserObj.accumulate("floginName", fuser.getFloginName()) ;
			jsonObject.accumulate("fuser", fuserObj) ;
			
			JSONArray jsonArray = new JSONArray() ;
			
			String filter = "where fstatus=1";
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
				
				JSONObject fvirwalletObj = new JSONObject() ;
				fvirwalletObj.accumulate("coinid", fvirtualcointype.getFid()) ;
				fvirwalletObj.accumulate("fshortName", fvirtualcointype.getfShortName()) ;
				fvirwalletObj.accumulate("fsymbol", fvirtualcointype.getfSymbol()) ;
				fvirwalletObj.accumulate("ftotal", fvirtualwallet.getFtotal()) ;
				fvirwalletObj.accumulate("ffrozen", fvirtualwallet.getFfrozen()) ;
				jsonArray.add(fvirwalletObj) ;
			}

			jsonObject.accumulate("fvirtualwallet", jsonArray) ;
			return jsonObject.toString() ;
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(ERROR_CODE, 10002) ;//系统错误
			return jsonObject.toString() ;
		}
		
	}
	
	
}
