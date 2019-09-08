package com.gt.util.wallet;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gt.service.front.FrontConstantMapService;
import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Numeric;
import com.gt.util.Strings;
import com.gt.util.Utils;

public class Ethereum extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4171862388379819514L;

	public Ethereum(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateAddress(String address) throws Exception {
		if (address.toLowerCase().matches("^(0x)?[0-9a-f]{40}$")==false) {
	        return false;
	    }
		return true ;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		double result = 0d;
		String coinbase = this.eth_coinbaseValue().toLowerCase();
		//可以查询指定的地址余额，也可以查询所有地址总额
		if(null!=address && !address.equals("") && !coinbase.equals(address.toLowerCase())){
			result = getbalance(address);
		}else{
			List<String> addresses = this.eth_accountsValue() ;
			for (String string : addresses) {
				result += getbalance(string);
			}
		}
		return result;
	}
	
	private List<String> eth_accountsValue() throws Exception {
		String s = main("eth_accounts", "[]");
		JSONObject json = JSONObject.fromObject(s);
		List<String> list = new ArrayList<String>() ;
		if(json.containsKey("result")){
			JSONArray arr = json.getJSONArray("result") ;
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getString(i)) ;
			}
		}
		return list;
	}
	
	private double getbalance(String address) throws Exception {
		double result = 0d;
		String s =  null;
		JSONObject json = null;
		//如果没有指定合约且没有未设定为erc20代币
		if(!super.isISERC20() || super.getCONTRACT().equals("")){
			result =getbalanceETH(address);
		}else{
			result =getbalanceCoin(address);
		}
		return result;
	}
	
	private double getbalanceETH(String address) throws Exception {
		double result = 0d;
		String s =  null;
		JSONObject json = null;
		
		s = main("eth_getBalance", "[\""+address+"\", \"latest\"]");
		json = JSONObject.fromObject(s); 
		if(json.containsKey("result")){
        	result =parseAmountETH(json.getString("result"));
        }
		
		return result;
	}
	
	private double getbalanceCoin(String address) throws Exception {
		double result = 0d;
		String s =  null;
		JSONObject json = null;
		
		String data = gendata(address);
		json =   eth_call(super.getCONTRACT(), data);
		if(json.containsKey("result")){
        	result =parseAmount(json.getString("result"));
        }

		return result;
	}
	
	private long getTrancationCount(String address) throws Exception {
		long result = 0L;
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		
		FrontConstantMapService constantMap = (FrontConstantMapService)wac.getBean("frontConstantMapService");
		
		if (constantMap.get("eth_nonce_"+address.toLowerCase())!=null) {
			result = Long.valueOf(constantMap.get("eth_nonce_"+address.toLowerCase()).toString());
		}
		if(result==0) {
			String s =  null;
			JSONObject json = null;
			
			s = main("eth_getTransactionCount", "[\""+address+"\", \"pending\"]");
			json = JSONObject.fromObject(s); 
			if(json.containsKey("result")){
	        	result =parseGas(json.getString("result"));
	        }
			constantMap.put("eth_nonce_"+address.toLowerCase(), result+"");
		}else {
			result++;
			constantMap.put("eth_nonce_"+address.toLowerCase(), result+"");
		}
		return result;
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		String s = main("personal_newAccount", "[\""+super.getPASSWORD()+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		String result = null;
		if(json.containsKey("result")){
        	result = json.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid, String address)
			throws Exception {
		//ETH的调用参数
		//txid存放区块编号
		//address存放交易索引
		long number = Long.valueOf(txid);
		int index = Integer.valueOf(address);
		BTCInfo info = this.getTransactionByBlockNumberAndIndexValue(number, index);
		
		return info;
	}

	@Override
	public int getConforms(String txid) throws Exception {
		long lastBlockNum = this.bestBlockNumberValue();
		int confirms = 0;
		JSONObject json = this.eth_getTransactionReceipt(txid);
		if(json.containsKey("result")){
			JSONObject item = json.getJSONObject("result") ;
			//必须是成功的交易
			if(item.getString("status").equals("0x1")){
				BigInteger valD = new BigInteger(item.getString("blockNumber").substring(2),16) ;
				long txBlockNum = Long.parseLong(valD.toString(10));
				confirms = Long.valueOf(lastBlockNum-txBlockNum).intValue();
			}else{
				//confirms为负数，表示交易失败
				confirms = -1;
			}
		}
		return confirms;
	}
	
	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin)
			throws Exception {
		long block_num = Long.valueOf(number);
		long count = eth_getBlockTransactionCountByNumberValue(block_num) ;
		List<BTCInfo> all = new ArrayList();
		for (int i = 0; i < count; i++) {
			BTCInfo info = getTransactionByBlockNumberAndIndexValue(block_num, i) ;
			if(info!=null){
				all.add(info) ;
			}
		}
        return all;
		//return getBlockByNumber(Long.valueOf(number),0);
	}

	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		//ETH类不需要此方法
		return null;
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		try {
			String s = main("personal_unlockAccount", "["+
					"\""+account+"\","+"\""+ super.getPASSWORD()+"\""+
					"]");
			JSONObject json = JSONObject.fromObject(s); 
			return json.getBoolean("result");
		} catch (Exception e) {
		}
		return false ;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		if(!account.equals("")){
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
		}
		return false ;
	}

	@Override
	public boolean getContractResult(String org_txid) throws Exception {
		return this.eth_getTransactionHasLogs(org_txid);
	}

	@Override
	public double queryFee() throws Exception {
		// ETH不处理该方法
		return 0;
	}

	@Override
	public long bestBlockNumberValue() throws Exception {
		String s = main("eth_blockNumber", "[]");
		//先用etherscan的代理方法
		//String s = ethercanProxy("action=eth_blockNumber");
		JSONObject json = JSONObject.fromObject(s);
		int count = 0 ;
		if(json.containsKey("result")){
			count = Integer.parseInt(json.getString("result").substring(2),16) ;
			
			BigInteger valD = new BigInteger(json.getString("result").substring(2),16) ;
			return Long.parseLong(valD.toString(10)) ;
			
		}
		return count ;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee,String Memo)
			throws Exception {
		String result = null;
		//代币转账方法
		if(super.isISERC20() && !super.getCONTRACT().equals("")){
			result = this.sendtoaddressCoin(from, super.getCONTRACT(), to, Utils.getDouble(amount,4) ,Double.valueOf(fee).longValue());
		}else{
			result = this.sendtoaddressValue(from, to, Utils.getDouble(amount,4), Double.valueOf(fee).longValue());
		}
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		if(super.isISERC20()){
			//代币转账方法
			this.sendMainCoin(mainAddr, mintran);
		}else{
			this.sendMain(mainAddr);
		}

	}
	

	private String parseAmountHex(double amount){
		BigDecimal valD = new BigDecimal(amount*Math.pow(10,18)) ;
		
		BigInteger val = valD.toBigInteger() ;
		//System.out.println(val);
		return "0x"+val.toString(16) ;
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
		double orgval = intVal.doubleValue()/Math.pow(10,super.getDECIMALS() );
		
		return orgval ;
	}
	
	private double parseAmountETH(String hexval){
		String val ="0";
		if (hexval.length()>2){
			val = hexval.substring(2) ;
			if(val.equals("")){
				val ="0";
			}
		}
		BigInteger intVal = new BigInteger(val,16) ;
		double orgval = intVal.doubleValue()/Math.pow(10,18);
		
		return orgval ;
	}
	
	private long parseGas(String hexval){
		String val = hexval.substring(2) ;
		BigInteger intVal = new BigInteger(val,16) ;
		
		return intVal.longValue() ;
	}

	private double fee(long gas) throws Exception {
//		String s = main("eth_gasPrice", "[]");
//		JSONObject json = JSONObject.fromObject(s); 
		Long gasprice = currentGasPrice();
		return gasprice*gas/Math.pow(10, 18) ;
	}
	
	private  Long currentGasPrice() throws Exception {
		
		Long gasprice = 0L;
		
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		//检测当前以太坊的gas price是否小于10GWei，如果小于10则设置为50GWei，否则在网络gas price的基础上加2Gwei，以确保转账成功，最高不超过80Gwei
		BigInteger ethgasprice = new BigInteger(json.getString("result").substring(2),16) ;
		String price_bottom = "306dc4200";
		String price_top = "12a05f2000";
		BigInteger limitprice = new BigInteger(price_bottom,16);
		BigInteger limitprice_top = new BigInteger(price_top,16);
		
		if (ethgasprice.subtract(limitprice).longValue()<=0){
			gasprice = limitprice.longValue();
		}else{
			if (ethgasprice.subtract(limitprice_top).longValue()>=0){
				gasprice = limitprice_top.longValue();
			}else{
				gasprice = ethgasprice.longValue()+2000000000;
			}
		}
		return gasprice ;
	}
	
	
	private long getContractGasPrice(String from,String contract,String to,double amount)throws Exception {
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
	
	private long getGasPrice(String txJson)throws Exception {
		long gaspriced =0;
		String s = main("eth_estimateGas",  txJson);
		JSONObject json = JSONObject.fromObject(s); 
		String gasprice = json.getString("result");
		gaspriced =parseGas(gasprice);
		return gaspriced ;
	}
	
	private JSONObject eth_call(String to,String data ) throws Exception {

		String s = main("eth_call", "[{"+
  "\"to\": \""+to+"\","+
 "\"data\": \""+data+"\""+
"},\"latest\"]");
		
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	
	}
	
	private String gendata(String to,double amount){
		String data = "";
		String addr = "";
		String invalue = "";
		if (to.substring(0,2).equals("0x")){
			addr=to.substring(2);
		}else{
			addr=to;
		}
		
		addr= Strings.zeros(64-addr.length()) + addr;
		BigDecimal valD = new BigDecimal(amount*Math.pow(10, super.getDECIMALS())) ;
		BigInteger bi = valD.toBigInteger() ;
		invalue = Numeric.toHexStringNoPrefixZeroPadded(bi, 64);
		
		data = "0xa9059cbb" +addr + invalue;
		return data;
		
	}
	
	private String gendata(String to){
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
	
	private long eth_getBlockTransactionCountByNumberValue(long id) throws Exception {
		String s = main("eth_getBlockTransactionCountByNumber", "[\"0x"+Long.toHexString(id)+"\"]");
		//先走ETHScan的代理模式
		//String s = ethercanProxy("action=eth_getBlockTransactionCountByNumber&tag="+"0x"+Long.toHexString(id));
		JSONObject json = JSONObject.fromObject(s); 
		long count = 0 ;
		if(json.containsKey("result")){
			count = Long.parseLong(json.getString("result").substring(2),16) ;
		}
		return count ;
	}
	
	//区块交易记录
	private JSONObject eth_getTransactionReceipt(String hash) throws Exception {
		String s = main("eth_getTransactionReceipt", "[\""+hash+"\"]");
		//String  s = ethercanProxy("action=eth_getTransactionReceipt&txhash="+hash);
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	
	private BTCInfo eth_getTransactionReceiptValue(String hash) throws Exception {
		JSONObject json = eth_getTransactionReceipt(hash); 
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
		
	private boolean eth_getTransactionHasLogs(String hash) throws Exception {
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
	
	private JSONObject eth_coinbase() throws Exception {
		String s = main("eth_coinbase", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	
	private String eth_coinbaseValue() throws Exception {
		JSONObject s = eth_coinbase();
		String coinbase = "";
		if(s.containsKey("result")){
			coinbase = s.getString("result") ;
			
		}
		return coinbase;
	}
	
	private BTCInfo getTransactionByBlockNumberAndIndexValue(long number,int index) throws Exception {
		BTCInfo btcInfo = null ;
		//coinbase地址用来存放邮费，如果邮费少于0.1则不做处理
		String coinbase = this.eth_coinbaseValue().toLowerCase();
		
		String s = main("eth_getTransactionByBlockNumberAndIndex", "[\"0x"+Long.toHexString(number)+"\",\"0x"+Integer.toHexString(index)+"\"]");
		//使用etherscan的代理方法进行访问
		//String s = ethercanProxy("action=eth_getTransactionByBlockNumberAndIndex&tag=0x"+Long.toHexString(number)+"&index=0x"+Integer.toHexString(index));
		JSONObject json = JSONObject.fromObject(s); 
		if(json.containsKey("result")){
			JSONObject item = json.getJSONObject("result") ;
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
			info.setContract("");
			//System.out.println(item.getString("hash"));
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
				if (methodid.equals("0xa9059cbb") && input.length()>72){
					//只读取符合ERC20规范的转账处理，transfer(address _to, uint256 _value)
					info.setComment(to);
					info.setContract(to);
					String addr = "0x" + input.substring(34,74);
					String amount = input.substring(74);
					info.setAddress(addr.toLowerCase());
					info.setAmount(parseAmount(amount));
				}else if (methodid.equals("0x23b872dd") && input.length()>138){
					//只读取符合ERC20规范的转账处理，transferFrom(address _from, address _to, uint256 _value)
					info.setComment(to);
					info.setContract(to);
					String addr = "0x" + input.substring(98,138);
					String amount = input.substring(138);
					info.setAddress(addr.toLowerCase());
					info.setAmount(parseAmount(amount));
				}else if (methodid.equals("0x2ef140ef") && input.length()>240){
					//只读取符合ERC20规范的转账处理，send(address _to, uint256 _amount, uint256 _fee, uint256 _timestamp)
					//需要扣除手续费
					info.setComment(to);
					info.setContract(to);
					String addr = "0x" + input.substring(34,74);
					String amount = input.substring(74,138);
					String fee = input.substring(138,202);
					info.setAddress(addr.toLowerCase());
					info.setAmount(parseAmount(amount)-parseAmount(fee));
				}else{
					//其他方法不处理
					info.setAddress(to);
					info.setAmount(0);
				}
				
			}
			return info ;
		
		}
		return btcInfo ;
	}
	
	
	private List<BTCInfo> getBlockByNumber(long number,int index) throws Exception {
		//coinbase地址用来存放邮费，如果邮费少于0.1则不做处理
		//String coinbase = this.eth_coinbaseValue().toLowerCase();
		
		String coinbase = "";
		
		List<BTCInfo> all = new ArrayList();
		String s = main("eth_getBlockByNumber", "[\"0x"+Long.toHexString(number)+"\",\"true\"]");
		//使用etherscan的代理方法进行访问
		//String s = ethercanProxy("action=eth_getBlockByNumber&boolean=true&tag=0x"+Long.toHexString(number));
		JSONObject json = JSONObject.fromObject(s); 
		if(json.containsKey("result") && json.getJSONObject("result")!=null){
			JSONArray allTrans = json.getJSONObject("result").getJSONArray("transactions");
			
			for(int i=0;i<allTrans.size();i++){
				JSONObject item = allTrans.getJSONObject(i);
				String from = item.getString("from") ;
				//如果是主地址的转账，不做入账处理
				if(from.toLowerCase().equals(coinbase)){
					continue;
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
				System.out.println(item.getString("hash"));
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
					if (methodid.equals("0xa9059cbb") && input.length()>72){
						//只读取符合ERC20规范的转账处理，transfer(address _to, uint256 _value)
						info.setComment(to);
						String addr = "0x" + input.substring(34,74);
						String amount = input.substring(74);
						info.setAddress(addr.toLowerCase());
						info.setAmount(parseAmount(amount));
					}else if (methodid.equals("0x23b872dd") && input.length()>138){
						//只读取符合ERC20规范的转账处理，transferFrom(address _from, address _to, uint256 _value)
						info.setComment(to);
						String addr = "0x" + input.substring(98,138);
						String amount = input.substring(138);
						info.setAddress(addr.toLowerCase());
						info.setAmount(parseAmount(amount));
					}else if (methodid.equals("0x2ef140ef") && input.length()>240){
						//只读取符合ERC20规范的转账处理，send(address _to, uint256 _amount, uint256 _fee, uint256 _timestamp)
						//需要扣除手续费
						info.setComment(to);
						String addr = "0x" + input.substring(34,74);
						String amount = input.substring(74,138);
						String fee = input.substring(138,202);
						info.setAddress(addr.toLowerCase());
						info.setAmount(parseAmount(amount)-parseAmount(fee));
					}else{
						//其他方法不处理
						info.setAddress(to);
						info.setAmount(parseAmount(value));
					}
					
				}
				all.add(info) ;
				
			}
		}
		return all ;
	}
	
	private JSONObject eth_sendTransaction(String from,String to,double amount,long gas ) throws Exception {
		unlockWallet(from) ;
		Long newgasprice = currentGasPrice();
		long nonce = this.getTrancationCount(from);
		String s = main("eth_sendTransaction", "[{"+
				 " \"from\": \""+from+"\","+
				  "\"to\": \""+to+"\","+
				 " \"gas\": \"0x"+Long.toHexString(gas)+"\","+
				  "\"gasPrice\": \"0x"+Long.toHexString(newgasprice)+"\","+
				 " \"value\": \""+parseAmountHex(amount)+"\","+
				 " \"nonce\": \"0x"+Long.toHexString(nonce)+"\" "+
				"}]");
		
		JSONObject json = JSONObject.fromObject(s); 
		System.out.println(json);
		lockWallet(from) ;
		return json;
	
	}
	
	/*专门出来代币交易的方法传参*/
	private JSONObject eth_sendTransaction(String from,String to,String data,long gas ) throws Exception {
		unlockWallet(from) ;
		Long newgasprice = currentGasPrice();
		long nonce = this.getTrancationCount(from);
		String s = main("eth_sendTransaction", "[{"+
						 " \"from\": \""+from+"\","+
						  "\"to\": \""+to+"\","+
						  "\"gas\": \"0x"+Long.toHexString(gas)+"\","+
						  "\"gasPrice\": \"0x"+Long.toHexString(newgasprice)+"\","+
						 "\"data\": \""+data+"\","+
						 " \"nonce\": \"0x"+Long.toHexString(nonce)+"\" "+
						"}]");
		JSONObject json = JSONObject.fromObject(s); 
		//System.out.println(json);
		
		lockWallet(from) ;
		
		return json;
	
	}
	
	//代币发送方法
	private String sendtoaddressCoin(String from,String contract,String to,double amount,long gas ) throws Exception {
		String result = "";
		try {
			String data = gendata(to,amount);
			JSONObject s = eth_sendTransaction( from, contract, data,gas );
			System.out.println(s);
			if(s.containsKey("result")){
				if(!s.get("result").toString().equals("null")){
					result = s.get("result").toString();
				}
			}else if(s.containsKey("error")){
				//处理Geth自行重复提交造成的订单存在导致无法获取订单号的问题
				String msg = s.getJSONObject("error").getString("message");
				if(msg.indexOf("known transaction")>-1){
					result = msg.replace("known transaction: ", "0x");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private String sendtoaddressValue(String from,String to,double amount ,long gas) throws Exception {
		String result = "";
		try {
			JSONObject s = eth_sendTransaction( from, to, amount ,gas);
			//System.out.println(s);
			if(s.containsKey("result")){
				if(!s.get("result").toString().equals("null")){
					result = s.get("result").toString();
				}
			}else if(s.containsKey("error")){
				//处理Geth自行重复提交造成的订单存在导致无法获取订单号的问题
				String msg = s.getJSONObject("error").getString("message");
				if(msg.indexOf("known transaction")>-1){
					result = msg.replace("known transaction: ", "0x");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private void sendMain(String mainAddr) throws Exception {
		List<String> address = this.eth_accountsValue() ;
		String coinbase = this.eth_coinbaseValue().toLowerCase();
		long gas = 100000;
		for (String string : address) {
			if(!mainAddr.toLowerCase().equals(string.toLowerCase())){
				try {
					
					double feeg= fee(gas+20000) ;
					double balance = this.getbalanceETH(string);
					//超过0.1的币才进行汇总到主账号
					
					if(balance>(0.1+feeg)){
						System.out.println(string + "：ETH余额："+balance+" 所需手续费："+feeg);
						String tx = sendtoaddressValue(string, mainAddr, balance-feeg,gas) ;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void sendMainCoin(String mainAddr,double mintran) throws Exception {
		
		//coinbase地址用来存放邮费，如果邮费少于0.1则不做处理
		String coinbase = this.eth_coinbaseValue().toLowerCase();
		//coinbase的ETH余额
		double feecount = this.getbalanceETH(coinbase);
		if (feecount<=0.1){
			return ;
		}
		
		//获取所有以太坊地址
		List<String> address = this.eth_accountsValue() ;
		
		long gas = 50000;
		for (String string : address) {
			if(!mainAddr.toLowerCase().equals(string.toLowerCase()) ){
				try {
					//获取代币数量
					double coinamount = this.getbalanceCoin(string);
					
					//如果代币帐号的以太不足以支付邮费，则需要提前充值邮费
					//代币数量少于最小提现额度不做转移处理
					if (coinamount>=mintran){
						
						//计算需要花费的燃料值
						gas=getContractGasPrice(string,super.getCONTRACT(), mainAddr, coinamount);
						
						double tfee= fee(gas+50000) ;
						//获取当前地址的ETH余额，需要用ETH余额方法
						double balance = this.getbalanceETH(string);
						
						System.out.println("代币余额："+coinamount+" 最小提币数："+mintran+"ETH余额："+balance+" 所需手续费："+tfee);
						if(balance>=tfee){
							String tx = sendtoaddressCoin(string,super.getCONTRACT(), mainAddr, coinamount-0.0001,Double.valueOf(gas+50000).longValue()) ;
						}else{
							//如果邮费不足，则要从主账号转入足额邮费，gas值固定40000
							String tx = sendtoaddressValue(coinbase,string,tfee,50000) ;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ super.getACCESS_KEY()
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String url = "http://"+super.getIP()+":"+super.getPORT();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());

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

		inputLine = in.readLine();
		response.append(inputLine);
		in.close();
		result = response.toString();
		return result;
	}
	
	private String ethercanProxy(String method) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		//authenticator();
		//Thread.sleep(100);
		
		String url = "http://api.etherscan.io/api?module=proxy&apikey=GB32B38QJ7S1RN7US3PR5Y559B6KZJSY3Q&"+method;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("GET");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		
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
	
	public static double parseAmountTest(String hexval){
		String val ="0";
		if (hexval.length()>2){
			val = hexval.substring(2) ;
			if(val.equals("")){
				val ="0";
			}
		}
		BigInteger intVal = new BigInteger(val,16) ;
		double orgval = intVal.doubleValue()/Math.pow(10,18);
		
		
		return orgval ;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("127.0.0.1");
		wmsg.setPORT("18332");
		wmsg.setACCESS_KEY("RPCuser");
		wmsg.setSECRET_KEY("RPCpasswd");
		wmsg.setISERC20(false);
		wmsg.setCONTRACT("");
		wmsg.setPASSWORD("password");
		WalletUtil wallet =  WalletUtil.createWalletByClass("com.ruizton.util.wallet.Ethereum", wmsg);
		System.out.println(wallet.listTransactionsValue("5668977","0"));
		//System.out.println(wallet.getBalanceValue("0x76EBfa0cCDC1938308582020F228A7B480Aa6935"));
//		System.out.println(parseAmountTest("0x00000000000000000000000000000000000000000000000009288ec48f60d600"));
//		System.out.println(parseAmountTest("0x0000000000000000000000000000000000000000000000018b7ae65ad8dc5c00"));
//		System.out.println(parseAmountTest("0x00000000000000000000000000000000000000000000000009288ec48f60d600"));
	}

}