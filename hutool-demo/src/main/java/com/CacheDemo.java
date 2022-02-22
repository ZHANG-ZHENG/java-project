package com;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;

public class CacheDemo {
	public static void main(String[] args) {
		// 创建先进先出的缓存，并设置过期时间
		FIFOCache<String, Object> cache = CacheUtil.newFIFOCache(1000, 3600 * 1000);

		// 向缓存中添加元素
		cache.put("a", 1);

		// 从缓存中读取元素
		cache.get("a");	
	}
}
