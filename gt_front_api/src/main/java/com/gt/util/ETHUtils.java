package com.gt.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gt.entity.BTCInfo;
import com.gt.entity.BTCMessage;
import com.gt.entity.ETHMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class ETHUtils  implements IWalletUtil {
	//用户名
	private  String ACCESS_KEY = null;
	//密码
	private  String SECRET_KEY = null;
	//钱包IP地址
	private  String IP = null;
	//端口
	private  String PORT = null;
	//比特币钱包密码
	private  String PASSWORD = null;
	
// 针对ERC20代币的合约地址
	private String CONTRACT = "";
	// ERC20代币的精度，默认是18，用来做计算
	private long DECIMALS = 18;
	// ERC20代币标志
	private boolean ISERC20 = false;
	// 转账方法ID，默认是0xa9059cbb
	// transferFrom的方法ID是0x23b872dd，第二个参数是到账地址，第三个参数金额
	private String TRANSFER = "";
	// 查询余额方法ID，默认是0xf8b2cb4f
	private String BALANCE = "";
	
	public static boolean validateaddress(String address){
		if (address.toLowerCase().matches("^(0x)?[0-9a-f]{40}$")==false) {
	        return false;
	    }
		return true ;
	}
	public ETHUtils(BTCMessage btcMessage) {
		this.ACCESS_KEY = btcMessage.getACCESS_KEY();
		this.SECRET_KEY = btcMessage.getSECRET_KEY();
		this.IP = btcMessage.getIP();
		this.PORT = btcMessage.getPORT();
		this.PASSWORD = btcMessage.getPASSWORD();
	}
	
	public ETHUtils(ETHMessage ethMessage) {
		this.ACCESS_KEY = ethMessage.getACCESS_KEY();
		this.SECRET_KEY = ethMessage.getSECRET_KEY();
		this.IP = ethMessage.getIP();
		this.PORT = ethMessage.getPORT();
		this.PASSWORD = ethMessage.getPASSWORD();
		this.ISERC20 = ethMessage.isISERC20();
		if (ethMessage.isISERC20()){
			this.CONTRACT = ethMessage.getCONTRACT();
			this.TRANSFER = ethMessage.getTRANSFER();
			this.BALANCE = ethMessage.getBALANCE();
			this.DECIMALS = ethMessage.getDECIMALS();
		}
	}
	
	public JSONObject getbalance(String address) throws Exception {
		String s = main("eth_getBalance", "[\""+address+"\", \"latest\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public JSONObject eth_coinbase() throws Exception {
		String s = main("eth_coinbase", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	
	public String eth_coinbaseValue() throws Exception {
		JSONObject s = eth_coinbase();
		String coinbase = "";
		if(s.containsKey("result")){
			coinbase = s.getString("result") ;
			
		}
		return coinbase;
	}
	
	public JSONObject eth_accounts() throws Exception {
		String s = main("eth_accounts", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	
	public List<String> eth_accountsValue() throws Exception {
		JSONObject s = eth_accounts();
		List<String> list = new ArrayList<String>() ;
		if(s.containsKey("result")){
			JSONArray arr = s.getJSONArray("result") ;
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getString(i)) ;
			}
		}
		return list;
	}
	public double getbalanceValue(String address) throws Exception {
		double result = 0d;
		JSONObject s = getbalance(address);
        if(s.containsKey("result")){
        	result =parseAmount(s.getString("result"));
        }
		return result;
	}
	public double getbalanceValue() throws Exception {
		double result = 0d;
		List<String> address = this.eth_accountsValue() ;
		for (String string : address) {
			result += getbalanceValue(string);
		}
		return result;
	}
	public JSONObject getNewaddress() throws Exception {
		String s = main("personal_newAccount", "[\""+PASSWORD+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String getNewaddressValue() throws Exception {
		String result = null;
		JSONObject s = getNewaddress();
        if(s.containsKey("result")){
        	result = s.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}
	
	public JSONObject eth_getTransactionByHash(String hash) throws Exception {
		String s = main("eth_getTransactionByHash", "[\""+hash+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		return json;
	}
	public BTCInfo eth_getTransactionByHashValue(String hash) throws Exception {
		JSONObject jsonObject = eth_getTransactionByHash(hash) ;
		if(jsonObject.containsKey("result")){
			JSONObject item = jsonObject.getJSONObject("result") ;
			if(item.containsKey("hash") == false ){
				return null ;
			}
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			
			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
			info.setBlockNumber(Long.parseLong(item.getString("blockNumber").substring(2),16)) ;
			info.setConfirmations((int) (eth_blockNumberValue()-Long.parseLong(item.getString("blockNumber").substring(2),16))) ;
			return info ;
		}
		return null ;
	}
	//区块高度
	public JSONObject eth_blockNumber() throws Exception {
		String s = main("eth_blockNumber", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	public long eth_blockNumberValue() throws Exception {
		JSONObject jsonObject = eth_blockNumber() ;
		int count = 0 ;
		if(jsonObject.containsKey("result")){
			count = Integer.parseInt(jsonObject.getString("result").substring(2),16) ;
			
			BigInteger valD = new BigInteger(jsonObject.getString("result").substring(2),16) ;
			return Long.parseLong(valD.toString(10)) ;
			
		}
		return count ;
	}
	
	//区块交易数量
	public JSONObject eth_getBlockTransactionCountByNumber(long id) throws Exception {
		String s = main("eth_getBlockTransactionCountByNumber", "[\"0x"+Long.toHexString(id)+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	public long eth_getBlockTransactionCountByNumberValue(long id) throws Exception {
		JSONObject jsonObject = eth_getBlockTransactionCountByNumber(id) ;
		long count = 0 ;
		if(jsonObject.containsKey("result")){
			count = Long.parseLong(jsonObject.getString("result").substring(2),16) ;
		}
		return count ;
	}
	
	
	//区块交易记录
	public JSONObject eth_getTransactionReceipt(String hash) throws Exception {
		String s = main("eth_getTransactionReceipt", "[\""+hash+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	public BTCInfo eth_getTransactionReceiptValue(String hash) throws Exception {
		JSONObject json = eth_getTransactionReceipt(hash); 
		System.out.println(json.toString());
		if(json.containsKey("result")){
			JSONObject item = json.getJSONObject("result") ;
			if(item.containsKey("hash") == false ){
				return null ;
			}
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			
			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
			return info;
		}
		return null ;
	}
	
	public boolean eth_getTransactionHasLogs(String hash) throws Exception {
		JSONObject json = eth_getTransactionReceipt(hash); 
		//System.out.println(json.toString());
		if(json.containsKey("result")){
			JSONObject item = json.getJSONObject("result") ;
			if(item.containsKey("blockHash") == false ){
				return false ;
			}
			String txhash = item.getString("transactionHash") ;
			String bkhash = item.getString("blockHash") ;
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String logs = item.getString("logs") ;
			JSONArray jlogs = JSONArray.fromObject(logs);
			if(jlogs!=null && jlogs.size()>0){
				return true;
			}
		}
		return false ;
	}
	
	//区块交易记录
	public JSONObject getTransactionByBlockNumberAndIndex(long number,int index) throws Exception {
		String s = main("eth_getTransactionByBlockNumberAndIndex", "[\"0x"+Long.toHexString(number)+"\",\"0x"+Integer.toHexString(index)+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	public BTCInfo getTransactionByBlockNumberAndIndexValue(long number,int index) throws Exception {
		BTCInfo btcInfo = null ;
		//coinbase地址用来存放邮费，如果邮费少于0.1则不做处理
		String coinbase = this.eth_coinbaseValue().toLowerCase();
		JSONObject jsonObject = getTransactionByBlockNumberAndIndex(number,index) ;
		if(jsonObject.containsKey("result")){
			JSONObject item = jsonObject.getJSONObject("result") ;
			String from = item.getString("from") ;
			//如果是主地址的转账，不做入账处理
			if(from.toLowerCase().equals(coinbase)){
				return btcInfo ;
			}
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			String input = item.getString("input") ;
			//System.out.println(input);
			BTCInfo info = new BTCInfo();
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
			info.setComment("");
			//检测是否合约交易，不是合约按照正常转账处理
			//to地址为空表示为创造智能合约的交易
			//没有事件日志的交易都是正常转账，不是代币转账
			if (input.equals("0x") || to.equals("null") || input.length()<70 || !eth_getTransactionHasLogs(item.getString("hash"))){
				info.setAddress(to);
				info.setAmount(parseAmount(value));
			}else{
				//如果是合约交易to是存储合约地址，转账地址和金额在input数据中，需要做解码操作
				//把合约地址存放到comment中对接合约代币，如果是非转币的合约操作不做分析处理
				String methodid = input.substring(0, 10);
				//System.out.println(methodid);
				//如果有提交input数据，则需要检查是否存在事件日志，否则就是伪造代币转账
				//System.out.println(input);
				if (methodid.equals("0xa9059cbb")){
					//只读取符合ERC20规范的转账处理，transfer(address _to, uint256 _value)
					info.setComment(to);
					String addr = "0x" + input.substring(34,74);
					String amount = input.substring(74);
					info.setAddress(addr.toLowerCase());
					info.setAmount(parseAmount(amount));
				}else if (methodid.equals("0x23b872dd")){
					//只读取符合ERC20规范的转账处理，transferFrom(address _from, address _to, uint256 _value)
					info.setComment(to);
					String addr = "0x" + input.substring(98,138);
					String amount = input.substring(138);
					info.setAddress(addr.toLowerCase());
					info.setAmount(parseAmount(amount));
				}else{
					//其他方法不处理
					info.setAddress(to);
					info.setAmount(parseAmount(value));
				}
				
			}
			return info ;
		
		}
		return btcInfo ;
	}
	
	public List<BTCInfo> listtransactionsValue(long number) throws Exception {
		long count = eth_getBlockTransactionCountByNumberValue(number) ;
		List<BTCInfo> all = new ArrayList();
		for (int i = 0; i < count; i++) {
			BTCInfo info = getTransactionByBlockNumberAndIndexValue(number, i) ;
			all.add(info) ;
		}
        return all;
	}
	
	public boolean lockAccount(String account)throws Exception{
		
		try {
			String s = main("personal_lockAccount", "["+
  "\""+account+"\""+
"]");
			JSONObject json = JSONObject.fromObject(s); 
			return json.getBoolean("result");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;
	
	}
	public boolean walletpassphrase(String account)throws Exception{
		
		try {
			String s = main("personal_unlockAccount", "["+
					"\""+account+"\","+"\""+ PASSWORD+"\""+
					"]");
			JSONObject json = JSONObject.fromObject(s); 
			return json.getBoolean("result");
		} catch (Exception e) {
		}
		return false ;
		
	}
	
	public JSONObject eth_sendTransaction(String from,String to,double amount,long gas ) throws Exception {
		walletpassphrase(from) ;
		
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		String gasprice = json.getString("result");
		
		s = main("eth_sendTransaction", "[{"+
 " \"from\": \""+from+"\","+
  "\"to\": \""+to+"\","+
 " \"gas\": \"0x"+Long.toHexString(gas)+"\","+
  "\"gasPrice\": \""+gasprice+"\","+
 " \"value\": \""+parseAmountHex(amount)+"\" "+
"}]");
		
		json = JSONObject.fromObject(s); 
		System.out.println(json);
		lockAccount(from) ;
		return json;
	
	}
	
	
	public String sendtoaddressValue(String from,String to,double amount ,long gas) throws Exception {
		String result = "";
		try {
			JSONObject s = eth_sendTransaction( from, to, amount ,gas);
			//System.out.println(s);
			if(s.containsKey("result")){
				if(!s.get("result").toString().equals("null")){
					result = s.get("result").toString();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public long getGasPrice(String txJson)throws Exception {
		long gaspriced =0;
		String s = main("eth_estimateGas",  txJson);
		JSONObject json = JSONObject.fromObject(s); 
		String gasprice = json.getString("result");
		gaspriced =parseGas(gasprice);
		return gaspriced ;
	}
	
	public long getContractGasPrice(String from,String contract,String to,double amount)throws Exception {
		long gaspriced =0;
		String data = gendata(to,amount);
		String txJson="[{"+
				 " \"from\": \""+from+"\","+
				  "\"to\": \""+to+"\","+
				 "\"data\": \""+data+"\""+
				"}]";
		gaspriced =getGasPrice(txJson);
		return gaspriced ;
	}
	
	/*专门出来代币交易的方法传参*/
	public JSONObject eth_sendTransaction(String from,String to,String data ) throws Exception {
		walletpassphrase(from) ;
		
//		long gasPrice =getGasPrice("[{"+
//				 " \"from\": \""+from+"\","+
//				  "\"to\": \""+to+"\","+
//				 "\"data\": \""+data+"\""+
//				"}]");
//		System.out.println("gasPrice:"+gasPrice);
//		Double gasfee = fee(gasPrice);
//		System.out.println("gasfee:"+gasfee);
		
		String s = main("eth_sendTransaction", "[{"+
 " \"from\": \""+from+"\","+
  "\"to\": \""+to+"\","+
 "\"data\": \""+data+"\""+
"}]");
		System.out.println(s);
		JSONObject json = JSONObject.fromObject(s); 
		//System.out.println(json);
		lockAccount(from) ;
		return json;
	
	}
	
	public JSONObject eth_call(String to,String data ) throws Exception {

		String s = main("eth_call", "[{"+
  "\"to\": \""+to+"\","+
 "\"data\": \""+data+"\""+
"},\"latest\"]");
		
		JSONObject json = JSONObject.fromObject(s); 
		System.out.println(json);
		return json;
	
	}
	
	//代币发送方法
	public String sendtoaddressCoin(String from,String contract,String to,double amount ) throws Exception {
		String result = "";
		try {
			String data = gendata(to,amount);
			JSONObject s = eth_sendTransaction( from, contract, data );
			System.out.println(s);
			if(s.containsKey("result")){
				if(!s.get("result").toString().equals("null")){
					result = s.get("result").toString();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
		
	public double getbalanceValue(String contract,String to) throws Exception {
		double result = 0d;
		String data = gendata(to);
		JSONObject s = eth_call( contract, data );
		System.out.println(s);
        if(s.containsKey("result")){
        	result =parseAmount(s.getString("result"));
        }
		return result;
	}
	
	public double getcoinbalanceValue(String contract) throws Exception {
		double result = 0d;
		List<String> address = this.eth_accountsValue() ;
		for (String string : address) {
			result += getbalanceValue(contract,string);
		}
		return result;
	}
	
	public String gendata(String to,double amount){
		String data = "";
		String addr = "";
		String invalue = "";
		if (to.substring(0,2).equals("0x")){
			addr=to.substring(2);
		}else{
			addr=to;
		}
		
		addr= Strings.zeros(64-addr.length()) + addr;
		BigDecimal valD = new BigDecimal(amount*Math.pow(10, DECIMALS)) ;
		BigInteger bi = valD.toBigInteger() ;
		invalue = Numeric.toHexStringNoPrefixZeroPadded(bi, 64);
		
		data = "0xa9059cbb" +addr + invalue;
		return data;
		
	}
	
	public String gendata(String to){
		String data = "";
		String addr = "";
		if (to.substring(0,2).equals("0x")){
			addr=to.substring(2);
		}else{
			addr=to;
		}
		
		addr= Strings.zeros(64-addr.length()) + addr;
		data = "0x70a08231" +addr ;
		return data;
		
	}
	
	//	'{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x407d73d8a49eeb85d32cf465507dd71d507100c1", "latest"],"id":1}'
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ ACCESS_KEY
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String url = "http://"+IP+":"+PORT;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());

		String postdata = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
		System.out.println(postdata);
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
	
	
	public String parseAmountHex(double amount){
		BigDecimal valD = new BigDecimal(amount*Math.pow(10, DECIMALS)) ;
		
		BigInteger val = valD.toBigInteger() ;
		System.out.println(val);
		return "0x"+val.toString(16) ;
	}
	
	public double parseAmount(String hexval){
		String val ="0";
		if (hexval.length()>2){
			val = hexval.substring(2) ;
			if(val.equals("")){
				val ="0";
			}
		}
		BigInteger intVal = new BigInteger(val,16) ;
		
		return intVal.doubleValue()/Math.pow(10,DECIMALS ) ;
	}
	
	public long parseGas(String hexval){
		String val = hexval.substring(2) ;
		BigInteger intVal = new BigInteger(val,16) ;
		
		return intVal.longValue() ;
	}
	
	public double fee(long gas) throws Exception {
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		Long gasprice = Long.parseLong( json.getString("result").substring(2),16);
		return gasprice*gas/Math.pow(10, 18) ;
	}
	public long EXfee(double ffee) throws Exception {
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		Long gasprice = Long.parseLong( json.getString("result").substring(2),16);
		return ((long)(Math.pow(10, 18)*ffee))/gasprice ;
	}
	public void sendMain(String mainAddr) throws Exception {
		List<String> address = this.eth_accountsValue() ;
		long gas = 50000;
		for (String string : address) {
			if(mainAddr.toLowerCase().equals(string) == false ){
				try {
					double fee= fee(gas) ;
					double balance = getbalanceValue(string);
					System.out.println(string+":"+balance);
					if(balance>fee){
						String tx = sendtoaddressValue(string, mainAddr, balance-fee,gas) ;
						System.out.println(tx);
					}
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void sendMainCoin(String mainAddr,double mintran) throws Exception {
		
		//coinbase地址用来存放邮费，如果邮费少于0.1则不做处理
		String coinbase = this.eth_coinbaseValue();
		double feecount = getbalanceValue(coinbase);
		if (feecount<=0.1){
			return ;
		}
		
		//获取所有以太坊地址
		List<String> address = this.eth_accountsValue() ;
		
		long gas = 40000;
		for (String string : address) {
			if(mainAddr.equals(string) == false ){
				try {
					//获取代币数量
					double coinamount = getbalanceValue(CONTRACT,string);
					//计算需要花费的燃料值
					gas=getContractGasPrice(string,CONTRACT, mainAddr, coinamount);
					
					double fee= fee(gas) ;
					double balance = getbalanceValue(string);
					
					//如果代币帐号的以太不足以支付邮费，则需要提前充值邮费
					//代币数量少于最小提现额度不做转移处理
					if (coinamount>=mintran){
						System.out.println(coinamount);
						if(balance>=fee){
							String tx = sendtoaddressCoin(string,CONTRACT, mainAddr, coinamount) ;
							System.out.println(tx);
						}else{
							//如果邮费不足，则要从主账号转入足额邮费，gas值固定40000
							String tx = sendtoaddressValue(coinbase,string,fee,40000) ;
							System.out.println(tx);
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static void main(String args[]) throws Exception{
		ETHMessage ethMessage = new ETHMessage() ;
		ethMessage.setIP("127.0.0.1") ;
		ethMessage.setPORT("8545") ;
		ethMessage.setPASSWORD("password") ;
		ethMessage.setISERC20(true);
		ethMessage.setDECIMALS(4);
		ETHUtils ethUtils = new ETHUtils(ethMessage) ;

	}
	
}