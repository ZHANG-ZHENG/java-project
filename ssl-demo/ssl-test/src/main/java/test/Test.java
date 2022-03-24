package test;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Test {
    public static void main(String[] args) throws Exception {  
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
        // ��ȡһ��SSLContextʵ��  
        SSLContext s = SSLContext.getInstance("SSL");  
        // ��ʼ��SSLContextʵ��  
        s.init(null, new TrustManager[] { x509m },  
                new java.security.SecureRandom());  
        // ��ӡ���SSLContextʵ��ʹ�õ�Э��  
        System.out.println("ȱʡ��ȫ�׽���ʹ�õ�Э��: " + s.getProtocol());  
        // ��ȡSSLContextʵ����ص�SSLEngine  
        SSLEngine e = s.createSSLEngine();  
        System.out  
                .println("֧�ֵ�Э��: " + Arrays.asList(e.getSupportedProtocols()));  
        System.out.println("���õ�Э��: " + Arrays.asList(e.getEnabledProtocols()));  
        System.out.println("֧�ֵļ����׼�: "  
                + Arrays.asList(e.getSupportedCipherSuites()));  
        System.out.println("���õļ����׼�: "  
                + Arrays.asList(e.getEnabledCipherSuites()));  
    }  
}
