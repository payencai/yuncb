package com.tool;

import com.example.yunchebao.base64.org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;


public class RSAUtil {
	public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCq4pPr1hrdUDkZ00nSJPZQ/K12pTDi7X9uGCUdo5q3+w1LTekttnvqmv4RkenfCA4uGjudFufhMMvi1fdudbCOU6Pend8NIGqhPiYfsvv6Yi9s8JZ83huybmcIHWo8cDtAndARAQiRFH5KBAPIgTxMwXv61btfw2YlGgmpuz4JwIDAQAB";
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 获取密钥对
	 * 
	 * @return 密钥对
	 */
	public static KeyPair getKeyPair() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024);
		return generator.generateKeyPair();
	}

	/**
	 * 获取私钥
	 * 
	 * @param privateKey
	 *            私钥字符串
	 * @return
	 */
	public static PrivateKey getPrivateKey(String privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		return keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 获取公钥
	 * 
	 * @param publicKey
	 *            公钥字符串
	 * @return
	 */
	public static PublicKey getPublicKey(String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * RSA加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static String encrypt(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		int inputLen = data.getBytes().length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offset = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offset > 0) {
			if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
			}
			out.write(cache, 0, cache.length);
			i++;
			offset = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		// 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
		// 加密后的字符串
		return new String(Base64.encodeBase64String(encryptedData));
	}

	/**
	 * RSA解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String decrypt(String data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] dataBytes = Base64.decodeBase64(data);
		int inputLen = dataBytes.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offset = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offset > 0) {
			if (inputLen - offset > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
			}
			out.write(cache, 0, cache.length);
			i++;
			offset = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		// 解密后的内容
		return new String(decryptedData, "UTF-8");
	}

	/**
	 * 签名
	 * 
	 * @param data
	 *            待签名数据
	 * @param privateKey
	 *            私钥
	 * @return 签名
	 */
	public static String sign(String data, PrivateKey privateKey) throws Exception {
		byte[] keyBytes = privateKey.getEncoded();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(key);
		signature.update(data.getBytes());
		return new String(Base64.encodeBase64(signature.sign()));
	}

	/**
	 * 验签
	 * 
	 * @param srcData
	 *            原始字符串
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            签名
	 * @return 是否验签通过
	 */
	public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
		byte[] keyBytes = publicKey.getEncoded();
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey key = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(key);
		signature.update(srcData.getBytes());
		return signature.verify(Base64.decodeBase64(sign.getBytes()));
	}

	/**
	 * 解密
	 */
	public static String passwordVerification(String encryptData) throws Exception {
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIKrik+vWGt1QORnTSdIk9lD8rXalMOLtf24YJR2jmrf7DUtN6S22e+qa/hGR6d8IDi4aO50W5+Ewy+LV9251sI5To96d3w0gaqE+Jh+y+/piL2zwlnzeG7JuZwgdajxwO0Cd0BEBCJEUfkoEA8iBPEzBe/rVu1/DZiUaCam7PgnAgMBAAECgYBkDgHcPkGQFrpXqhnTyKkdJgBE61gZ23LBElsxkw4+G7P8i+EdiL2b3HzMINRhFJR/mgUPAZ3eqYPjdekT6GbeOxI6c5BnxELdyd8/1vQ5IRgxzyhJ2WTezhDWNIzhynorcccG2YjYT8GNwWvwYFOwAZ9gqA1Fmw0tfzPcdC/cIQJBANBrMfhwWWI855Et2+O3tfDaMBNZMuopTx05jPHMjVA6QlogjCAIz1T/3A+dce82CbHLbNQhIe6he4rxoptBvukCQQCggGj/VcXcFJ0tdAYq2OLc5IgufC+WhfFT4UfvB60wMV8A7X+vTalugAduCoPqF5PdgaKCGH6KhWSwkZXzjDSPAkEAtODjTFbM7BopanDfTkEI0M+7O3+FVX2mYJmqvcsltUPqg+eNVNvwfDdVl+OvlshSH5CJkYxzs3NIEezlj1K+yQJAK0WTiUuRtZ0lJy0BwKmKuG0wvf+jQNzJzIiPQUV7juOwrmpZo2S3yl/gIO3a1NEYf37E1nhOHYCLPgNyPyBMxwJBAMiyEWUXKXB9thP3f6x/lkMnZFmMIM7NcrhgTUwsrNs5iMiG4t+kpM3hKaLFau6RafAEaXp8ThWECnDvgh0mqq8=";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCq4pPr1hrdUDkZ00nSJPZQ/K12pTDi7X9uGCUdo5q3+w1LTekttnvqmv4RkenfCA4uGjudFufhMMvi1fdudbCOU6Pend8NIGqhPiYfsvv6Yi9s8JZ83huybmcIHWo8cDtAndARAQiRFH5KBAPIgTxMwXv61btfw2YlGgmpuz4JwIDAQAB";
		// RSA解密
		String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
		System.out.println("解密后内容:" + decryptData);
		// RSA签名
		String sign = sign(encryptData, getPrivateKey(privateKey));
		// RSA验签
		boolean result = verify(encryptData, getPublicKey(publicKey), sign);
		if(!result) {
			return null;
		}
		return decryptData;
	}
	
	
	public static void main(String[] args) {
		try {
		String encryptData = "XJ+2A3aiu8H3Cogve3b/CR9ugId1Mm4Zg1ylNbdpV/XJskqJ0ctFMre/9IQO+pG9djqmyviij49cBAy+H9vf5/BrQhu7tG/bNcBm4yTKr89rTmna7HSZd1sAvEaA4g8y2XbXzEBANmP2P6fM3zBZEJpMM/aJemj+9Q+V/ltCfrc=";
		System.out.println("解密后内容:" + passwordVerification(encryptData));
			// 生成密钥对
//			KeyPair keyPair = getKeyPair();
//			String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
//			String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
//			String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIKrik+vWGt1QORnTSdIk9lD8rXalMOLtf24YJR2jmrf7DUtN6S22e+qa/hGR6d8IDi4aO50W5+Ewy+LV9251sI5To96d3w0gaqE+Jh+y+/piL2zwlnzeG7JuZwgdajxwO0Cd0BEBCJEUfkoEA8iBPEzBe/rVu1/DZiUaCam7PgnAgMBAAECgYBkDgHcPkGQFrpXqhnTyKkdJgBE61gZ23LBElsxkw4+G7P8i+EdiL2b3HzMINRhFJR/mgUPAZ3eqYPjdekT6GbeOxI6c5BnxELdyd8/1vQ5IRgxzyhJ2WTezhDWNIzhynorcccG2YjYT8GNwWvwYFOwAZ9gqA1Fmw0tfzPcdC/cIQJBANBrMfhwWWI855Et2+O3tfDaMBNZMuopTx05jPHMjVA6QlogjCAIz1T/3A+dce82CbHLbNQhIe6he4rxoptBvukCQQCggGj/VcXcFJ0tdAYq2OLc5IgufC+WhfFT4UfvB60wMV8A7X+vTalugAduCoPqF5PdgaKCGH6KhWSwkZXzjDSPAkEAtODjTFbM7BopanDfTkEI0M+7O3+FVX2mYJmqvcsltUPqg+eNVNvwfDdVl+OvlshSH5CJkYxzs3NIEezlj1K+yQJAK0WTiUuRtZ0lJy0BwKmKuG0wvf+jQNzJzIiPQUV7juOwrmpZo2S3yl/gIO3a1NEYf37E1nhOHYCLPgNyPyBMxwJBAMiyEWUXKXB9thP3f6x/lkMnZFmMIM7NcrhgTUwsrNs5iMiG4t+kpM3hKaLFau6RafAEaXp8ThWECnDvgh0mqq8=";
//			String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCq4pPr1hrdUDkZ00nSJPZQ/K12pTDi7X9uGCUdo5q3+w1LTekttnvqmv4RkenfCA4uGjudFufhMMvi1fdudbCOU6Pend8NIGqhPiYfsvv6Yi9s8JZ83huybmcIHWo8cDtAndARAQiRFH5KBAPIgTxMwXv61btfw2YlGgmpuz4JwIDAQAB";
//			System.out.println("私钥:" + privateKey);
//			System.out.println("公钥:" + publicKey);
			// RSA加密
//			String data = "待加密的文字内容";
//			String encryptData = encrypt(data, getPublicKey(publicKey));
//			String encryptData = "XJ+2A3aiu8H3Cogve3b/CR9ugId1Mm4Zg1ylNbdpV/XJskqJ0ctFMre/9IQO+pG9djqmyviij49cBAy+H9vf5/BrQhu7tG/bNcBm4yTKr89rTmna7HSZd1sAvEaA4g8y2XbXzEBANmP2P6fM3zBZEJpMM/aJemj+9Q+V/ltCfrc=";
//			System.out.println("加密后内容:" + encryptData);
//			// RSA解密
//			String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
//			System.out.println("解密后内容:" + decryptData);
//			String data = decryptData;
			// RSA签名
//			String sign = sign(data, getPrivateKey(privateKey));
//			// RSA验签
//			boolean result = verify(data, getPublicKey(publicKey), sign);
//			System.out.print("验签结果:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("加解密异常");
		}
	}
}
