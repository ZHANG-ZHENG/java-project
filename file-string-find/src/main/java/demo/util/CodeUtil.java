package demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeUtil {

    /**
     * 遍历文件夹下所有文件
     */
    public static Map<String, String> getCodeMap(String folderPath) {
        Map<String, String> codeMap = new HashMap<>();

        File folder = new File(folderPath);
        // 检查路径是否存在且是目录
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {  // 只处理文件
                        // System.out.println("文件: " + file.getName());
                        readFile(file, codeMap);
                    }
                }
            }
        } else {
            System.out.println("指定的路径不存在或不是目录");
        }

        return codeMap;
    }

    private static void readFile(File file, Map<String, String> codeMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("String ")) {
                    String linetrim = line.trim();
                    String[] lineArr = linetrim.split(" ");
                    String key = lineArr[1];
                    String val = lineArr[3].replace("\"","").replace(";","").replace("{","").replace("}","");
                    // System.out.println("key:" + key + " val" + val);
                    codeMap.put(key, val);
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        CodeUtil.getCodeMap("F:\\workspace\\workspace-security-cloud2802\\security\\security-common\\security-common-core\\src\\main\\java\\com\\ruijie\\security\\common\\core\\constant");

    }
}
