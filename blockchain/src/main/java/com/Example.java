package com;

public class Example {

    public static void main(String[] args) {
        Blockchain myBlockchain = new Blockchain();

        // 添加一些区块
        myBlockchain.addBlock(new Block(1, "Block 1 Data", ""));
        myBlockchain.addBlock(new Block(2, "Block 2 Data", ""));
        myBlockchain.addBlock(new Block(3, "Block 3 Data", ""));

        // 输出区块链
        myBlockchain.printBlockchain();

        // 检查区块链的有效性
        System.out.println("Is blockchain valid? " + myBlockchain.isValid());
    }
}
