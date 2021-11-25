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
 *发布端
 */
public class PublishSampleSsl {
    public static void main(String[] args) {

        String topic = "test";
        String content = "hello zz 1zzzzz ssl "+System.currentTimeMillis();
        int qos = 1;
        String broker = "ssl://itscreen.ruijie.com.cn:1884";
        String userName = "admin";
        String password = "Ruijie@Artemis";
        String clientId = "pubClient";
        // 内存存储
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            // 创建客户端
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            // 创建链接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 在重新启动和重新连接时记住状态
            connOpts.setCleanSession(false);
            // 设置连接的用户名
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            
            try {
                //ssl 连接 , 这里的 TrustManager 是自己实现的，没有去校验服务端的证书
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

            
            // 建立连接
            sampleClient.connect(connOpts);
            // 创建消息
            MqttMessage message = new MqttMessage(content.getBytes());
            // 设置消息的服务质量
            message.setQos(qos);
            // 发布消息
            sampleClient.publish(topic, message);
            // 断开连接
            sampleClient.disconnect();
            // 关闭客户端
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