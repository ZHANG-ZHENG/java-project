package com;

public class Example {

    public static void main(String[] args) {
        Blockchain myBlockchain = new Blockchain();

        // ���һЩ����
        myBlockchain.addBlock(new Block(1, "Block 1 Data", ""));
        myBlockchain.addBlock(new Block(2, "Block 2 Data", ""));
        myBlockchain.addBlock(new Block(3, "Block 3 Data", ""));

        // ���������
        myBlockchain.printBlockchain();

        // �������������Ч��
        System.out.println("Is blockchain valid? " + myBlockchain.isValid());
    }
}
