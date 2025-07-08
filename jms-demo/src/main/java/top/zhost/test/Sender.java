package top.zhost.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Sender {
    //Ĭ�ϴ����ַ "failover://tcp://localhost:61616"  ��������ַ��ͬIP�޸Ĳ�ͬ��IP
    //private static final String BROKER_URL=ActiveMQConnection.DEFAULT_BROKER_URL;
	private static final String BROKER_URL="failover://tcp://sub.zhost.top:61616";
    //��Ϣ��������
    private static final String SUBJECT="zz-queue";
    private static int i=1;
    public static void main(String[] args) throws JMSException, InterruptedException {
        //��ʼ�����ӹ���
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("admin","zz@Artemis",BROKER_URL);
        //��������
        Connection conn=  connectionFactory.createConnection();
        conn.setClientID("zz_jms_"+System.currentTimeMillis());
        //��������
        conn.start();
        //����Session���˷�����һ��������ʾ�Ự�Ƿ���������ִ�У��ڶ��������趨�Ự��Ӧ��ģʽ
        Session session= conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //����Ŀ�����
        Destination dest = session.createQueue(SUBJECT);
        //ͨ��session������Ϣ�ķ�����
        MessageProducer producer=session.createProducer(dest);
        while(true){
            //����Ҫ���͵���Ϣ
            TextMessage message= session.createTextMessage("======ActiveMQ������Ϣ===="+i+"===");
            //������Ϣ
            producer.send(message);
            System.out.println("message:"+message);
            //����2��
            Thread.sleep(2000);
            i++;
        }
//      conn.close();

    }

}
