package com.gt.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * md5签名和验签
 * @author zhouyong
 *
 */
public class MD5SignHelper {
	
	/**
     * 验证商户签名
     * @param paramMap  签名参数
     * @param paySecret 签名私钥
     * @param signStr   原始签名密文
     * @return
     */
    public static boolean isRightSign(String strParam , String paySecret ,String signStr){

        if (StringUtils.isBlank(signStr)){
            return false;
        }

        String sign = getSign(strParam, paySecret);
        if(signStr.equals(sign)){
            return true;
        }else{
            return false;
        }
    }
    
    
    /**
     * 获取参数签名
     * @param paramMap  签名参数
     * @param paySecret 签名密钥
     * @return
     */
    public static String  getSign (String strParam , String paySecret){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(strParam);
        String argPreSign = stringBuffer.append("&secret_key=").append(paySecret).toString();
        String signStr =getMD5_32_xx(argPreSign).toUpperCase();

        return signStr;
    }
    
    
    public static String getMD5_32_xx(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
    
    /**
     * 获取参数拼接串
     * @param paramMap
     * @return
     */
    public static String  getParamStr(Map<String , String> paramMap){
        SortedMap<String, Object> smap = new TreeMap<String, Object>(paramMap);
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
//            if (value != null && StringUtils.isNotBlank(String.valueOf(value))){
                stringBuffer.append(m.getKey()).append("=").append(value).append("&");
//            }
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());

        return stringBuffer.toString();
    }
}
