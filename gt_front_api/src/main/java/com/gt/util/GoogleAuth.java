package com.gt.util;

import java.util.HashMap;
import java.util.Map;

/*
 * Not really a unit test- but it shows usage
 */
public class GoogleAuth  {
    //网站名称，可自定义
	private static String webName = Constant.GoogleAuthName;
	
	/**
	 * 注册GOOGLE认证，先传入用户登陆名，返回一个MAP，里面有两个KEY，一个是url(value为二维码地址),一个是secret(为此用户的地址，此地址用于校验)
	 * 
	 * */
	public static Map genSecret(String userName) {
		Map map = new HashMap();
		String secret = GoogleAuthenticator.generateSecretKey();
		String url = GoogleAuthenticator.getQRBarcodeURL(webName, userName, secret);
		map.put("secret", secret);
		map.put("url", url);
		return map;
	}

	/**
	 * 校验GOOGLE认证是否成功
	 * **/
	public static boolean auth(long code,String secret)  {
		long t = System.currentTimeMillis();
		GoogleAuthenticator ga = new GoogleAuthenticator();
		ga.setWindowSize(5);  //should give 5 * 30 seconds of grace...
		boolean r = ga.check_code(secret, code, t);
        return r;	
	}


	public static void main(String args[]){
		Map<String, String> map = GoogleAuth.genSecret("hanklee") ;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getValue());
		}
	}
	
}