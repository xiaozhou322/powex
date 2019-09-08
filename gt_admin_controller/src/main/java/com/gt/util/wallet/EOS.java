package com.gt.util.wallet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;
import com.gt.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EOS extends WalletUtil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4160034658719122536L;

	public EOS(WalletMessage walletMessage) {
		super(walletMessage);
		// TODO Auto-generated constructor stub
	}

	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String url = "http://"+super.getIP()+":"+super.getPORT()+"/v1/"+method;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		
		//System.out.println(postdata);
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(condition);
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
	
	private String wallet(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		String wallport = String.valueOf(Integer.valueOf(super.getPORT())+1);
		String url = "http://"+super.getIP()+":"+wallport+"/v1/"+method;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		

		// add reuqest header
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		
		//System.out.println(postdata);
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(condition);
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
	
	/**
	 * 获取所有 accounts 的可用余额表。可以指定 table_key 为某一个用户名，只获取该用户名的可用余额。
	 * @param accountName 用户名
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public double accounts_table(String accountName, String limit) throws Exception{
		double balance = 0d;
		List<JSONObject> arr = get_table_rows(accountName, "accounts", "eosio.token", limit);
		if(!Utils.isNull(arr) && arr.size()>0){
			for(JSONObject obj : arr){
				balance += Double.valueOf(obj.get("balance").toString().split(" ")[0]);
			}
		}
		return balance;
	}
	
	/**
	 * 获取超级节点的列表。
	 * @param bpName 指定的超级节点名
	 * @param limit 最大数量
	 * @return
	 * @throws Exception
	 */
	public JSONArray bps_table(String bpName, String limit) throws Exception{
		return get_table_rows("eosio.token", "bps", bpName, limit);
	}
	
	/**
	 * 获取一个用户名下的投票信息列表。
	 * @param accountName 指定的用户名
	 * @param bpName 指定的超级节点名
	 * @param limit 最大数量
	 * @return
	 * @throws Exception
	 */
	public JSONArray votes_table(String accountName,String bpName, String limit) throws Exception{
		return get_table_rows(accountName, "votes", bpName, limit);
	}
	
	/**
	 * 获取每一届超级节点的排序表。
	 * @param scheduleVersion 指定的某一届
	 * @param limit 最大数量
	 * @return
	 * @throws Exception
	 */
	public JSONArray schedules_table(String scheduleVersion, String limit) throws Exception{
		return get_table_rows("eosio.token", "schedules", scheduleVersion, limit);
	}
	
	public JSONArray get_table_rows(String scope, String table, String table_key, String limit) throws Exception{
		JSONObject obj = new JSONObject();
		obj.accumulate("scope", scope);
		obj.accumulate("code", "eosio.token");
		obj.accumulate("table", table);
		obj.accumulate("json", true);
		if(!Utils.isNull(table_key)){
			obj.accumulate("table_key", table_key);
			if(Utils.isNumeric(limit)){
				obj.accumulate("limit", limit);
			}
		}
		String str = main("chain/get_table_rows", obj.toString());
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		if(!json.containsKey("error") && json.containsKey("rows")){
			return json.getJSONArray("rows");
		}
		return null;
	}

	/**
	 * 获取给定公钥下的账号列表。
	 * @param publickey 公钥
	 * @return
	 * @throws Exception
	 */
	public List<String> get_key_accounts(String publickey) throws Exception{
		String str = main("history/get_key_accounts", "{\"public_key\":\""+publickey+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error") && json.containsKey("account_names")){
			return json.getJSONArray("account_names");
		}
		return null;
	}
	
	/**
	 * 获取某个用户名下交易记录。
	 * @param accountName 指定的用户名
	 * @param pos 起始文章
	 * @param offset 偏移量
	 * @return
	 * @throws Exception
	 */
	public List<String> get_actions(String accountName, String pos, String offset) throws Exception{
		JSONObject obj = new JSONObject();
		obj.accumulate("account_name", accountName);
		if(Utils.isNumeric(pos)){
			obj.accumulate("pos", pos);
			if(Utils.isNumeric(offset)){
				obj.accumulate("offset", offset);
			}
		}
		
		String str = main("history/get_actions", obj.toString());
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error") && json.containsKey("actions")){
			return json.getJSONArray("actions");
		}
		return null;
	}
	
	/**
	 * 获取智能合约
	 * @param accountName
	 * @throws Exception
	 */
	public String get_code(String accountName) throws Exception{
		String str = main("chain/get_code", "{\"account_name\":\""+accountName+"\",\"code_as_wasm\":\"false\"}");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(str);
		if(!json.containsKey("error")){
			return json.get("code_hash").toString();
		}
		return "";
	}
	
	/**
	 * 获取abi信息
	 * @param accountName
	 * @throws Exception
	 */
	public String get_abi(String accountName) throws Exception{
		String str = main("chain/get_abi", "{\"account_name\":\""+accountName+"\"}");
		System.out.println(str);
		return str;
	}
	
	/**
	 * 查询代币余额
	 * @param code
	 * @param account
	 * @param symbol
	 * @throws Exception
	 */
	public double get_currency_balance(String code,String account,String symbol) throws Exception{
		double balance = 0d;
		String str = main("chain/get_currency_balance", "{\"code\":\""+code+"\",\"account\":\""+account+"\",\"symbol\":\""+symbol+"\"}");
		System.out.println(str);
		if(str.startsWith("[")){
			List<String> arr = JSONArray.fromObject(str);
			if(arr.size()>0){
				for(String amount : arr){
					balance += Double.valueOf(amount.split(" ")[0]);
				}
			}
		}
		return balance;
	}
	
	/**
	 * 查询代币状态
	 * @param code
	 * @param symbol
	 * @throws Exception
	 */
	public String get_currency_stats(String code,String symbol) throws Exception{
		String str = main("chain/get_currency_stats", "{\"code\":\""+code+"\",\"symbol\":\""+symbol+"\"}");
		System.out.println(str);
		return str;
	}
	
	/**
	 * 反序列化成json
	 * @param code
	 * @param action
	 * @param binargs
	 * @throws Exception
	 */
	public JSONObject abi_bin_to_json(String code , String action, String binargs ) throws Exception{
		String str = main("chain/abi_bin_to_json", "{\"code\":\""+code+"\",\"action\":\""+action+"\",\"binargs\":\""+binargs+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error") && json.containsKey("args")){
			return json.getJSONObject("args");
		}
		return null;
	}

	/**
	 * json序列化，序列化结果用于push_transaction的data字段。
	 *  目前仅支持 eosio and eosio.token 账号的abi， 之后会支持eosio.bios账号，包括newaccount等系统action
	 * @param code
	 * @param action
	 * @param args
	 * @throws Exception
	 */
	public String abi_json_to_bin(String code , String action, JSONObject args) throws Exception{
		JSONObject json = new JSONObject();
		json.accumulate("code", code);
		json.accumulate("action", action);
		json.accumulate("args", args);
		String str = main("chain/abi_json_to_bin", json.toString());
		JSONObject obj = JSONObject.fromObject(str);
		System.out.println(obj);
		if(!obj.containsKey("error") && obj.containsKey("binargs")){
			return obj.get("binargs").toString();
		}
		return "";
	}
	
	public String last_irreversible_block() throws Exception {
		String str = main("chain/get_info", "{}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			return json.get("last_irreversible_block_id").toString();
		}
		return "";
	}
	
	/**
	 * 获取链id
	 * @return
	 * @throws Exception
	 */
	public String get_chain_id() throws Exception {
		String str = main("chain/get_info", "{}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			return json.get("chain_id").toString();
		}
		return "";
	}

	/**
	 * 列出所有钱包中的所有公钥。
	 * @return
	 * @throws Exception
	 */
	public JSONArray get_public_keys() throws Exception{
		JSONArray arr = new JSONArray();
		String str = wallet("wallet/get_public_keys", "{}");
		System.out.println(str);
		if(!str.contains("error")){
			arr = JSONArray.fromObject(str);
		}
		return arr;
	}
	
	/**
	 * 获取签名所需要的密钥
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public JSONArray get_required_keys(JSONObject transaction, JSONArray available_keys) throws Exception{
		JSONArray arr = new JSONArray();
		String str = main("chain/get_required_keys", "{\"transaction\":"+transaction.toString()+",\"available_keys\":"+available_keys.toString()+"}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error") && json.containsKey("required_keys")){
			arr = json.getJSONArray("required_keys");
		}
		return arr;
	}
	
	/**
	 * 交易签名
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public JSONArray sign_transaction(String data) throws Exception{
		JSONArray result = new JSONArray();
		String str = wallet("wallet/sign_transaction", data);
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		if(!json.containsKey("error") && json.containsKey("signatures")){
			result = json.getJSONArray("signatures");
		}
		return result;
	}
	
	/**
	 * 交易
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String push_transaction(String data) throws Exception{
		String str = main("chain/push_transaction", data);
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error") && json.containsKey("transaction_id")){
			return json.get("transaction_id").toString();
		}
		return "";
	}
	
	@Override
	public boolean validateAddress(String address) throws Exception {
		if (address.toLowerCase().matches("^[1-5a-z]{12}$")==false) {
	        return false;
	    }
		return true ;
	}
	
	@Override
	public double getBalanceValue(String address) throws Exception {
		if(!super.isISERC20()){
			return accounts_table(address, "");
		}else{
			String issuer = super.getCONTRACT().split("\\|#\\|")[0];
			String symbol = super.getCONTRACT().split("\\|#\\|")[1];
			return get_currency_balance(issuer, address, symbol);
		}
	}

	@Override
	public String getNewaddressValue(String seedInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BTCInfo getTransactionsValue(String txid, String address) throws Exception {
		BTCInfo info = null;
		String str = main("history/get_transaction", "{\"id\":\""+txid+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		if(!json.containsKey("error")){
			String status = json.getJSONObject("trx").getJSONObject("receipt").get("status").toString();
			JSONObject trx = json.getJSONObject("trx").getJSONObject("trx");
			JSONArray actions = trx.getJSONArray("actions");
			if("transfer".equals(actions.getJSONObject(0).get("name")) && "executed".equals(status)){
				info = new BTCInfo();
				info.setCategory("receive");
				info .setTxid(json.get("id").toString());
				info.setBlockNumber(json.getLong("block_num"));
				info.setConfirmations(0);
				try{
					String formate = "yyyy-MM-dd'T'HH:mm:ss";
					SimpleDateFormat formater = new SimpleDateFormat(formate);
					Date date = formater.parse(json.getString("block_time"));
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sDateTime = sdf.format(date);
					info.setTime(Timestamp.valueOf(sDateTime));
				}catch(Exception e){
					info.setTime(Utils.getTimestamp());
				}
				JSONObject data = actions.getJSONObject(0).getJSONObject("data");
				String from = data.get("from").toString();
				String to = data.get("to").toString();
				String memo = data.get("memo").toString();
				String quantity = data.get("quantity").toString();
				Double amount = Double.valueOf(quantity.split(" ")[0]);
				String account = actions.getJSONObject(0).getString("account");
				String symbol = quantity.split(" ")[1];
				if(!"EOS".equals(symbol) || !account.equals("eosio.token")){
					info.setContract(account+"|#|"+symbol);
				}
				info.setAddress(to);
				info.setAmount(amount);
				info.setComment(memo);
			}
			return info;
		}
		return null;
	}

	@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		List<BTCInfo> all = new ArrayList<BTCInfo>();
		/*List<String> alltxids = listBlockTransactions(number);
		for(int i = 0; i < alltxids.size(); i++){
			BTCInfo btcinfos = getTransactionsValue(alltxids.get(i),"");
			if(null!=btcinfos){
				all.add(btcinfos);
			}
		}*/
		
		JSONObject obj = getBlock(number);
		if(!obj.containsKey("error") && obj.getJSONArray("transactions").size()>0){
			List<JSONObject> li = obj.getJSONArray("transactions");
			for(JSONObject transaction : li){
				String status = transaction.get("status").toString();
				JSONObject trx = null;
				try {
					trx = transaction.getJSONObject("trx");
				} catch (net.sf.json.JSONException e) {
					continue;
				}
				JSONArray actions = trx.getJSONObject("transaction").getJSONArray("actions");
				if("transfer".equals(actions.getJSONObject(0).get("name")) && "executed".equals(status)){
					BTCInfo info = new BTCInfo();
					info.setCategory("receive");
					info .setTxid(trx.get("id").toString());
					info.setBlockNumber(Long.valueOf(number));
					info.setConfirmations(0);
					try{
						String formate = "yyyy-MM-dd'T'HH:mm:ss";
						SimpleDateFormat formater = new SimpleDateFormat(formate);
						Date date = formater.parse(trx.getJSONObject("transaction").getString("block_time"));
						SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sDateTime = sdf.format(date);
						info.setTime(Timestamp.valueOf(sDateTime));
					}catch(Exception e){
						info.setTime(Utils.getTimestamp());
					}
					JSONObject data =null;
					try {
						data = actions.getJSONObject(0).getJSONObject("data");
					} catch (net.sf.json.JSONException e) {
						continue;
					}
					String from = data.get("from").toString();
					if(null==data.get("from")) {
						continue;
					}
					String to = data.get("to").toString();
					if(null==data.get("to")) {
						continue;
					}
					String memo = data.get("memo").toString();
					if(null==data.get("quantity")) {
						continue;
					}
					String quantity = data.get("quantity").toString();
					Double amount = Double.valueOf(quantity.split(" ")[0]);
					String account = actions.getJSONObject(0).getString("account");
					String symbol = quantity.split(" ")[1];
					if(!"EOS".equals(symbol) || !account.equals("eosio.token")){
						info.setContract(account+"|#|"+symbol);
					}
					info.setAddress(to);
					info.setAmount(amount);
					info.setComment(memo);
					all.add(info);
				}
			}
		}
		return all;
	}

	/*@Override
	public List<BTCInfo> listTransactionsValue(String number, String begin) throws Exception {
		List<BTCInfo> all = new ArrayList<BTCInfo>();
		JSONObject obj = getBlock(number);
		if(null != obj && obj.containsKey("transactions") && obj.getJSONArray("transactions").size()>0){
			List<JSONObject> list = obj.getJSONArray("transactions");
			for(JSONObject transaction : list){
				BTCInfo btcinfos = getTransactionsValue(alltxids.get(i),"");
				if(null!=btcinfos){
					all.add(btcinfos);
				}
			}
		}
		return all;
	}*/
	
	/**
	 * 根据区块hash值获取区块信息
	 * @param hash
	 * @return
	 * @throws Exception
	 */
	public JSONObject getBlock(String hash) throws Exception{
		String str = main("chain/get_block", "{\"block_num_or_id\":\""+hash+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			return json;
		}
		return null;
	}
	
	public int getConformsByBlock(String block) throws Exception {
		int conforms = 0 ;
		String str = main("chain/get_block", "{\"block_num_or_id\":\""+block+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			conforms = Integer.valueOf(json.get("confirmed").toString());
		}
		return conforms;
	}
	
	@Override
	public List<String> listBlockTransactions(String blockid) throws Exception {
		List<String> list = new ArrayList<String>();
		JSONObject obj = getBlock(blockid);
		if(!obj.containsKey("error") && obj.getJSONArray("transactions").size()>0){
			List<JSONObject> li = obj.getJSONArray("transactions");
			for(JSONObject transaction : li){
				if("executed".equals(transaction.get("status").toString())){
					list.add(transaction.getJSONObject("trx").get("id").toString());
				}
			}
		}
		return list;
	}

	@Override
	public boolean unlockWallet(String wallet) throws Exception {
		String str = wallet("wallet/unlock", "[\""+wallet+"\",\""+super.getPASSWORD()+"\"]");
		return true;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		String str = wallet("wallet/lock", "\""+account+"\"");
		return true;
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
		String str = main("chain/get_info", "{}");
		JSONObject json = JSONObject.fromObject(str);
		long bheight = 0L;
		if(!json.containsKey("error")){
			//EOS必须使用不可逆区块高度，否则会出现大量假充值订单
			bheight = Long.parseLong(json.get("last_irreversible_block_num").toString());
		}
		return bheight;
	}

	@Override
	public String transfer(String from, String to, double amount, double fee, String Memo) throws Exception {
		String result = "";
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			unlockWallet("default");
		}
		
		JSONObject json = new JSONObject();
		DecimalFormat df = new DecimalFormat("0.0000");
		json.accumulate("from", from);
		json.accumulate("to", to);
		if(!super.isISERC20()){
			json.accumulate("quantity", df.format(amount)+" EOS");
		}else{
			json.accumulate("quantity", df.format(amount)+" "+super.getCONTRACT().split("\\|#\\|")[1]);
		}
		json.accumulate("memo", Memo);
		String code = "";
		if(!super.isISERC20()){
			code = "eosio.token";
		}else{
			code = super.getCONTRACT().split("\\|#\\|")[0];
		}
		String hex_data = abi_json_to_bin(code, "transfer", json);
		
		String formate = "yyyy-MM-dd'T'HH:mm:ss";
		String last_irreversible_block_id = bestBlockNumberValue()+"";
		JSONObject block= getBlock(last_irreversible_block_id);
		String ref_block_num = block.get("block_num").toString();
		String ref_block_prefix = block.get("ref_block_prefix").toString();
		String timestamp = block.get("timestamp").toString();
		SimpleDateFormat formater = new SimpleDateFormat(formate);
		String date  = formater.format(new Date(formater.parse(timestamp).getTime() + 60*1000));
		
		JSONArray actions = new JSONArray();
		JSONObject action = new JSONObject();
		action.accumulate("account", code);
		action.accumulate("name", "transfer");
		JSONArray authorization = new JSONArray();
		authorization.add(new JSONObject().accumulate("actor", from).accumulate("permission", "active"));
		action.accumulate("authorization", authorization);
		action.accumulate("data", hex_data);
		actions.add(action);
		
		JSONObject transfer = new JSONObject();
		transfer.accumulate("expiration", date);
		transfer.accumulate("ref_block_num", ref_block_num);
		transfer.accumulate("ref_block_prefix", ref_block_prefix);
		transfer.accumulate("actions", actions);
		//transfer.accumulate("fee", df.format(fee)+" EOS");
		String chain_id = get_chain_id();
		
		JSONArray public_keys = get_public_keys();
		JSONArray publicKeys = get_required_keys(transfer, public_keys);
		JSONArray signs = sign_transaction("["+transfer.toString()+","+publicKeys.toString()+",\""+chain_id+"\"]");

		JSONObject data = new JSONObject();
		data.accumulate("transaction", transfer);
		data.accumulate("compression", "none");
		data.accumulate("signatures", signs);
		result = push_transaction(data.toString());
		
		if(super.getPASSWORD() != null && super.getPASSWORD().trim().length() >0){
			lockWallet("default");
		}
		
		return result;
	}

	@Override
	public void sendMain(String mainAddr, double mintran) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int getConforms(String txid) throws Exception {
		long last_num = this.bestBlockNumberValue();
		int conforms = 0 ;
		long curBlockId = this.bestBlockNumberValue();
		String str = main("history/get_transaction", "{\"id\":\""+txid+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json.toString());
		if(!json.containsKey("error")){
			long block_num = Long.valueOf(json.get("block_num").toString());
			conforms = Long.valueOf(curBlockId-block_num).intValue();
		}
		return 12;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("192.168.0.155");
		//wmsg.setIP("node2.liquideos.com");
		wmsg.setPORT("8889");
		//wmsg.setPORT("8888");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("");
		//wmsg.setISERC20(true);
		//wmsg.setCONTRACT("octtothemoon|#|OCT");
		//wmsg.setPASSWORD("PW5KFiLYwwMERH8pG3n6Y2KTkgJPa4W63kYc88ptE9QaAsNQuRqCz");
		EOS wallet = (EOS) WalletUtil.createWalletByClass("com.gtwallet.wallet.core.EOS", wmsg);
		
		System.out.println(wallet.bestBlockNumberValue());
		
		
		//System.out.println();
		
		
		//System.out.println(wallet.getBlock("14603632"));
		
		/*System.out.println(wallet.get_abi("epraofficial"));
		System.out.println(wallet.get_code("epraofficial"));
		System.out.println(wallet.get_currency_stats("epraofficial", "EPRA"));*/
		
		//System.out.println(wallet.get_currency_stats("toyotawallet", "OCT"));
		//System.out.println(wallet.get_currency_stats("octtothemoon", "OCT"));
		//System.out.println(wallet.getTransactionsValue("2449375899e44c9fdcc73fa3575046247ca249dfdc33c020073954dcee6355df",""));

		//System.out.println(wallet.transfer("eosrsjeosrsj", "eosxlleosxll", 0.1, 0, "test"));
		
		//System.out.println(wallet.getBalanceValue("eosrsjeosrsj"));
		
		//System.out.println(wallet.accounts_table("newdexpocket", ""));
		
		//System.out.println(wallet.getTransactionsValue("b4f2762fa95733beb84d725f28dfdbb93f11996f1e9c7e28e7a7a1c125912154", ""));
		
		//System.out.println(wallet.getBlock("01327549d1a94a245e9f2b291a2f7e56576ab35a82572e5c1770051ac6f24993"));
		
	}

}
