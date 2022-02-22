package com;

import java.security.KeyPair;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

public class SecureDemo {

	public static void main(String[] args) {
		// md5ժҪ����
		String md5 = SecureUtil.md5("abc");

		// sha1ժҪ����
		String sha1 = SecureUtil.sha1("abc");

		// ���ɷǶԳ���Կ��
		KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
		String publicKey = Base64Encoder.encode(keyPair.getPublic().getEncoded());
		String privateKey = Base64Encoder.encode(keyPair.getPrivate().getEncoded());

		// ���ù�Կ����
		String encryptBase64 = SecureUtil.rsa(privateKey, publicKey).encryptBase64("abc", KeyType.PublicKey);

		// ����˽Կ����
		String decrypt = new String(SecureUtil.rsa(privateKey, publicKey).decrypt(encryptBase64, KeyType.PrivateKey));
		//-------------------------------------------
		
		
		// ����ǩ������
		Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);

		// ����ǩ��
		final byte[] bytes = "abc".getBytes();
		byte[] signed = sign.sign(bytes);

		// ��֤ǩ��
		boolean verify = sign.verify(bytes, signed);
		System.out.println(verify);

	}

}
