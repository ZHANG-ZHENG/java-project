package keystore;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

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

            bos.write("Client Message zz".getBytes());
            bos.flush();

            byte[] buffer = new byte[20];
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
        	String kclientKeystorePath = this.getClass().getClassLoader().getResource("kclient.keystore").getPath();
        	System.out.println(kclientKeystorePath);
        	String tserverKeystorePath = this.getClass().getClassLoader().getResource("tserver.keystore").getPath();
        	System.out.println(tserverKeystorePath);        	
        	
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(kclientKeystorePath),CLIENT_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());

            KeyStore tks = KeyStore.getInstance("JKS");
            tks.load(new FileInputStream(tserverKeystorePath),CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(tks);

            SSLContext ctx = SSLContext.getInstance("SSL");
            //ctx.init(null, tmf.getTrustManagers(), null); // 单向验证
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null); // 双向验证

            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
