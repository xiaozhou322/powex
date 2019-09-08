package com.gt.util;

import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;


public class WalletAPI {

	private static String host = Constant.WalletSystemHost;
	
	private static String path = Constant.WalletSystemPath;
	
	private static String api_key = Constant.WalletSystemApiKey;
	
	private static String secret_key = Constant.WalletSystemSecretKey;
	
	private static String publicKeyStr = Constant.WalletSystemPublicKeyStr;
	
	private static JSONObject walletType = null;
	
	public static JSONObject main(String method,Map<String,Object> params) {
		try {
			
			//String url="http://192.168.0.196:8080/wallet/interface/main?action="+method;
			Map<String,String> bodys = new HashMap<String,String>();
			String timestamp = new Date().getTime()+"";
			params.put("timestamp", timestamp );
			params.put("api_key", api_key);
			
			TreeSet<String> paramTreeSet = new TreeSet<String>() ;
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey() ;
				Object values = entry.getValue() ;
				bodys.put(key, values.toString());
				paramTreeSet.add(key+"="+values) ;
			}
			StringBuffer paramString = new StringBuffer() ;
			for (String p : paramTreeSet) {
				if(paramString.length()>0){
					paramString.append("&") ;
				}
				paramString.append(p) ;
			}
			paramString.append("&secret_key="+secret_key) ;
			System.out.println(""+paramString.toString());
			String sign = Utils.getMD5_32_xx(paramString.toString());
			bodys.put("action", method);
			bodys.put("sign", sign);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Accept", "application/json");
			headers.put("Content-Type", "application/json");
			HttpResponse response = HttpUtils.doPost(host, path, "", headers, bodys, "");
			//String resource = HttpClient.httpPost(url, bodys);
			String result = EntityUtils.toString(response.getEntity());
			System.out.println(result);
			return JSONObject.fromObject(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject getWalletType(){
		if(null == walletType){
			JSONObject obj = WalletAPI.main("getWalletType", new HashMap<String, Object>());
			if(null != obj && 200 == obj.getInt("code")){
				walletType = obj.getJSONObject("data");
			}
		}
		return walletType;
	}
	
	public static String encryptPasswors(String password){
		try {
			PublicKey publicKey = RSAUtils.loadPublicKey(publicKeyStr);
			return RSAUtils.encrypt(publicKey , password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		
		/*JSONObject obj = WalletAPI.main("getWalletType", new HashMap<String, Object>());
		JSONObject walletType = obj.getJSONObject("data");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("walletTypeId", walletType.get("QTUM"));
		params.put("walletPassword", "123456789");
		JSONObject result = WalletAPI.main("validatePasswors", params );
		System.out.println(result);*/
		
		List<JSONObject> arr = null;
		JSONObject obj = WalletAPI.main("getWalletType", new HashMap<String, Object>());
		JSONObject walletType = obj.getJSONObject("data");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("walletTypeId", walletType.get("EOSforce"));
		JSONObject result = WalletAPI.main("listTransactions", params );
		if(null != result && "200".equals(result.get("code")+"")){
			arr = result.getJSONArray("data");
		}
		System.out.println(arr);
		
	}
	
}
