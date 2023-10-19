package com;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.util.Base64;

import javax.crypto.Cipher;

//https://www.bejson.com/enc/rsa/
public class Jtest {

	public static void main(String[] args) throws Exception {
		test1();//生成RSA密钥对
		
//		PublicKey publicKey = convertStringToPublicKey();
//        String plaintext = "test";
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));
//        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
//        System.out.println("Encrypted Text: " + encryptedText);		
	}
	

    public static void test1() {
    	try {
    		//生成RSA密钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // 设置密钥长度
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            System.out.println("Public Key: " + keyPair.getPublic());
            System.out.println("Private Key: " + keyPair.getPrivate());
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            //加密数据
            String plaintext = "zz, RSA Encryption!";
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Encrypted Text: " + encryptedText);
            //解密数据
            Cipher cipher2 = Cipher.getInstance("RSA");
            cipher2.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedBytes2 = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes2 = cipher2.doFinal(encryptedBytes2);
            String decryptedText = new String(decryptedBytes2, "UTF-8");
            System.out.println("Decrypted Text: " + decryptedText);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PublicKey convertStringToPublicKey() throws Exception {
    	String publicKeyPEM = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbyfJitY1nyE4Js8o01t/itKSjwtVwBbg5aN3ZiOUasXB9cym+iod0+2yygm9MWmkUD1H5OUEkxX/h/iA6z7LyDaj6Iz3sStHnUxVwkg+8LsxL9TK4ABHJf8NhKe/tqdg6N8hVwtNUyfRR5c9Ze+0EIkE59xxkJv8MT2p9Kx8wqQIDAQAB";
        String publicKeyPEMContent = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEMContent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }
    
}	