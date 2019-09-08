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

public class EOSforce extends WalletUtil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7084108066627473796L;

	public EOSforce(WalletMessage walletMessage) {
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
	
	
	/**
	 * 获取所有 accounts 的可用余额表。可以指定 table_key 为某一个用户名，只获取该用户名的可用余额。
	 * @param accountName 用户名
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public double accounts_table(String accountName, String limit) throws Exception{
		double balance = 0d;
		List<JSONObject> arr = get_table_rows("eosio", "accounts", accountName, limit);
		if(!Utils.isNull(arr) && arr.size()>0){
			for(JSONObject o : arr){
				balance += Double.valueOf(o.get("available").toString().split(" ")[0]);
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
		return get_table_rows("eosio", "bps", bpName, limit);
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
		return get_table_rows("eosio", "schedules", scheduleVersion, limit);
	}
	
	public JSONArray get_table_rows(String scope, String table, String table_key, String limit) throws Exception{
		JSONObject obj = new JSONObject();
		obj.accumulate("scope", scope);
		obj.accumulate("code", "eosio");
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
				for(String string : arr){
					balance += Double.valueOf(string.split(" ")[0]);
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
	public void get_currency_stats(String code,String symbol) throws Exception{
		String str = main("chain/get_currency_stats", "{\"code\":\""+code+"\",\"symbol\":\""+symbol+"\"}");
		System.out.println(str);
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
		if(!obj.containsKey("error") && obj.containsKey("binargs")){
			return obj.get("binargs").toString();
		}
		return "";
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
		String str = main("wallet/get_public_keys", "{}");
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
		String str = main("wallet/sign_transaction", data);
		JSONObject json = JSONObject.fromObject(str);
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
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public double getBalanceValue(String address) throws Exception {
		if(!super.isISERC20()){
			return accounts_table(address, "");
		}else{
			return get_currency_balance("eosio.token", address, super.getCONTRACT());
			
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
		if(!json.containsKey("error")){
			String status = json.getJSONObject("trx").getJSONObject("receipt").get("status").toString();
			JSONObject trx = json.getJSONObject("trx").getJSONObject("trx");
			JSONArray actions = trx.getJSONArray("actions");
			if("transfer".equals(actions.getJSONObject(0).get("name")) && "executed".equals(status)){
				info = new BTCInfo();
				info.setCategory("receive");
				info .setTxid(json.get("id").toString());
				info.setBlockNumber(json.getLong("block_num"));
				info.setConfirmations(getConforms(txid));
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
				if(!"EOS".equals(quantity.split(" ")[1])){
					info.setContract(quantity.split(" ")[1]);
				}else{
					info.setContract("");
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
		List<String> alltxids = listBlockTransactions(number);
		for(int i = 0; i < alltxids.size(); i++){
			BTCInfo btcinfos = getTransactionsValue(alltxids.get(i),"");
			if(null!=btcinfos){
				all.add(btcinfos);
			}
		}
		return all;
	}

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
			for(JSONObject o : li){
				if("executed".equals(o.get("status").toString())){
					list.add(o.getJSONObject("trx").get("id").toString());
				}
			}
		}
		return list;
	}

	@Override
	public boolean unlockWallet(String wallet) throws Exception {
		String str = main("wallet/unlock", "[\""+wallet+"\",\""+super.getPASSWORD()+"\"]");
		return true;
	}

	@Override
	public boolean lockWallet(String account) throws Exception {
		String str = main("wallet/lock", "\""+account+"\"");
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
			bheight = Long.parseLong(json.get("head_block_num").toString());
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
			json.accumulate("quantity", df.format(amount)+" "+super.getCONTRACT());
		}
		json.accumulate("memo", Memo);
		String code = "";
		if(!super.isISERC20()){
			code = "eosio";
		}else{
			code = "eosio.token";
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
		transfer.accumulate("fee", df.format(fee)+" EOS");
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
		int conforms = 0 ;
		String str = main("history/get_transaction", "{\"id\":\""+txid+"\"}");
		JSONObject json = JSONObject.fromObject(str);
		if(!json.containsKey("error")){
			String block_num = json.get("block_num").toString();
			conforms = getConformsByBlock(block_num);
		}
		return conforms;
	}
	
	public static void main(String[] args) throws Exception {
		WalletMessage wmsg = new WalletMessage();
		wmsg.setIP("192.168.0.196");
		//wmsg.setIP("47.52.54.232");
		wmsg.setPORT("8888");
		wmsg.setACCESS_KEY("");
		wmsg.setSECRET_KEY("");
		wmsg.setISERC20(true);
		wmsg.setCONTRACT("EAT");
		wmsg.setPASSWORD("PW5KH8DW3Rx2RyfYDQ5M1M819vF8YXaYe1DNKrJnCm87M9bX2JVqD");
		EOSforce wallet = (EOSforce) WalletUtil.createWalletByClass("com.gtwallet.wallet.core.EOSforce", wmsg);
		
		System.out.println(wallet.bestBlockNumberValue());
		
		//System.out.println("000fcd63a53b09d3b1394147d50224317a498c70a0041eb982b8a337066e632b");
		//System.out.println(wallet.listBlockTransactions("000fcd63a53b09d3b1394147d50224317a498c70a0041eb982b8a337066e632b"));
		
		//System.out.println(wallet.getBalanceValue("gpmn"));
	
		//System.out.println(wallet.get_key_accounts("EOS6tWDBqV3FSP9fqeKuar9eiUUtnbZLeS5TTAC8q8kU4AjUFj8f5"));
		
		//System.out.println(wallet.get_actions("jiqix", "", ""));
		
		//System.out.println(wallet.getTransactionsValue("f8665a0c861e4f155aaab24e9e82ae2415bb2ea9b2fc61de15c0bd34a3976679", ""));
		//System.out.println(wallet.getConforms("f8665a0c861e4f155aaab24e9e82ae2415bb2ea9b2fc61de15c0bd34a3976679"));
		
		//System.out.println(wallet.get_key_accounts("EOS5KFXy8LiruGAQqY1pzwp5GqCaUkpMivSkpQfu3Rgg78QyFRHmA"));

		//System.out.println(wallet.getBalanceValue("gygenesis111"));
		
		//System.out.println(wallet.bps_table("","100"));

		//System.out.println(wallet.get_key_accounts("EOS5MFoVFWAkeZgjxMCBTDjhrxmNefFKSGZUbewH35tJGSqzaFRpU"));
		
		/*System.out.println(wallet.get_key_accounts("EOS65BbTBcG3kyczxt2vo6d5DGgj8kQ7MkSjk2KQeGhbYAjdY8Nkw"));
		
		System.out.println(wallet.getBalanceValue("eosrsj"));
		
		System.out.println(wallet.get_actions("eosrsj", "", ""));*/
		
		//System.out.println(wallet.listBlockTransactions("1122083"));
		
		//System.out.println(wallet.getTransactionsValue("61e2d5623ec645993c2ac025439a0ba3e10a7f627396b26e7e54cf21ccaf5107", "'"));
		
		/*System.out.println(wallet.get_key_accounts("EOS8ChnF2DBEMFqRwms9R7TU3XoLuT7A8qiwiXu4vBsy4bXBT9Kw7"));
		
		System.out.println(wallet.getBalanceValue("eosrsj"));
		
		System.out.println(wallet.votes_table("eosrsj", "", ""));*/
		
		//System.out.println(wallet.getTransactionsValue("8b1aaa2abe59c7c01d757f8bba98ef89f5e6e234bd88d0cacd5cd7c0dcc081d1", ""));
		
		System.out.println();
		
		
		//wallet.get_abi("eosio.token");
		//String code = wallet.get_code("eosio.token");
		
		/*JSONObject json = new JSONObject();
		json.accumulate("from", "eosxll");
		json.accumulate("to", "eosrsj");
		json.accumulate("quantity", "0.5000 EOS");
		json.accumulate("memo", "");
		String data = wallet.abi_json_to_bin("eosio", "transfer", json);
		System.out.println(data);
		
		System.out.println(wallet.abi_bin_to_json("eosio", "transfer", data));*/
		
		//System.out.println(wallet.last_irreversible_block());
		
		//wallet.transfer("eosrsj", "eosxll", 0.5, 0.01, "test");

		//System.out.println(wallet.getTransactionsValue("c50cca4c41f64af369875d4faa512181a63197fecc8498a982817f461c61e70a", ""));
		
		//System.out.println(wallet.get_abi("eosio.token"));
		
		//System.out.println(wallet.get_public_keys());
		
		//System.out.println(wallet.get_currency_balance("eosio.token", "awaketoken", "EAT"));
		
		System.out.println(wallet.getTransactionsValue("9836143bb43fe50c01d9b8cf97381083bac4df95cc6a03fce0c9017870a2f91e",""));
		
		System.out.println(wallet.listTransactionsValue("1496897", ""));
		
		//System.out.println(wallet.getBalanceValue("eosrsj"));
		
		System.out.println(wallet.getBalanceValue("eosrsj"));
		
		System.out.println(wallet.getTransactionsValue("f817135588cd7223099ed6ea00b163632574e61a9c5715ca25a4640c91758b3c", ""));
		
	}

}
