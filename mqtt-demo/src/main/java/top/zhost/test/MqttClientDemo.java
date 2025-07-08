package top.zhost.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientDemo {
    /**
     * ���������ip��ַ
     */
    public static final String MQTT_BROKER_HOST = "tcp://sub.zhost.top:1883";

    /**
     * �ͻ���Ψһ��ʶ
     */
    public static final String MQTT_CLIENT_ID = "zz_mqtt_test";

    /**
     * ���ı�ʶ
     */
//    public static final String MQTT_TOPIC = "xiasuhuei321";

    /**
     *
     */
    public static final String USERNAME = "admin";
    /**
     * ����
     */
    public static final String PASSWORD = "zz@Artemis";
    public static final String TOPIC_FILTER = "test";

    private volatile static MqttClient mqttClient;
    private static MqttConnectOptions options;
    
    private void testConnect() {
        try {
            // hostΪ��������clientid������MQTT�Ŀͻ���ID��һ���Կͻ���Ψһ��ʶ����ʾ��
            // MemoryPersistence����clientid�ı�����ʽ��Ĭ��Ϊ���ڴ汣��
            mqttClient = new MqttClient(MQTT_BROKER_HOST, MQTT_CLIENT_ID+System.currentTimeMillis(), new MemoryPersistence());
            // ���ò�����Ϣ
            options = new MqttConnectOptions();
            // �����Ƿ����session,�����������Ϊfalse��ʾ�������ᱣ���ͻ��˵����Ӽ�¼��
            // ��������Ϊtrue��ʾÿ�����ӵ������������µ��������
            options.setCleanSession(true);
            // �����û���
            options.setUserName(USERNAME);
            // ��������
            options.setPassword(PASSWORD.toCharArray());
            // ���ó�ʱʱ�� ��λΪ��
            options.setConnectionTimeout(10);
            // ���ûỰ����ʱ�� ��λΪ�� ��������ÿ��1.5*20���ʱ����ͻ��˷��͸���Ϣ�жϿͻ����Ƿ����ߣ������������û�������Ļ���
            options.setKeepAliveInterval(20);
            // ����
            mqttClient.connect(options);
            // ����
            mqttClient.subscribe(TOPIC_FILTER);
            // ���ûص�
            mqttClient.setCallback(new MqttCallback() {
              
                public void connectionLost(Throwable throwable) {
                    System.out.println("connectionLost");
                }

               
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println("Topic: " + s + " Message: " + mqttMessage.toString());
                }

                
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            Thread.sleep(10000);
            mqttClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }    	
    }

    public static void main(String... args) {
    	Thread t = new Thread() {
    		@Override
    		public void run() {
    			MqttClientDemo mqttClientDemo = new MqttClientDemo();
    			mqttClientDemo.testConnect();
    		}
    	};
    	t.start();

    }

}
