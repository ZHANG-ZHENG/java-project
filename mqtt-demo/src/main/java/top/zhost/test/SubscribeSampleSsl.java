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

	static String username = "admin";  //ע������ �����Լ���mqtt�˺�����
    static String password = "zz@Artemis"; //ע������ �����Լ���mqtt�˺�����
    //String broker = "tcp://xxx.xx.xxx.xxx:1883"; //ע������Ҫ���Լ�mqtt���������ڵ�ַ
    static String broker = "ssl://itscreen.zz.com.cn:1884"; //ע������Ҫ���Լ�mqtt���������ڵ�ַ
    
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

            //���� ����mqtt������
            subscribeConnect();

            //���� ����mqtt������
            //... ��

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
        System.out.println("��������");
        if (mqttClient != null) {
            try {
                MqttConnectOptions connOpts = new MqttConnectOptions();

                connOpts.setCleanSession(true);
                connOpts.setMaxInflight(100000);
				
				//���������¼���ӣ���2��ע�͵�
                connOpts.setUserName(username);
                connOpts.setPassword(password.toCharArray());

                //ssl ���� , ����� TrustManager ���Լ�ʵ�ֵģ�û��ȥУ�����˵�֤��
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
                        	//���� topic Ϊtest ����Ϣ,��Ϣ����1
                            mqttClient.subscribe("test", 1);
                            System.out.println("�ɹ�����topicΪtest����Ϣ"); 
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
                        System.out.println("mqtt û��������:" + exception.getMessage());
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

	//MyTM ���Լ�ʵ�ֵ���֤�����࣬���沢��У�����˵�֤��ͷ���true,���óɹ���
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
            System.out.println("mqtt ʧȥ������");
        }

        public void deliveryComplete(IMqttDeliveryToken arg0)
        {
            System.out.println("mqtt ������ɣ�");
        }

        public void messageArrived(String topic, MqttMessage message)
                throws Exception
        {
            String content = new String(message.getPayload(), "utf-8");
            System.out.println("�յ�mqtt��Ϣ,topic: "+topic+" ,content: "+content);
        }
    }
}
