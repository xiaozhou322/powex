package com.gt.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;

public class ETPUtils implements IWalletUtil{
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
	
	
	public static boolean validateaddress(String address){
		if (address.matches("^(0x)?[0-9a-f]{40}$")==false) {
	        return false;
	    }
		return true ;
	}
	public ETPUtils(BTCMessage btcMessage) {
		this.ACCESS_KEY = btcMessage.getACCESS_KEY();
		this.SECRET_KEY = btcMessage.getSECRET_KEY();
		this.IP = btcMessage.getIP();
		this.PORT = btcMessage.getPORT();
		this.PASSWORD = btcMessage.getPASSWORD();
	}
	
	public JSONObject getbalance() throws Exception {
		String s = main("getbalance", "[\""+ACCESS_KEY+"\", \""+SECRET_KEY+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public JSONObject getbalanceMap() throws Exception {
		String s = main("listbalances", "[\""+ACCESS_KEY+"\", \""+SECRET_KEY+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public double getbalanceValue() throws Exception {
		double result = 0d;
		JSONObject s = getbalance();
        if(s.containsKey("total-confirmed")){
        	result =parseAmount(s.getString("total-unspent"));
        }
		return result;
	}
	
	public Map<String,Double> getbalanceValueMap() throws Exception {
		Map<String,Double> map = new HashMap<String, Double>();
		JSONObject s = getbalanceMap();
        if(s.containsKey("balances")){
        	JSONArray array = s.getJSONArray("balances");
        	if(array != null && array.size() >0){
        		for (int i=0;i<array.size();i++) {
					JSONObject js = array.getJSONObject(i).getJSONObject("balance");
                    String address = js.getString("address");
                    double qty = Double.valueOf(js.getString("confirmed"));
                    if(qty <= 0) continue;
                    map.put(address, qty);
				}
        	}
        }
		return map;
	}

	
	public String getNewaddress() throws Exception {
		String s = main("getnewaddress", "[\""+ACCESS_KEY+"\", \""+SECRET_KEY+"\"]");
		return s;
	}
	
	//取得最新的HASH
	public String getBestBlockHash() throws Exception{
		String s = main("getbestblockhash", "[]");
		return s;
	}
	
	//区块高度
	public int getBlockHeight(String hash) throws Exception {
		String s = main("getblock", "[\""+hash+"\", \"--json=true\"]");
		JSONObject json = JSONObject.fromObject(s); 
		return json.getJSONObject("header").getJSONObject("result").getInt("number");
	}
	
	public JSONObject queryBlockTrades(int height) throws Exception {
		//请求参数（method:listtx,username:为钱包的用户名，password：为钱包的密码，address:为钱包的地址）  
		String s = main("listtxs", "[\"-e"+height+":"+height+"\", \""+ACCESS_KEY+"\", \""+SECRET_KEY+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}

	public List<BTCInfo> listAll(int height) throws Exception {
		List<BTCInfo> alls = new ArrayList<BTCInfo>();
		JSONObject jsonObject = queryBlockTrades(height);
		if(jsonObject.containsKey("transactions")){
			JSONArray items = jsonObject.getJSONArray("transactions") ;
			if(items != null && items.size() >0){
				for(int i=0;i<items.size();i++){
					JSONObject js =	items.getJSONObject(i);
					String direction = js.getString("direction");
					if(direction.equals("receive")){
						if(js.containsKey("outputs")){
							JSONArray last = js.getJSONArray("outputs");
							if(last != null && last.size() >0){
								JSONObject jj = last.getJSONObject(0);
								String type = jj.getJSONObject("attachment").getString("type");
								if(!type.equals("etp")) continue;
								String to = jj.getString("address") ;
								double value = parseAmount(jj.getString("etp-value"));
								long send = Long.valueOf(js.getString("height"));
								long now = Long.valueOf(getBlockHeight(getBestBlockHash()));
								long times = now-send;
//								if(times < 100) continue;
								BTCInfo info = new BTCInfo();
								info.setAddress(to);
								info.setAmount(value);
								info.setConfirmations(3);
								info.setTime(Utils.getTimestamp());
								info.setTxid(js.getString("hash"));
								info.setBlockNumber(times) ;
								alls.add(info);
							}
						}
						
					}
				}
			}

		}
		return alls ;
	}
	
	//username:为钱包的用户名，password：为钱包的密码，address：接收地址，quantity：金额
	public JSONObject sendTransaction(String toAddress,double qty) throws Exception {
		String s = main("send", "[\""+ACCESS_KEY+"\", \""+SECRET_KEY+"\", \""+toAddress+"\", \""+parseAmountHex(qty)+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	
	public String sendtoaddressValue(String to,double amount) throws Exception {
		String result = null;
		try {
			JSONObject s = sendTransaction(to, amount);
			if(s.containsKey("transaction")){
				result = s.getJSONObject("transaction").getString("hash");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	//	'{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x407d73d8a49eeb85d32cf465507dd71d507100c1", "latest"],"id":1}'
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ ACCESS_KEY
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String url = "http://"+IP+":"+PORT+"/rpc";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());

		String postdata = "{\"jsonrpc\":\"3.0\",\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
		System.out.println(condition);
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
		StringBuffer response = new StringBuffer();

		int ch;
		while ((ch = in.read()) != -1) {
			response.append((char) ch);
		}
		in.close();
		result = response.toString();
		System.out.println("打印信息===="+result);
		return result;
	}
	
	
	public static long parseAmountHex(double amount){
		Long val = (long) (amount*Math.pow(10, 8)) ;
		return val;
	}
	
	public static double parseAmount(String hexval){
		return Utils.getDouble(Double.valueOf(hexval)/Math.pow(10, 8), 4);
	}

	public static void main(String args[]) throws Exception{
		
		BTCMessage btcMessage = new BTCMessage() ;
		btcMessage.setIP("121.41.28.88") ;
		btcMessage.setPORT("8820") ;
		btcMessage.setACCESS_KEY("wwwbitgocn");
		btcMessage.setSECRET_KEY("liming828100");
		btcMessage.setPASSWORD("liming82810ss0") ;
		ETPUtils ethUtils = new ETPUtils(btcMessage) ;
	}
	
}