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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;


public class Achain extends WalletUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1004213585092502267L;

	public Achain(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateAddress(String address) throws Exception {
		// TODO Auto-generated method stub
		openWallet();
		String s = main("wallet_check_address", "[\""+ address+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		System.out.println(s);
		boolean result = false;
		if (null != json){
			if (!json.getString("result").equals(null)){
				result = json.getBoolean("result");
			}
		}
		//closeWallet();
		return result;
	}

	@Override
	public double getBalanceValue(String address) throws Exception {
		// TODO Auto-generated method stub
		double balance = 0d;
		String s = null;
		JSONObject json = null;
		
		//如果是资产余额查询
		if(!super.isISERC20()){
			s = main("blockchain_list_address_balances", "[\""+ address+"\"]");
			json = JSONObject.fromObject(s);
			if(json.containsKey("result") && !json.get("result").toString().equals("null")){
				List allResult = (List)json.get("result");
	        	Iterator it = allResult.iterator();
	        	while(it.hasNext()){
	        		JSONArray result = (JSONArray)it.next();
	        		if(result.getJSONObject(1).getJSONObject("condition").getString("asset_id").equals("0")){
	        			balance+= parseAmount(result.getJSONObject(1).getString("balance"));
	        		}
	        	}
			}
		}else{
			String contract = super.getCONTRACT();
			if (null!=contract && !contract.equals("")){
				//解锁钱包
				unlockWallet("");
				s = main("call_contract_testing", "[\""+ contract+"\",\""+address+"\",\"query_balance\",\"ACT\"]");
				System.out.println(s);
				lockWallet("");
			}
		}
		
		return balance;
	}

	@Override
	public String getNewaddressValue(String seedInfo) {
		//只生成随机数，需要套接在主地址后面
		String result = null;
		
		result = seedInfo+Utils.getUUID();
        
		return result;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid,String address) throws Exception {
		// TODO Auto-generated method stub
		String s = main("blockchain_get_transaction", "[\""+ txid+"\"]");
		BTCInfo btcinfo = null;
		JSONObject json = JSONObject.fromObject(s);
		if (null != json){
			if (!json.getString("result").equals(null)){
				JSONObject trx  = json.getJSONArray("result").getJSONObject(1).getJSONObject("trx");
				long asset_id = trx.getJSONObject("alp_inport_asset").getLong("asset_id");
				String op_type = trx.getJSONArray("operations").getJSONObject(0).getString("type");
				//留存子地址
				String alp_account =  trx.getString("alp_account");
				//asset_id 为0，并且op_type是withdraw_op_type或者deposit_op_type则为ACT交易
				if (op_type.equals("withdraw_op_type") || op_type.equals("deposit_op_type")){
					s = main("blockchain_get_pretty_transaction", "[\""+ txid+"\"]");
					JSONObject info = JSONObject.fromObject(s).getJSONObject("result");
					btcinfo = new BTCInfo();
					btcinfo.setBlockNumber(Long.valueOf(info.getString("block_num")));
					btcinfo.setTxid(info.getString("trx_id"));
					btcinfo.setConfirmations(0);
					btcinfo.setTime(dealTime(info.getString("timestamp")));
					JSONObject ledger = info.getJSONArray("ledger_entries").getJSONObject(0);
					if(null!=alp_account && !alp_account.equals("")){
						btcinfo.setAddress(alp_account);
					}else{
						btcinfo.setAddress(ledger.getString("to_account"));
					}
					btcinfo.setAmount(parseAmount(ledger.getJSONObject("amount").getString("amount")));
					//备注存放资源ID，0是ACT,需要设置为空
					if(ledger.getJSONObject("amount").getString("asset_id").equals("0")){
						btcinfo.setComment("");
						btcinfo.setContract("");
					}else{
						btcinfo.setComment(ledger.getJSONObject("amount").getString("asset_id"));
						btcinfo.setContract(ledger.getJSONObject("amount").getString("asset_id"));
					}
				}else if(op_type.equals("transaction_op_type")) {
					JSONObject event_type = trx.getJSONArray("operations").getJSONObject(2);
					//必须检测事件是否为转账已经成功
					if (event_type.getString("type").equals("event_op_type") && event_type.getJSONObject("data").getString("event_type").equals("transfer_to_success")){
						s = main("blockchain_get_pretty_contract_transaction", "[\""+ txid+"\"]");

						JSONObject info = JSONObject.fromObject(s).getJSONObject("result");
						btcinfo = new BTCInfo();
						btcinfo.setBlockNumber(Long.valueOf(info.getLong("block_num")));
						btcinfo.setTxid(info.getString("orig_trx_id"));
						btcinfo.setConfirmations(0);
						//备注存放合约地址，后续供平台核对
						btcinfo.setComment(info.getJSONObject("to_contract_ledger_entry").getString("to_account"));
						btcinfo.setContract(info.getJSONObject("to_contract_ledger_entry").getString("to_account"));
						btcinfo.setTime(dealTime(info.getString("timestamp")));
						String addrinfo = info.getJSONArray("reserved").getString(1);
						String addr = addrinfo.split("\\|")[0];
						String amount = addrinfo.split("\\|")[1];
						if(null!=alp_account && !alp_account.equals("")){
							btcinfo.setAddress(alp_account);
						}else{
							btcinfo.setAddress(addr);
						}
						btcinfo.setAmount(Double.valueOf(amount).doubleValue());
					}
				}else if(op_type.equals("call_contract_op_type")) {
					//如果是合约调用类型，则要先获取最终真实交易记录，再获取交易信息
					String ttxid = getContractTrueTxid(txid);
					return getTransactionsValue(ttxid,"");
				}else{
					s = main("blockchain_get_pretty_transaction", "[\""+ txid+"\"]");
					System.out.println("OTHER" + s);
				}
			}
		}
		return btcinfo;
	}
	
	@Override
	public int getConforms(String txid) throws Exception {
		long lastBlockNum = this.bestBlockNumberValue();
		int confirms = 0;
		String s = main("blockchain_get_transaction", "[\""+ txid+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		System.out.println(s);
		if (null != json){
			if (!json.getString("result").equals(null)){
				JSONObject localtion  = json.getJSONArray("result").getJSONObject(1).getJSONObject("chain_location");
				long txblockNum = Long.valueOf(localtion.getString("block_num"));
				confirms = Long.valueOf(lastBlockNum-txblockNum).intValue();
			}
		}
		return confirms;
	}
	
	private String getContractTrueTxid(String org_txid) throws Exception{
		String s = main("blockchain_get_contract_result", "[\""+ org_txid+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		String result = null;
		if (null != json){
			if (!json.getString("result").equals(null)){
				result = json.getJSONObject("result").getString("trx_id");
			}
		}
		
		return result;
	}
	
	@Override
	public boolean getContractResult(String org_txid) throws Exception {
		// TODO Auto-generated method stub
		String s = main("blockchain_get_contract_result", "[\""+ org_txid+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		String tx_id = null;
		String blocknum = null;
		if (null != json){
			if (!json.getString("result").equals(null)){
				tx_id = json.getJSONObject("result").getString("trx_id");
				blocknum = json.getJSONObject("result").getString("block_num");
				s = main("blockchain_get_events", "[\"" + blocknum + "\",\""+ tx_id+"\"]");
				json = JSONObject.fromObject(s);
				String event_type = json.getJSONArray("result").getJSONObject(0).getString("event_type");
				if (event_type.equals("transfer_to_success")){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		// TODO Auto-generated method stub
		//begin参数不做使用，number参数放置区块ID
		
		List<BTCInfo> all = new ArrayList();
		List<String> alltxids =listBlockTransactions(number);
		for(int i = 0; i < alltxids.size(); i++){
			BTCInfo btcinfo = getTransactionsValue(alltxids.get(i),"");
			if(null!=btcinfo){
				all.add(btcinfo) ;
			}
		}
		return all;
	}

	@Override
	public boolean unlockWallet(String account) throws Exception {
		//打开钱包
		openWallet();
		//解锁钱包
		String s = main("wallet_unlock", "[10,\"" + super.getPASSWORD() + "\"]");
		JSONObject json = JSONObject.fromObject(s);
		
		boolean rcode = true;
		if(json.containsKey("error")){
			rcode = false;
		}
		
		return rcode;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		String s = main("wallet_lock", "[]");
		//关闭钱包
		//closeWallet();
		// TODO Auto-generated method stub
		return true;
	}

	private void openWallet() throws Exception {
		main("wallet_open", "[\"wallet\"]");

		return ;
	}
	
	private void closeWallet() throws Exception {
		main("wallet_close", "[]");
		
		// TODO Auto-generated method stub
		return ;
	}
	
	
	@Override
	public double queryFee() throws Exception {
		unlockWallet("");
		String s = main("wallet_get_transaction_fee", "[]");
		JSONObject json = JSONObject.fromObject(s);
		double result = 0d;
		if (null != json){
			if (json.containsKey("result") && !json.get("result").toString().equals("null")){
				result = parseAmount(json.getJSONObject("result").getString("amount"));
			}
		}
		lockWallet("");
		return result;
	}
	
	

	@Override
	public long bestBlockNumberValue() throws Exception {
		// TODO Auto-generated method stub
		String s = main("blockchain_get_block_count", "[]");
		JSONObject json = JSONObject.fromObject(s);
		long result = 0;
		if (null != json){
			if (!json.getString("result").equals(null)){
				result = json.getLong("result");
			}
		}
		return result;
	}
	
	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		// TODO Auto-generated method stub
		String s = main("blockchain_get_block", "[\""+ blockid+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		List<String> tids = new ArrayList();;
		if (null != json){
			if (!json.getString("result").equals(null)){
				JSONObject result =json.getJSONObject("result");
				JSONArray  ids = result.getJSONArray("user_transaction_ids");
				if (ids.size()>0){
					for(int i=0;i<ids.size();i++){
						tids.add(ids.getString(i));
					}
				}
			}
		}
		return tids;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee,String Memo) throws Exception {
		unlockWallet("");
		String s = null;
		JSONObject json = null;
		String result = null;
		if(from.equals("") || from.equals("ACTBUJVZnrQMNXWNcFPoppmMfVrjfafRsc4f")){
			from = "gbcax";
		}
		//正常的ACT转账
		if(!super.isISERC20()){
			s = main("wallet_transfer_to_address", "[\""+ amount+"\",\"ACT\",\""+from+"\",\""+ to +"\"]");
			json = JSONObject.fromObject(s);
			
		}else{
			if( null!=super.getCONTRACT() && !super.getCONTRACT().equals("")){
				s = main("call_contract", "[\""+ super.getCONTRACT()+"\",\""+ from +"\",\"transfer_to\",\""+ to+"|"+amount +"\",\"ACT\",\"1\"]");
				json = JSONObject.fromObject(s);
				System.out.println(s);
			}
		}
		
		if(json.containsKey("result") && !json.get("result").toString().equals(null)){
			result = json.getJSONObject("result").getString("entry_id");
		}
		
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) {
		// TODO Auto-generated method stub
		//AChain不需要处理该函数
	}

	
	private Timestamp dealTime(String timestamp) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = format.parse(timestamp);
            Timestamp ts = new Timestamp(date.getTime());
            return ts;
        } catch (Exception e) {
            return null;
        }
    }

	private double parseAmount(String hexval){
		String val = hexval ;
		if(val.equals("")){
			val ="0";
		}
		Double intVal = Double.valueOf(val) ;
		
		return intVal/Math.pow(10,5) ;
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		//authenticator();

		String url = "http://"+super.getIP()+":"+super.getPORT()+"/rpc";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String userpass = super.getACCESS_KEY() + ":" + super.getSECRET_KEY();
		String basicAuth = (int) ((Math.random() * 9 + 1) * 100000) + ""
				+ DatatypeConverter.printBase64Binary(userpass.getBytes());
		

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("Authorization", basicAuth);
		
		String postdata = "{\"jsonrpc\":\"2.0\", \"params\":"+condition+", \"id\": 1,\"method\":\""+method+"\"}";

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
		wmsg.setPORT("8299");
		wmsg.setACCESS_KEY("user");
		wmsg.setSECRET_KEY("passwd");
		wmsg.setISERC20(true);
		wmsg.setCONTRACT("CONRLa5aC72U8XzvJLMnoAfGNz6Kpf7bbFV");
//		wmsg.setISERC20(false);
//		wmsg.setCONTRACT("");
		wmsg.setPASSWORD("password");
		WalletUtil wallet =  WalletUtil.createWalletByClass("com.gt.util.wallet.Achain", wmsg);
		//System.out.println(wallet.getContractResult("b9cc4b5e8845611f70f5e213ab7930a899fb19cc"));
		//System.out.println(wallet.getConforms("dbcb366b28a53b9845f082842811c66da72ff96c"));
		//System.out.println(wallet.transfer("gbcaxtest", "ACTBUJVZnrQMNXWNcFPoppmMfVrjfafRsc4f", 5.1, 0.01));
		//System.out.println(wallet.getBalanceValue("gbcaxtest"));
		BTCInfo btcinfo= wallet.getTransactionsValue("f3192f0b889122ddcbc38b0d82d049c42628acf6", "");
		System.out.println(btcinfo.getComment());
	}


}
