package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import net.sf.json.JSONObject;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;



public class Decent extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2707531606642444333L;

	public Decent(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateAddress(String address) throws Exception {
		String s = main("get_account", "[\""+address+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		boolean result = false;
		try {
			if(json.containsKey("result")){
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		String s = main("list_account_balances", "[\"gbcax\", \"1.3.0\"]");
		JSONObject json = JSONObject.fromObject(s); 
		double result = 0d;
        if(json.containsKey("result")){
        	List allResult = (List)json.get("result");
        	Iterator it = allResult.iterator();
        	if(it.hasNext()){
        		Map map = (Map)it.next();
        		result =parseAmount(map.get("amount").toString());
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getConforms(String txid) throws Exception {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin)
			throws Exception {
		if(number.equals("")){
			number = "500";
		}
		int count = Integer.getInteger(number);
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
        				info.setComment("");
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

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		// DCT不需要处理该方法
		return null;
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		boolean flag = false;
		try {
			String s =main("unlock","[\""+super.getPASSWORD()+"\"]");
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

	@Override
	public boolean lockWallet(String account) throws Exception {
		main("lock","[]");
		return true;
	}

	@Override
	public boolean getContractResult(String org_txid) throws Exception {
		// DCT不需要处理该方法
		return false;
	}

	@Override
	public double queryFee() throws Exception {
		// DCT不需要处理该方法
		return 0;
	}

	@Override
	public long bestBlockNumberValue() throws Exception {
		
		long result = curBlockNumberValue();
		
		return -1;
	}

	private long curBlockNumberValue() throws Exception {
		String s = main("get_dynamic_global_properties", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		long result = 0L;
		if (json.containsKey("result") && !json.get("result").equals(null)){
			result = Long.valueOf(json.getJSONObject("result").getString("head_block_number"));
		}
		return result;
	}
	
	@Override
	public String transfer(String from, String to, double amount, double fee,String Memo)
			throws Exception {
		//DCT转账的需要特殊处理，把from参数存放comment备注信息
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String s = main("transfer2", "[\"gbcax\",\""+to+"\",\""+amount+"\",\"DCT\","+"\""+from+"\"]");
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		JSONObject json = JSONObject.fromObject(s); 
		String result = "";
        if(json.containsKey("result")){
        	if(!json.get("result").toString().equals("null")){
        		result = json.get("result").toString();
        	}
        }
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// DCT不需要处理该方法

	}
	
	private double parseAmount(String hexval){
		String val = hexval ;
		if(val.equals("")){
			val ="0";
		}
		Double intVal = Double.valueOf(val) ;
		
		return intVal/Math.pow(10,8 ) ;
	}
	
	private JSONObject listtransactions(int count,String from) throws Exception {
		//walletpassphrase(10);
		String s = main("search_account_history", "[\"gbcax\", \"-time\", \"" +from+"\", "+count+"]");
		JSONObject json = JSONObject.fromObject(s); 
		//walletlock();
        return json;
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ super.getACCESS_KEY()
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String hash = getSignature(params, super.getSECRET_KEY());

		String url = "http://"+super.getIP()+":"+super.getPORT()+"/rpc";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String userpass = super.getACCESS_KEY() + ":" + super.getSECRET_KEY();
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

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("127.0.0.1");
		wmsg.setPORT("8092");
		wmsg.setACCESS_KEY("user");
		wmsg.setSECRET_KEY("passwd");
		wmsg.setISERC20(false);
		wmsg.setCONTRACT("");
		wmsg.setPASSWORD("password");
		WalletUtil wallet =  WalletUtil.createWalletByClass("com.gt.util.wallet.Decent", wmsg);
		//System.out.println(wallet.getContractResult("b9cc4b5e8845611f70f5e213ab7930a899fb19cc"));
		System.out.println(wallet.bestBlockNumberValue());
	}

}
