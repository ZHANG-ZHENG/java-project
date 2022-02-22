package com;

import java.time.LocalDateTime;
import java.util.Date;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

public class DateDemo {

	public static void main(String[] args) {
		// ��ȡ���
		int year = DateUtil.year(new Date());

		// ��ȡ�������� yyyy-MM-dd��ʽ
		String today = DateUtil.today();

		// ��ȡ��Ф
		String chineseZodiac = DateUtil.getChineseZodiac(1990);

		// ������ת�ɷ����Ķ���ʱ�䣬��3Сʱ25��23��232����
		String readableTime = DateUtil.formatBetween(12323232);

		// תΪũ������
		ChineseDate chineseDate = new ChineseDate(new Date());
		// ũ����ݣ���2021
		final int chineseYear = chineseDate.getChineseYear();
		// ũ���·ݣ�������
		final String chineseMonthName = chineseDate.getChineseMonthName();
		// ũ�����ڣ������
		final String chineseDay = chineseDate.getChineseDay();

		// ����ؽ�Dateת��ΪLocalDateTime
		final LocalDateTime localDateTime = LocalDateTimeUtil.of(new Date());

		// ��ȡһ�쿪ʼʱ��
		LocalDateTimeUtil.beginOfDay(localDateTime);
		// ��ȡһ�����ʱ��
		LocalDateTimeUtil.endOfDay(localDateTime);
	}

}
