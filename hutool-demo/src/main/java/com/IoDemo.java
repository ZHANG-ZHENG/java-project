package com;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

public class IoDemo {

	public static void main(String[] args) {
		// ���ļ��л�ȡ������
		BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
		BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");

		// �����ļ�
		IoUtil.copy(in, out);

	}

}
