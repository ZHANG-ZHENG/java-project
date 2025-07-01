package demo;

import demo.bean.FileBean;
import demo.bean.FindStringBean;
import demo.util.ExcelUtil;
import demo.util.FileUtil;
import demo.util.YamlUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileStringFind {
    public static void main(String[] args) {
        String folderPath = "F:\\workspace\\workspace-security-cloud290\\ui\\src\\views\\soc";
        List<String> filesPath = FileUtil.getAllFiles(folderPath);
        //files.forEach(System.out::println);
        Map<String, FileBean> fileBeanMap = new HashMap<>();
        for(String fPath : filesPath) {
            FileBean fileBean = new FileBean();
            fileBean.setFilePath(fPath);
            fileBeanMap.put(fPath, fileBean);

            FileUtil.fingString(fileBean);
            FileUtil.fingFrom(fileBean);
        }

        // 依赖
        Map<String, FileBean> fromFileBeanMap = new HashMap<>();
        for (Map.Entry<String, FileBean> entry : fileBeanMap.entrySet()) {
            // String key = entry.getKey();
            FileBean fileBean = entry.getValue();
            for (FileBean formFileBean : fileBean.getFileFromList()) {
                if ("内部依赖".equals(formFileBean.getFromType())) {
                    String formFilePath = formFileBean.getFilePath();
                    if (fromFileBeanMap.get(formFilePath) == null && fileBeanMap.get(formFilePath) == null) {
                        fromFileBeanMap.put(formFileBean.getFilePath(), formFileBean);
                        FileUtil.fingString(formFileBean);
                    }
                }
            }
        }

        // 翻译
        Map<String, Object> zhYamlMap = YamlUtil.getYamlMap("F:\\workspace\\workspace-security-cloud290\\ui\\locales\\zh-CN.yaml");
        Map<String, Object> enYamlMap = YamlUtil.getYamlMap("F:\\workspace\\workspace-security-cloud290\\ui\\locales\\en.yaml");

        for (Map.Entry<String, FileBean> entry : fileBeanMap.entrySet()) {
            String key = entry.getKey();
            FileBean fileBean = entry.getValue();
            List<FindStringBean> findStringList = fileBean.getFindStringList();

            for (FindStringBean findStringBean : findStringList) {
                String findString = findStringBean.getFindString();
                if (findString.length() > 6 && findString.startsWith("t(") && findString.endsWith(")")) {
                    String yamlKey = findString.substring(3, findString.length() - 2);
                    String yamlZhVal = YamlUtil.getVal(zhYamlMap, yamlKey);
                    String yamlEnVal = YamlUtil.getVal(enYamlMap, yamlKey);
                    findStringBean.setZhString(yamlZhVal);
                    findStringBean.setEnString(yamlEnVal);
                }
            }
        }
        for (Map.Entry<String, FileBean> entry : fromFileBeanMap.entrySet()) {
            String key = entry.getKey();
            FileBean fileBean = entry.getValue();
            List<FindStringBean> findStringList = fileBean.getFindStringList();

            for (FindStringBean findStringBean : findStringList) {
                String findString = findStringBean.getFindString();
                if (findString.length() > 6 && findString.startsWith("t(") && findString.endsWith(")")) {
                    String yamlKey = findString.substring(3, findString.length() - 2);
                    String yamlZhVal = YamlUtil.getVal(zhYamlMap, yamlKey);
                    String yamlEnVal = YamlUtil.getVal(enYamlMap, yamlKey);
                    findStringBean.setZhString(yamlZhVal);
                    findStringBean.setEnString(yamlEnVal);
                }
            }
        }

        String savePath = "F:\\output.xlsx";
        ExcelUtil.exportToExcel(savePath, fileBeanMap, fromFileBeanMap);
    }
}
