package top.zhost.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *���Ķ�
 */
public class SubscribeSample {

    public static void main(String[] args) throws MqttException {
        String HOST = "tcp://sub.zhost.top:1883";
        String TOPIC = "test2";
        int qos = 1;
        String clientid = "subClient";
        String userName = "admin";
        String passWord = "zz123";
        try {
            // hostΪ��������testΪclientid������MQTT�Ŀͻ���ID��һ���Կͻ���Ψһ��ʶ����ʾ��MemoryPersistence����clientid�ı�����ʽ��Ĭ��Ϊ���ڴ汣��
            MqttClient client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT����������
            MqttConnectOptions options = new MqttConnectOptions();
            // �����Ƿ����session,�����������Ϊfalse��ʾ�������ᱣ���ͻ��˵����Ӽ�¼����������Ϊtrue��ʾÿ�����ӵ������������µ��������
            options.setCleanSession(true);
            // �������ӵ��û��� �������ӵ�����
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            // ���ó�ʱʱ�� ��λΪ��
            options.setConnectionTimeout(10);
            // ���ûỰ����ʱ�� ��λΪ�� ��������ÿ��1.5*20���ʱ����ͻ��˷��͸���Ϣ�жϿͻ����Ƿ����ߣ������������û�������Ļ���
            options.setKeepAliveInterval(20);
            // ���ûص�����
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost");
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("topic:"+topic);
                    System.out.println("Qos:"+message.getQos());
                    System.out.println("message content:"+new String(message.getPayload()));

                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------"+ token.isComplete());
                }

            });
            client.connect(options);
            //������Ϣ
            client.subscribe(TOPIC, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
