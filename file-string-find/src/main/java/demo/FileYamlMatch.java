package demo;
import demo.bean.LangBean;
import demo.config.Config;
import demo.util.ExcelUtil;
import demo.util.YamlUtil;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class FileYamlMatch {


    public static void main(String[] args) {
        int startRow = 162;
        int endRow = 158;
        if (startRow > endRow) {
            endRow = startRow;
        }
        try {
            String excelFilePath = Config.PROJECT_PATH + "lang.xlsx";
            Map<String, LangBean> langBeanMap = ExcelUtil.getLangBeanMap(excelFilePath, startRow, endRow);
            System.out.println("langBeanMap size: " + langBeanMap.size());

            String[] fileNames = new String[]{"tr", "id"};
            for (String fileName : fileNames) {
                int editCount = 0;
                // String fileName = "zh-CN";
                String filePath = Config.PROJECT_PATH + "ui292\\locales\\" + fileName + ".yaml";
                Path path = Paths.get(filePath);
                // 读取所有行
                List<String> lines = Files.readAllLines(path);
                for (Map.Entry<String, LangBean> entry : langBeanMap.entrySet()) {
                    String key = entry.getKey();
                    Integer lineNumber = YamlUtil.getLineNumberOfKey(filePath, key);
                    // System.out.println(fileName + " Key 在第 " + lineNumber + " 行");

                    if (lineNumber != null) {
                        String newContent = null;
                        if (fileName.equals("tr")) {
                            newContent = entry.getValue().getTr();
                        } else if (fileName.equals("id")) {
                            newContent = entry.getValue().getId();
                        }
                        if (newContent == null) {
                            continue;
                        }

                        // 检查行号是否有效
                        if (lineNumber < 1 || lineNumber > lines.size()) {
                            throw new IllegalArgumentException("行号超出范围: " + lineNumber + "，文件共有 " + lines.size() + " 行");
                        }
                        String oldLine = lines.get(lineNumber - 1);
                        String newLine = oldLine.substring(0, oldLine.indexOf(":")) + ": " + newContent + " # " + fileName;
                        if (oldLine.equals(newLine)) {
                            System.out.println(fileName + " key:" + key + " 原值=新值" + oldLine );
                        } else {
                            System.out.println(fileName + " key:" + key + " 原值：" + oldLine + " 新值：" + newLine);
                            lines.set(lineNumber - 1, newLine);
                            editCount++;
                        }
                    }
                }
                // 写回文件
                Files.write(path, lines);
                System.out.println(fileName + " editCount:" + editCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}