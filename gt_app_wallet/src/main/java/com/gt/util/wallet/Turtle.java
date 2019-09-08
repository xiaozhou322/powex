package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.gt.util.DateUtil;
import com.gt.util.Utils;
import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;

import net.sf.json.JSONObject;

public class Turtle extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -958225219367535431L;
	public Turtle(WalletMessage walletMessage) {
		super(walletMessage);
	}

	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT()+ "/json_rpc";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		//con.setRequestProperty("Authorization", basicAuth);
		
		String postdata = "{\"jsonrpc\":\"2.0\",\"password\":\""+super.getPASSWORD()+"\",\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double result = 0;
		String str = main("getBalance", "{\"address\":\""+address+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			result = json.getJSONObject("result").getDouble("availableBalance")/Math.pow(10, 2);
		}
		return result;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String result = "";
		String str = main("createAddress", "{}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			result = json.getJSONObject("result").get("address").toString();
		}
		
		/*List<String> addresses = getAddresses();
		String addr = addresses.get(new Random().nextInt(addresses.size()));
		String paymentId = Utils.getUUID()+Utils.getUUID();
		result = createIntegratedAddress(addr, paymentId);*/
		return result;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid, String address) throws Exception {
		BTCInfo info = null;
		String str = main("getTransaction", "{\"transactionHash\":\""+txid+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			JSONObject transaction = json.getJSONObject("result").getJSONObject("transaction");
			long blockIndex = transaction.getLong("blockIndex");
			long timestamp = transaction.getLong("timestamp");
			String transactionHash = transaction.getString("transactionHash");
			boolean isBase = transaction.getBoolean("isBase");
			String paymentId = transaction.getString("paymentId");
			paymentId = toStringHexTest(paymentId);
			List<JSONObject> transfers = transaction.getJSONArray("transfers");
			for(JSONObject transfer : transfers){
				String addr = transfer.getString("address");
				double amount = transfer.getLong("amount")/Math.pow(10, 2);
				int type = transfer.getInt("type");
				if(!isBase && !Utils.isNull(addr) && amount>0 && 0 == type){
					info = new BTCInfo();
					info.setAddress(addr);
					info.setAmount(amount);
					info.setBlockNumber(blockIndex);
					info.setCategory("receive");
					info.setComment(paymentId);
					info.setConfirmations(0);
					info.setContract("");
					try {
						SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						java.util.Date dt = new Date(timestamp * 1000); 
						String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
						info.setTime(Timestamp.valueOf(sDateTime));
					} catch (Exception e) {
						info.setTime(Utils.getTimestamp());
					}
					info.setTxid(transactionHash);
					break;
				}
			}
			
		}
		return info;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		List<BTCInfo> result = null;
		String str = main("getTransactions", "{\"firstBlockIndex\":"+number+",\"blockCount\":1}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			List<JSONObject> items = json.getJSONObject("result").getJSONArray("items");
			if(items.size()>0){
				result = new ArrayList<BTCInfo>();
				List<JSONObject> transactions = items.get(0).getJSONArray("transactions");
				for(JSONObject transaction : transactions){
					long blockIndex = transaction.getLong("blockIndex");
					long timestamp = transaction.getLong("timestamp");
					String transactionHash = transaction.getString("transactionHash");
					boolean isBase = transaction.getBoolean("isBase");
					String paymentId = transaction.getString("paymentId");
					paymentId = toStringHexTest(paymentId);
					List<JSONObject> transfers = transaction.getJSONArray("transfers");
					for(JSONObject transfer : transfers){
						String addr = transfer.getString("address");
						double amount = transfer.getLong("amount")/Math.pow(10, 2);
						int type = transfer.getInt("type");
						if(!isBase && !Utils.isNull(addr) && amount>0 && 0 == type){
							BTCInfo info = new BTCInfo();
							info.setAddress(addr);
							info.setAmount(amount);
							info.setBlockNumber(blockIndex);
							info.setCategory("receive");
							info.setComment(paymentId);
							info.setConfirmations(0);
							info.setContract("");
							try {
								SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								java.util.Date dt = new Date(timestamp * 1000); 
								String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
								info.setTime(Timestamp.valueOf(sDateTime));
							} catch (Exception e) {
								info.setTime(Utils.getTimestamp());
							}
							info.setTxid(transactionHash);
							result.add(info);
							break;
						}
					}
				}
			}
		}
		
		/*result = new ArrayList<BTCInfo>();
		List<String> alltxids = listBlockTransactions(number);
		for(int i = 0; i < alltxids.size(); i++){
			BTCInfo btcinfo = getTransactionsValue(alltxids.get(i),"");
			result.add(btcinfo);
		}
        Collections.reverse(result);*/
		return result;
	}

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		List<String> result = null;
		String str = main("getTransactionHashes", "{\"firstBlockIndex\":"+blockid+",\"blockCount\":1}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			List<JSONObject> items = json.getJSONObject("result").getJSONArray("items");
			if(items.size()>0){
				result = items.get(0).getJSONArray("transactionHashes");
			}
		}
		return result;
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
		long result = 0;
		String str = main("getStatus", "{}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			result = json.getJSONObject("result").getLong("blockCount");
		}
		return result;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee, String Memo) throws Exception {
		String result = "";
		Long amount_l = Double.valueOf(amount*Math.pow(10, 2)).longValue();
		Long fee_l = Double.valueOf(fee*Math.pow(10, 2)).longValue();
		String addr = getNewaddressValue("");
		String str = "";
		if(!Utils.isNull(Memo)){
			str = main("sendTransaction", "{"
					+ "\"transfers\":[{\"address\":\""+to+"\",\"amount\":"+amount_l+"}],"
						+ "\"fee\":"+fee_l+","
						+ "\"anonymity\":3," //隐私级别
						+ "\"changeAddress\":\""+addr+"\"," //如果钱包仅包含1个地址，changeAddress则可以保留为空并且更改将发送到此地址。在其他情况下，changeAddress字段是必填字段，必须包含地址。
						+ "\"paymentId\":\""+padLeft(str2HexStr(Memo), 64)+"\"}");//64char hex
		}else{
			str = main("sendTransaction", "{"
					+ "\"transfers\":[{\"address\":\""+to+"\",\"amount\":"+amount_l+"}],"
					+ "\"fee\":"+fee_l+","
					+ "\"anonymity\":3," //隐私级别
					+ "\"changeAddress\":\""+addr+"\"}");
		}
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			result = json.getJSONObject("result").getString("transactionHash");
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

	/**
	 * 组合地址
	 * @param address
	 * @param paymentId 64char hex
	 * @return
	 * @throws Exception
	 */
	public String createIntegratedAddress(String address, String paymentId) throws Exception {
		String result = "";
		String str = main("createIntegratedAddress", "{\"paymentId\":\""+paymentId+"\",\"address\":\""+address+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			result = json.getJSONObject("result").getString("integratedAddress");
		}
		return result;
	}
	
	public List<String> getAddresses() throws Exception {
		List<String> result = null;
		String str = main("getAddresses", "{}");
		JSONObject json = JSONObject.fromObject(str);
		if(json.containsKey("result") && !"null".equals(json.get("result").toString())){
			result = json.getJSONObject("result").getJSONArray("addresses");
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
			s= new String(baKeyword,"UTF-8").trim();// UTF-16le:Not  
		} catch (Exception e1) {  
			e1.printStackTrace();  
		}  
		return s;  
	}  
	
	public static String padLeft(String s, int length){
	    byte[] bs = new byte[length];
	    byte[] ss = s.getBytes();
	    Arrays.fill(bs, (byte) (48 & 0xff));
	    System.arraycopy(ss, 0, bs,length - ss.length, ss.length);
	    return new String(bs);
	}
	public static void main(String[] args) throws Exception{
		
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("192.168.0.196");
		wmsg.setPORT("8070");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("");
		wmsg.setPASSWORD("123456789");
		Turtle wallet = (Turtle) WalletUtil.createWalletByClass("com.gtwallet.wallet.core.Turtle", wmsg);
		System.out.println(wallet.bestBlockNumberValue());
		
		//System.out.println(wallet.getNewaddressValue(""));
		System.out.println(wallet.getBalanceValue(""));
		System.out.println(wallet.getBalanceValue("TRTLv1C7b8WCNgpynkZHrjZ9smew65AksT9ozRc3FFYge9XQoXfzrNA33xZB2iM6kaU13gDdCxMdi7MaMAHV6DjMiXxdyuiZsCq"));
		System.out.println(wallet.getBalanceValue("TRTLuxRLfC75jWvvLbgZzdcB24Zr7JC4tYAkS6pt5buk6AQ9YGx1Wzi33xZB2iM6kaU13gDdCxMdi7MaMAHV6DjMiXxdyzX66wW"));
		
		System.out.println(wallet.getTransactionsValue("a59c7acb651b220e7c7e4f9780751a749854b2aa1b5be55383d0c0ab865c6f5a", ""));
		
		System.out.println(wallet.listBlockTransactions("1135674"));
		
		System.out.println(wallet.listTransactionsValue("1135674", ""));
		
		//System.out.println(wallet.transfer("", "TRTLuxRLfC75jWvvLbgZzdcB24Zr7JC4tYAkS6pt5buk6AQ9YGx1Wzi33xZB2iM6kaU13gDdCxMdi7MaMAHV6DjMiXxdyzX66wW", 4, 0.1, "364236"));
		System.out.println(wallet.getTransactionsValue("5e02219bf5e22ab4ffa280a13da588876ba1a4a09b67d98cd7f2fd5d583df982", ""));

		//System.out.println(padLeft(str2HexStr("abc"),64));
		System.out.println(wallet.getAddresses());
		
	}
	
}
