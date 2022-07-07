package keystoreoneauth;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * SSL Client
 */
public class SSLClientMyTrust {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 7777;


    private SSLSocket sslSocket;

    /**
     * �����ͻ��˳���
     * 
     * @param args
     */
    public static void main(String[] args) {
        SSLClientMyTrust client = new SSLClientMyTrust();
        client.init();
        client.process();
    }

    /**
     * ͨ��ssl socket�����˽�������,���ҷ���һ����Ϣ
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

            bos.write("Client Message noAuth".getBytes());
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
     * <li>ssl���ӵ��ص�:</li>
     * <li>��ʼ��SSLSocket</li>
     * <li>����ͻ���˽ԿKeyStore������ͻ��������ε�KeyStore(����˵�֤��)</li>
     * </ul>
     */
    public void init() {
        try {
            X509TrustManager x509m = new X509TrustManager() {                 
                @Override  
                public X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
          
                @Override  
                public void checkServerTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException {  
                }  
          
                @Override  
                public void checkClientTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException {  
                }  
            };        	

            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, new TrustManager[]{x509m}, null); // ������֤

            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}