package top.zhost.test.zztest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



public class MQTTScpSignatureDemo {
	final static String SAVE_DEST = "server/report"; // 客户端图元数据上传topic：server.save。图元操作发布topic
	final static String P2P_MESSAGE_PREFIX = "client/p2p/"; // 点对点消息topic。如果不订阅client/#会收不到云端的回调和专门发给当前客户端的消息

    public static void main(String[] args) throws Exception {
    	
        /**
         * 	接入点地址
         */
        String url = "ssl://192.168.51.225:11884";
//        String url = "tcp://localhost:1883";
        /**
         * 	账号 secretKey，仅在Signature鉴权模式下需要设置。公钥就是这个，后期应该不会变，请妥善保管，不要泄露
         */
        String accessKey = "ZSwH7ct6";
        String secretKey = "caed8c7060ed2ebfd1d42311d9ef4ea1ae967a2f";
        /**
         * 	客户端SN，clientId 总长度不得超过64个字符。
         */
        String clientId = "test_zz11112";
        /**
         * QoS参数代表传输质量，可选0，1，2，根据实际需求合理设置
         */
        final int qosLevel = 1;
        
        ScpConnectionOptionWrapper connectionOptionWrapper = new ScpConnectionOptionWrapper(clientId, accessKey, secretKey, true);
        final MemoryPersistence memoryPersistence = new MemoryPersistence();
        final MqttAsyncClient mqttClient = new MqttAsyncClient(url, clientId, memoryPersistence);
        /**
         * 	客户端设置好发送超时时间，防止无限阻塞
         */
//        mqttClient.setTimeToWait(5000);
        final ExecutorService executorService = new ThreadPoolExecutor(1, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                /**
                 * 	客户端连接成功后就需要尽快订阅需要的 topic
                 */
//                System.out.println("connect success");
//                executorService.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            final String topicFilter[] = {P2P_MESSAGE_PREFIX + clientId}; // 订阅p2p的topic
//                            final int[] qos = {1};
//                            mqttClient.subscribe(topicFilter, qos);
//                            System.out.println("subscribe topics: " + P2P_MESSAGE_PREFIX + clientId);
//                        } catch (MqttException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
            }

            @Override
            public void connectionLost(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                /**
                 * 	消费消息的回调接口，需要确保该接口不抛异常，该接口运行返回即代表消息消费成功。
                 * 	消费消息需要保证在规定时间内完成，如果消费耗时超过服务端约定的超时时间，对于可靠传输的模式，服务端可能会重试推送，业务需要做好幂等去重处理。
                 * (注：云端的图元数据持久化成功后会发送callback消息（会通过p2p的topic发送，所以一定要订阅p2p）。)
                 */
                System.out.println(
                    "receive msg from topic " + s + " , body is " + new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                System.out.println("send msg succeed topic is : " + iMqttDeliveryToken.getTopics()[0]);
            }
        });
        try {
        	mqttClient.connect(connectionOptionWrapper.getMqttConnectOptions(), null, new IMqttActionListener() {
    			
    			@Override
    			public void onSuccess(IMqttToken asyncActionToken) {
    				// TODO Auto-generated method stub
    				
    			}
    			
    			@Override
    			public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
    				System.out.println(exception.getMessage());
    				try {
    					mqttClient.connect();
    				} catch (MqttException e) {
    					System.out.println("e " + e.getMessage());
    					e.printStackTrace();
    				}
    			}
    		});
        }catch (Throwable e) {
        	System.out.println("g " + e.getMessage());
			e.printStackTrace();
		}
        Thread.sleep(2000);
        for (int i = 0; i < 10; i++) {
            /**
             * 	支持点对点发送消息给对方，即如果发送方明确知道该消息只需要给特定的一个设备接收，且知道对端的 clientId，则可以直接发送点对点消息。
             * 	点对点消息的 topic 格式规范是  client/p2p/{{targetClientId}}
             */
            MqttMessage message = new MqttMessage(("hello mq4Iot p2p msg: " + i).getBytes());
            message.setQos(qosLevel);
            mqttClient.publish(SAVE_DEST, message, null,
        			new IMqttActionListener() {

						@Override
						public void onSuccess(IMqttToken asyncActionToken) {
							System.out.println("Success：");
						}

						@Override
						public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
							System.out.println("Failure：" + exception.getMessage());
						}
						
            	
            });
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}