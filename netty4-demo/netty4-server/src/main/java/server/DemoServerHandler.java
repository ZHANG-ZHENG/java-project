package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.Inet4Address;

/**
 * ������ҵ���߼�
 */
public class DemoServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client say : " + msg.toString());
        //���ؿͻ�����Ϣ - ���Ѿ����յ��������Ϣ
        ctx.writeAndFlush("Received your message : " + msg.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " active !");
        ctx.writeAndFlush("���ӳɹ���");
        super.channelActive(ctx);
    }
}