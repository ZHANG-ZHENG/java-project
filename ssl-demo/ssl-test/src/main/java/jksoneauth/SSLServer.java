package jksoneauth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

/*********************************************************************************************************************** 
 * <ul> 
 * <li>1)���ɷ����˽Կ</li> 
 * <li>keytool -genkey -alias serverkey -keystore kserver.keystore</li> 
 * <li>2)����˽Կ,���������֤��</li> 
 * <li>keytool -exoport -alias serverkey -keystore kserver.keystore -file server.crt</li> 
 * <li>3)��֤����뵽�ͻ��������ε�keystore��</li> 
 * <li>keytool -import -alias serverkey -file server.crt -keystore tclient.keystore</li> 
 * </ul> 
 **********************************************************************************************************************/

/**
 * SSL Server
 */
public class SSLServer {

    private static final int DEFAULT_PORT = 7777;

    private static final String SERVER_KEY_STORE_PASSWORD = "123456";

    private SSLServerSocket serverSocket;

    /**
     * ��������
     * 
     * @param args
     */
    public static void main(String[] args) {
        SSLServer server = new SSLServer();
        server.init();
        server.start();
    }

    /**
     * <ul>
     * <li>��SSL Server Socket</li>
     * <li>���ڸó�������ʾSocket���������Լ򵥲��õ��߳���ʽ�����ҽ������ܿͻ��˵���Ϣ�����ҷ��ؿͻ���ָ����Ϣ</li>
     * </ul>
     */
    public void start() {
        if (serverSocket == null) {
            System.out.println("ERROR");
            return;
        }
        while (true) {
            try {
                Socket s = serverSocket.accept();
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                BufferedInputStream bis = new BufferedInputStream(input);
                BufferedOutputStream bos = new BufferedOutputStream(output);

                byte[] buffer = new byte[20];
                bis.read(buffer);
                System.out.println(new String(buffer));

                bos.write("Server Reply zz".getBytes());
                bos.flush();

                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * <ul>
     * <li>ssl���ӵ��ص�:</li>
     * <li>��ʼ��SSLServerSocket</li>
     * <li>��������˽ԿKeyStore���������������ε�KeyStore(�ͻ��˵�֤��)</li>
     * </ul>
     */
    public void init() {
        try {
        	String kserverKeystorePath = this.getClass().getClassLoader().getResource("keystore.jks").getPath();//sChat.jks
        	System.out.println(kserverKeystorePath);
        	
//        	String tclientKeystorePath = this.getClass().getClassLoader().getResource("tclient.keystore").getPath();
//        	System.out.println(tclientKeystorePath);
        	
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(kserverKeystorePath),SERVER_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());

//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
//            KeyStore tks = KeyStore.getInstance("JKS");
//            tks.load(new FileInputStream(tclientKeystorePath),SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
//            tmf.init(tks);

            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(kmf.getKeyManagers(), null, null); // ������֤
            //ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);// ˫����֤

            serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(DEFAULT_PORT);
            //serverSocket.setNeedClientAuth(true); // false���߲�������Ϊ������֤
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

