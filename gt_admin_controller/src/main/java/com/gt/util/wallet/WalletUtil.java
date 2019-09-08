package com.gt.util.wallet;

import java.lang.reflect.Constructor;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.gt.entity.BTCInfo;
import com.gt.entity.WalletMessage;


public abstract class WalletUtil {
	
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
	// 针对各种代币的合约地址，存在合约地址的，就表示不是基础币，由子类特别处理
	private String CONTRACT = "";
	// ERC20代币的精度，默认是18，用来做计算
	private long DECIMALS = 18;
	// 代币标志
	private boolean ISERC20;
	
	WalletUtil() {
		super();
	}
	
	public WalletUtil(WalletMessage walletMessage) {
		this.setACCESS_KEY(walletMessage.getACCESS_KEY());
		this.setSECRET_KEY(walletMessage.getSECRET_KEY());
		this.setIP(walletMessage.getIP());
		this.setPORT(walletMessage.getPORT());
		this.setPASSWORD(walletMessage.getPASSWORD());
		this.setCONTRACT(walletMessage.getCONTRACT());
		this.setDECIMALS(walletMessage.getDECIMALS());
		this.setISERC20(walletMessage.isISERC20());
	}
	
	//验证地址是否正确
	public abstract boolean validateAddress(String address)  throws Exception ;
	//获取账户余额，为空则是查询全部地址的余额，不同钱包的要求不一样
	public abstract double getBalanceValue(String address) throws Exception ;
	//获取新地址，seedInfo可以为空
	public abstract String getNewaddressValue(String seedInfo) throws Exception ;
	//获取交易
	public abstract BTCInfo getTransactionsValue(String txid,String address) throws Exception ;
	//获取交易
	public abstract List<BTCInfo> listTransactionsValue(String number,String begin) throws Exception ;
	//获取指定区块的所有交易
	public abstract List<String> listBlockTransactions(String blockid) throws Exception ;
	//解锁钱包，btc类的钱包的account为空
	public abstract boolean unlockWallet(String account) throws Exception ;
	//锁定钱包
	public abstract boolean lockWallet(String account) throws Exception ;
	//锁定钱包
	public abstract boolean getContractResult(String org_txid) throws Exception ;
	//查询交易手续费
	public abstract double queryFee() throws Exception ;
	//获取最新区块地址
	public abstract long bestBlockNumberValue() throws Exception ;
	//转账处理方法,btc核心的from值为空即可
	public abstract String transfer(String from,String to,double amount,double fee,String Memo) throws Exception ;
	//资产汇总，eth核心专用
	public abstract void sendMain(String mainAddr,double mintran) throws Exception ;
	//获取确认数
	public abstract int getConforms(String txid) throws Exception ;
	
	public String getACCESS_KEY() {
		return ACCESS_KEY;
	}

	public void setACCESS_KEY(String aCCESS_KEY) {
		ACCESS_KEY = aCCESS_KEY;
	}

	public String getSECRET_KEY() {
		return SECRET_KEY;
	}

	public void setSECRET_KEY(String sECRET_KEY) {
		SECRET_KEY = sECRET_KEY;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getPORT() {
		return PORT;
	}

	public void setPORT(String pORT) {
		PORT = pORT;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getCONTRACT() {
		return CONTRACT;
	}

	public void setCONTRACT(String cONTRACT) {
		CONTRACT = cONTRACT;
	}

	public long getDECIMALS() {
		return DECIMALS;
	}

	public void setDECIMALS(long dECIMALS) {
		DECIMALS = dECIMALS;
	}

	public boolean isISERC20() {
		return ISERC20;
	}

	public void setISERC20(boolean iSERC20) {
		ISERC20 = iSERC20;
	}
	
	//The easiest way to tell Java to use HTTP Basic authentication is to set a default Authenticator: 
	public void authenticator() {
		Authenticator.setDefault(new Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication (ACCESS_KEY, SECRET_KEY.toCharArray());
		      }
		});
	}
	
	public String getSignature(String data, String key) throws Exception {
		// get an hmac_sha1 key from the raw key bytes
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
				"HmacSHA1");

		// get an hmac_sha1 Mac instance and initialize with the signing key
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signingKey);

		// compute the hmac on input data bytes
		byte[] rawHmac = mac.doFinal(data.getBytes());
		return bytArrayToHex(rawHmac);
	}

	private String bytArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (byte b : a)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}
	
	public static WalletUtil createWalletByClass(String calsspath,WalletMessage wmsg) throws Exception{
		Class c=Class.forName(calsspath);
		Constructor c1=c.getDeclaredConstructor(WalletMessage.class);
		c1.setAccessible(true);
		WalletUtil wallet =  (WalletUtil)c1.newInstance(wmsg);
		return wallet;
	}

	
	
}
