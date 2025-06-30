package demo.util;

import demo.bean.FileBean;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static void exportToExcel(List<List<String>> data, String filePath, Map<String, FileBean> fileBeanMap) {
        // 创建工作簿
        try {
            Workbook workbook = new XSSFWorkbook();

            // 创建工作表
            Sheet sheet = workbook.createSheet("文件");

            // 创建样式
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // 填充数据
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i);
                List<String> rowData = data.get(i);

                for (int j = 0; j < rowData.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(rowData.get(j));

                    // 如果是表头行，应用样式
                    if (i == 0) {
                        cell.setCellStyle(headerStyle);
                    }
                }
            }

            // 自动调整列宽
            for (int i = 0; i < data.get(0).size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // 写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            System.err.println("导出失败: " + e.getMessage());
        }
    }
}
