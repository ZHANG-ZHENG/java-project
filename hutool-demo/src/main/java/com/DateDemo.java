package com;

import java.time.LocalDateTime;
import java.util.Date;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

public class DateDemo {

	public static void main(String[] args) {
		// 获取年份
		int year = DateUtil.year(new Date());

		// 获取今天日期 yyyy-MM-dd格式
		String today = DateUtil.today();

		// 获取生肖
		String chineseZodiac = DateUtil.getChineseZodiac(1990);

		// 将毫秒转成方便阅读的时间，如3小时25分23秒232毫秒
		String readableTime = DateUtil.formatBetween(12323232);

		// 转为农历日期
		ChineseDate chineseDate = new ChineseDate(new Date());
		// 农历年份，如2021
		final int chineseYear = chineseDate.getChineseYear();
		// 农历月份，如腊月
		final String chineseMonthName = chineseDate.getChineseMonthName();
		// 农历日期，如初三
		final String chineseDay = chineseDate.getChineseDay();

		// 方便地将Date转换为LocalDateTime
		final LocalDateTime localDateTime = LocalDateTimeUtil.of(new Date());

		// 获取一天开始时间
		LocalDateTimeUtil.beginOfDay(localDateTime);
		// 获取一天结束时间
		LocalDateTimeUtil.endOfDay(localDateTime);
	}

}
