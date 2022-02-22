package com;

import java.security.KeyPair;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

public class SecureDemo {

	public static void main(String[] args) {
		// md5摘要加密
		String md5 = SecureUtil.md5("abc");

		// sha1摘要加密
		String sha1 = SecureUtil.sha1("abc");

		// 生成非对称密钥对
		KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
		String publicKey = Base64Encoder.encode(keyPair.getPublic().getEncoded());
		String privateKey = Base64Encoder.encode(keyPair.getPrivate().getEncoded());

		// 利用公钥加密
		String encryptBase64 = SecureUtil.rsa(privateKey, publicKey).encryptBase64("abc", KeyType.PublicKey);

		// 利用私钥解密
		String decrypt = new String(SecureUtil.rsa(privateKey, publicKey).decrypt(encryptBase64, KeyType.PrivateKey));
		//-------------------------------------------
		
		
		// 创建签名对象
		Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);

		// 生成签名
		final byte[] bytes = "abc".getBytes();
		byte[] signed = sign.sign(bytes);

		// 验证签名
		boolean verify = sign.verify(bytes, signed);
		System.out.println(verify);

	}

}
