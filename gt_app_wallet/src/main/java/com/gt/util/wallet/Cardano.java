package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.gt.util.DateUtil;
import com.gt.util.Utils;
import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;

import net.sf.json.JSONObject;

public class Cardano extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8557494734756162718L;

	public Cardano(WalletMessage walletMessage) {
		super(walletMessage);
	}
	
	class MyX509TrustManager implements X509TrustManager {
        private X509TrustManager sunJSSEX509TrustManager;

        MyX509TrustManager() throws Exception {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("C:/Users/Dell/Desktop/ca.jks"), "123456".toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
            tmf.init(ks);
            TrustManager tms[] = tmf.getTrustManagers();

            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof X509TrustManager) {
                    sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                    return;
                }
            }
            throw new Exception("Couldn't initialize");
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
            } catch (CertificateException excep) {
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException excep) {
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return sunJSSEX509TrustManager.getAcceptedIssuers();
        }
    }

	private SSLSocketFactory sslFactory = null;
	
	private synchronized SSLSocketFactory getSSLFactory() throws Exception {
        if (sslFactory == null) {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            
            TrustManager[] tm = {new MyX509TrustManager()};
            KeyStore truststore = KeyStore.getInstance("JKS");
            truststore.load(new FileInputStream("C:/Users/Dell/Desktop/cert.jks"), "123456".toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(truststore, "123456".toCharArray());

            sslContext.init(kmf.getKeyManagers(), tm, new java.security.SecureRandom());
            sslFactory = sslContext.getSocketFactory();
        }
        return sslFactory;
    }

	
	private String get(String method) throws Exception {
        String result = "";

		String url = "https://"+super.getIP()+":"+super.getPORT()+"/api/v1/"+method;
		URL obj = new URL(url);
		
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(this.getSSLFactory());
        con.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
	        
		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept","application/json;charset=utf-8");
		con.setRequestMethod("GET");
		
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
	
	private String post(String method,String condition) throws Exception {
        String result = "";

		String url = "https://"+super.getIP()+":"+super.getPORT()+"/api/v1/"+method;
		URL obj = new URL(url);
		
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setSSLSocketFactory(this.getSSLFactory());
        con.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
	        
		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept","application/json;charset=utf-8");
		con.setRequestMethod("POST");
		
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

	@Override
	public boolean validateAddress(String address) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBalanceValue(String accountIndex) throws Exception {
		double amount = 0;
		String str = "";
		if(!Utils.isNull(super.getSECRET_KEY())){
			str = get("wallets/"+super.getACCESS_KEY()+"/accounts/"+super.getSECRET_KEY());
			System.out.println(str);
			JSONObject json = JSONObject.fromObject(str);
			if(json.containsKey("status") && "success".equals(json.get("status").toString())){
				JSONObject data = json.getJSONObject("data");
				amount = data.getDouble("amount") / Math.pow(10, 6);
			}
		}else{
			str = get("wallets/"+super.getACCESS_KEY()+"/accounts/");
			System.out.println(str);
			JSONObject json = JSONObject.fromObject(str);
			if(json.containsKey("status") && "success".equals(json.get("status").toString())){
				List<JSONObject> data = json.getJSONArray("data");
				for(JSONObject account : data){
					double amount_ = account.getDouble("amount");
					amount +=  amount_ / Math.pow(10, 6);
				}
			}
		}
		return amount;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String accountIndex = "";
		if(!Utils.isNull(super.getSECRET_KEY())){
			accountIndex = super.getSECRET_KEY();
		}else{
			List<String> list = getAccountsIndex();
			accountIndex = list.get(0);
		}
		String str = post("addresses", "{\"accountIndex\":"+accountIndex+","
				+ "\"walletId\":\""+super.getACCESS_KEY()+"\",\"spendingPassword\":\""+super.getPASSWORD()+"\"}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("status") && "success".equals(json.get("status").toString())){
			JSONObject data = json.getJSONObject("data");
			return data.getString("id");
		}
		return "";
	}

	@Override
	public BTCInfo getTransactionsValue(String txid, String address) throws Exception {
		String str = "";
		if(!Utils.isNull(address)){
			str = get("transactions?wallet_id="+super.getACCESS_KEY()+"&id="+txid+"&address="+address);
		}else{
			str = get("transactions?wallet_id="+super.getACCESS_KEY()+"&id="+txid);
		}
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("status") && "success".equals(json.get("status").toString())){
			List<JSONObject> data = json.getJSONArray("data");
			if(data.size()>0){
				JSONObject transaction = data.get(0);
				String id = transaction.getString("id");
				int confirmations = transaction.getInt("confirmations");
				double amount = transaction.getDouble("amount") / Math.pow(10, 6);
				String creationTime = transaction.getString("creationTime");
				
				if(Utils.isNull(address)){
					List<JSONObject> outputs = transaction.getJSONArray("outputs");
					for(JSONObject output : outputs){
						String address_ = output.getString("address");
						double amount_ = output.getDouble("amount") / Math.pow(10, 6);
						if(amount == amount_){
							if(checkAddress(address_)){
								address = address_;
							}
						}
					}
					
				}
				if(Utils.isNull(address)){
					return null;
				}
				BTCInfo info = new BTCInfo();
				try{
					String formate = "yyyy-MM-dd'T'HH:mm:ss";
					SimpleDateFormat formater = new SimpleDateFormat(formate);
					Date date = formater.parse(creationTime);
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sDateTime = sdf.format(date);
					info.setTime(Timestamp.valueOf(sDateTime));
					info.setBlockNumber(date.getTime());//存放时间戳
				}catch(Exception e){
					info.setTime(Utils.getTimestamp());
					info.setBlockNumber(Utils.getTimestamp().getTime());
				}
				info.setAddress(address);
				info.setAmount(amount);
				info.setCategory("receive");
				info.setComment("");
				info.setConfirmations(confirmations);
				info.setContract("");
				info.setTxid(id);
				return info;
			}
		}
		
		return null;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		List<BTCInfo> list = new ArrayList<BTCInfo>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dateTime = df.format(new Date(Long.valueOf(number)));
		String str = get("transactions?wallet_id="+super.getACCESS_KEY()+"&created_at=GT[" + dateTime + "]");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("status") && "success".equals(json.get("status").toString())){
			List<JSONObject> data = json.getJSONArray("data");
			for(JSONObject transaction : data){
				String id = transaction.getString("id");
				int confirmations = transaction.getInt("confirmations");
				double amount = transaction.getDouble("amount") / Math.pow(10, 6);
				String creationTime = transaction.getString("creationTime");
				
				String address = "";
				List<JSONObject> outputs = transaction.getJSONArray("outputs");
				for(JSONObject output : outputs){
					String address_ = output.getString("address");
					double amount_ = output.getDouble("amount") / Math.pow(10, 6);
					if(amount == amount_){
						if(checkAddress(address_)){
							address = address_;
						}
					}
				}
				if(Utils.isNull(address)){
					continue;
				}
				BTCInfo info = new BTCInfo();
				try{
					String formate = "yyyy-MM-dd'T'HH:mm:ss";
					SimpleDateFormat formater = new SimpleDateFormat(formate);
					Date date = formater.parse(creationTime);
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sDateTime = sdf.format(date);
					info.setTime(Timestamp.valueOf(sDateTime));
					info.setBlockNumber(date.getTime());//存放时间戳
				}catch(Exception e){
					info.setTime(Utils.getTimestamp());
					info.setBlockNumber(Utils.getTimestamp().getTime());
				}
				info.setAddress(address);
				info.setAmount(amount);
				info.setCategory("receive");
				info.setComment("");
				info.setConfirmations(confirmations);
				info.setContract("");
				info.setTxid(id);
				list.add(info);
			}
		}
		return list;
	}

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
		long height = 0;
		String str = get("node-info");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("status") && "success".equals(json.get("status").toString())){
			JSONObject data = json.getJSONObject("data");
			JSONObject localBlockchainHeight = data.getJSONObject("localBlockchainHeight");
			height = localBlockchainHeight.getLong("quantity");
		}
		
		return height;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee, String Memo) throws Exception {
		String accountIndex = "";
		if(!Utils.isNull(super.getSECRET_KEY())){
			accountIndex = super.getSECRET_KEY();
		}else{
			List<String> list = getAccountsIndex();
			accountIndex = list.get(0);
		}
		String destinations = "[{\"address\":\""+to+"\",\"amount\":"+new Double(amount*Math.pow(10, 6)).intValue()+"}]";
		String source = "{\"accountIndex\":"+accountIndex+",\"walletId\":\""+super.getACCESS_KEY()+"\"}";
		String str = post("transactions", "{\"groupingPolicy\":null,"
				+ "\"destinations\":"+destinations+","
				+ "\"source\":"+source+","
				+ "\"spendingPassword\":\""+super.getPASSWORD()+"\"}");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("status") && "success".equals(json.get("status").toString())){
			JSONObject data = json.getJSONObject("data");
			return data.getString("id");
		}
		return "";
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int getConforms(String txid) throws Exception {
		int confirms = 0;
		try {
			BTCInfo info = getTransactionsValue(txid, "");
			confirms = info.getConfirmations();
		} catch (Exception e) {
		}
		return confirms;
	}
	
	public List<String> getAccountsIndex() throws Exception {
		List<String> list = new ArrayList<String>();
		String str = get("wallets/"+super.getACCESS_KEY()+"/accounts");
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("status") && "success".equals(json.get("status").toString())){
			List<JSONObject> data = json.getJSONArray("data");
			for(JSONObject account : data){
				String index = account.get("index").toString();
				list.add(index);
			}
		}
		return list;
	}
	
	public boolean checkAddress(String address) throws Exception {
		String str = get("addresses/"+address);
		System.out.println(str);
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("status") && "success".equals(json.get("status").toString())){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("192.168.0.155");
		wmsg.setPORT("8090");
		wmsg.setACCESS_KEY("Ae2tdPwUPEZ6mbVmN7SbFEXLTFVv8sP7hoCkSBgHg13QRi2S4uonX7eXa5w");//wallet id
		wmsg.setSECRET_KEY("2592176319");//account index
		wmsg.setPASSWORD("3865333238373764366534633534363263653737353461353065313134623861");
		Cardano wallet = (Cardano) WalletUtil.createWalletByClass("com.gt.util.wallet.Cardano", wmsg);
		
		System.out.println(wallet.bestBlockNumberValue());

		
		System.out.println(wallet.getTransactionsValue("ba5a05b533ea09b3fa95173a846209b08d082993e3056e70d96febda9966643b", ""));
		
		
		System.out.println(wallet.listTransactionsValue(DateUtil.strToDate("2018-12-12").getTime()+"", ""));
		
		//System.out.println(wallet.transfer("", "DdzFFzCqrht6gJt8W4Xfs9XjikPmNfjVYnDsiipoexFmmrjDzkny98jsechqkYaoPDFhz39wWhboApNG9UmtNv5bSLHBmNGyD1j3kS8x", 1, 0, ""));
		
	}

}
