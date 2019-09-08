package com.gt.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class IDCardVerifyUtil {
	
	public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY =Constant.APPKEY;
    
    
	public boolean isRealPerson(String realName,String number){
		boolean flag = false;
		String host = "https://op.juhe.cn";
  	    String path = "/idcard/query";
  	    String method = "GET";
  	    String appcode = Constant.APPKEY;
  	    Map<String, String> headers = new HashMap<String, String>();
  	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
  	    headers.put("Authorization", "APPCODE " + appcode);
  	    Map<String, String> querys = new HashMap<String, String>();
  	    querys.put("idcard", number);
  	    querys.put("realname", realName);
  	    querys.put("key", appcode);
  	    try {
  	    	/**
  	    	* 重要提示如下:
  	    	* HttpUtils请从
  	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
  	    	* 下载
  	    	*
  	    	* 相应的依赖请参照
  	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
  	    	*/
  	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
  	    	System.out.println(response.toString());
  	    	//获取response的body
  	    	String result = EntityUtils.toString(response.getEntity());
  	    	//System.out.println(result);
  	    	if (JSONObject.fromObject(result).getJSONObject("result")!=null){
	  	    	int res = JSONObject.fromObject(result).getJSONObject("result").getInt("res");
	  	    	int error_code = JSONObject.fromObject(result).getInt("error_code");
	  	    	if(error_code ==0&&res==1){
	  	    		flag = true;
	  	    	}
  	    	}
  	    } catch (Exception e) {
  	    	e.printStackTrace();
  	    }
		return flag;   

	}
 
    public static void main(String[] args) {
    	System.out.println(new IDCardVerifyUtil().isRealPerson("李汉深", "441522198702082157"));
    }
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
