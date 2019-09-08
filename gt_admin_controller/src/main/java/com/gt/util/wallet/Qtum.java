package com.gt.util.wallet;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.gt.util.Utils;
import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Qtum extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 154440359204008295L;

	public Qtum(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		//authenticator();

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
		
		String postdata = "{\"method\":\""+method+"\",\"params\":"+condition+", \"id\": 1}";
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
		String s = main("validateaddress", "[\""+ address+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		System.out.println(s);
		boolean result = false;
		if (null != json){
			if (!json.getString("result").equals(null)){
				result = json.getJSONObject("result").getBoolean("isvalid");
			}
		}
		return result;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double balance = 0d;
		String s = null;
		JSONObject json = null;
		if(!super.isISERC20()){
			s = main("getbalance", "[]");
			json = JSONObject.fromObject(s);
			System.out.println(json);
			if(json.containsKey("result") && !json.get("result").toString().equals("null")){
				balance = Double.valueOf(json.get("result").toString());
			}
		}else{
			String data = "70a08231"+gethexaddress(address);
			s = main("callcontract", "[\""+super.getCONTRACT()+"\",\""+data+"\"]");
			json = JSONObject.fromObject(s);
			System.out.println(json);
			if(json.containsKey("result") && !json.get("result").toString().equals("null")){
				String output = json.getJSONObject("result").getJSONObject("executionResult").get("output").toString();
				if(!Utils.isNull(output)){
					return parseAmount(output);
				}
			}
			
		}
		
		return balance;
	}

	public static String padLeft(String s, int length){
	    byte[] bs = new byte[length];
	    byte[] ss = s.getBytes();
	    Arrays.fill(bs, (byte) (48 & 0xff));
	    System.arraycopy(ss, 0, bs,length - ss.length, ss.length);
	    return new String(bs);
	}
	
	private String parseAmountHex(double amount){
		//BigDecimal valD = new BigDecimal(amount*Math.pow(10,super.getDECIMALS() )) ;
		BigDecimal valD = new BigDecimal(amount) ;
		
		BigInteger val = valD.toBigInteger() ;
		
		return padLeft(val.toString(16),64);
	}
	
	private double parseAmount(String hexval){
		String val ="0";
		if (hexval.length()>2){
			val = hexval.substring(2) ;
			if(val.equals("")){
				val ="0";
			}
		}
		BigInteger intVal = new BigInteger(val,16) ;
		return intVal.doubleValue();

		//处理精度
		//double orgval = intVal.doubleValue()/Math.pow(10,super.getDECIMALS() );
		//return orgval;
	}
	
	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String result = null;
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String s = main("getnewaddress", "[\""+seedInfo+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
        if(json.containsKey("result")){
        	result = json.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}

	/**
	 * 根据区块hash值获取区块信息
	 * @param hash
	 * @return
	 * @throws Exception
	 */
	public JSONObject getBlock(String block) throws Exception{
		try {
			long block_num = Long.parseLong(block);
			block = getBlockHash(block_num);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String str = main("getblock", "[\""+block+"\"]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.getString("result"))){
			return json.getJSONObject("result");
		}
		return null;
	}
	
	public String getBlockHash(long block_num) throws Exception{
		String str = main("getblockhash", "["+block_num+"]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.getString("result"))){
			return json.get("result").toString();
		}
		return null;
	}

	public List<BTCInfo> getTransactions(String txid, String address) throws Exception {
		List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
		String s = main("getrawtransaction", "[\""+txid+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	
        	Map map = (Map)json.get("result");
        	String tx_id = map.get("txid").toString();
        	String blockhash = map.get("blockhash").toString();
        	JSONObject block = getBlock(blockhash);
        	int height = block.getInt("height");
        	
        	int confirmations = 0;
        	try {
				if(map.get("confirmations") != null 
						&& map.get("confirmations").toString().trim().length() >0){
					confirmations = Integer.parseInt(map.get("confirmations").toString());
				}
			} catch (Exception e) {
			}
        	
        	Timestamp block_time = Utils.getTimestamp();
			try {
				long time = Long.parseLong(map.get("time").toString());
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date dt = new Date(time * 1000); 
				String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
				block_time = Timestamp.valueOf(sDateTime);
			} catch (Exception e) {
			}
			
        	List xList = (List)map.get("vout");
        	Iterator it = xList.iterator();
        	while(it.hasNext()){
        		Map xMap = (Map)it.next();
        		Map mp = (Map) xMap.get("scriptPubKey");
        		if(mp.get("type").toString().equals("pubkeyhash")){
        			BTCInfo btcInfo = new BTCInfo() ;
        			btcInfo.setTxid(tx_id);
        			btcInfo.setBlockNumber(height);
        			btcInfo.setConfirmations(confirmations);
        			btcInfo.setTime(block_time);
    				btcInfo.setAccount("");
    				btcInfo.setAddress(mp.get("address")+"");
    				btcInfo.setAmount(Double.valueOf(xMap.get("value").toString()));
    				btcInfo.setCategory("receive");
    				btcInfo.setComment("");
    				btcInfo.setContract("");
    				btcInfos.add(btcInfo);
        		}
        	}
        }
        return btcInfos;
	}
	
	@Override
	public BTCInfo getTransactionsValue(String txid,String address) throws Exception {
		/*BTCInfo info = getTransactions(txid, address);
		if(super.isISERC20()){
			String s = main("searchlogs","["+info.getBlockNumber()+","+info.getBlockNumber()+",{\"addresses\": [\""+super.getCONTRACT()+"\"]},"
					+ "{\"topics\": [\"ddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef\"]}]");
			JSONObject json = JSONObject.fromObject(s);
			System.out.println(json);
			if(json.containsKey("result") && !json.get("result").toString().equals("null")){
	        	List list = json.getJSONArray("result");
	        	Iterator it = list.iterator();
	        	while(it.hasNext()){
	        		JSONObject map = (JSONObject)it.next();
	        		String transactionHash = map.get("transactionHash").toString();
	        		if(txid.equals(transactionHash)){
	        			String data = map.getJSONArray("log").getJSONObject(0).get("data").toString();
	        			String to = map.getJSONArray("log").getJSONObject(0).getJSONArray("topics").getString(2);
	        			to =to.substring(24);
	        			info.setAddress(fromhexaddress(to));
	        			info.setAmount(parseAmount(data));
	        			info.setContract(map.get("contractAddress").toString());
	        		}
	        	}
	        	
	        }
		}
        return info;*/
		return null;
	}
	
	public List<BTCInfo> listContractTransactionsValue(String block_num) throws Exception {
		List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
		String s = main("searchlogs","["+block_num+","+block_num+",{\"addresses\": []},"
				+ "{\"topics\": [\"ddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef\"]}]");
		JSONObject json = JSONObject.fromObject(s);
		System.out.println(json);
		JSONObject block = getBlock(block_num);
		int confirmations = 0;
    	try {
			if(block.get("confirmations") != null 
					&& block.get("confirmations").toString().trim().length() >0){
				confirmations = Integer.parseInt(block.get("confirmations").toString());
			}
		} catch (Exception e) {
		}
		Timestamp block_time = Utils.getTimestamp();
		try {
			long time = Long.parseLong(block.get("time").toString());
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date dt = new Date(time * 1000); 
			String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
			block_time = Timestamp.valueOf(sDateTime);
		} catch (Exception e) {
		}
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			List list = json.getJSONArray("result");
        	Iterator it = list.iterator();
        	while(it.hasNext()){
        		BTCInfo info = new BTCInfo() ;
        		JSONObject map = (JSONObject)it.next();
        		String transactionHash = map.get("transactionHash").toString();
    			String data = map.getJSONArray("log").getJSONObject(0).get("data").toString();
    			JSONArray topics = map.getJSONArray("log").getJSONObject(0).getJSONArray("topics");
    			if(topics.size()==3){
    				String to = topics.getString(2);
        			to =to.substring(24);
        			info.setBlockNumber(new Long(block_num));
        			info.setConfirmations(confirmations);
        			info.setTxid(transactionHash);
        			info.setAddress(fromhexaddress(to));
        			info.setAmount(parseAmount(data));
        			info.setTime(block_time);
    				info.setAccount("");
    				info.setComment("");
    				info.setCategory("receive");
    				info.setContract(map.get("contractAddress").toString());
    				btcInfos.add(info);
    			}
    		}
		}
		return btcInfos;
	}
	
	public List<BTCInfo> getTransactionsValue(String txid) throws Exception {
		List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
		String s = main("getrawtransaction", "[\""+txid+"\",1]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			
			Map map = (Map)json.get("result");
			String blockhash = map.get("blockhash").toString();
			JSONObject block = getBlock(blockhash);
			int height = block.getInt("height");
			int confirmations = 0;
			Timestamp timestamp = Utils.getTimestamp();
			try {
				if(map.get("confirmations") != null 
						&& map.get("confirmations").toString().trim().length() >0){
					confirmations = Integer.parseInt(map.get("confirmations").toString());
				}
			} catch (Exception e) {
			}
			try {
				long time = Long.parseLong(map.get("blocktime").toString());
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date dt = new Date(time * 1000); 
				String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
				timestamp = Timestamp.valueOf(sDateTime);
			} catch (Exception e) {
			}
			String tx_id = map.get("txid")+"";
			
			List xList = (List)map.get("vout");
			Iterator it = xList.iterator();
			BTCInfo btcInfo = null ;
			while(it.hasNext()){
				Map xMap = (Map)it.next();
				Map scriptPubKey = (Map) xMap.get("scriptPubKey");
				if(scriptPubKey.containsKey("addresses")){
					String address2 = JSONArray.fromObject(scriptPubKey.get("addresses")).get(0).toString();
					btcInfo = new BTCInfo() ;
					btcInfo.setBlockNumber(height);
					btcInfo.setConfirmations(confirmations);
					btcInfo.setTime(timestamp);
					btcInfo.setTxid(tx_id);
					btcInfo.setAccount("");
					btcInfo.setAddress(address2);
					btcInfo.setAmount(Double.valueOf(xMap.get("value").toString()));
					btcInfo.setComment("");
					btcInfo.setContract("");
					btcInfo.setCategory("receive");
					btcInfos.add(btcInfo);
				}
				
			}
		}
		return btcInfos;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		/*String s = main("listtransactions", "[\"*\",1000,0]");
		JSONObject json = JSONObject.fromObject(s); 
		List<BTCInfo> all = new ArrayList();
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	List allResult = (List)json.get("result");
        	Iterator it = allResult.iterator();
        	while(it.hasNext()){
        		Map map = (Map)it.next();
        		if(map.get("category").toString().equals("receive")){
        			String txid = map.get("txid").toString();
        			String address = map.get("address").toString();
        			all.add(getTransactionsValue(txid, address));
        		}
        	}
        }
        Collections.reverse(all);
		return all;*/
		
		List<BTCInfo> all = new ArrayList<BTCInfo>();
		JSONObject block = getBlock(number);
		List<String> alltxids = listBlockTransactions(block.get("hash").toString());
		for(int i = 0; i < alltxids.size(); i++){
			List<BTCInfo> btcinfos = getTransactionsValue(alltxids.get(i));
			if(null!=btcinfos && btcinfos.size()>0){
				all.addAll(btcinfos);
			}
		}
		all.addAll(listContractTransactionsValue(number));
		return all;
	}

	@Override
	public List<String> listBlockTransactions(String blockhash) throws Exception {
		String s = main("getblock", "[\""+ blockhash+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		List<String> tids = new ArrayList<String>();
		if (null != json){
			if (!json.getString("result").equals(null)){
				JSONObject result =json.getJSONObject("result");
				JSONArray  ids = result.getJSONArray("tx");
				if (ids.size()>0){
					for(int i=0;i<ids.size();i++){
						tids.add(ids.getString(i));
					}
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
			System.out.println(json.toString());
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
		String s = main("walletlock","[]");
		System.out.println(s);
		return true;
	}

	@Override
	public boolean getContractResult(String org_txid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double queryFee() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long bestBlockNumberValue() throws Exception {
		String s = main("getblockchaininfo", "[]");
		JSONObject json = JSONObject.fromObject(s);
		long result = 0;
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			result = json.getJSONObject("result").getLong("blocks");
		}
		return result;
	}

	public void settxfee(double ffee) throws Exception {
		JSONArray js = new JSONArray();
		js.add(ffee);
		main("settxfee",js.toString());
	}
	
	public String gethexaddress(String address) throws Exception{
		String str = main("gethexaddress", "[\""+address+"\"]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json.toString());
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			 return padLeft(json.get("result").toString(), 64);
		}
		return "";
	}
	
	public String fromhexaddress(String hex) throws Exception{
		String str = main("fromhexaddress", "[\""+hex+"\"]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json.toString());
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			return json.get("result").toString();
		}
		return "";
	}
	
	@Override
	public String transfer(String from, String to, double amount, double fee, String Memo) throws Exception {
		String result = "";
		settxfee(fee);
		if(!super.isISERC20()){
			if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
				unlockWallet("");
			}
			String s = main("sendtoaddress", "[\""+to+"\","+amount+"]");
			
			if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
				lockWallet("");
			}
			JSONObject json = JSONObject.fromObject(s); 
			if(json.containsKey("result")){
				if(!json.get("result").toString().equals("null")){
					result = json.get("result").toString();
				}
			}
		}else{
			if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
				unlockWallet("");
			}
			String data = "a9059cbb"+gethexaddress(to)+parseAmountHex(amount);
			String s = main("sendtocontract", "[\""+ super.getCONTRACT()+"\",\""+data+"\"]");
			//String s = main("sendtocontract", "[\""+ super.getCONTRACT()+"\",\""+data+"\",0,250000,0.0000004,\""+from+"\"]");
			if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
				lockWallet("");
			}
			JSONObject json = JSONObject.fromObject(s); 
			if(json.containsKey("result")){
				if(!json.get("result").toString().equals("null")){
					result = json.getJSONObject("result").get("txid").toString();
				}
			}
			
		}
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int getConforms(String txid) throws Exception {
		String str = main("getrawtransaction", "[\""+txid+"\",1]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	return Integer.parseInt(json.getJSONObject("result").get("confirmations").toString());
        }
		return 0;
	}
	
	public List<JSONObject> listunspent(String address) throws Exception {
		String str = "";
		if(null != address && !"".equals(address)){
			str = main("listunspent", "[12,9999999,\""+address+"\"]");
		}else{
			str = main("listunspent", "[12,9999999]");
		}
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	return json.getJSONArray("result");
        }
        return null;
	}
	
	public String createrawtransaction(JSONArray inputs,JSONObject outputs) throws Exception{
		String result = "";

		String str = main("createrawtransaction", "["+inputs+","+outputs+"]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	result = json.get("result").toString();
        }
		
		return result;
		
	}
	
	public boolean signrawtransaction(String hex) throws Exception{
		boolean result = false;
		
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String str = main("signrawtransaction", "[\""+hex+"\"]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	result = json.getJSONObject("result").getBoolean("complete");
        }
        if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		
		return result;
	}
	
	public String sendrawtransaction(String hex) throws Exception{
		String result = "";
		
		String str = main("sendrawtransaction", "[\""+hex+"\"]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	result = json.get("result").toString();
        }
        
		return result;
	}
	
	public String sendmessage1(String address, String message, double fee) throws Exception{
		String result = "";
		DecimalFormat df = new DecimalFormat("#0.00000000");
		
		List<JSONObject> list = listunspent(address);
		
		JSONArray inputs = new JSONArray();
		JSONObject outputs = new JSONObject();
		double amount = 0;
		for(JSONObject obj : list){
			amount += obj.getDouble("amount");
			JSONObject input = new JSONObject();
			input.put("txid", obj.get("txid").toString());
			input.put("vout", obj.getInt("vout"));
			inputs.add(input);
			
			if(amount - fee > 0.00000001){
				String amount1 = df.format(amount - fee);
				if(obj.containsKey("address")){
					outputs.put(obj.get("address").toString(), Double.valueOf(amount1));
				}else{
					outputs.put(getNewaddressValue(""), Double.valueOf(amount1));
				}
				outputs.put("data", str2HexStr(message));
				break;
			}
		}
		
		String hex = createrawtransaction(inputs, outputs);

		if(signrawtransaction(hex)){
			result = sendrawtransaction(hex);
		}
		
		return result;
	}
	
	public String decodeAndPack(String data, String message){
		int i = 0;
		String version = data.substring(i, i+=8);
		
		String input_len= data.substring(i, i+=2);
		long v = Long.parseLong(input_len, 16);
		
		if(v==0xFF){//255
			v = Long.parseLong(data.substring(i, i+=8),16) + Integer.parseInt(data.substring(i, i+=8),16)*4294967296l;
		}else if(v==0xFE){//254
			v = Long.parseLong(data.substring(i, i+=8),16);
		}else if(v==0xFD){//253
			v = Long.parseLong(data.substring(i, i+=4),16);
		}
		
		String inputs = "";
		for(long j=v;j>0;j--){
			String tx = data.substring(i, i+=64);
			inputs += tx;
			
			String index = data.substring(i, i+=8);
			inputs += index;
			
			String script_len = data.substring(i, i+=2);
			inputs += script_len;
			
			if(Integer.parseInt(script_len,16) > 0){
				String script = data.substring(i, i+=50);
				inputs += script;
			}

			String sequence = data.substring(i, i+=8);
			inputs += sequence;
			
		}
		
		String output_len= data.substring(i, i+=2);
		long v2 = Long.parseLong(output_len, 16);
		
		if(v2==0xFF){//255
			v2 = Long.parseLong(data.substring(i, i+=8),16) + Integer.parseInt(data.substring(i, i+=8),16)*4294967296l;
		}else if(v2==0xFE){//254
			v2 = Long.parseLong(data.substring(i, i+=8),16);
		}else if(v2==0xFD){//253
			v2 = Long.parseLong(data.substring(i, i+=4),16);
		}
		
		String outputs = data.substring(i);
		/*for(long j=v2;j>0;j--){
			String amount = data.substring(i, i+=16);
			outputs += amount;
			System.out.println(amount);
			
			String script_len = data.substring(i, i+=2);
			amount += script_len;
			System.out.println(script_len);
			
			String script = data.substring(i, i+=50);
			script_len += script;
			System.out.println(script);

		}
		
		String locktime = data.substring(i, i+=8);
		System.out.println(locktime);*/
		
		long len = Long.parseLong(output_len, 16) + 1;
		String outputlen;
		if(len<16){
			outputlen = "0"+Long.toHexString(len);
		}else{
			outputlen = Long.toHexString(len);
		}
		
		String payload = "";
		payload += "00000000";
		int message_len = message.getBytes().length;
		
		if(message_len+2<16){
			payload += "0"+Integer.toHexString(message_len+2);
		}else{
			payload += Integer.toHexString(message_len+2);
		}
		payload += "6a"; //OP_RETURN 	https://en.bitcoin.it/wiki/Script
		if(message_len <= 75){
			payload += str2HexStr(((char) message_len)+"").toLowerCase();
 		}else if(message_len <= 256){
 			payload += "4c" + str2HexStr(((char) message_len)+"").toLowerCase();
 		}else{
 			payload += "4d" + str2HexStr(((char) message_len%256)+"").toLowerCase() + str2HexStr(((char) (int)message_len/256)+"").toLowerCase();
 		}
		payload += str2HexStr(message).toLowerCase();
		payload += "00000000";
		
		String hex = version + input_len + inputs + outputlen + outputs + payload;
		
		return hex;
	}
	
	public String sendmessage2(String address, String message, double fee) throws Exception{
		String result = "";
		DecimalFormat df = new DecimalFormat("#0.00000000");
		List<JSONObject> list = listunspent(address);
		
		JSONArray inputs = new JSONArray();
		JSONObject outputs = new JSONObject();
		double amount = 0;
		for(JSONObject obj : list){
			amount += obj.getDouble("amount");
			JSONObject input = new JSONObject();
			input.put("txid", obj.get("txid").toString());
			input.put("vout", obj.getInt("vout"));
			inputs.add(input);
			
			if(amount - fee > 0.00000001){
				String amount1 = df.format(amount - fee);
				if(obj.containsKey("address")){
					outputs.put(obj.get("address").toString(), Double.valueOf(amount1));
				}else{
					outputs.put(getNewaddressValue(""), Double.valueOf(amount1));
				}
				break;
			}
			
		}
		
		String hex = createrawtransaction(inputs, outputs);
		hex = decodeAndPack(hex, message);
		
		if(signrawtransaction(hex)){
			result = sendrawtransaction(hex);
		}
		
		return result;
	}
	
	/**
	 * 字符串转16进制
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			// sb.append(' ');
		}
		return sb.toString().trim();
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage message = new WalletMessage();
		message.setIP("127.0.0.1");
		message.setPORT("3889");
		message.setACCESS_KEY("test");
		message.setSECRET_KEY("test1234");
		message.setISERC20(true);
		message.setCONTRACT("09800417b097c61b9fd26b3ddde4238304a110d5");//QBT
		//message.setCONTRACT("f2703e93f87b846a7aacec1247beaec1c583daa4");//HPY
		message.setDECIMALS(8);
		message.setPASSWORD("123456789");
		//WalletUtil qtum =  WalletUtil.createWalletByClass("com.ruizton.util.Qtum", message);
		Qtum qtum =  new Qtum(message);
		
		
		//System.out.println(Integer.toHexString(123456));
		
		//System.out.println(qtum.getBalanceValue("Qjfg5X9hmXDh4t7CipYFxCSLKx3psuFZUe"));
		//System.out.println(qtum.gethexaddress("QXMspXCnoGrYK6KQnWMEvhTvRaTgv2mfQY"));
		//System.out.println(qtum.getData(qtum.gethexaddress("QXMspXCnoGrYK6KQnWMEvhTvRaTgv2mfQY")));
		//qtum.getTransactionsValue(txid, height, address);
		
		
		//System.out.println(qtum.getTransactionsValue("b50e98bac57265ef26ed72da58ae48da03f5bfca736ffe1394f36a0beb09b891","Qjfg5X9hmXDh4t7CipYFxCSLKx3psuFZUe"));
		//System.out.println(qtum.getTransactionsValue("36734643fef00a573961daed67660931a60ab6c144c0abcf099b3f2addf6b5aa", 178339, ""));
		//System.out.println(qtum.getTransactionsValue("36734643fef00a573961daed67660931a60ab6c144c0abcf099b3f2addf6b5aa","QXMspXCnoGrYK6KQnWMEvhTvRaTgv2mfQY"));
		//System.out.println(qtum.listTransactionsValue("",""));
		//System.out.println(qtum.transfer("", "", amount, fee, Memo));
		
		//sendtocontract 09800417b097c61b9fd26b3ddde4238304a110d5 a9059cbb00000000000000000000000067c454142142be9d62208950d10d16ef9fac3cd70xde0b6b3a7640000 0 250000 0.00000040 QXMspXCnoGrYK6KQnWMEvhTvRaTgv2mfQY
		//System.out.println(qtum.listTransactionsValue("", ""));
		//System.out.println(qtum.getBalanceValue("Qjfg5X9hmXDh4t7CipYFxCSLKx3psuFZUe"));
		
		//System.out.println(qtum.listTransactionsValue("", ""));
		//System.err.println(qtum.transfer("QXMspXCnoGrYK6KQnWMEvhTvRaTgv2mfQY", "Qjfg5X9hmXDh4t7CipYFxCSLKx3psuFZUe", 1, 0, ""));
		
		
		//System.out.println(qtum.listBlockTransactions("88fd21496c7d7a0b31dd99856782a47c43423a70ed055bfe742c904d704dd648"));
		
		//System.out.println(qtum.getBalanceValue("Qjfg5X9hmXDh4t7CipYFxCSLKx3psuFZUe"));
		/*BTCInfo info = qtum.getTransactionsValue("36734643fef00a573961daed67660931a60ab6c144c0abcf099b3f2addf6b5aa","QXMspXCnoGrYK6KQnWMEvhTvRaTgv2mfQY");
		System.out.println(info.toString());
		JSONObject json = JSONObject.fromObject(info.toString());
		System.out.println(json.toString());*/
		
		/*List<BTCInfo> list = qtum.listTransactionsValue("", "");
		System.out.println(list.toString());*/
		
		//qtum.listContractTransactionsValue("207841");
		
		//System.out.println(padLeft("fcfd8171771865abff5c6219bb0bfa19ffe891b6", 64));
		//System.out.println(qtum.parseAmount("000000000000000000000000000000000000000000000000000006885de2bee0"));
		
		
		
		//System.out.println(qtum.getBlock("2548e56c3150c5f3ed885a5776d2cc30e634c3c201c1debb1eb393ae5bfc20be"));
		
		
		qtum.sendmessage2("", "我们仔细分析一下上面这个输出", 0.002);
		
		
	}
	
}
