package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * �ͻ��������߼�
 */
public class DemoClient {

    public static String host = "127.0.0.1"; //������IP��ַ
    public static int port = 8000; //�������˿�

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer());
            //���ӿͻ���
            Channel channel = b.connect(host, port).sync().channel();

            //����̨����
            System.out.println("��������Ϣ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                //�����˷�������
                channel.writeAndFlush(line);
            }
        } finally {
            //�����˳����ͷ��̳߳���Դ
            group.shutdownGracefully();
        }
    }
}

