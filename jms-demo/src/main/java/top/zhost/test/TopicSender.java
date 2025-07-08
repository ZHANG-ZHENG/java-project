package top.zhost.test;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @Description ���ڷ���/����ģʽ�������͵������߲���
 * https://www.cnblogs.com/xiguadadage/p/11217604.html
 */
public class TopicSender {

    private static final String ACTIVEMQ_URL =  "tcp://sub.zhost.top:61616";

    public static void main(String[] args) throws JMSException {
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
        // ����������
        MessageConsumer consumer = session.createConsumer(destination);
        // �������ѵļ���
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("���ѵ���Ϣ��" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
