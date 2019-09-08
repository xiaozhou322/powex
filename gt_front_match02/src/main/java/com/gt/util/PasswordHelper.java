package com.gt.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

	private static String algorithmName = "md5";
	private static int hashIterations = 2;

	public static String encryString(String pwd, String salt) {
	    String newPassword = new SimpleHash(algorithmName,Utils.getMD5_32_xx(pwd+"hello, 51SZZC"), ByteSource.Util.bytes(salt), hashIterations).toHex();

	    return newPassword;
	}
	
	public static void main(String args[]) throws Exception {
	}
}