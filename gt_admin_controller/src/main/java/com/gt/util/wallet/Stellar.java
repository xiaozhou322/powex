package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;

import net.sf.json.JSONObject;

public class Stellar extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2073503851713020716L;

	public Stellar(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT()+"/"+method;
		url = MessageFormat.format(url, condition);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		

		// add reuqest header
		/*con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("GET");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());*/
		
		
		//String postdata = "{\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
		//System.out.println(postdata);
		// Send post request
		/*con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(condition);
		wr.flush();
		wr.close();*/

		int responseCode = con.getResponseCode();
		if(responseCode/10 != 20){
			return "{\"result\":null,\"error\":"+responseCode+",\"id\":1}";
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine = "";
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		result = response.toString().replaceAll(" ", "");
		return result;
	}

	@Override
	public boolean validateAddress(String address) throws Exception {
		boolean result = false;
		Server server = new Server("http://"+super.getIP()+":"+super.getPORT());
		try {
			server.accounts().account(KeyPair.fromAccountId(address));
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double balance = 0;
		String str = main("accounts/{0}", address);
		if(!Utils.isNull(str)){
			JSONObject json = JSONObject.fromObject(str);
			if(json.containsKey("balances")){
				List<JSONObject> balances = json.getJSONArray("balances");
				for(JSONObject obj : balances){
					if("native".equals(obj.get("asset_type").toString())){
						balance = obj.getDouble("balance");
						break;
					}
				}
			}
		}
		
		/*Server server = new Server("http://"+super.getIP()+":"+super.getPORT());
		AccountResponse account = server.accounts().account(KeyPair.fromAccountId(address));
		for (AccountResponse.Balance balance_ : account.getBalances()) {
			if("native".equals(balance_.getAssetType())){
				balance = new Double(balance_.getBalance());
				break;
			}
		}*/
		return balance;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		/*KeyPair pair = KeyPair.random();
		System.out.println(new String(pair.getSecretSeed()));
		System.out.println(pair.getAccountId());*/
		return null;
	}

	@Override
	public BTCInfo getTransactionsValue(String tx_hash, String ledger_index) throws Exception {
		BTCInfo btcInfo = null;
		String str = main("transactions/{0}/payments", tx_hash);
		System.out.println(str);
		if(!Utils.isNull(str)){
			JSONObject json = JSONObject.fromObject(str);
			JSONObject re = json.getJSONObject("_embedded").getJSONArray("records").getJSONObject(0);
			if("payment".equals(re.get("type").toString()) && "native".equals(re.get("asset_type").toString())){
				btcInfo = new BTCInfo();
				btcInfo.setComment("");
				btcInfo.setBlockNumber(Long.valueOf(0+ledger_index));
				btcInfo.setTxid(re.getString("transaction_hash"));
				btcInfo.setConfirmations(0);
				Timestamp timestamp = Utils.getTimestamp();
				try {
					long time = Long.parseLong(re.get("created_at").toString());
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date dt = new Date(time * 1000); 
					String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
					timestamp = Timestamp.valueOf(sDateTime);
				} catch (Exception e) {
				}
				btcInfo.setTime(timestamp);
				btcInfo.setAddress(re.getString("source_account"));
				btcInfo.setAmount(re.getDouble("amount"));
				btcInfo.setCategory("receive");
			}
		}
		return btcInfo;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		List<BTCInfo> list = new ArrayList<BTCInfo>();
		String str = main("ledgers/{0}/payments", number);
		System.out.println(str);
		if(!Utils.isNull(str)){
			JSONObject json = JSONObject.fromObject(str);
			List<JSONObject> res = json.getJSONObject("_embedded").getJSONArray("records");
			for(JSONObject re : res){
				if("payment".equals(re.get("type").toString()) && "native".equals(re.get("asset_type").toString())){
					BTCInfo btcInfo = new BTCInfo();
					btcInfo.setComment("");
					btcInfo.setBlockNumber(Long.valueOf(number));
					btcInfo.setTxid(re.getString("transaction_hash"));
					btcInfo.setConfirmations(0);
					Timestamp timestamp = Utils.getTimestamp();
					try {
						long time = Long.parseLong(re.get("created_at").toString());
						SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						java.util.Date dt = new Date(time * 1000); 
						String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
						timestamp = Timestamp.valueOf(sDateTime);
					} catch (Exception e) {
					}
					btcInfo.setTime(timestamp);
					btcInfo.setAddress(re.getString("source_account"));
					btcInfo.setAmount(re.getDouble("amount"));
					btcInfo.setCategory("receive");
					list.add(btcInfo);
				}
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
		long ledger = 0;
		String str = main("/ledgers?limit=1&order=desc", "");
		System.out.println(str);
		if(!Utils.isNull(str)){
			JSONObject json = JSONObject.fromObject(str);
			JSONObject re = json.getJSONObject("_embedded").getJSONArray("records").getJSONObject(0);
			ledger = re.getLong("sequence");
		}
		return ledger;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee, String memo) throws Exception {
		String result = "";
		//Network.usePublicNetwork();
		Network.useTestNetwork();
		Server server = new Server("http://"+super.getIP()+":"+super.getPORT());
		KeyPair source = KeyPair.fromSecretSeed(super.getPASSWORD());
		KeyPair destination = KeyPair.fromAccountId(to);
		
		server.accounts().account(destination);
		AccountResponse sourceAccount = server.accounts().account(source);
		Transaction transaction = new Transaction.Builder(sourceAccount)
		        .addOperation(new PaymentOperation.Builder(destination, new AssetTypeNative(), (new Double(amount*Math.pow(10, 6)).longValue())+"").build())
		        .addMemo(Memo.text(memo))
		        .setTimeout(1000)
		        .build();
		
		transaction.sign(source);
		System.out.println(transaction.toEnvelopeXdrBase64());
		try {
			  SubmitTransactionResponse response = server.submitTransaction(transaction);
			  System.out.println(response.isSuccess());
			  System.out.println(response.getHash());
			  if(response.isSuccess()){
				  result = response.getHash();
			  }
			} catch (Exception e) {
			  System.out.println(e.getMessage());
			}
		
		return result;
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
	
	public static void main(String[] args) throws Exception {
		
		/*KeyPair pair = KeyPair.random();
		System.out.println(new String(pair.getSecretSeed()));
		System.out.println(pair.getAccountId());*/
		//SB3VIXZYSJCGMP4XFCNUHZ63B3MCJFXQGARLQNV5YK6TVCECMCR4UB4M
		//GBEYMSUVMSE2NLGJYKKFKTP3NFYYFRFU2Y7HQWXAF2FK4SCUYWVOG5U6
		
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("192.168.0.155");
		wmsg.setPORT("8000");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("");
		//wmsg.setPASSWORD("SDY7UC2QZFOAKTFPDBOM4ZUKTIHDXGZR474ZCGC6HAZP5N24SYP7NPNL");
		wmsg.setPASSWORD("SB3VIXZYSJCGMP4XFCNUHZ63B3MCJFXQGARLQNV5YK6TVCECMCR4UB4M");
		Stellar wallet = (Stellar) WalletUtil.createWalletByClass("com.gtwallet.wallet.core.Stellar", wmsg);

		System.out.println(wallet.bestBlockNumberValue());
		
		System.out.println(wallet.getBalanceValue("GA2C5RFPE6GCKMY3US5PAB6UZLKIGSPIUKSLRB6Q723BM2OARMDUYEJ5"));//19445.1897
		
		System.out.println(wallet.getBalanceValue("GBBX4UPF7ON4W5I3EVZ5HWT5JVBPOL3QM6T7L6BDGWMOC4DLGUVU6KHI"));//9868.99995

		System.out.println(wallet.getBalanceValue("GBEYMSUVMSE2NLGJYKKFKTP3NFYYFRFU2Y7HQWXAF2FK4SCUYWVOG5U6"));//9868.99995

		
		//System.out.println(wallet.getTransactionsValue("619faf6438dfbb176737ee9b342b143151bb0265861376a2f9f1a621f0843454", ""));
		
		System.out.println(wallet.transfer("", "GBBX4UPF7ON4W5I3EVZ5HWT5JVBPOL3QM6T7L6BDGWMOC4DLGUVU6KHI", 100, 0, "abc"));
		
	}

}
