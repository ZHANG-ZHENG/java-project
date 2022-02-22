package com;

import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

public class ExcelDemo {

	public static void main(String[] args) {
		// ���ļ�ת��ΪExcelReader
		ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");

		// ��ȡ�����к��е�����
		List<List<Object>> data = reader.read();

		// ��ȡΪMap�б�Ĭ��excel��һ��Ϊ�����У�Map�е�keyΪ���⣬valueΪ�����Ӧ�ĵ�Ԫ��ֵ��
		List<Map<String,Object>> dataMap = reader.readAll();

		//����writer
		ExcelWriter writer = ExcelUtil.getWriter("d:/bbb.xlsx");

		// �������ر��ʱ��ʹ��BigExcelWriter���󣬿��Ա����ڴ����
		ExcelWriter bigWriter = ExcelUtil.getBigWriter("d:/bbb.xlsx");

		// ��������
		List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
		List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
		List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
		List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
		List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");
		List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

		// һ���Խ�����д��excel��
		writer.write(rows, true);

	}

}
