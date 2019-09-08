package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SiaCoin extends WalletUtil {

	public SiaCoin(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	private String get(String method,String condition) throws Exception {
		String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT() + "/" + method;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// add reuqest header
		//con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("GET");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("User-Agent", "Sia-Agent");
		
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
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT() + "/" + method;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String userpass = super.getACCESS_KEY() + ":" + super.getSECRET_KEY();
		String basicAuth = "Basic "
				+ DatatypeConverter.printBase64Binary(userpass.getBytes());
		
		// add reuqest header
		//con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("Authorization", basicAuth);
		con.setRequestProperty("User-Agent", "Sia-Agent");
		
		//String postdata = "{\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
		//System.out.println(postdata);
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
	
	@Override
	public boolean validateAddress(String address) throws Exception {
		boolean result = false;
		String str = get("wallet/verify/address/" + address, "");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			result = json.getBoolean("valid");
		}
		return result;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double result = 0;
		String str = get("wallet", "");
		JSONObject json = JSONObject.fromObject(str);
		//System.out.println(json);
		if(!json.containsKey("error")){
			result = new BigDecimal(json.getString("confirmedsiacoinbalance")).divide(BigDecimal.valueOf(Math.pow(10, 24))).doubleValue();
		}
		return result;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String result = "";
		String str = get("wallet/address", "");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			result = json.getString("address");
		}
		return result;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid, String address) throws Exception {
		String str = get("wallet/transaction/"+txid, "");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		return null;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		List<BTCInfo> infos = null;
		String str = get("wallet/transactions?startheight="+number+"&endheight="+number, "");
		JSONObject json = JSONObject.fromObject(str);
		//System.out.println(json);
		if(!json.containsKey("error")){
			if(json.containsKey("confirmedtransactions") && json.get("confirmedtransactions") instanceof JSONArray){
				infos = new ArrayList<BTCInfo>();
				List<JSONObject> confirmedtransactions = json.getJSONArray("confirmedtransactions");
				for(JSONObject confirmedtransaction : confirmedtransactions){
					String transactionid = confirmedtransaction.getString("transactionid");
					long confirmationheight = confirmedtransaction.getLong("confirmationheight");
					long confirmationtimestamp = confirmedtransaction.getLong("confirmationtimestamp");
					Timestamp time = null;
					try {
						SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						java.util.Date dt = new Date(confirmationtimestamp * 1000); 
						String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
						time = Timestamp.valueOf(sDateTime);
					} catch (Exception e) {
						time = Utils.getTimestamp();
					}
					List<JSONObject> outputs = confirmedtransaction.getJSONArray("outputs");
					for(JSONObject output : outputs){
						boolean walletaddress = output.getBoolean("walletaddress");
						if(walletaddress){
							BTCInfo info = new BTCInfo();
							String fundtype = output.getString("fundtype");
							String relatedaddress = output.getString("relatedaddress");
							String value = output.getString("value");
							if("siacoin output".equals(fundtype)){
								info.setAccount("");
								info.setAddress(relatedaddress);
								info.setAmount(new BigDecimal(value).divide(BigDecimal.valueOf(Math.pow(10, 24))).doubleValue());
								info.setBlockNumber(confirmationheight);
								info.setCategory("");
								info.setComment("");
								info.setConfirmations(0);
								info.setContract("");
								info.setTime(time);
								info.setTxid(transactionid);
							}else{
								
							}
							infos.add(info);
						}
					}
				}
			}
			/*if(json.containsKey("unconfirmedtransactions") && json.get("unconfirmedtransactions") instanceof JSONArray){
				List<JSONObject> unconfirmedtransactions = json.getJSONArray("unconfirmedtransactions");
				System.out.println("unconfirmedtransactions-->"+unconfirmedtransactions);
			}*/
		}
		return infos;
	}

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		String str = post("wallet/unlock","encryptionpassword=" + super.getPASSWORD());
		return false;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		String str = post("wallet/lock","");
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
		long result = 0;
		String str = get("consensus", "");
		JSONObject json = JSONObject.fromObject(str);
		//System.out.println(json);
		if(!json.containsKey("error") && json.containsKey("height")){
			String currentblock = json.getString("currentblock");
			JSONObject block = getblock(currentblock);
			if(null != block){
				result = block.getLong("height");
			}
		}
		return result;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee, String Memo) throws Exception {
		String result = "";
		BigInteger b_amount = BigDecimal.valueOf(amount * Math.pow(10, 24)).toBigInteger();
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("");
		}
		String str = post("wallet/siacoins", "amount="+b_amount.toString()+"&destination="+to);
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("");
		}
		JSONObject json = JSONObject.fromObject(str);
		//System.out.println(json);
		if(!json.containsKey("error")){
			List<String> transactionids = json.getJSONArray("transactionids");
			if(transactionids.size()>0){
				result = transactionids.get(transactionids.size()-1);
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
		int result = 0;
		/*String str = get("tpool/confirmed/"+txid, "");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			result = json.getBoolean("confirmed")?144:0;
		}*/
		String str = get("wallet/transaction/"+txid, "");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			long lastheight = bestBlockNumberValue();
			long confirmationheight = json.getLong("confirmationheight");
			result = Long.valueOf(lastheight - confirmationheight).intValue();
		}
		return result;
	}
	
	public JSONObject getblock(String block) throws Exception{
		JSONObject result = null;
		String str = "";
		try {
			long height = Long.valueOf(block);
			str = get("consensus/blocks?height=" + height, "");
		} catch (Exception e) {
			str = get("consensus/blocks?id=" + block, "");
		}
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			result = json;
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("192.168.0.155");
		wmsg.setPORT("9980");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("05863c27d70738fb1b271b54ab7fd5eb");
		wmsg.setPASSWORD("italics victim dads public myriad inexact vegan rustled february swagger movement umpire odometer nail sugar uptight nibs fidget abort cabin otherwise citadel pheasants foyer movement duplex ghost paper");
		SiaCoin wallet = (SiaCoin) WalletUtil.createWalletByClass("com.gt.util.wallet.SiaCoin", wmsg);
		System.out.println(wallet.bestBlockNumberValue());
		
		//System.out.println(wallet.validateAddress("e1c568abd08dba7186789aee082c927c3f4b2f718bb47abd700088555f6fdd6d687be61c3183"));
		
		System.out.println(wallet.validateAddress("29e85dbeace777a358c5bd32bd3435ae4edf291e59cb4abbb3e14980ea8dd2aac96244aeefd5"));

		//System.out.println(wallet.unlockWallet(""));
		System.out.println(wallet.listTransactionsValue("200974", ""));

		//System.out.println(wallet.getTransactionsValue("b76e0c7eaf86a51c2e81ca6f1202fd423abc44ea952e3e9c19e98345996661d1", ""));
		System.out.println(wallet.getTransactionsValue("45c86369963f2244fe0c1b3da29bdfa70b7048ede8c7b8f1f0b1e8ea0d3dd337", ""));
		System.out.println(wallet.getTransactionsValue("12265c14ea5d3d30c55c4b3831bcef566ff8fa1304d3e5eec6af968c859e8a67", ""));
		
		System.out.println(wallet.getBalanceValue(""));
		
		//System.out.println(wallet.transfer("", "846065a6abeffd1cebaefef02010353e694b67cc7c1f29c22ef020c8025b1197a983a9371848", 1, 0, ""));
		//System.out.println(wallet.transfer("", "e1c568abd08dba7186789aee082c927c3f4b2f718bb47abd700088555f6fdd6d687be61c3183", 1, 0, ""));
		
		
		
		
	}

}
