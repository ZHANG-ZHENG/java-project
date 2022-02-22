package com;

import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

public class ExcelDemo {

	public static void main(String[] args) {
		// 将文件转换为ExcelReader
		ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");

		// 读取所有行和列的数据
		List<List<Object>> data = reader.read();

		// 读取为Map列表，默认excel第一行为标题行，Map中的key为标题，value为标题对应的单元格值。
		List<Map<String,Object>> dataMap = reader.readAll();

		//创建writer
		ExcelWriter writer = ExcelUtil.getWriter("d:/bbb.xlsx");

		// 数据量特别大时，使用BigExcelWriter对象，可以避免内存溢出
		ExcelWriter bigWriter = ExcelUtil.getBigWriter("d:/bbb.xlsx");

		// 构造数据
		List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
		List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
		List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
		List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
		List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
		List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

		// 一次性将数据写入excel中
		writer.write(rows, true);

	}

}
