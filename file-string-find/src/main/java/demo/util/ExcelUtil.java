package demo.util;

import demo.bean.FileBean;
import demo.bean.FindStringBean;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static void exportToExcel(String filePath, Map<String, FileBean> fileBeanMap) {
        // 创建工作簿
        try {
            Workbook workbook = new XSSFWorkbook();

            createFileSheet(workbook, fileBeanMap);
            createStringSheet(workbook, fileBeanMap);

            // 写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            System.err.println("导出失败: " + e.getMessage());
        }
    }

    private static void createFileSheet(Workbook workbook, Map<String, FileBean> fileBeanMap){
        // 创建工作表
        Sheet sheet = workbook.createSheet("文件");

        // 创建样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] header = new String[]{"文件", "引用", "被引用", "文件路径"};
        Row headerRow = sheet.createRow(0);
        for (int j = 0; j < header.length; j++) {
            Cell cell = headerRow.createCell(j);
            cell.setCellValue(header[j]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(j, 30 * 256); // 30个字符宽
        }

        // 使用TreeMap自动按键排序
        //Map<String, Object> sortedMap = new TreeMap<>(originalMap);
        int rowIndex = 1;
        for (Map.Entry<String, FileBean> entry : fileBeanMap.entrySet()) {
            String key = entry.getKey();
            FileBean fileBean = entry.getValue();
            // System.out.println("Key: " + key + ", Value: " + value);

            Row row = sheet.createRow(rowIndex);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(fileBean.getFileName());
            Cell cell1 = row.createCell(1);
            cell1.setCellValue("0");
            Cell cell2 = row.createCell(2);
            cell2.setCellValue("0");
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(key);
            rowIndex++;
        }

    }

    private static void createStringSheet(Workbook workbook, Map<String, FileBean> fileBeanMap){
        // 创建工作表
        Sheet sheet = workbook.createSheet("字符");

        // 创建样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] header = new String[]{"文件", "位置", "字符", "行信息"};
        Row headerRow = sheet.createRow(0);
        for (int j = 0; j < header.length; j++) {
            Cell cell = headerRow.createCell(j);
            cell.setCellValue(header[j]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(j, 30 * 256); // 30个字符宽
        }

        // 使用TreeMap自动按键排序
        //Map<String, Object> sortedMap = new TreeMap<>(originalMap);
        int rowIndex = 1;
        for (Map.Entry<String, FileBean> entry : fileBeanMap.entrySet()) {
            String key = entry.getKey();
            FileBean fileBean = entry.getValue();
            // System.out.println("Key: " + key + ", Value: " + value);

            List<FindStringBean> findStringList = fileBean.getFindStringList();

            for(FindStringBean findStringBean : findStringList) {
                Row row = sheet.createRow(rowIndex);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(fileBean.getFileName());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue("第"+findStringBean.getLineIndex()+"行："+findStringBean.getStart()+"-"+findStringBean.getEnd());
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(findStringBean.getFindString());
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(findStringBean.getLineString().trim());
                rowIndex++;
            }


        }

    }
}
