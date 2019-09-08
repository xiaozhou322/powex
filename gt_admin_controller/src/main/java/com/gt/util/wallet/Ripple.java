package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;
import com.ripple.core.coretypes.AccountID;
import com.ripple.core.coretypes.Amount;
import com.ripple.core.coretypes.uint.UInt32;
import com.ripple.core.types.known.tx.signed.SignedTransaction;
import com.ripple.core.types.known.tx.txns.Payment;

import net.sf.json.JSONObject;

public class Ripple extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4867407800712807482L;


	public Ripple(WalletMessage walletMessage) {
		super(walletMessage);
	}

	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		
		
		String postdata = "{\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
		//System.out.println(postdata);
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postdata);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(responseCode/10 != 20){
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
	
	public JSONObject account_info(String address) throws Exception {
		JSONObject result = null;
		String str = main("account_info", "[{\"account\":\""+address+"\",\"ledger_index\":\"validated\"}]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
			result = json.getJSONObject("result");
		}
		return result;
	}
	
	@Override
	public boolean validateAddress(String address) throws Exception {
		boolean result = false;
		JSONObject json = account_info(address);
		if(null != json){
			result = true;
		}
		return result;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double result = 0d;
		JSONObject json = account_info(address);
		if(null != json){
			JSONObject account_data = json.getJSONObject("account_data");
        	double balance = Double.valueOf(account_data.get("Balance").toString()).doubleValue() / Math.pow(10, 6);
        	//可用余额    xrp会冻结20个币
        	if(balance >= 20){
        		return balance - 20;
        	}
        }
		return result;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String str = main("wallet_propose", "[{\"passphrase\":\""+super.getPASSWORD()+"\"}]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
			return json.getJSONObject("result").get("account_id").toString();
		}
		return null;
	}

	@Override
	public BTCInfo getTransactionsValue(String tx_hash, String ledger_index) throws Exception {
		if(!Utils.isNull(ledger_index)){
			return getTransaction(tx_hash, ledger_index);
		}else{
			return getTransaction(tx_hash);
		}
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String ledger_index, String begin) throws Exception {
		List<BTCInfo> all = new ArrayList<BTCInfo>();
		List<String> alltxids = listBlockTransactions(ledger_index);
		for(int i = 0; i < alltxids.size(); i++){
			BTCInfo btcinfos = getTransactionsValue(alltxids.get(i),ledger_index);
			if(null!=btcinfos){
				all.add(btcinfos);
			}
		}
		return all;
	}

	@Override
	public List<String> listBlockTransactions(String ledger_index) throws Exception {
		String str = main("ledger", "[{\"ledger_index\":"+ledger_index+",\"transactions\":true}]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
			JSONObject ledger = json.getJSONObject("result").getJSONObject("ledger");
			return ledger.getJSONArray("transactions");
		}
		return new ArrayList<String>();
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		// TODO Auto-generated method stub
		return false;
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
		String str = main("ledger", "[{\"ledger_index\":\"validated\"}]");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
			return json.getJSONObject("result").getLong("ledger_index");
		}

		return 0;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee, String Memo) throws Exception {
		String tx_blob = sign2(from, to, amount, super.getPASSWORD(), fee, Memo);
		if(!"".equals(tx_blob)){
			return submit(tx_blob);
		}
		return null;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int getConforms(String txid) throws Exception {
		int conforms = 0;
		long height = bestBlockNumberValue();
		BTCInfo info = getTransactionsValue(txid, "");
		if(null != info){
			conforms = (int) (height - info.getBlockNumber());
		}
		return conforms;
	}
	
	public BTCInfo getTransaction(String tx_hash) throws Exception{
		BTCInfo btcInfo = null ;
		String str = main("tx", "[{\"transaction\":\""+tx_hash+"\",\"binary\":false}]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
			JSONObject result = json.getJSONObject("result");
			JSONObject meta = result.getJSONObject("meta");
			if("tesSUCCESS".equals(meta.getString("TransactionResult"))){
				if("Payment".equals(result.get("TransactionType"))){
					double Amount = 0;
					try {
						Amount = result.getDouble("Amount") / Math.pow(10, 6);
					} catch (net.sf.json.JSONException e) {
						return null;
					}
					btcInfo = new BTCInfo();
					String Destination = result.getString("Destination");
					String DestinationTag = result.getString("DestinationTag");
					String hash = result.getString("hash");
					String ledgerIndex = result.getString("ledger_index");
					btcInfo.setAddress(Destination);
					btcInfo.setAmount(Amount);
					btcInfo.setTime(Utils.getTimestamp());
					btcInfo.setTxid(hash);
					btcInfo.setBlockNumber(Long.valueOf(ledgerIndex));
					btcInfo.setCategory("receive");
					btcInfo.setComment(DestinationTag);
				}
			}
		}
		return btcInfo;
	}
	
	public BTCInfo getTransaction(String tx_hash,String ledger_index) throws Exception{
		BTCInfo btcInfo = null ;
		String str = main("transaction_entry", "[{\"tx_hash\":\""+tx_hash+"\",\"ledger_index\":"+ledger_index+"}]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
			JSONObject result = json.getJSONObject("result");
			JSONObject metadata = result.getJSONObject("metadata");
			if("tesSUCCESS".equals(metadata.getString("TransactionResult"))){
				String ledgerIndex = result.getString("ledger_index");
				JSONObject tx_json = json.getJSONObject("result").getJSONObject("tx_json");
				if("Payment".equals(tx_json.get("TransactionType"))){
					double Amount = 0;
					try {
						Amount = tx_json.getDouble("Amount") / Math.pow(10, 6);
					} catch (net.sf.json.JSONException e) {
						return null;
					}
					btcInfo = new BTCInfo();
					btcInfo.setComment("");
					String hash = tx_json.getString("hash");
					String Destination = tx_json.getString("Destination");
					String DestinationTag = tx_json.getString("DestinationTag");
					btcInfo.setAddress(Destination);
					btcInfo.setAmount(Amount);
					btcInfo.setTime(Utils.getTimestamp());
					btcInfo.setTxid(hash);
					btcInfo.setBlockNumber(Long.valueOf(ledgerIndex));
					btcInfo.setCategory("receive");
					btcInfo.setComment(DestinationTag);
				}
			}
		}
		return btcInfo;
	}
	
	/**
     * 签名 、已弃用
     * @param address
     * @param value
     * @return tx_blob
	 * @throws Exception 
     */
    public String sign(String fromAddress, String toAddress,double value,String secret,double fee) throws Exception{
    	String result = "";
    	JSONObject param = new JSONObject();
    	param.accumulate("offline", false);
    	param.accumulate("secret", secret);
    	JSONObject tx_json = new JSONObject();
    	tx_json.accumulate("Account", fromAddress);
    	tx_json.accumulate("Amount", new Double(value * Math.pow(10, 6)).longValue());
    	tx_json.accumulate("Destination", toAddress);
    	tx_json.accumulate("TransactionType", "Payment");

    	param.accumulate("tx_json", tx_json);
    	param.accumulate("fee_mult_max", new Double(fee*Math.pow(10, 6)).longValue());
    	
    	String str = main("sign", "["+param.toString()+"]");
    	JSONObject json = JSONObject.fromObject(str);
    	System.out.println(json);
    	if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
    		result = json.getJSONObject("result").getString("tx_blob");
    	}
        return result;
    }
    
    /**
     * @param fromAddress
     * @param toAddress
     * @param value
     * @param fee
     * @return
     * @throws Exception
     */
    public String sign2(String fromAddress, String toAddress,double value,String secret, double fee, String memo) throws Exception{
    	String result = "";
    	Long vLong = new Double(value*Math.pow(10, 6)).longValue();
    	Long fLong = new Double(fee*Math.pow(10, 6)).longValue();
    	Map<String, String> map = getAccountSequenceAndLedgerCurrentIndex(fromAddress);
    	Payment payment = new Payment();
        payment.as(AccountID.Account, fromAddress);
        payment.as(AccountID.Destination, toAddress);
        payment.as(Amount.Amount, vLong.toString());
        payment.as(UInt32.Sequence, map.get("accountSequence"));
        payment.as(UInt32.LastLedgerSequence, map.get("ledgerCurrentIndex")+4);
        payment.as(Amount.Fee, fLong.toString());
        payment.as(UInt32.DestinationTag, memo);//只能是数值
        SignedTransaction signed = payment.sign(secret);
        if (signed != null) {
        	result = signed.tx_blob;
		}
		return result;
    	
    }
	
	public Map<String, String> getAccountSequenceAndLedgerCurrentIndex(String fromAddress) throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
    	params.put("account", fromAddress);
    	params.put("strict", "true");
    	params.put("ledger_index", "current");
    	params.put("queue", "true");
    	JSONObject json = account_info(fromAddress);
        if (json != null) {
			if ("success".equals(json.getString("status"))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("accountSequence", json.getJSONObject("account_data").getString("Sequence"));
				map.put("ledgerCurrentIndex", json.getString("ledger_index"));
				return map;
			}
		}
        return null;
	}

	public String submit(String tx_blob) throws Exception{
		String str = main("submit", "[{\"tx_blob\":\""+tx_blob+"\"}]");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && "success".equals(json.getJSONObject("result").getString("status"))){
			JSONObject tx_json = json.getJSONObject("result").getJSONObject("tx_json");
			return tx_json.getString("hash");
		}
		return "";
	}
	

	public static void main(String[] args) throws Exception {
		
		WalletMessage wmsg = new WalletMessage();
		/*wmsg.setIP("127.0.0.1");
		wmsg.setPORT("5005");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("");
		wmsg.setPASSWORD("sndoJNo34XAm5WnmHPtgi1RPQsvao");*/
		
		//testNET
		wmsg.setIP("s.altnet.rippletest.net");
		wmsg.setPORT("51234");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("");
		wmsg.setPASSWORD("sndoJNo34XAm5WnmHPtgi1RPQsvao");
		Ripple wallet = (Ripple) WalletUtil.createWalletByClass("com.gt.util.wallet.Ripple", wmsg);
		
		System.out.println(wallet.bestBlockNumberValue());
		
		//System.out.println(wallet.getNewaddressValue(""));
		
		//System.out.println(wallet.listBlockTransactions("43915260"));
		System.out.println(wallet.listTransactionsValue("43915131",""));
		
		/*for(long i=wallet.bestBlockNumberValue();i>0;i--){
			System.out.println(i);
			System.out.println(wallet.listTransactionsValue(i+"",""));
		}*/
		
		
		System.out.println(wallet.validateAddress("r9cZA1mLK5R5Am25ArfXFmqgNwjZgnfk59"));
		
		
		System.out.println(wallet.getBalanceValue("r9cZA1mLK5R5Am25ArfXFmqgNwjZgnfk59"));
		
		
		
		System.out.println(wallet.getBalanceValue("rUxPbPHeQKChnMhYjaFcYT4vqcLNRnPLrM"));
		System.out.println(wallet.getBalanceValue("rU5rLQYrUAmWH2yph7C1F2kKrd3YWDDmSo"));

		
		System.out.println(wallet.getTransactionsValue("8E32C4CB7CABC81219C077F2B48074792819B2D575E8759FFE601C57594B119B",""));
		//System.out.println(wallet.getTransactionsValue("22042C62CD23B322B33C9DA7C064222DD47DC436168E1C8023A5D072F0123793",""));
		//System.out.println(wallet.getTransactionsValue("1D170E300C95B110115EDDFBB4D1C0D6D45E5C6885C3B5FFA588646F2A61E5B2",""));
		
		
		//System.out.println(wallet.sign2("rUxPbPHeQKChnMhYjaFcYT4vqcLNRnPLrM", "rU5rLQYrUAmWH2yph7C1F2kKrd3YWDDmSo", 10, "sndoJNo34XAm5WnmHPtgi1RPQsvao", 0.001));
		
		/*String tx_blob = wallet.sign2("rUxPbPHeQKChnMhYjaFcYT4vqcLNRnPLrM", "rU5rLQYrUAmWH2yph7C1F2kKrd3YWDDmSo", 1.0,"sndoJNo34XAm5WnmHPtgi1RPQsvao", 0.001);
		System.out.println("tx_blob:"+tx_blob);
		System.out.println(wallet.submit(tx_blob));*/
		
		//System.out.println(wallet.getTransactionsValue("4556EBB6337DDD5F3FA17A00B2D6F5CFDAE144CFEE8CA268215877CDB8C40C3C",""));
		
		//System.out.println(wallet.transfer("rUxPbPHeQKChnMhYjaFcYT4vqcLNRnPLrM", "rU5rLQYrUAmWH2yph7C1F2kKrd3YWDDmSo", 2, 0.001, "123"));
		
		System.out.println(wallet.getTransactionsValue("1518668C433EA316A1104FF4E2B13E00B7EBF9283BD10ABF76983AD3E533BACD","16264221"));

		System.out.println(wallet.listTransactionsValue("14534288", ""));
		//System.out.println(wallet.getAccountSequenceAndLedgerCurrentIndex("r9cZA1mLK5R5Am25ArfXFmqgNwjZgnfk59"));
		
		//System.out.println(wallet.getTransactionsValue("A1221D4DD9D5ABFD4BF52048FE6EEACA2D9EB0528013F0AC38DE4D16CC6B4FD0",""));
		
		
		//System.out.println(wallet.listTransactionsValue(wallet.bestBlockNumberValue()+"", ""));
		
	}

}
