package com;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

public class IoDemo {

	public static void main(String[] args) {
		// 从文件中获取缓冲流
		BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
		BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");

		// 拷贝文件
		IoUtil.copy(in, out);

	}

}
