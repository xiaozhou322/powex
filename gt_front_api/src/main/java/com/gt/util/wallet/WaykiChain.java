package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;


import com.gt.util.Utils;
import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WaykiChain extends WalletUtil {

	public WaykiChain(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ super.getACCESS_KEY()
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String hash = getSignature(params, super.getSECRET_KEY());

		String url = "http://"+super.getACCESS_KEY()+":"+super.getSECRET_KEY()+"@"+super.getIP()+":"+super.getPORT();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String userpass = super.getACCESS_KEY() + ":" + super.getSECRET_KEY();
		String basicAuth = "Basic "
				+ DatatypeConverter.printBase64Binary(userpass.getBytes());
		
		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("Authorization", basicAuth);
		
		String postdata = "{\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
		//System.out.println(postdata);
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
	
	
	@Override
	public boolean validateAddress(String address) throws Exception {
		boolean result = false;
		String s = main("validateaddress", "[\""+address+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		if(json.containsKey("result")){
			String xx =JSONObject.fromObject(json.get("result")).getString("isvalid");
			if(xx.equals("true")){
				result = true;
			}
		}
		return result;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double result = 0d;
		String s = main("getbalance", "[\""+address+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			JSONObject balance = json.getJSONObject("result");
        	result = balance.getDouble("balance");
        }
		return result;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String result = null;
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String s = main("getnewaddress", "[]");
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && "null".equals(json.get("error").toString())){
			JSONObject addr = json.getJSONObject("result");
        	result = addr.get("addr").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid,String address) throws Exception {
		BTCInfo btcInfo = null ;
		String s = main("gettxdetail", "[\"" + txid + "\"]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			JSONObject result = json.getJSONObject("result");
			
			String txtype = result.getString("txtype");
			if("COMMON_TX".equals(txtype)){
				btcInfo = new BTCInfo();
				String hash = result.getString("hash");
				long height = result.getLong("height");
				String desaddr = result.getString("desaddr");
				double money = result.getDouble("money");
				
				btcInfo.setAddress(desaddr);
				btcInfo.setAmount(money/Math.pow(10, 8));
				btcInfo.setBlockNumber(height);
				btcInfo.setCategory("receive");
				btcInfo.setComment("");
				btcInfo.setContract("");
				btcInfo.setConfirmations(0);
				btcInfo.setTxid(hash);
				try {
					long time = Long.parseLong(result.get("confirmedtime").toString());
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date dt = new Date(time * 1000); 
					String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
					btcInfo.setTime(Timestamp.valueOf(sDateTime));
				} catch (Exception e) {
					btcInfo.setTime(Utils.getTimestamp());
				}
				
			}
			
		}
        return btcInfo;
	}

	@Override
	public int getConforms(String txid) throws Exception {
		int confirms = 0;
		String s = main("gettransaction", "[\""+txid+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	Map map = (Map)json.get("result");
			try {
				if(map.get("confirmations") != null 
						&& map.get("confirmations").toString().trim().length() >0){
					confirms=Integer.parseInt(map.get("confirmations").toString());
				}
			} catch (Exception e) {
				confirms=0;
			}
        }
		return confirms;
	}
	
	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin)
			throws Exception {
		List<BTCInfo> all = new ArrayList<BTCInfo>();
		List<String> alltxids = listBlockTransactions(number);
		for(int i = 0; i < alltxids.size(); i++){
			BTCInfo btcinfo = getTransactionsValue(alltxids.get(i),"");
			if(null!=btcinfo){
				all.add(btcinfo);
			}
		}
        Collections.reverse(all);
        return all;
	}

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		List<String> tids = null;
		String s = main("getblock", "[" + blockid + "]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			JSONArray txids = json.getJSONObject("result").getJSONArray("tx");
			if (txids.size()>0){
				tids = new ArrayList();
				for(int i=0;i<txids.size();i++){
					tids.add(txids.getString(i));
				}
			}
		}
		return tids;
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		boolean flag = false;
		try {
			String s =main("walletpassphrase","[\""+super.getPASSWORD()+"\",30]");
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

	@Override
	public boolean lockWallet(String account) throws Exception {
		main("walletlock","[]");
		return true;
	}

	@Override
	public boolean getContractResult(String org_txid) throws Exception {
		return false;
	}

	@Override
	public double queryFee() throws Exception {
		return 0;
	}

	@Override
	public long bestBlockNumberValue() throws Exception {
		// TODO Auto-generated method stub
		String s = main("getbestblockhash", "[]");
		String result = null ;
		long bheight = 0L;
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && "null".equals(json.get("error").toString())){
			result = json.get("result").toString();
        	if(!result.equals("null")){
        		s = main("getblock", "[\"" + result + "\"]");
        		json = JSONObject.fromObject(s);
        		if(json.containsKey("result") && !json.get("result").toString().equals("null") && "null".equals(json.get("error").toString())){
        			Map map = (Map)json.get("result");
        			bheight =Long.valueOf(map.get("height").toString());
        		}
        	}else{
        		s = main("getinfo", "[]");
        		json = JSONObject.fromObject(s);
        		if(json.containsKey("result") && !json.get("result").toString().equals("null") && "null".equals(json.get("error").toString())){
        			Map map = (Map)json.get("result");
        			bheight =Long.valueOf(map.get("blocks").toString());
        		}
        	}
        }
		return bheight;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee,String Memo)
			throws Exception {
		String result = "";
		settxfee(new Double(fee * Math.pow(10, 8)).longValue());
		
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String s = main("sendtoaddress", "[\""+from+"\",\""+to+"\","+new Double(amount * Math.pow(10, 8)).longValue()+"]");
		System.out.println(s);
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		JSONObject json = JSONObject.fromObject(s); 
        if(json.containsKey("result")){
        	if(!json.get("result").toString().equals("null")){
        		result = json.getJSONObject("result").get("hash").toString();
        	}
        }
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// TODO Auto-generated method stub
	}

	private void settxfee(long ffee) throws Exception {
		JSONArray js = new JSONArray();
		js.add(ffee);
		main("settxfee",js.toString());
	}
	
	/**
	 * 地址激活。 维基链地址激活后才能转账。
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private String registeraccounttx(String address) throws Exception {
		String result = "";
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String str = main("registeraccounttx", "[\""+address+"\",1000000]");
		System.out.println(str);
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		JSONObject json = JSONObject.fromObject(str);
        if(json.containsKey("result")){
        	if(!json.get("result").toString().equals("null")){
        		result = json.getJSONObject("result").get("hash").toString();
        	}
        }
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("192.168.0.153");
		wmsg.setPORT("6968");
		wmsg.setACCESS_KEY("wikichain");
		wmsg.setSECRET_KEY("admin");
		wmsg.setPASSWORD("123456789");
		WaykiChain wallet = (WaykiChain) WalletUtil.createWalletByClass("com.gtwallet.wallet.core.WaykiChain", wmsg);
		System.out.println(wallet.bestBlockNumberValue());
		
		//System.out.println(wallet.getNewaddressValue(""));
		
		System.out.println(wallet.getBalanceValue("WV4UGamSDuuMCC5FhsjbVFPvp2PXcUd4Nf"));
		System.out.println(wallet.listTransactionsValue("1662387",""));
		
		//System.out.println(wallet.transfer("WV4UGamSDuuMCC5FhsjbVFPvp2PXcUd4Nf", "WiSTB84okkkenEsJ6XcSKd9Mx1jPdb52bX", 1, 0.0001, ""));
		
		//System.out.println(wallet.listBlockTransactions("19357"));
		
		//System.out.println(wallet.listTransactionsValue("19357", ""));
		
		//System.out.println(wallet.registeraccounttx("WV4UGamSDuuMCC5FhsjbVFPvp2PXcUd4Nf"));
		//e3af040d9a57ba9185c969c8a9f840af5e87af68cf0ab5beec4581f7910e30a4
		
		
	}

}
