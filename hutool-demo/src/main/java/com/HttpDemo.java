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
		// 发送get请求
		String getResult = HttpUtil.get("https://www.baidu.com", params);

		// 发送post请求
		String postResult = HttpUtil.post("https://www.baidu.com", params);

		// 以application/json方式发送post请求
		String jsonPostResult = HttpUtil.post("https://www.baidu.com", "");

		// 下载文件，提供生命周期钩子
		HttpUtil.downloadFile("https://www.baidu.com", FileUtil.file("e:/"), new StreamProgress() {
		    @Override
		    public void start() {
		        System.out.println("开始下载");
		    }

		    @Override
		    public void progress(long progressSize) {
		        System.out.println("下载中，已下载" + FileUtil.readableFileSize(progressSize));
		    }

		    @Override
		    public void finish() {
		        System.out.println("下载完成");
		    }
		});		
	}
}
