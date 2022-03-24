package jks;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * SSL Client
 */
public class SSLClient {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 7777;

    private static final String CLIENT_KEY_STORE_PASSWORD = "123456";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";

    private SSLSocket sslSocket;

    /**
     * 启动客户端程序
     * 
     * @param args
     */
    public static void main(String[] args) {
        SSLClient client = new SSLClient();
        client.init();
        client.process();
    }

    /**
     * 通过ssl socket与服务端进行连接,并且发送一个消息
     */
    public void process() {
        if (sslSocket == null) {
            System.out.println("ERROR");
            return;
        }
        try {
            InputStream input = sslSocket.getInputStream();
            OutputStream output = sslSocket.getOutputStream();

            BufferedInputStream bis = new BufferedInputStream(input);
            BufferedOutputStream bos = new BufferedOutputStream(output);

            bos.write("Client Message jks".getBytes());
            bos.flush();
            
            byte[] buffer = new byte[1024];
            bis.read(buffer);
            System.out.println(new String(buffer));
            sslSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * <ul>
     * <li>ssl连接的重点:</li>
     * <li>初始化SSLSocket</li>
     * <li>导入客户端私钥KeyStore，导入客户端受信任的KeyStore(服务端的证书)</li>
     * </ul>
     */
    public void init() {
        try {

        	String jksPath = this.getClass().getClassLoader().getResource("keystore.jks").getPath();//sChat.jks
        	System.out.println(jksPath);        	
        	
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(jksPath),CLIENT_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());

            KeyStore tks = KeyStore.getInstance("JKS");
            tks.load(new FileInputStream(jksPath),CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(tks);

            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null); // 双向验证

            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

}
