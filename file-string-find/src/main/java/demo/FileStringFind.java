package demo;

import demo.bean.FileBean;
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
        System.out.println("1111111" + YamlUtil.getVal(zhYamlMap, "buttons.hsLoginOut"));

        String savePath = "F:\\output.xlsx";
        ExcelUtil.exportToExcel(savePath, fileBeanMap, fromFileBeanMap);
    }
}
