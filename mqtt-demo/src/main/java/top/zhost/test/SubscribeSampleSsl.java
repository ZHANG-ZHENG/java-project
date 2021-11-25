package top.zhost.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class SubscribeSampleSsl {

    static MqttClientCallback mqttClientCallback = new MqttClientCallback();
    static MqttAsyncClient mqttClient = null;

	static String username = "admin";  //注意这里 填你自己的mqtt账号密码
    static String password = "Ruijie@Artemis"; //注意这里 填你自己的mqtt账号密码
    //String broker = "tcp://xxx.xx.xxx.xxx:1883"; //注意这里要填自己mqtt服务器所在地址
    static String broker = "ssl://itscreen.ruijie.com.cn:1884"; //注意这里要填自己mqtt服务器所在地址
    
    public static void main(String[] args) throws InterruptedException {
        start();
        while (true){
            Thread.sleep(10000);
        }
    }

    public static void start() {
        String clientId = "mqttserver" + String.valueOf(System.currentTimeMillis());

        try {
            mqttClient = new MqttAsyncClient(broker, clientId, new MemoryPersistence());
            mqttClient.setCallback(mqttClientCallback);

            //订阅 连接mqtt服务器
            subscribeConnect();

            //发布 连接mqtt服务器
            //... 略

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public static void subscribeConnect() {
        System.out.println("订阅连接");
        if (mqttClient != null) {
            try {
                MqttConnectOptions connOpts = new MqttConnectOptions();

                connOpts.setCleanSession(true);
                connOpts.setMaxInflight(100000);
				
				//如果匿名登录连接，这2行注释掉
                connOpts.setUserName(username);
                connOpts.setPassword(password.toCharArray());

                //ssl 连接 , 这里的 TrustManager 是自己实现的，没有去校验服务端的证书
                TrustManager[] trustAllCerts = new TrustManager[1];
                TrustManager tm = new MyTM();
                trustAllCerts[0] = tm;
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, null);
                SocketFactory factory = sc.getSocketFactory();
                connOpts.setSocketFactory(factory);
                connOpts.setHttpsHostnameVerificationEnabled(false);
                //

                mqttClient.connect(connOpts, null, new IMqttActionListener() {
                    public void onSuccess(IMqttToken asyncActionToken) {
                        try {
                        	//订阅 topic 为test 的消息,消息质量1
                            mqttClient.subscribe("test", 1);
                            System.out.println("成功订阅topic为test的消息"); 
                        } catch (MqttException me) {
                            System.out.println("reason " + me.getReasonCode());
                            System.out.println("msg " + me.getMessage());
                            System.out.println("loc " + me.getLocalizedMessage());
                            System.out.println("cause " + me.getCause());
                            System.out.println("excep " + me);
                            me.printStackTrace();
                        }
                    }

                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        System.out.println("mqtt 没有连接上:" + exception.getMessage());
                        exception.printStackTrace();
                    }
                });
            } catch (MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
                me.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
    }

	//MyTM 是自己实现的认证管理类，里面并有校验服务端的证书就返回true,永久成功！
    static class MyTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

    public static class MqttClientCallback implements MqttCallback{

        public void connectionLost(Throwable arg0)
        {
            System.out.println("mqtt 失去了连接");
        }

        public void deliveryComplete(IMqttDeliveryToken arg0)
        {
            System.out.println("mqtt 发送完成！");
        }

        public void messageArrived(String topic, MqttMessage message)
                throws Exception
        {
            String content = new String(message.getPayload(), "utf-8");
            System.out.println("收到mqtt消息,topic: "+topic+" ,content: "+content);
        }
    }
}
