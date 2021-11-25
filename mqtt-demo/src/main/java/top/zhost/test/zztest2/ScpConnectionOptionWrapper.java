package top.zhost.test.zztest2;

import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * 工具类：负责封装客户端的初始化参数设置
 */
public class ScpConnectionOptionWrapper {
	/**
	 * 账号 accessKey
	 */
	private String accessKey;
	/**
	 * 账号 secretKey
	 */
	private String secretKey;
	/**
	 * 由业务系统自己定义，需要保证每个 tcp 连接都不一样，保证全局唯一，如果不同的客户端对象（tcp 连接）使用了相同的 clientId
	 * 会导致连接异常断开。clientId 总长度不得超过64个字符。
	 */
	private String clientId;
	/**
	 * 内部连接参数
	 */
	private MqttConnectOptions mqttConnectOptions;

	/**
	 * Signature 鉴权模式下构造方法
	 *
	 * @param clientId  MQ4IOT clientId，客户端SN
	 * @param accessKey 账号 accessKey
	 * @param secretKey 账号 secretKey
	 * @throws Exception
	 */
	public ScpConnectionOptionWrapper(String clientId, String accessKey, String secretKey, boolean ssl) throws Exception {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.clientId = clientId;
		mqttConnectOptions = new MqttConnectOptions();
//		mqttConnectOptions.setUserName("Signature" + "|" + accessKey);
//		String password = Tools.hmacSHA1Encrypt(clientId, secretKey);
//		System.out.println(mqttConnectOptions.getUserName());
//		System.out.println(clientId);
//		System.out.println(password);
		mqttConnectOptions.setUserName("admin");
		mqttConnectOptions.setPassword("ruijie123".toCharArray());
		mqttConnectOptions.setCleanSession(true);
		mqttConnectOptions.setKeepAliveInterval(90);
		mqttConnectOptions.setAutomaticReconnect(true);
		mqttConnectOptions.setMqttVersion(MQTT_VERSION_3_1_1);
		mqttConnectOptions.setConnectionTimeout(5000);
		//使用ssl连接端口使用1884。单向认证
		if (ssl) {
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			// 正式环境
//          sc.init(null, null, null);
//			SSLSocketFactory factory = sc.getSocketFactory();
//			mqttConnectOptions.setSocketFactory(factory);
			// 正式环境 END
			// 测试环境
			sc.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return;
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} }, new java.security.SecureRandom());

			SSLSocketFactory factory = sc.getSocketFactory();
			mqttConnectOptions.setSocketFactory(factory);
			mqttConnectOptions.setHttpsHostnameVerificationEnabled(false);
			// 测试环境 END
		}
	}

	public MqttConnectOptions getMqttConnectOptions() {
		return mqttConnectOptions;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String getClientId() {
		return clientId;
	}
}
