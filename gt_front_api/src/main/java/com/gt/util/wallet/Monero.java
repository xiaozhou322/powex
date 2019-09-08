package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.gt.util.Utils;
import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;

import net.sf.json.JSONObject;

public class Monero extends WalletUtil implements Serializable{

	
	/*private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT()+"/json_rpc";
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
		
		String postdata = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
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

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		result = response.toString().replaceAll(" ", "");
		return result;
	}*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2772399325134860859L;

	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT()+"/json_rpc";
		
		
		HttpPost httpPost = new HttpPost(url);
		
		String postdata = "{\"jsonrpc\":\"2.0\", \"params\":"+condition+", \"id\": 1,\"method\":\""+method+"\"}";
		
		httpPost.setEntity(new StringEntity(postdata));
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json;charset=utf-8");
		
		/*
		//HttpClient 4.3以后
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(
				new AuthScope(AuthScope.ANY_HOST,AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(super.getACCESS_KEY(),super.getSECRET_KEY())
		);
		HttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();*/
		
		//HttpClient 4.3以前
		DefaultHttpClient httpClient = new DefaultHttpClient();
		Credentials credentials = new UsernamePasswordCredentials(super.getACCESS_KEY(),super.getSECRET_KEY());
		httpClient.getCredentialsProvider().setCredentials(
                new AuthScope(AuthScope.ANY_HOST,AuthScope.ANY_PORT),
                credentials
        );
		httpClient.getParams().setParameter(AuthSchemes.DIGEST, Collections.singleton(AuthSchemes.DIGEST));
		httpClient.getAuthSchemes().register(AuthSchemes.DIGEST,new DigestSchemeFactory());
		
		HttpResponse resp = httpClient.execute(httpPost);
		
		if(resp.getStatusLine().getStatusCode() != 200){
			return "{\"result\":null,\"error\":"+resp.getStatusLine().getStatusCode()+",\"id\":1}";
		}
		
		byte[] bytes = EntityUtils.toByteArray(resp.getEntity());
		if (bytes != null) {
			String str = new String(bytes, "UTF-8");
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			result = m.replaceAll("");
		}
		return result;
	}
	
	public Monero(WalletMessage walletMessage) {
		super(walletMessage);
	}

	@Override
	public boolean validateAddress(String address) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double amount = 0;
		String str = main("get_balance", "{}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.get("result"))){
			JSONObject result = json.getJSONObject("result");
			Long l_amount = result.getLong("unlocked_balance");
			amount = l_amount / Math.pow(10, 12);
		}
		return amount;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String address = null;
		String str = main("create_address", "{\"account_index\":0}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.get("result"))){
			JSONObject result = json.getJSONObject("result");
			address = result.getString("address");
		}
		return address;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid, String address) throws Exception {
		BTCInfo btcInfo = null;
		String str = main("get_transfer_by_txid", "{\"txid\":\""+txid+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.get("result"))){
			btcInfo = new BTCInfo();
			JSONObject result = json.getJSONObject("result");
			JSONObject transfer = result.getJSONObject("transfer");
			long block_height = transfer.getLong("height");
			String comment = transfer.getString("note");
			int confirmations = transfer.getInt("confirmations");
			Timestamp timestamp = Utils.getTimestamp();
			try {
				long time = transfer.getLong("timestamp");
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date dt = new Date(time * 1000); 
				String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
				timestamp = Timestamp.valueOf(sDateTime);
			} catch (Exception e) {
			}
			List<JSONObject> destinations = transfer.getJSONArray("destinations");
			String addr = destinations.get(0).getString("address");
			double amount = destinations.get(0).getLong("amount") / Math.pow(10, 12);
			
			btcInfo.setBlockNumber(block_height);
			btcInfo.setAmount(amount);
			btcInfo.setCategory("receive");
			btcInfo.setConfirmations(confirmations);
			btcInfo.setComment(comment);
			btcInfo.setTxid(txid);
			btcInfo.setTime(timestamp);
			btcInfo.setAddress(addr);
		}
		return btcInfo;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		List<BTCInfo> list = null;
		//String str = main("get_transfers", "{\"in\":true,\"account_index\":0,\"subaddr_indices\":[0],\"filter_by_height\":true,\"min_height\":"+number+"}");
		String str = main("get_transfers", "{\"in\":true,\"filter_by_height\":true,\"min_height\":"+begin+",\"max_height\":"+number+"}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.getString("result"))){
			list = new ArrayList<BTCInfo>();
			JSONObject result = json.getJSONObject("result");
			List<JSONObject> li = result.getJSONArray("in");
			for(int i=0;i<li.size();i++){
				BTCInfo btcInfo = new BTCInfo();
				JSONObject tx = li.get(i);
				String address = tx.getString("address");
				long l_amount = tx.getLong("amount");
				double amount = l_amount / Math.pow(10, 12);
				int confirmations = tx.getInt("confirmations");
				int height = tx.getInt("height");
				String note = tx.getString("note");
				String txid = tx.getString("txid");
				Timestamp timestamp = Utils.getTimestamp();
				try {
					long time = tx.getLong("timestamp");
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date dt = new Date(time * 1000); 
					String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
					timestamp = Timestamp.valueOf(sDateTime);
				} catch (Exception e) {
				}
				btcInfo.setBlockNumber(height);
				btcInfo.setAmount(amount);
				btcInfo.setCategory("receive");
				btcInfo.setConfirmations(confirmations);
				btcInfo.setComment(note);
				btcInfo.setTxid(txid);
				btcInfo.setTime(timestamp);
				btcInfo.setAddress(address);
				list.add(btcInfo);
			}
		}
		
		return list;
	}

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		String str = main("get_block", "{\"height\":"+blockid+"}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.getString("result"))){
			JSONObject result = json.getJSONObject("result");
			if("ok".equals(result.get("status").toString().toLowerCase())){
				JSONObject tx_json = result.getJSONObject("json");
				return tx_json.getJSONArray("tx_hashes");
			}
		}
		return null;
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		String str = main("open_wallet", "{\"filename\":\""+account+"\",\"password\":\""+super.getPASSWORD()+"\"}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result")){
			return true;
		}
		return false;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		String str = main("close_wallet", "[]");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result")){
			return true;
		}
		return false;
	}

	@Override
	public boolean getContractResult(String org_txid) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double queryFee() throws Exception {
		String str = main("get_fee_estimate", "");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.getString("result"))){
			JSONObject result = json.getJSONObject("result");
			if("ok".equals(result.get("status").toString().toLowerCase())){
				return result.getLong("fee")/Math.pow(10, 12);
			}
		}
		return 0;
	}

	@Override
	public long bestBlockNumberValue() throws Exception {
		/*long height = 0;
		String str = main("get_block_count", "[]");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.getString("result"))){
			JSONObject result = json.getJSONObject("result");
			if("ok".equals(result.get("status").toString().toLowerCase())){
				return result.getLong("count");
			}
		}
		return height;*/
		
		long height = 0;
		String str = main("get_height", "[]");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.getString("result"))){
			JSONObject result = json.getJSONObject("result");
			return result.getLong("height");
		}
		return height;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee, String Memo) throws Exception {
		String str_result = "";
		long l_amount = new Double(amount * Math.pow(10, 12)).longValue();
		/*String str = main("transfer", "{\"destinations\":[{\"amount\":"+l_amount+",\"address\":\""+to+"\"}],"
				+ "\"account_index\":0,\"subaddr_indices\":[0],\"priority\":0,\"ring_size\":7,\"get_tx_key\": true}");*/
		String str = main("transfer", "{\"destinations\":[{\"amount\":"+l_amount+",\"address\":\""+to+"\"}],\"get_tx_key\": true}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.get("result"))){
			JSONObject result = json.getJSONObject("result");
			str_result = result.getString("tx_hash");
			set_tx_notes(str_result, Memo);
		}
		return str_result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int getConforms(String txid) throws Exception {
		int confirmations = 0;
		String str = main("get_transfer_by_txid", "{\"txid\":\""+txid+"\"}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.get("result"))){
			JSONObject result = json.getJSONObject("result");
			JSONObject transfer = result.getJSONObject("transfer");
			confirmations = transfer.getInt("confirmations");
		}
		return confirmations;
	}
	
	public boolean set_tx_notes(String txid, String notes) throws Exception{
		String str = main("set_tx_notes", "{\"txids\":[\""+txid+"\"],\"notes\":[\""+notes+"\"]}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !Utils.isNull(json.get("result"))){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("127.0.0.1");
		wmsg.setPORT("28083");
		wmsg.setACCESS_KEY("rpcuser");
		wmsg.setSECRET_KEY("rpcpassword");
		wmsg.setPASSWORD("jt123698745");
		Monero wallet = (Monero) WalletUtil.createWalletByClass("com.gtwallet.wallet.core.Monero", wmsg);
		
		
		System.out.println(wallet.bestBlockNumberValue());
		System.out.println(wallet.getBalanceValue(""));
		
		/*System.out.println(wallet.unlockWallet("wallet01"));
		System.out.println(wallet.getBalanceValue(""));
		System.out.println(wallet.lockWallet(""));
		System.out.println(wallet.getBalanceValue(""));*/
		
		
		//System.out.println(wallet.listBlockTransactions("912347"));
		
		/*for(long i = wallet.bestBlockNumberValue();i>0;i--){
			List<String> li = wallet.listBlockTransactions(i+"");
			if(null != li && li.size()>0){
				System.out.println(i);
				break;
			}
		}*/
		
		System.out.println(wallet.listTransactionsValue("1103081", "1103021"));
		
		//System.out.println(wallet.transfer("", "BZqfyuxbEQbAxMdR7brKKidqZWLZ5xXy5beyoQqCYREhhDQRWG3AEnAiMxBA8RKRYjhEU49F8dRiX2ma416sDyRWF4qMdoS", 12.3, 0, "test"));
		
		System.out.println(wallet.getConforms("5e4e7581be5c848ad57488df5c6fc9e6cba4228629e78c7adba155f5d8160a9d"));
		
	}

}
