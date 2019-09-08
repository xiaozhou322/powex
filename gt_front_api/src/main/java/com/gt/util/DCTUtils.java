package com.gt.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
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

public class DCTUtils implements IWalletUtil {
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
	
	public DCTUtils(BTCMessage btcMessage) {
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
		String s = main("list_account_balances", "[\"gbcax\", \"1.3.0\"]");
		JSONObject json = JSONObject.fromObject(s); 
		System.out.println(json.toString());
        return json;
	}
	
	public JSONObject isValidateaddress(String address) throws Exception {
		String s = main("get_account", "[\""+address+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	//DCT小数精度是8位
	public double getbalanceValue() throws Exception {
		double result = 0d;
		JSONObject s = getbalance();
        if(s.containsKey("result")){
        	List allResult = (List)s.get("result");
        	Iterator it = allResult.iterator();
        	if(it.hasNext()){
        		Map map = (Map)it.next();
        		result =parseAmount(map.get("amount").toString());
        	}
        	
        }
		return result;
	}
	
	//判断地址是否有效
	public boolean validateaddress(String address) {
		boolean result = false;
		try {
			JSONObject s = isValidateaddress(address);
			if(s.containsKey("result")){
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	//Decent 地址是固定的钱包账号，只是需要为每个用户提供一个不同的memo信息
	public String getNewaddressValueForAdmin(String userId) throws Exception {
		String result = null;
		
		result = Utils.getUUID();
        
		return result;
	}
	

	/**
	 * 查所有
	 * **/
	public JSONObject listtransactions(int count,String from) throws Exception {
		//walletpassphrase(10);
		String s = main("search_account_history", "[\"gbcax\", \"-time\", \"" +from+"\", "+count+"]");
		JSONObject json = JSONObject.fromObject(s); 
		//walletlock();
        return json;
	}
	
	/**
	 * 取得所有的收到的交易记录
	 * **/
	public List<BTCInfo> listtransactionsValue(int count,String from) throws Exception {
		JSONObject json = listtransactions(count, "0.0.0");
		//System.out.println(json.toString());
		List<BTCInfo> all = new ArrayList();
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	List allResult = (List)json.get("result");
        	Iterator it = allResult.iterator();
        	while(it.hasNext()){
        		Map map = (Map)it.next();
        		//检测到账地址为我们的账号，才是充值记录
        		if(map.get("m_to_account").toString().equals("1.2.9478")){
        			BTCInfo info = new BTCInfo();
        			info.setAccount(map.get("m_from_account")+"");
        			//处理充值金额，只有资产类型为DCT才做为DCT充值，处理失败作为充值为空
        			info.setAmount(0d);
        			try{
        				Map transaction = (Map) map.get("m_transaction_amount");
        				if(transaction!=null){
        					//只有资产类型为DCT(1.3.0)的才做处理
        					if (transaction.get("asset_id").equals("1.3.0")){
        						info.setAmount(parseAmount(transaction.get("amount").toString()));
        					}
        				}
        			}catch (Exception e){
        				info.setAmount(0d);
        			}
        			
        			info.setAddress("");
        			
        			try{
        				String memo =  map.get("m_str_description").toString();
        				memo=memo.replace("transfer - ", "");
        				info.setAddress(memo);
        				info.setComment(memo);
        			}catch (Exception e){
        				info.setAddress("");
        				info.setComment("");
        			}
        			
        			info.setCategory("receive");
        			
        			info.setConfirmations(0);
        			try {
        				String sDateTime = map.get("m_timestamp").toString().replace("T", " ");  //得到精确到秒的表示：08/31/2006 21:08:00
        				info.setTime(Timestamp.valueOf(sDateTime));
					} catch (Exception e) {
						info.setTime(Utils.getTimestamp());
					}
        			info.setTxid(map.get("id")+"");
        			all.add(info);
        		}
        	}
        }
        Collections.reverse(all);
        return all;
	}
	
	public double parseAmount(String hexval){
		String val = hexval ;
		if(val.equals("")){
			val ="0";
		}
		Double intVal = Double.valueOf(val) ;
		
		return intVal/Math.pow(10,8 ) ;
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
		String s = main("transfer2", "[\"gbcax\",\""+address+"\",\""+account+"\",\"DCT\","+"\""+comment+"\"]");
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletlock();
		}
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String sendtoaddressValue(String address,double account,double ffees,String comment) throws Exception {
		String result = "";
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
			String s =main("unlock","[\""+PASSWORD+"\"]");
			JSONObject json = JSONObject.fromObject(s); 
			if(json.containsKey("result")){
				String error = json.get("result").toString();
				if(error.equals("null") || error == null){
					flag = true;
				}
			}
		} catch (Exception e) {}
		return flag;
	}
	
	//锁
	public void walletlock() throws Exception {
		main("lock","[]");
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ ACCESS_KEY
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String hash = getSignature(params, SECRET_KEY);

		String url = "http://"+IP+":"+PORT+"/rpc";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String userpass = ACCESS_KEY + ":" + SECRET_KEY;
		String basicAuth = "Basic "
				+ DatatypeConverter.printBase64Binary(userpass.getBytes());

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		//con.setRequestProperty("Authorization", basicAuth);

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
		btcMessage.setPORT("8092") ;
		btcMessage.setPASSWORD("password") ;
		DCTUtils dtcUtil = new DCTUtils(btcMessage) ;
		System.out.println(dtcUtil.getbalance());
		
	}
	
}