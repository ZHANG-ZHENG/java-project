package com;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;

public class CacheDemo {
	public static void main(String[] args) {
		// �����Ƚ��ȳ��Ļ��棬�����ù���ʱ��
		FIFOCache<String, Object> cache = CacheUtil.newFIFOCache(1000, 3600 * 1000);

		// �򻺴������Ԫ��
		cache.put("a", 1);

		// �ӻ����ж�ȡԪ��
		cache.get("a");	
	}
}
