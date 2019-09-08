package com.gt.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class AliyunSignatureUtils {
	private final static String CHARSET_UTF8 = "utf8";
	private final static String ALGORITHM = "UTF-8";
	private final static String SEPARATOR = "&";
	private static final String EQUAL = "=";
	
	private static String buildCanonicalizedQueryString(Map<String, String> parameterMap) throws UnsupportedEncodingException {
        // 对参数进行排序
        List<String> sortedKeys = new ArrayList<String>(parameterMap.keySet());
        Collections.sort(sortedKeys);

        StringBuilder temp = new StringBuilder();
        for (String key : sortedKeys) {
            // 此处需要对key和value进行编码
            String value = parameterMap.get(key);
            temp.append(SEPARATOR).append(percentEncode(key)).append(EQUAL).append(percentEncode(value));
        }
        return temp.toString().substring(1);
    }

	public static Map<String, String> splitQueryString(String url)
			throws URISyntaxException, UnsupportedEncodingException {
		URI uri = new URI(url);
		String query = uri.getQuery();
		final String[] pairs = query.split("&");
		TreeMap<String, String> queryMap = new TreeMap<String, String>();
		for (String pair : pairs) {
			final int idx = pair.indexOf("=");
			final String key = idx > 0 ? pair.substring(0, idx) : pair;
			if (!queryMap.containsKey(key)) {
				queryMap.put(key, URLDecoder.decode(pair.substring(idx + 1), CHARSET_UTF8));
			}
		}
		return queryMap;
	}

	public static String generate(String method, Map<String, String> parameter, String accessKeySecret)
			throws Exception {
		String signString = generateSignString(method, parameter);
		System.out.println("signString---" + signString);
		byte[] signBytes = hmacSHA1Signature(accessKeySecret + "&", signString);
		String signature = newStringByBase64(signBytes);
		System.out.println("signature---" + signature);
		if ("POST".equals(method))
			return signature;
		return URLEncoder.encode(signature, "UTF-8");
	}
	
	public static String buildRequestURL(String hostUrl, String signature, Map<String, String> parameterMap) throws UnsupportedEncodingException {
        // 生成请求URL
        StringBuilder temp = new StringBuilder(hostUrl);
        temp.append(URLEncoder.encode("Signature", "UTF-8")).append("=").append(signature);
        for (Map.Entry<String, String> e : parameterMap.entrySet()) {
            temp.append("&").append(percentEncode(e.getKey())).append("=").append(percentEncode(e.getValue()));
        }
        return temp.toString();
    }

	public static String generateSignString(String httpMethod, Map<String, String> parameter) throws IOException {
		TreeMap<String, String> sortParameter = new TreeMap<String, String>();
		sortParameter.putAll(parameter);
		String canonicalizedQueryString = UrlUtil.generateQueryString(sortParameter, true);
		if (null == httpMethod) {
			throw new RuntimeException("httpMethod can not be empty");
		}
		StringBuilder stringToSign = new StringBuilder();
		stringToSign.append(httpMethod).append(SEPARATOR);
		stringToSign.append(percentEncode("/")).append(SEPARATOR);
		stringToSign.append(percentEncode(canonicalizedQueryString));
		return stringToSign.toString();
	}

	public static String percentEncode(String value) {
		try {
			return value == null ? null
					: URLEncoder.encode(value, CHARSET_UTF8).replace("+", "%20").replace("*", "%2A").replace("%7E",
							"~");
		} catch (Exception e) {
		}
		return "";
	}

	public static byte[] hmacSHA1Signature(String secret, String baseString) throws Exception {
		if (StringUtils.isEmpty(secret)) {
			throw new IOException("secret can not be empty");
		}
		if (StringUtils.isEmpty(baseString)) {
			return null;
		}
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), ALGORITHM);
		mac.init(keySpec);
		return mac.doFinal(baseString.getBytes(CHARSET_UTF8));
	}

	public static String newStringByBase64(byte[] bytes) throws UnsupportedEncodingException {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		return new String(Base64.encodeBase64(bytes, false), CHARSET_UTF8);
	}
	
	/**
	 * get SignatureNonce
	 * @return
	 */
	public static String getUniqueNonce() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	/**
	 * get Timestamp
	 * @return
	 */
	public static String getISO8601Time() {
		Date nowDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(new SimpleTimeZone(0, "GMT"));
		return df.format(nowDate);
	}
	
	//测试代码    
	private static String getQueryUrl(Map<String, String> param) throws Exception {
	        Map<String, String> publicParam = new HashMap<String, String>();
	        publicParam.put("AccessKeyId", "testid");
	        publicParam.put("Format", "json");
	        publicParam.put("SignatureMethod", "Hmac-SHA1");
	        publicParam.put("SignatureNonce", UUID.randomUUID().toString());
	        publicParam.put("SignatureVersion", "1.0");
	        publicParam.put("Timestamp", AliyunSignatureUtils.getISO8601Time());
	        publicParam.put("Version", "2014-05-15");
	        publicParam.put("RegionId", "cn-shanghai");

	        if (param != null) {
	            for (Map.Entry<String, String> entry : param.entrySet()) {
	                publicParam.put(entry.getKey(), entry.getValue());
	            }
	        }

//	        String s = AliyunSignatureUtils.generateSignString("GET", publicParam);
//	        byte[] signBytes;
//	        signBytes = AliyunSignatureUtils.hmacSHA1Signature("testsecret" + "&", s);
//	        String signature = AliyunSignatureUtils.newStringByBase64(signBytes);
	        String signature = AliyunSignatureUtils.generate("GET", publicParam, "testsecret");
	        publicParam.put("signature", signature);

	        String url = "http://slb.aliyuncs.com/?";
	        //对参数进行url编码
	        for (Map.Entry<String, String> entry : publicParam.entrySet()) {
	            publicParam.put(entry.getKey(), URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
	        }
	        //拼接请求url
	        for (Map.Entry<String, String> entry : publicParam.entrySet()) {
	            url = url + entry + "&";
	        }
	        //去掉最后一个&
	        url = url.substring(0, url.length() - 1);
	        return url;
	    }

	    /*public static void main(String[] args) {
	        try {
	        	//结果： http://slb.aliyuncs.com/?Format=json&Action=DescribeLoadBalancerAttribute&AccessKeyId=testid&SignatureMethod=Hmac-SHA1&RegionId=cn-shanghai&SignatureNonce=25b24a03-f4e0-47f8-9664-393ff1bf7bad&Version=2014-05-15&SignatureVersion=1.0&signature=%2FkF3TNCiIr%2Btd%2BU037X1Ew68Ars%3D&Timestamp=2018-10-10T01%3A07%3A15Z
	        	
	            Map<String, String> map = new HashMap<String, String>();
	            map.put("Action", "DescribeLoadBalancerAttribute");
//	            map.put("LoadBalancerId", "lb-********");
	            String url = getQueryUrl(map);
	            System.err.println("\n" + url);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        }
	    }*/
	

	public static void main(String[] args) {
		String str = "GET&%2F&AccessKeyId%3DtestId%26Action%3DSearchTemplate%26Format%3DXML%26PageSize%3D2%26SignatureMethod%3DHMAC-SHA1%26SignatureNonce%3D4902260a-516a-4b6a-a455-45b653cf6150%26SignatureVersion%3D1.0%26Timestamp%3D2015-05-14T09%253A03%253A45Z%26Version%3D2014-06-18";
		byte[] signBytes;
		try {
			signBytes = AliyunSignatureUtils.hmacSHA1Signature("testKeySecret" + "&", str.toString());
			String signature = AliyunSignatureUtils.newStringByBase64(signBytes);
			System.out.println(signature);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
