package com.gt.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;

public class BTCUtils implements IWalletUtil {
	//用户名
	private  String ACCESS_KEY = null;
	//密码
	private  String SECRET_KEY = null;
	//钱包IP地址
	private  String IP = null;
	//端口
	private  String PORT = null;
	//比特币钱包密码
	private  String PASSWORD = null;
	
	private  String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	public BTCUtils(BTCMessage btcMessage) {
		this.ACCESS_KEY = btcMessage.getACCESS_KEY();
		this.SECRET_KEY = btcMessage.getSECRET_KEY();
		this.IP = btcMessage.getIP();
		this.PORT = btcMessage.getPORT();
		this.PASSWORD = btcMessage.getPASSWORD();
	}
	
	public String getSignature(String data, String key) throws Exception {
		// get an hmac_sha1 key from the raw key bytes
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
				HMAC_SHA1_ALGORITHM);

		// get an hmac_sha1 Mac instance and initialize with the signing key
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);

		// compute the hmac on input data bytes
		byte[] rawHmac = mac.doFinal(data.getBytes());
		return bytArrayToHex(rawHmac);
	}

	private String bytArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (byte b : a)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}
	
	//The easiest way to tell Java to use HTTP Basic authentication is to set a default Authenticator: 
	private void authenticator() {
		Authenticator.setDefault(new Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication (ACCESS_KEY, SECRET_KEY.toCharArray());
		      }
		});
	}
	
	/***
	 * 取得钱包相关信息
	 * {"result":{"version":80300,"protocolversion":70001,"walletversion":60000,
	 * "balance":0.00009500,"blocks":284795,"timeoffset":-1,"connections":6,
	 * "proxy":"","difficulty":2621404453.06461525,"testnet":false,
	 * "keypoololdest":1388131357,"keypoolsize":102,"paytxfee":0.00000000,
	 * "errors":""},"error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject getInfo() throws Exception {
		String s = main("getinfo", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/***
	 * 取得最新区块hash
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
	/*public JSONObject getbesthash() throws Exception {
		String s = main("getbestblockhash", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String getbesthashvalue() throws Exception {
		String result = "";
		JSONObject s = getbesthash();
        if(s.containsKey("result")){
        	result =s.get("result").toString();
        }
		return result;
	}
	*/
	/***
	 * 取得区块高度
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
/*	public JSONObject getblockheight(String hash) throws Exception {
		String s = main("getblock", "[\""+hash+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public double getblockheightvalue(String hash) throws Exception {
		double result = 0d;
		JSONObject s = getblockheight(hash);
		System.out.println(s.toString());
        if(s.containsKey("result")){
        	result =Double.valueOf(s.get("result").toString());
        }
		return result;
	}*/
	
	/***
	 * 取得钱包余额
	 * {"result":9.5E-5,"error":null,"id":1}
	 * {"result":0,"error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject getbalance() throws Exception {
		String s = main("getbalance", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public JSONObject isValidateaddress(String address) throws Exception {
		String s = main("validateaddress", "[\""+address+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public double getbalanceValue() throws Exception {
		double result = 0d;
		JSONObject s = getbalance();
		System.out.println(s.toString());
        if(s.containsKey("result")){
        	result =Double.valueOf(s.get("result").toString());
        }
		return result;
	}
	
	//判断地址是否有效
	public boolean validateaddress(String address) {
		boolean result = false;
		try {
			JSONObject s = isValidateaddress(address);
			if(s.containsKey("result")){
				String xx =JSONObject.fromObject(s.get("result")).getString("isvalid");
				if(xx.equals("true")){
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/***
	 * 根据用户ID，生成交易地址
	 * @throws Exception 
	 * {"result":"1MySAsi6bzPLY3HbCcrDd7qgiG7CYnQn3k","error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject getNewaddress(int userId) throws Exception {
		String s = main("getnewaddress", "[\""+userId+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String getNewaddressValue(int userId) throws Exception {
		String result = null;
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletpassphrase(30);
		}
		JSONObject s = getNewaddress(userId);
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletlock();
		}
        if(s.containsKey("result")){
        	result = s.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}
	
	public JSONObject getNewaddressForAdmin(String userId) throws Exception {
		String s = main("getnewaddress", "[\""+userId+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public JSONObject getNewaddressForAdmin() throws Exception {
		String s = main("getnewaddress", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String getNewaddressValueForAdmin(String userId) throws Exception {
		String result = null;
		JSONObject s = null;
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletpassphrase(30);
			s = getNewaddressForAdmin(userId);
		}else{
			s = getNewaddressForAdmin();
		}
		
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletlock();
		}
        if(s.containsKey("result")){
        	result = s.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
        System.out.println(s.toString());
		return result;
	}
	
	/**
	 * 根据用户ID，获取交易记录
	 *  {"result":[{"account":"test","address":"i6W5Ng4X49gJDAVnPafFm3rQwEAfU28SUo",
	 *  "category":"receive","amount":10,"confirmations":166,
	 *  "blockhash":"9b227a05cce30b33d991199af734cdd72171c6db608ec36aa71f48952ad1a639",
	 *  "blockindex":1,"txid":"4ed4de64672cb86cc3458a12a8a1d1c4a79478627a536ec62033367ba180ffc9",
	 *  "time":1391943409,"comment":"","from":"","message":"","to":""}],"error":null,"id":1}
		没有交易记录的结果：{"result":[],"error":null,"id":1}
		若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject listtransactions(int userId) throws Exception {
		String s = main("listtransactions", "[\""+userId+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/**
	 * 查所有
	 * **/
	public JSONObject listtransactions(int count,int from) throws Exception {
		String s = main("listtransactions", "[\"*\","+count+","+from+"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/**
	 * 取得所有的收到的交易记录
	 * **/
	public List<BTCInfo> listtransactionsValue(int count,int from) throws Exception {
		JSONObject json = listtransactions(count, from);
		List<BTCInfo> all = new ArrayList();
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	List allResult = (List)json.get("result");
        	Iterator it = allResult.iterator();
        	while(it.hasNext()){
        		Map map = (Map)it.next();
        		if(map.get("category").toString().equals("receive")){
        			BTCInfo info = new BTCInfo();
        			info.setAccount(map.get("account")+"");
        			info.setAddress(map.get("address")+"");
        			info.setAmount(Double.valueOf(map.get("amount").toString()));
        			info.setCategory(map.get("category")+"");
        			info.setComment(map.get("comment")+"");
        			try {
						if(map.get("confirmations") != null 
								&& map.get("confirmations").toString().trim().length() >0){
							info.setConfirmations(Integer.parseInt(map.get("confirmations").toString()));
						}
					} catch (Exception e) {
						info.setConfirmations(0);
					}
        			try {
        				long time = Long.parseLong(map.get("time").toString());
        				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        				java.util.Date dt = new Date(time * 1000); 
        				String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        				info.setTime(Timestamp.valueOf(sDateTime));
					} catch (Exception e) {
						info.setTime(Utils.getTimestamp());
					}
        			info.setTxid(map.get("txid")+"");
        			all.add(info);
        		}
        	}
        }
        Collections.reverse(all);
        return all;
	}
	
	/***
	 * 以地址GROUP BY
	 * 查出所有收到的币以地址
	 * [
		{
		"address" : "YBjKaMR7XC3yLck2aaNmkJiy2cbr37jezD",
		"account" : "fdafdsa",
		"amount" : 0.40000000,
		"confirmations" : 210
		},
		]
	 * */
	public JSONObject listreceivedbyaddress() throws Exception {
		String s = main("listreceivedbyaddress", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/***
	 * 
	 * 根据交易ID取得交易明细
	 * */
	private JSONObject gettransaction(String xid) throws Exception {
		String s = main("gettransaction", "[\""+xid+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/**
	 * category receive or send
	 * **/
	public BTCInfo gettransactionValue(String xid,String address,String category) throws Exception {
		JSONObject json = gettransaction(xid);
		BTCInfo btcInfo = null ;
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	btcInfo = new BTCInfo() ;
        	
        	Map map = (Map)json.get("result");
        	List xList = (List)map.get("details");
        	Iterator it = xList.iterator();
        	while(it.hasNext()){
        		Map xMap = (Map)it.next();
        		if(xMap.get("category").toString().equals(category)){
        			String address2 = xMap.get("address")+"";
        			
        			if(address.equals(address2)){
        				btcInfo.setAccount(xMap.get("account")+"");
        				btcInfo.setAddress(xMap.get("address")+"");
        				btcInfo.setAmount(Double.valueOf(xMap.get("amount").toString()));
        				break;
        			}
        			
        		}
        	}
			btcInfo.setCategory(category);
			btcInfo.setComment(map.get("comment")+"");
			try {
				if(map.get("confirmations") != null 
						&& map.get("confirmations").toString().trim().length() >0){
					btcInfo.setConfirmations(Integer.parseInt(map.get("confirmations").toString()));
				}
			} catch (Exception e) {
				btcInfo.setConfirmations(0);
			}
			try {
				long time = Long.parseLong(map.get("time").toString());
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date dt = new Date(time * 1000); 
				String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
				btcInfo.setTime(Timestamp.valueOf(sDateTime));
			} catch (Exception e) {
				btcInfo.setTime(Utils.getTimestamp());
			}
			btcInfo.setTxid(map.get("txid")+"");
        }
        return btcInfo;
	}
	
	/***
	 * 查出所有的币以标签
	 * 
		[
		{
		"account" : "dfasf",
		"amount" : 0.50100000,
		"confirmations" : 575
		},
		]
	 * */
	public JSONObject listreceivedbyaccount() throws Exception {
		String s = main("listreceivedbyaddress", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	
	/***
	 * 根据收款地址，提现比特币
	 * Returns the transaction ID <txid> if successful.
	 * {"result":"2278857ca4ab04b41b36fe0f5ec8415d8e72c66a4688c60d8d2eefe994d3042c","error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * {"result":null,"error":500,"id":1}
	 * */
	public JSONObject sendtoaddress(String address,double account,String comment) throws Exception {
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletpassphrase(30);
		}
		String s = main("sendtoaddress", "[\""+address+"\","+account+","+"\""+comment+"\"]");
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletlock();
		}
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String sendtoaddressValue(String address,double account,double ffees,String comment) throws Exception {
		String result = "";
		settxfee(ffees);
		JSONObject s = sendtoaddress(address, account, comment);
        if(s.containsKey("result")){
        	if(!s.get("result").toString().equals("null")){
        		result = s.get("result").toString();
        	}
        }
		return result;
	}
	
	//设置手续费
	public void settxfee(double ffee) throws Exception {
		JSONArray js = new JSONArray();
		js.add(ffee);
		main("settxfee",js.toString());
	}
	
	//解锁
	//解锁
	public boolean walletpassphrase(int times) throws Exception {
		boolean flag = false;
		try {
			String s =main("walletpassphrase","[\""+PASSWORD+"\","+times+"]");
			JSONObject json = JSONObject.fromObject(s); 
			if(json.containsKey("error")){
				String error = json.get("error").toString();
				if(error.equals("null") || error == null || error == "" || error.trim().length() ==0){
					flag = true;
				}
			}
		} catch (Exception e) {}
		return flag;
	}
	
	//锁
	public void walletlock() throws Exception {
		main("walletlock","[]");
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ ACCESS_KEY
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String hash = getSignature(params, SECRET_KEY);

		String url = "http://"+ACCESS_KEY+":"+SECRET_KEY+"@"+IP+":"+PORT;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String userpass = ACCESS_KEY + ":" + SECRET_KEY;
		String basicAuth = "Basic "
				+ DatatypeConverter.printBase64Binary(userpass.getBytes());
		

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("Authorization", basicAuth);
		
		String postdata = "{\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postdata);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(responseCode != 200){
			return "{\"result\":null,\"error\":"+responseCode+",\"id\":1}";
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		inputLine = in.readLine();
		response.append(inputLine);
		in.close();
		result = response.toString();
		return result;
	}
	
	public static void main(String args[]) throws Exception{
		BTCMessage btcMessage = new BTCMessage() ;
		btcMessage.setIP("127.0.0.1") ;
		btcMessage.setPORT("8545") ;
		btcMessage.setPASSWORD("123456") ;
		BTCUtils btcUtil = new BTCUtils(btcMessage) ;
	}
	
}