package com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

public class Blockchain {
    private ArrayList<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<Block>();
        // ������������
        addBlock(new Block(0, "Genesis Block", "0"));
    }

    public void addBlock(Block newBlock) {
        // �����������ǰһ�������ϣֵ
        if (chain.size() > 0) {
            Block previousBlock = chain.get(chain.size() - 1);
            newBlock = new Block(newBlock.getIndex(), newBlock.getData(), previousBlock.getHash());
        }
        chain.add(newBlock);
    }

    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // ����ϣֵ
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // ���ǰһ������Ĺ�ϣֵ
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Index: " + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Data: " + block.getData());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println();
        }
    }
}