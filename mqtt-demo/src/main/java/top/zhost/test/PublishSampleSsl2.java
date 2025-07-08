package top.zhost.test;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import top.zhost.test.SubscribeSampleSsl.MyTM;

/**
 *������
 */
public class PublishSampleSsl2 {
    public static void main(String[] args) {

        String topic = "test";
        String content = "hello zz 1zzzzz ssl "+System.currentTimeMillis();
        int qos = 1;
        String broker = "ssl://sub.zhost.top:1884";
        String userName = "admin";
        String password = "zz123";
        String clientId = "pubClient";
        // �ڴ�洢
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            // �����ͻ���
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            // �������Ӳ���
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // ��������������������ʱ��ס״̬
            connOpts.setCleanSession(false);
            // �������ӵ��û���
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            
            try {
                //ssl ���� , ����� TrustManager ���Լ�ʵ�ֵģ�û��ȥУ�����˵�֤��
                TrustManager[] trustAllCerts = new TrustManager[1];
                TrustManager trustManager = new MyTM();
                trustAllCerts[0] = trustManager;
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, null);
                SocketFactory factory = sslContext.getSocketFactory();
                connOpts.setSocketFactory(factory);
				
			} catch (Exception e) {
				// TODO: handle exception
			}

            
            // ��������
            sampleClient.connect(connOpts);
            // ������Ϣ
            MqttMessage message = new MqttMessage(content.getBytes());
            // ������Ϣ�ķ�������
            message.setQos(qos);
            // ������Ϣ
            sampleClient.publish(topic, message);
            // �Ͽ�����
            sampleClient.disconnect();
            // �رտͻ���
            sampleClient.close();
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}