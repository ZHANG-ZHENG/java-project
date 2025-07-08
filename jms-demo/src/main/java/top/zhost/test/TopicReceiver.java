package top.zhost.test;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @Description ���ڷ���/����ģʽ�������͵������߲���
 */
public class TopicReceiver {

    private static final String ACTIVEMQ_URL = "tcp://sub.zhost.top:61616";

    public static void main(String[] args) throws JMSException, InterruptedException {
        // �������ӹ���
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin","zz@Artemis",ACTIVEMQ_URL);
        // ��������
        Connection connection = activeMQConnectionFactory.createConnection();
        // ������
        connection.start();
        // �����Ự
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // ��������Ŀ��,����ʶ�������ƣ������߸��ݶ������ƽ�������
        Destination destination = session.createTopic("topicTest");
        // ����һ��������
        MessageProducer producer = session.createProducer(destination);
        // ���������10���ı���Ϣ����
        int i = 0;
        while(true){
        	i++;
            // �����ı���Ϣ
            TextMessage message = session.createTextMessage("��" + i + "���ı���Ϣ");
            //������Ϣ
            producer.send(message);
            //�ڱ��ش�ӡ��Ϣ
            System.out.println("�ѷ��͵���Ϣ��" + message.getText());
            Thread.sleep(2000);
        }
        //�ر�����
       // connection.close();
    }

}
