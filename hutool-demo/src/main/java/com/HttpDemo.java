package com;

import java.util.Map;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;

public class HttpDemo {
	public static void main(String[] args) {
		Map<String, Object> params = MapUtil.<String, Object>builder().put("a", 1).build();
		// ����get����
		String getResult = HttpUtil.get("https://www.baidu.com", params);

		// ����post����
		String postResult = HttpUtil.post("https://www.baidu.com", params);

		// ��application/json��ʽ����post����
		String jsonPostResult = HttpUtil.post("https://www.baidu.com", "");

		// �����ļ����ṩ�������ڹ���
		HttpUtil.downloadFile("https://www.baidu.com", FileUtil.file("e:/"), new StreamProgress() {
		    @Override
		    public void start() {
		        System.out.println("��ʼ����");
		    }

		    @Override
		    public void progress(long progressSize) {
		        System.out.println("�����У�������" + FileUtil.readableFileSize(progressSize));
		    }

		    @Override
		    public void finish() {
		        System.out.println("�������");
		    }
		});		
	}
}
