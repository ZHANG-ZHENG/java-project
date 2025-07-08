package top.zhost.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver implements MessageListener{
    //Ĭ�ϴ����ַ "failover://tcp://localhost:61616"  ��������ַ��ͬIP�޸Ĳ�ͬ��IP
    private static final String BROKER_URL="failover://tcp://sub.zhost.top:61616";
    //��Ϣ��������
    private static final String SUBJECT="zz-queue";
    public static void main(String[] args) throws JMSException {
        //��ʼ�����ӹ���
    	ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("admin","zz@Artemis",BROKER_URL);
        //��������
        Connection conn= connectionFactory.createConnection();
        //��������
        conn.start();
        //����Session���˷�����һ��������ʾ�Ự�Ƿ���������ִ�У��ڶ��������趨�Ự��Ӧ��ģʽ
        Session session= conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //����Ŀ�����
        Destination dest=session.createQueue(SUBJECT);
        //ͨ��session������Ϣ�Ľ�����
        MessageConsumer consumer= session.createConsumer(dest);
        //��ʼ������
        Receiver receiver=new Receiver();
        //����������Ӽ�������
        consumer.setMessageListener(receiver);
    }

    public void onMessage(Message arg0) {
        TextMessage message=(TextMessage) arg0;
        try {
            System.out.println(message.getText());
            //Thread.sleep(1000);
        } catch (Exception e) {
        }

    }
}
