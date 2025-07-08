package top.zhost.test.zztest;

import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * �����ࣺ�����װ�ͻ��˵ĳ�ʼ����������
 */
public class ScpConnectionOptionWrapper {
	/**
	 * �˺� accessKey
	 */
	private String accessKey;
	/**
	 * �˺� secretKey
	 */
	private String secretKey;
	/**
	 * ��ҵ��ϵͳ�Լ����壬��Ҫ��֤ÿ�� tcp ���Ӷ���һ������֤ȫ��Ψһ�������ͬ�Ŀͻ��˶���tcp ���ӣ�ʹ������ͬ�� clientId
	 * �ᵼ�������쳣�Ͽ���clientId �ܳ��Ȳ��ó���64���ַ���
	 */
	private String clientId;
	/**
	 * �ڲ����Ӳ���
	 */
	private MqttConnectOptions mqttConnectOptions;

	/**
	 * Signature ��Ȩģʽ�¹��췽��
	 *
	 * @param clientId  MQ4IOT clientId���ͻ���SN
	 * @param accessKey �˺� accessKey
	 * @param secretKey �˺� secretKey
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
		mqttConnectOptions.setUserName("uclass");
		mqttConnectOptions.setPassword("UClass_Mq2@123".toCharArray());
		mqttConnectOptions.setCleanSession(true);
		mqttConnectOptions.setKeepAliveInterval(90);
		mqttConnectOptions.setAutomaticReconnect(true);
		mqttConnectOptions.setMqttVersion(MQTT_VERSION_3_1_1);
		mqttConnectOptions.setConnectionTimeout(5000);
		//ʹ��ssl���Ӷ˿�ʹ��1884��������֤
		if (ssl) {
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			// ��ʽ����
//          sc.init(null, null, null);
//			SSLSocketFactory factory = sc.getSocketFactory();
//			mqttConnectOptions.setSocketFactory(factory);
			// ��ʽ���� END
			// ���Ի���
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
			// ���Ի��� END
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
