package demo.util;

import demo.bean.FileBean;
import demo.bean.FindStringBean;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static void exportToExcel(String filePath, Map<String, FileBean> fileBeanMap, Map<String, FileBean> fromFileBeanMap) {
        // 创建工作簿
        try {
            Workbook workbook = new XSSFWorkbook();

            createFileSheet(workbook, fileBeanMap, "源文件");
            createStringSheet(workbook, fileBeanMap, "源文件字符");
            createFromSheet(workbook, fileBeanMap);

            createFileSheet(workbook, fromFileBeanMap,"依赖文件");
            createStringSheet(workbook, fromFileBeanMap, "依赖文件字符");

            // 写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            System.err.println("导出失败: " + e.getMessage());
        }
    }

    private static void createFileSheet(Workbook workbook, Map<String, FileBean> fileBeanMap, String sheetName){
        // 创建工作表
        Sheet sheet = workbook.createSheet(sheetName);

        // 创建样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] header = new String[]{"文件", "依赖数", "文件路径"};
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
            if (fileBean.getFileFromList() != null) {
                cell1.setCellValue(fileBean.getFileFromList().size());
            } else {
                cell1.setCellValue("-");
            }
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(key);
            rowIndex++;
        }

    }

    private static void createStringSheet(Workbook workbook, Map<String, FileBean> fileBeanMap, String sheetName){
        // 创建工作表
        Sheet sheet = workbook.createSheet(sheetName);

        // 创建样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] header = new String[]{"文件", "位置", "字符", "zh", "en", "行信息"};
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
                if (findStringBean.getZhString() != null) {
                    cell3.setCellValue(findStringBean.getZhString());
                } else {
                    cell3.setCellValue(findStringBean.getFindString());
                }
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(findStringBean.getEnString());
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(findStringBean.getLineString().trim());
                rowIndex++;
            }
        }
    }

    private static void createFromSheet(Workbook workbook, Map<String, FileBean> fileBeanMap){
        // 创建工作表
        Sheet sheet = workbook.createSheet("源文件依赖");

        // 创建样式
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] header = new String[]{"文件", "位置", "依赖", "依赖类型", "依赖文件路径"};
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

            List<FileBean> fileFromList = fileBean.getFileFromList();

            for(FileBean fileFrom : fileFromList) {
                Row row = sheet.createRow(rowIndex);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(fileBean.getFileName());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue("第" + fileFrom.getFromFileLineIndex() + "行");
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(fileFrom.getFileNameFrom());
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(fileFrom.getFromType());
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(fileFrom.getFilePath());

//                Cell cell5 = row.createCell(5);
//                cell5.setCellValue(findStringBean.getLineString().trim());
                rowIndex++;
            }
        }
    }
}
