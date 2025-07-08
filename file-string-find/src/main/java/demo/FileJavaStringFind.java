package demo;

import demo.bean.FileBean;
import demo.bean.FindStringBean;
import demo.util.ExcelUtil;
import demo.util.FileUtil;
import demo.util.PropertyUtil;
import demo.util.YamlUtil;

import java.util.*;

public class FileJavaStringFind {
    public static void main(String[] args) {
        FileJavaStringFind.export("F:\\workspace\\workspace-security-cloud2802\\security\\security-rule", "F:\\output-rule.xlsx");
        //FileJavaStringFind.export("F:\\workspace\\workspace-security-cloud2802\\security\\security-devicelogs", "F:\\output-devicelogs.xlsx");
        //FileJavaStringFind.export("F:\\workspace\\workspace-security-cloud2802\\security\\security-device", "F:\\output-device.xlsx");
       // FileJavaStringFind.export("F:\\workspace\\workspace-security-cloud2802\\security\\security-upms", "F:\\output-upms.xlsx");
    }

    public static void export(String folderPath, String savePath) {
        List<String> filesPathALL = FileUtil.getAllFiles(folderPath);
        // 剔除文件
        List<String> filesPath = new ArrayList<>();
        for (String filePath : filesPathALL) {
            if (filePath.endsWith("pom.xml") || filePath.endsWith(".jar") || filePath.endsWith(".jar.original") || filePath.endsWith(".class") || filePath.contains("\\target\\")) {
                continue;
            }
            if (filePath.endsWith(".html") || filePath.endsWith(".sql")) {
                continue;
            }
            if (filePath.contains("\\src\\test\\java\\")){
                continue;
            }
            if (filePath.endsWith(".awdb") || filePath.endsWith(".mmdb") || filePath.endsWith(".ttf")
                    || filePath.endsWith(".log.gz") || filePath.endsWith(".log")
            ) {
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

        // 翻译
        Properties zhProp = PropertyUtil.getProperty("F:\\workspace\\workspace-security-cloud2802\\security\\security-common\\security-common-core\\src\\main\\resources\\i18n\\messages_zh.properties");
        Properties enProp = PropertyUtil.getProperty("F:\\workspace\\workspace-security-cloud2802\\security\\security-common\\security-common-core\\src\\main\\resources\\i18n\\messages_en.properties");
//        System.out.println("zhProp:" + PropertyUtil.getVal(zhProp,"rule.tag.manager.addtag.detail"));
//        System.out.println("enProp:" + PropertyUtil.getVal(enProp,"rule.tag.manager.addtag.detail"));
        Map<String, String> zhMap = new HashMap<>();
        Map<String, String> enMap = new HashMap<>();
        for (String key : zhProp.stringPropertyNames()) {
            String value = zhProp.getProperty(key);
            String mapKey = key.replace(".", "").toLowerCase();
            zhMap.put(mapKey, value);
        }
        for (String key : enProp.stringPropertyNames()) {
            String value = enProp.getProperty(key);
            String mapKey = key.replace(".", "").toLowerCase();
            enMap.put(mapKey, value);
        }
        for (Map.Entry<String, FileBean> entry : fileBeanMap.entrySet()) {
            String key = entry.getKey();
            FileBean fileBean = entry.getValue();
            List<FindStringBean> findStringList = fileBean.getFindStringList();

            for (FindStringBean findStringBean : findStringList) {
                String findString = findStringBean.getFindString();
                if (findString.length() > 0 && (findString.startsWith("Codes.") || findString.startsWith("Constants."))) {
                    System.out.println("findString:" + findString);
                    String findKey = findString.replace("Codes.", "").replace("Constants.", "").replace("_", "").toLowerCase();
                    String zhVal = zhMap.get(findKey);
                    String enVal = enMap.get(findKey);;
                    findStringBean.setZhString(zhVal);
                    findStringBean.setEnString(enVal);
                }
            }
        }


        ExcelUtil.exportToExcel(savePath, fileBeanMap, fromFileBeanMap);
        System.out.println("导出完成 " + savePath);
    }
}
