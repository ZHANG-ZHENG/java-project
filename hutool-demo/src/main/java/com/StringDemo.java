package com;

import java.util.Map;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

public class StringDemo {

	public static void main(String[] args) {
		String str = "123";
		// �ж��ַ����Ƿ�Ϊnull��մ�
		boolean isEmpty = StrUtil.isEmpty(str);

		// �ж��ַ����Ƿ�Ϊnull��մ���հ��ַ�
		boolean isBlank = StrUtil.isBlank(str);

		// ���ַ�����ָ���ַ���䵽ָ������
		String filled = StrUtil.fillAfter(str, '*', 10);

		// ����ַ���ģ��
		//String format = StrUtil.format("a��ֵΪ{a}, b��ֵΪ{b}", Map.of("a", "aValue", "b", "bValue"));

		// �ж��ַ����Ƿ�Ϊ�����ַ���
		boolean match = ReUtil.isMatch(ReUtil.RE_CHINESES, "�й���");
	}

}
