package com;

import java.util.Map;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

public class StringDemo {

	public static void main(String[] args) {
		String str = "123";
		// 判断字符串是否为null或空串
		boolean isEmpty = StrUtil.isEmpty(str);

		// 判断字符串是否为null或空串或空白字符
		boolean isBlank = StrUtil.isBlank(str);

		// 将字符串用指定字符填充到指定长度
		String filled = StrUtil.fillAfter(str, '*', 10);

		// 填充字符串模板
		//String format = StrUtil.format("a的值为{a}, b的值为{b}", Map.of("a", "aValue", "b", "bValue"));

		// 判断字符串是否为中文字符串
		boolean match = ReUtil.isMatch(ReUtil.RE_CHINESES, "中国人");
	}

}
