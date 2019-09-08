package com.gt.entity;

public class ETHMessage {
	// 用户名
	private String ACCESS_KEY;
	// 密码
	private String SECRET_KEY;
	// 钱包IP地址
	private String IP;
	// 端口
	private String PORT;
	// 比特币钱包密码
	private String PASSWORD;
	// 针对ERC20代币的合约地址
	private String CONTRACT;
	// ERC20代币的精度，默认是18，用来做计算
	private long DECIMALS;
	// ERC20代币标志
	private boolean ISERC20;
	// 转账方法ID，默认是0xa9059cbb
	// transferFrom的方法ID是0x23b872dd，第二个参数是到账地址，第三个参数金额
	private String TRANSFER;
	// 查询余额方法ID，默认是
	private String BALANCE;
	
	
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

	public String getTRANSFER() {
		return TRANSFER;
	}

	public void setTRANSFER(String tRANSFER) {
		TRANSFER = tRANSFER;
	}

	public String getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}

	

}
