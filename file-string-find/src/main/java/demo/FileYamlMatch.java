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
        try {
            String excelFilePath = Config.PROJECT_PATH + "lang.xlsx";
            Map<String, LangBean> langBeanMap = ExcelUtil.getLangBeanMap(excelFilePath, 1006, 1048);
            System.out.println("langBeanMap size: " + langBeanMap.size());

            String[] fileNames = new String[]{"tr", "id"};
            for (Map.Entry<String, LangBean> entry : langBeanMap.entrySet()) {
                for (String fileName : fileNames) {
                    // String fileName = "zh-CN";
                    String filePath = Config.PROJECT_PATH + "ui292\\locales\\" + fileName + ".yaml";
                    Path path = Paths.get(filePath);
                    // 读取所有行
                    List<String> lines = Files.readAllLines(path);
                    String key = entry.getKey();
                    Integer lineNumber = YamlUtil.getLineNumberOfKey(filePath, key);
                    System.out.println(fileName + " Key 在第 " + lineNumber + " 行");

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
                        System.out.println("原值：" + oldLine + " 新值：" + newLine);
                        // 修改指定行（lineNumber从1开始，List索引从0开始）
                        lines.set(lineNumber - 1, newLine);
                    }
                    // 写回文件
                    Files.write(path, lines);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}