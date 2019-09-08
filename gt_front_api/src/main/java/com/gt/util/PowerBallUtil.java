package com.gt.util;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PowerBallUtil {

	public static String getBall(){
		String ball = "";
		try {
			HttpResponse response = HttpUtils.doGet(Constant.PowerBallUrl, "");
			String result = EntityUtils.toString(response.getEntity());
			JSONArray arr = JSONArray.fromObject(result);
			JSONObject obj = arr.getJSONObject(0);
			ball = obj.get("field_winning_numbers").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ball;
	}
	
	public static int[] toArray(String str){
		int[] ball = null;
		try {
			String[] strs = str.split(",");
			ball = new int[strs.length];
			for(int i=0;i<strs.length;i++){
				ball[i] = Integer.valueOf(strs[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ball;
	}
	
}
