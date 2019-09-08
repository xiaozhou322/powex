package com.gt.sdk;

import javax.servlet.http.HttpServletRequest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AnalyzeNvcRequest;
import com.aliyuncs.afs.model.v20180112.AnalyzeNvcResponse;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.gt.util.Constant;
import com.gt.util.NetworkUtil;

/**
 * 阿里云API接口
 * @author zhouyong
 *
 */
public class AliyunCheck {

	private final static String regionid = "cn-hangzhou";
	private final static String accessKeyId = "LTAIEOVSvJWVCE9M";
	private final static String accessKeySecret = "PF1Qb57E050uRnJMvNh0KeDjDJzzGG";
//	private final static String appkey = "FFFF0N00000000006DE5";
	
	
	private static volatile IAcsClient client = null;
	 
	public static IAcsClient getAcsClient() {
		if (client == null) {
			synchronized (IAcsClient.class) {
				if (client == null) {
					IClientProfile profile = DefaultProfile.getProfile(Constant.aliyun_regionid, Constant.aliyun_accessKeyId, Constant.aliyun_accessKeySecret);
					client = new DefaultAcsClient(profile);
					try {
						DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "afs", "afs.aliyuncs.com");
					} catch (ClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return client;
	}
	
	
	
	/**
	 * 阿里云人机验证-智能模式
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean checkAliyunICMode(HttpServletRequest request) throws Exception {

		boolean result = false;
		String sessionId = request.getParameter("sessionid");
		String sig = request.getParameter("sig");
		String token = request.getParameter("token");
		String scene = request.getParameter("scene");
		String appkey = request.getParameter("appkey");

		AuthenticateSigRequest req = new AuthenticateSigRequest();
		req.setSessionId(sessionId);// 必填参数，从前端获取，不可更改
		req.setSig(sig);// 必填参数，从前端获取，不可更改
		req.setToken(token);// 必填参数，从前端获取，不可更改
		req.setScene(scene);// 必填参数，从前端获取，不可更改
		req.setAppKey(appkey);// 必填参数，后端填写
		req.setRemoteIp(NetworkUtil.getIpAddress(request));// 必填参数，后端填写
		try {
			// response的code枚举：100验签通过，900验签失败
			AuthenticateSigResponse response = AliyunCheck.getAcsClient().getAcsResponse(req);
			if (100 == response.getCode()) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 阿里云人机验证-无痕模式
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String checkAliyunNVCMode(HttpServletRequest request) throws Exception {

		String retResult = null;
		String nvcData = request.getParameter("nvcData");

		AnalyzeNvcRequest req = new AnalyzeNvcRequest();
		req.setData(nvcData);//必填参数，前端获取getNVCVal函数的值
		req.setScoreJsonStr("{\"200\":\"PASS\",\"400\":\"NC\",\"600\":\"SC\",\"800\":\"BLOCK\"}");// 根据需求填写
		 try {
             AnalyzeNvcResponse response = client.getAcsResponse(req);
             retResult = response.getBizCode();
             if(response.getBizCode().equals("100")) {
                 System.out.println("验签通过");
             } else if (response.getBizCode().equals("200")) {
                 System.out.println("直接通过");
             } else if (response.getBizCode().equals("400")) {
                 System.out.println("前端弹出nc");
             } else if (response.getBizCode().equals("600")) {
                 System.out.println("前端弹出sc");
             } else if (response.getBizCode().equals("800")) {
                 System.out.println("直接拦截");
             } else if (response.getBizCode().equals("900")) {
                 System.out.println("验签失败");
             } 
	     } catch (Exception e) {
	         e.printStackTrace();            
	     }
//		 retResult = "600";
		return retResult;
	}
}
