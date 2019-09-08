package com.gt.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gt.entity.Ftrademapping;


public class Utils {
	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static String toS(double val){
	        BigDecimal bigDecimal = new BigDecimal(val);
			String result = bigDecimal.toString();
			return result ;
	}
	public static String curl(String u){
		if(u!=null&&u.startsWith("http")==false){
			if(u.startsWith("/")){
				u = u.substring(1) ;
			}
			u = Constant.Domain+u ;
		}
		return u ;
	}
	public static String wget(String u) throws Exception {
		URL url = new URL(u);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream(),"UTF-8"));
		StringBuffer content = new StringBuffer();
		String tmp = null;

		while ((tmp = br.readLine()) != null) {
			content.append(tmp);
		}
		br.close();
		return content.toString();
	}

	// 获得随机字符串
	public static String randomString(int count) {
		String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
		int size = str.length();
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		while (count > 0) {
			sb.append(String.valueOf(str.charAt(random.nextInt(size))));
			count--;
		}
		return sb.toString();
	}

	public static String randomInteger(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(new Random().nextInt(10));
		}
		return sb.toString();
	}

	public static String getRandomImageName() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmsss");
		String path = simpleDateFormat.format(new Date());
		path += "_" + randomString(5);
		return path;
	}

	public static boolean saveFile(String dir, String fileName,
			InputStream inputStream,String uploadDir) {
		boolean flag = false;
        File directory = new File(dir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (!directory.isDirectory()) {
            return flag;
        }

        if (inputStream == null) {
            return false;
        }

        File realFile = new File(directory, fileName);

        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(inputStream);
            ImageIO.write(bufferedImage, fileName.split("\\.")[1], realFile);
            flag = true;
        } catch (IOException e) {
            flag = false;
        } finally {
            bufferedImage.flush();
        }
        
		if(!Constant.IS_OPEN_OSS.equals("false")){
			OSSPostObject oss = new OSSPostObject() ;
			flag = oss.PostObject( uploadDir+"/"+fileName, dir+"/"+fileName) ;
			try {
				File xx = new File(directory,fileName);
				xx.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	//
	// public static String MD5(String content) throws Exception {
	// MessageDigest md5 = MessageDigest.getInstance("MD5");
	// sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
	// String retString = baseEncoder.encode(md5.digest(content.getBytes()));
	// return retString;
	// }

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

	public static String MD5(String content, String salt) throws Exception {
		return PasswordHelper.encryString(content, salt);
	}


	public static String getCookie(Cookie[] cookies, String key)
			throws Exception {
		String value = null;
		if (cookies != null && key != null) {
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					value = cookie.getValue();
				}
			}
		}

		return value;
	}

	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	public static int getNumPerPage() {
		return 40;
	}

	public static synchronized String UUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	// return seconds
	public static long timeMinus(Timestamp t1, Timestamp t2) {
		return (t1.getTime() - t2.getTime()) / 1000;
	}

	// 获得今天0点
	public static long getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static String getCurTimeString() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String number2String(double f) {
		DecimalFormat df = new DecimalFormat();
		String style = "0.00000";// 定义要显示的数字的格式
		df.applyPattern(style);
		return df.format(f);
	}

	public static void main(String args[]) throws Exception {
		System.out.print(getMD5_32_xx("amount=0.1&api_key=d7d47e0d-e879-454c-8ed4-68dad0b95081&price=5000&symbol=1&type=1&secret_key=5G7OOWPZNNZ9Y955BOCGKGNCYKSGDGXTYL8B").toUpperCase());
	}

	public static double getDoubleUp(double value, int scale) {
		return ( (long)(value*Math.pow(10, scale)) ) /Math.pow(10.0, scale) ;
	}
	public static double getDouble(double value, int scale) {
		return new BigDecimal(String.valueOf(value)).setScale(scale,BigDecimal.ROUND_DOWN).doubleValue();
	}

	
	public static String getDoubleS(double value, int scale) {
		value +=Math.pow(10, -8) ;
		return new BigDecimal(String.valueOf(value)).setScale(scale,BigDecimal.ROUND_DOWN).toString();
	}

	public static String dateFormat(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}

	public static boolean isNumeric(String str) {
		if (str == null || str.trim().length() == 0)
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "01234567890123456789012345678901234567890123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	public static Hashtable getAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	public static String getAfterDay(int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -day);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到本周周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek(int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, days);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return sdf.format(c.getTime());
	}

	/**
	 * 得到本周周日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek(int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, days);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return sdf.format(c.getTime());
	}
//
//	public static boolean openTrade(Ftrademapping type,Timestamp now) {
//		int nows = Integer.parseInt(new SimpleDateFormat("HH").format(now));
//
//		boolean flag = true;
//		String value = type.getFtradeTime();
//		int min = Integer.parseInt(value.trim().split("-")[0]);
//		int max = Integer.parseInt(value.trim().split("-")[1]);
//		// 24-0代表24小时，0-24代表不开放交易
//		if (min == 0 && max == 24) {
//			return false;
//		}
//		if (min == 24 && max == 0) {
//			return true;
//		}
//
//		if (min <= max) {
//			if (nows >= min && nows <= max) {
//				flag = false;
//			}
//		}
//
//		if (max < min) {
//			if (!(nows > max && nows < min)) {
//				flag = false;
//			}
//		}
//
//		return flag;
//	}
	
	public static boolean openTrade(Ftrademapping type,Timestamp now1){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		boolean flag = false ;
		String value = type.getFtradeTime().trim();
		
		if(value.equals("0-24")){
			return false;
		}else if(value.equals("24-0")){
			return true;
		}
		try {
			Date date = new Date();
			long now = now1.getTime();
			long s = sdf.parse(sdf1.format(date)+" "+(value.trim().split("-")[0])+":00").getTime();
			long e = sdf.parse(sdf1.format(date)+" "+(value.trim().split("-")[1])+":00").getTime();
			if(s >= now && e <= now){
				flag = true ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false ;
		}
		
		return flag ;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static String getPrototypeUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
