package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;

public class ASCH extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8429425952574882236L;

	public ASCH(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateAddress(String address) throws Exception {
		String s = httpGet("accounts", "address="+address);
		JSONObject json = JSONObject.fromObject(s);
		long result = 0;
		if (null != json){
			if (json.getString("success").toString().equals("true")){
				return true;
			}
		}
		return false;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		String s = "";
		JSONObject json = null;
		double result = 0;
		if(!super.isISERC20()){
			s = httpGet("accounts/getBalance", "address="+address);
			json = JSONObject.fromObject(s);
			if (json.getString("success").equals("true")){
				result = parseAmount(json.getString("balance"));
			}
		}else{
			s = httpGet("uia/balances/"+address+"/"+super.getCONTRACT(), "");
			json = JSONObject.fromObject(s);
			if (json.getString("success").equals("true")){
				result = parseAmount(json.getJSONObject("balance").getString("balance"));
			}
		}
		
		return result;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String result = null;
		result = Utils.getUUID();
		return result;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid, String address)
			throws Exception {
		//此处不处理该方法
		return null;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin)
			throws Exception {
		//begin参数不做使用，number参数放置区块ID
		List<BTCInfo> all = new ArrayList();
		String s = httpGet("blocks/full", "height="+number);
		JSONObject json = JSONObject.fromObject(s);
		if (json.getString("success").equals("true")){
			JSONObject block = json.getJSONObject("block");
			if (null!=block && block.getInt("numberOfTransactions")>0){
				List allResult = (List)block.get("transactions");
	        	Iterator it = allResult.iterator();
	        	String mainAddr = "A8L26BH4MLpaS3mu4CnamRv8etkoc9wn2c".toLowerCase();
	        	while(it.hasNext()){
	        		Map map = (Map)it.next();
	        		String addr = map.get("recipientId").toString().toLowerCase();
	        		if(addr.equals(mainAddr)){
		        		BTCInfo info = new BTCInfo();
		        		info.setTxid(map.get("id").toString());
		        		info.setTime(Utils.getTimestamp());
		        		//我们只采用共用主地址和备注相结合的方式进行处理充值
	        			info.setAddress(map.get("message").toString());
	        			info.setAmount(parseAmount(map.get("amount").toString()));
	        			info.setBlockNumber(Long.valueOf(map.get("height").toString()));
	        			//资产转账需要特别处理
	        			if(map.get("type").toString().equals("14")){
	        				JSONObject asset =(JSONObject) map.get("asset");
	        				info.setComment(asset.getJSONObject("uiaTransfer").getString("currency"));
	        				info.setContract(asset.getJSONObject("uiaTransfer").getString("currency"));
	        				info.setAmount(parseAmount(asset.getJSONObject("uiaTransfer").getString("amount")));
	        			}
	        			all.add(info);
	        		}
	        	}
	        	
			}
			
		}
		return all;
	}

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		//ASCH不需要此方法
		return null;
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		//ASCH不需要该方法
		return true;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		//ASCH不需要该方法
		return true;
	}

	@Override
	public boolean getContractResult(String org_txid) throws Exception {
		//ASCH不需要该方法
		return false;
	}

	@Override
	public double queryFee() throws Exception {
		// ASCH默认0.1XAS
		return 0.1;
	}

	@Override
	public long bestBlockNumberValue() throws Exception {
		String s = httpGet("blocks/getheight", "");
		JSONObject json = JSONObject.fromObject(s);
		long result = 0;
		if (null != json){
			if (json.getString("success").toString().equals("true")){
				result = json.getLong("height");
			}
		}
		return result;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee,String Memo)
			throws Exception {
		JSONObject postdata = new JSONObject();
		String method = "transactions";
		postdata.accumulate("secret", super.getPASSWORD());
		//代币需要声明币种
		if(super.isISERC20()){
			postdata.accumulate("amount", new BigDecimal(amount * Math.pow(10,8)).toString());
			postdata.accumulate("recipientId", to);
			postdata.accumulate("message", Memo);
			postdata.accumulate("currency", super.getCONTRACT());
			method = "uia/transfers";
		}else{
			postdata.accumulate("amount", new BigDecimal(amount * Math.pow(10,8)));
			postdata.accumulate("recipientId", to);
			postdata.accumulate("message", Memo);
		}
		System.out.println(postdata.toString());
		String s = main(method,postdata.toString());
		JSONObject json = JSONObject.fromObject(s);
		String result = "";
		if (null != json){
			if (json.getString("success").toString().equals("true")){
				result = json.getString("transactionId");
			}
		}
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// ASCH不需要该方法

	}

	@Override
	public int getConforms(String txid) throws Exception {
		String s = httpGet("transactions/get", "id="+txid);
		JSONObject json = JSONObject.fromObject(s);
		int result = 0;
		if (null != json){
			if (json.getString("success").equals("true")){
				result = json.getJSONObject("transaction").getInt("confirmations");
			}
		}
		return result;
		
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		//authenticator();

		String url = "http://"+super.getIP()+":"+super.getPORT()+"/api/"+method;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("PUT");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		
		String postdata = "{\"jsonrpc\":\"2.0\", \"params\":"+condition+", \"id\": 1,\"method\":\""+method+"\"}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(condition);
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
	
	private String httpGet(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		//authenticator();

		String url = "http://"+super.getIP()+":"+super.getPORT()+"/api/"+ method +"?"+condition;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("GET");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		
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
	
	private double parseAmount(String hexval){
		String val = hexval ;
		if(val.equals("")){
			val ="0";
		}
		Double intVal = Double.valueOf(val) ;
		
		return intVal/Math.pow(10,8) ;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("127.0.0.1");
		wmsg.setPORT("8192");
		wmsg.setPASSWORD("this is a test password");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("");
		wmsg.setCONTRACT("dreamworld.DDC");
		wmsg.setISERC20(true);
		wmsg.setDECIMALS(8);
		
		WalletUtil asch = WalletUtil.createWalletByClass("com.gt.util.wallet.ASCH", wmsg);
		System.out.println(asch.transfer("", "ALjk25kdZkftqBY12NBhdTn3MChzrBaTRc", 100, 0.1, "this is a test"));
		//System.out.println(asch.getConforms("cc758eb587604f1e8454a03ca37776ef932fbabe4c15f22eaef3f7a4c053c969"));
	}

}
