package top.zhost.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttServerDemo {
    /**
     * ���������ip��ַ
     */
    public static final String MQTT_BROKER_HOST = "tcp://zhost.top:1883";

    /**
     * ���ı�ʶ
     */
    public static final String MQTT_TOPIC = "topic2";

    private static String userName = "admin";
    private static String password = "password";

    /**
     * �ͻ���Ψһ��ʶ
     */
    public static final String MQTT_CLIENT_ID = "zz_server_test";
    private static MqttTopic topic;
    private static MqttClient client;

    public static void main(String... args) {
        // ������Ϣ
        MqttMessage message = new MqttMessage();
        try {
            client = new MqttClient(MQTT_BROKER_HOST, MQTT_CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            //options.setUserName(userName);
            //options.setPassword(password.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);

            topic = client.getTopic(MQTT_TOPIC);

            message.setQos(1);
            message.setRetained(false);
            String messageContent = "message from server "+System.currentTimeMillis();
            message.setPayload(messageContent.getBytes());
            client.connect(options);

            while (true) {
                MqttDeliveryToken token = topic.publish(message);
                token.waitForCompletion();
                System.out.println("server�Ѿ����� ");
                Thread.sleep(10000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}