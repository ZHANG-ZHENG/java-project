package demo;

import demo.bean.FileBean;
import demo.bean.FindStringBean;
import demo.util.ExcelUtil;
import demo.util.FileUtil;
import demo.util.YamlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileJavaStringFind {
    public static void main(String[] args) {
        String folderPath = "F:\\workspace\\workspace-security-cloud2802\\security\\security-rule";
        List<String> filesPathALL = FileUtil.getAllFiles(folderPath);
        // 剔除文件
        List<String> filesPath = new ArrayList<>();
        for (String filePath : filesPathALL) {
            if (filePath.endsWith("pom.xml") || filePath.endsWith(".jar") || filePath.endsWith(".jar.original") || filePath.endsWith(".class") || filePath.contains("\\target\\")) {
                continue;
            }
            filesPath.add(filePath);
        }
        filesPath.forEach(System.out::println);

        Map<String, FileBean> fileBeanMap = new HashMap<>();
        for(String fPath : filesPath) {
            FileBean fileBean = new FileBean();
            fileBean.setFilePath(fPath);
            fileBeanMap.put(fPath, fileBean);
            FileUtil.fingJavaString(fileBean);
        }

        // 依赖
        Map<String, FileBean> fromFileBeanMap = new HashMap<>();

        String savePath = "F:\\output-java.xlsx";
        ExcelUtil.exportToExcel(savePath, fileBeanMap, fromFileBeanMap);
    }
}
