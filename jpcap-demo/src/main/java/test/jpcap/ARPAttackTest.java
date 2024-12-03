package test.jpcap;

import jpcap.NetworkInterface;

public class ARPAttackTest {
    public static void main(String[] args) {
        NetworkInterface[] devices = ARPAttack.getAllNIC();;
        NetworkInterface device = devices[3];
        try {
            ARPAttack.attackHost(device, "10.52.24.79", "CE-22-33-44-55-66");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
