package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Jtest {

	public static void main(String[] args) throws Exception {

        //读取本地文件
        File file = new File("C:\\Users\\Administrator\\Desktop\\210803 CD型.xls");
        FileInputStream fis = new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        HSSFSheet sheet1 = workbook.getSheetAt(0);
        //获取当前sheet页的总行数
        int totalRowNums = sheet1.getPhysicalNumberOfRows();
        for (int i = 1; i < totalRowNums; i++) {
            Row row = sheet1.getRow(i);
            String valueNum = getCellValue(row.getCell(0));
            System.out.println(valueNum);
            String valueContent = getCellValue(row.getCell(4));
            if("".equals(valueContent) || null==valueContent){
            	 System.out.println(i+" valueContent="+valueContent);
            	 
            	 for (int j= 1; j < totalRowNums; j++) {
            		 Row rowJ = sheet1.getRow(j);
            		 String valueNumJ = getCellValue(rowJ.getCell(0));
            		 String valueContentJ = getCellValue(rowJ.getCell(4));
            		 if("".equals(valueContentJ) || null==valueContentJ) {
            			 continue;
            		 }
            		 if(valueNum.equals(valueNumJ) ) {
            			 Cell setCell = row.createCell(5);
            			 System.out.println("设置"+i +" 值="+valueContentJ);
            			 setCell.setCellValue(valueContentJ);
            			 break;
            		 }
            	 }
            }else {
            	 Cell setCell = row.createCell(5);
            	 setCell.setCellValue(valueContent);
            }
        }
        
        //保存到本地
        File fileOut = new File("C:\\Users\\Administrator\\Desktop\\210803 CD型-3.xls");
        FileOutputStream outputStream = new FileOutputStream(fileOut);
        //将Excel写入输出流中
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();


	}
	

    public static String getCellValue(Cell cell) {
    	if(cell==null) {
    		return null;
    	}
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            Double d = cell.getNumericCellValue();
            return String.valueOf(d.intValue());
        }
        return String.valueOf(cell.getStringCellValue());
    }

}	