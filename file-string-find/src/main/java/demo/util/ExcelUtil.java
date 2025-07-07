package demo.util;

import demo.bean.FileBean;
import demo.bean.FindStringBean;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;


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

        // 设置字体颜色
        CellStyle styleFont = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(IndexedColors.RED.getIndex()); // 使用预定义颜色
        styleFont.setFont(font);

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
                cell1.setCellValue("第"+findStringBean.getLineIndex()+"行"); //"第"+findStringBean.getLineIndex()+"行："+findStringBean.getStart()+"-"+findStringBean.getEnd()
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


                // 判断cell1和上一行是否相等
                Row rowBefore = sheet.getRow(rowIndex - 1);
                Row rowNow = sheet.getRow(rowIndex);
                String beforeVal = rowBefore.getCell(1).getStringCellValue();
                String val = rowNow.getCell(1).getStringCellValue();
                if (beforeVal.equals(val)) {
                    rowNow.getCell(1).setCellStyle(styleFont);
                }

                rowIndex++;
            }
        }
        // 3. 合并第一列内容相同的单元格
        mergeSameContentCells(sheet, 0); // 0表示第一列
        // mergeSameContentCells(sheet, 1);
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

            if (fileFromList == null) {
                continue;
            }

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



    /**
     * 合并指定列中内容相同的连续单元格
     * @param sheet 工作表
     * @param columnIndex 要处理的列索引(0-based)
     */
    public static void mergeSameContentCells(Sheet sheet, int columnIndex) {
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();

        int mergeStart = -1;
        String previousValue = null;

        for (int i = firstRow; i <= lastRow; i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null) continue;

            Cell currentCell = currentRow.getCell(columnIndex);
            String currentValue = currentCell == null ? "" : getCellValueAsString(currentCell);

            if (previousValue == null) {
                // 第一个单元格
                previousValue = currentValue;
                mergeStart = i;
            } else if (currentValue.equals(previousValue)) {
                // 内容相同，继续检查下一个
                if (i == lastRow) {
                    // 最后一行，需要合并
                    if (mergeStart != i) {
                        sheet.addMergedRegion(new CellRangeAddress(mergeStart, i, columnIndex, columnIndex));
                    }
                }
            } else {
                // 内容不同，合并之前的区域
                if (mergeStart != i - 1) {
                    sheet.addMergedRegion(new CellRangeAddress(mergeStart, i - 1, columnIndex, columnIndex));
                }
                // 开始新的合并区域
                mergeStart = i;
                previousValue = currentValue;
            }
        }
    }

    /**
     * 获取单元格内容为字符串
     */
    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
