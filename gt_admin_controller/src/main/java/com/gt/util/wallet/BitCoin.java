package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
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

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BitCoin extends WalletUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2257445969169491962L;

	public BitCoin(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
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
		if (!super.isISERC20()){
			//只要不是代币就直接查询比特币的余额
			result = this.getBalanceValue_BTC();
		}else{
			//Omni的余额需要按照地址进行统计
			List<String> allAddr = this.getAllAddress();
			for (String addr : allAddr) {
				result += this.getBalanceValue_Omni(addr);
			}
		}
		return result;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String result = null;
		String s = main("getnewaddress", "[]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result")){
        	result = json.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		//如果没有获取到地址，则需要重新填充地址缓存
		if(null==result) {
			if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
				unlockWallet("");
			}
			main("keypoolrefill", "[]");
			if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
				lockWallet("");
			}
		}
		return result;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid,String address) throws Exception {
		BTCInfo btcInfo = null ;
		String s = main("gettransaction", "[\""+txid+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	btcInfo = new BTCInfo() ;
        	
        	Map map = (Map)json.get("result");
        	List xList = (List)map.get("details");
        	Iterator it = xList.iterator();
        	while(it.hasNext()){
        		Map xMap = (Map)it.next();
        		if(xMap.get("category").toString().equals("receive")){
        			String address2 = xMap.get("address")+"";
        			
        			if(address.equals(address2)){
        				//检测是否为Omni的代币转账交易
            			Map promap = omni_getTransaction(txid);
            			
            			if(promap!=null){
            				//如果地址不一致，就表示这是一个Omni的付款交易，不需要处理
            				if (!promap.get("address").toString().equals(address2)){
            					break;
            				}
            				//存放Omni的收款信息以及代币ID，31是USDT
            				btcInfo.setAddress(promap.get("address").toString());
            				btcInfo.setAmount(Double.valueOf(promap.get("amount").toString()));
            				btcInfo.setComment(promap.get("propertyid").toString());
            			}else{
	        				btcInfo.setAccount(xMap.get("account")+"");
	        				btcInfo.setAddress(xMap.get("address")+"");
	        				btcInfo.setAmount(Double.valueOf(xMap.get("amount").toString()));
	        				btcInfo.setComment("");
            			}
        				break;
        			}
        			
        		}
        	}
			btcInfo.setCategory("receive");
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
		String s = main("listtransactions", "[\"*\",1000,0]");
		JSONObject json = JSONObject.fromObject(s); 
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
        			info.setComment("");
        			info.setContract("");
        			
        			//检测是否为Omni的代币转账交易
        			Map promap = omni_getTransaction(map.get("txid").toString());
        			
        			if(promap!=null){
        				//如果地址不一致，就表示这是一个Omni的付款交易，不需要处理
        				if (!promap.get("address").toString().equals(map.get("address").toString())){
        					continue;
        				}
        				//存放Omni的收款信息以及代币ID，31是USDT
        				info.setAddress(promap.get("address").toString());
            			info.setAmount(Double.valueOf(promap.get("amount").toString()));
            			info.setComment(promap.get("propertyid").toString());
            			info.setContract(promap.get("propertyid").toString());
        			}
        			
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

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		// 获取指定区块的交易ID列表，比特币的交易量很大
		// 传参是区块hash值
		List<String> tids = null;
		String s = main("getblock", "[\"" + blockid + "\"]");
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
		// Bitcoin不需要处理该方法
		return false;
	}

	@Override
	public double queryFee() throws Exception {
		// Bitcoin不需要处理该方法
		return 0;
	}

	@Override
	public long bestBlockNumberValue() throws Exception {
		// TODO Auto-generated method stub
		String s = main("getbestblockhash", "[]");
		String result = null ;
		long bheight = 0L;
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result")){
			result = json.getString("result");
        	if(!result.equals("null")){
        		result = json.get("result").toString();
        		s = main("getblock", "[\"" + result + "\"]");
        		json = JSONObject.fromObject(s);
        		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        			Map map = (Map)json.get("result");
        			bheight =Long.valueOf(map.get("height").toString());
        		}
        	}
        }
		return -1;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee,String Memo)
			throws Exception {
		String result = "";
		//设置转账手续费，BTC/kb
		settxfee(fee);

		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String s = "";
		if(null == super.getCONTRACT() || "".equals(super.getCONTRACT())){
			s = main("sendtoaddress", "[\""+to+"\","+amount+","+"\"\"]");
		}else{
			//s = main("omni_send", "[\""+from+"\",\""+to+"\","+super.getCONTRACT()+",\""+amount+"\"]");
			String feeaddress = getUnspentAddress(fee);
			String fromaddress= getUnspentAddress(super.getCONTRACT(), amount);
			s = omni_funded_send(fromaddress, to, super.getCONTRACT(), amount, feeaddress);
		}
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		JSONObject json = JSONObject.fromObject(s); 

        if(json.containsKey("result")){
        	if(!json.get("result").toString().equals("null")){
        		result = json.get("result").toString();
        	}
        }
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// TODO Auto-generated method stub
		//BTC不需要处理该方法，Omni代币需要处理该方法
		//地址不正确不做处理，防止空地址和错误地址
		if (!this.validateAddress(mainAddr)) {
			return ;
		}
		if(null!=super.getCONTRACT() && !"".equals(super.getCONTRACT())) {
			String str = main("omni_getwalletaddressbalances", "[]");
			JSONObject json = JSONObject.fromObject(str);
			System.out.println(json);
			if(json.containsKey("result") && null != json.get("result")){
				List<Map> list = json.getJSONArray("result");
				for(Map map : list){
					String address = map.get("address").toString();
					if(!mainAddr.toLowerCase().equals(address.toLowerCase())) {
						List<Map> balances = (List<Map>) map.get("balances");
						for(Map mp : balances){
							if(mp.containsKey("propertyid") && super.getCONTRACT().equals(mp.get("propertyid").toString())){
								Double balance = Double.valueOf(mp.get("balance").toString());
								if(balance>mintran){
									//转账到主地址
									omni_funded_send(address, mainAddr, super.getCONTRACT(), balance, mainAddr);
								}
							}
						}
					}
				}
			}
		}
		
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
			/*if(responseCode == 500){
				System.out.println(url + "  " +postdata);
				BufferedReader errin = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String errinputLine;
				StringBuffer errresponse = new StringBuffer();
				errinputLine = errin.readLine();
				errresponse.append(errinputLine);
				errin.close();
				System.out.println(errresponse.toString());
			}*/
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
	
	
	private void settxfee(double ffee) throws Exception {
		JSONArray js = new JSONArray();
		js.add(ffee);
		main("settxfee",js.toString());
	}
	
	private Map omni_getTransaction(String txid) throws Exception {
		Map<String, String> map = null; 
		JSONArray js = new JSONArray();
		js.add(txid);
		String s = main("omni_gettransaction",js.toString());
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			Map mjson = (Map)json.get("result");
			if(mjson.get("valid").toString().equals("true")) {
				map =  new HashMap<String, String>();
				map.put("address", mjson.get("referenceaddress").toString());
				map.put("amount", mjson.get("amount").toString());
				map.put("propertyid", mjson.get("propertyid").toString());
			}
		}
		return map;
	}
	
	private String omni_funded_send(String fromaddress,String toaddress,String propertyid,double amount, String feeaddress) throws Exception{
		String result = "";
		//设置转账手续费，BTC/kb
		//settxfee(fee);

		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		result = main("omni_funded_send", "[\""+fromaddress+"\",\""+toaddress+"\","+propertyid+",\""+amount+"\",\""+feeaddress+"\"]");
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		//System.out.println(result);
		return result;
	}
	
	private List<String> getAllAddress() throws Exception {
		List<String> list = new ArrayList();
		String s = main("listaccounts","[]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			Map map = (Map)json.get("result");
			//遍历所有账户
			for (Object key : map.keySet()) { 
				JSONArray js = new JSONArray();
				js.add(key.toString());
				s = main("getaddressesbyaccount",js.toString());
				JSONObject jaddres = JSONObject.fromObject(s);
				if(jaddres.containsKey("result") && !jaddres.get("result").toString().equals("null")){
					JSONArray addrs = jaddres.getJSONArray("result");
					for (int i = 0; i < addrs.size(); i++) {
						list.add(addrs.getString(i)) ;
					}
				}
			}
		}
		return list;
	}
	
	private double getBalanceValue_BTC() throws Exception {
		double result = 0d;
		String s = main("getbalance", "[]");
		JSONObject json = JSONObject.fromObject(s);
		if(json.containsKey("result")){
        	result =Double.valueOf(json.get("result").toString());
        }
		return result;
	}
	
	private double getBalanceValue_Omni(String address) throws Exception {
		double result = 0d;
		//必须是为代币且合约地址不为空
		if(super.isISERC20() && !super.getCONTRACT().equals("")){
			JSONArray js = new JSONArray();
			js.add(address);
			js.add(Integer.valueOf(super.getCONTRACT().toString()).intValue());
			//Omni的代币ID必须为数字，否则无法获取
			String s = main("omni_getbalance", js.toString());
			JSONObject json = JSONObject.fromObject(s);
			if(json.containsKey("result") && !json.get("result").toString().equals("null")){
				JSONObject rstinfo = json.getJSONObject("result");
	        	result =Double.valueOf(rstinfo.get("balance").toString());
	        	if (result>0){
	        		System.out.println(address);
	        	}
	        }
		}
		return result;
	}
	
	/**
	 * 获取USDT大于指定余额的一个地址
	 * @param minAmount
	 * @return
	 * @throws Exception
	 */
	public String getUnspentAddress(double minAmount) throws Exception{
		String str = main("listunspent", "[]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && null != json.get("result")){
			List<Map> list = json.getJSONArray("result");
			for(Map map : list){
				Double amount = Double.valueOf(map.get("amount").toString());
				if(amount>minAmount){
					System.out.println("fee address:"+map.get("address").toString()+"  amount:"+amount);
					return map.get("address").toString();
				}
			}
        }
		return "";
	}
	
	/**
	 * 获取某种代币余额大于指定值的地址
	 * @param propertyid
	 * @param minAmount
	 * @return
	 * @throws Exception
	 */
	public String getUnspentAddress(String propertyid, double minAmount) throws Exception{
		//将返回钱包内所有余额不为0的地址列表
		String str = main("omni_getwalletaddressbalances", "[]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && null != json.get("result")){
			List<Map> list = json.getJSONArray("result");
			for(Map map : list){
				String address = map.get("address").toString();
				List<Map> balances = (List<Map>) map.get("balances");
				for(Map mp : balances){
					if(mp.containsKey("propertyid") && propertyid.equals(mp.get("propertyid").toString())){
						Double balance = Double.valueOf(mp.get("balance").toString());
						if(balance>minAmount){
							System.out.println("usdt address:"+map.get("address").toString()+"  amount:"+balance);
							return address;
						}
					}
				}
			}
		}
		return "";
	}
	
	
	public List<JSONObject> listunspent(String address) throws Exception {
		String str = "";
		if(null != address && !"".equals(address)){
			str = main("listunspent", "[6,9999999,\""+address+"\"]");
		}else{
			str = main("listunspent", "[6,9999999]");
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
	
	public String createrawtransaction(JSONArray inputs,JSONArray outputs) throws Exception{
		String result = "";
		
		String str = main("createrawtransaction", "["+inputs+","+outputs+"]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		if(json.containsKey("result") && !json.get("result").toString().equals("null")){
			result = json.get("result").toString();
		}
		
		return result;
		
	}
	
	public JSONObject signrawtransaction(String hex) throws Exception{
		
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String str = main("signrawtransactionwithwallet", "[\""+hex+"\"]");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	return json.getJSONObject("result");
        }
        if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		
		return null;
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
		if(null == list || list.size() <= 0){
			return result;
		}
		
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
				outputs.accumulate("data", str2HexStr(message));
				break;
			}
		}
		
		String hex = createrawtransaction(inputs, outputs);
		
		JSONObject signResult= signrawtransaction(hex);
		
		if(null != signResult && signResult.getBoolean("complete")){
			result = sendrawtransaction(signResult.get("hex").toString());
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
		int message_len = message.length();
		if(message_len+2<16){
			payload += "0"+Integer.toHexString(message_len+2);
		}else{
			payload += Integer.toHexString(message_len+2);
		}
		payload += "6a"; //OP_RETURN https://en.bitcoin.it/wiki/Script
		payload += str2HexStr(((char) message_len)+"").toLowerCase();
		payload += str2HexStr(message).toLowerCase();
		payload += "00000000";
		
		String hex = version + input_len + inputs + outputlen + outputs + payload;
		
		return hex;
	}
	
	public String sendmessage2(String address, String message, double fee) throws Exception{
		String result = "";
		DecimalFormat df = new DecimalFormat("#0.00000000");

		List<JSONObject> list = listunspent(address);
		if(null == list || list.size() <= 0){
			return result;
		}
		
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
		
		JSONObject signResult= signrawtransaction(hex);
		
		if(null != signResult && signResult.getBoolean("complete")){
			result = sendrawtransaction(signResult.get("hex").toString());
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
	
	public static String toStringHexTest(String s) {  
		byte[] baKeyword = new byte[s.length() / 2];  
		for (int i =0; i < baKeyword.length; i++) {  
			try {  
				baKeyword[i]=(byte)(0xff&Integer.parseInt(s.substring(i*2,i*2+2),16));  
			}catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		try {  
			s= new String(baKeyword,"UTF-8");// UTF-16le:Not  
		} catch (Exception e1) {  
			e1.printStackTrace();  
		}  
		return s;  
	}  
	
	public static BigInteger parseInt(String hexval){
		String val ="0";
		if (hexval.length()>2){
			val = hexval.substring(2) ;
			if(val.equals("")){
				val ="0";
			}
		}
		BigInteger intVal = new BigInteger(val,16) ;
		
		return intVal ;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("127.0.0.1");
		wmsg.setPORT("8332");
		wmsg.setACCESS_KEY("user");
		wmsg.setSECRET_KEY("passwd");
		wmsg.setISERC20(true);
		wmsg.setCONTRACT("31");
		wmsg.setPASSWORD("password");
		WalletUtil wallet =  WalletUtil.createWalletByClass("com.gt.util.wallet.BitCoin", wmsg);
		System.out.println(wallet.getBalanceValue(""));

	}

}
