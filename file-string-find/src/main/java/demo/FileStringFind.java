package demo;

import demo.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileStringFind {
    public static void main(String[] args) {
        System.out.println("FileStringFind");
        String filePath = "F:\\workspace\\workspace-security-cloud290\\ui\\src\\views\\soc\\periodicReport-test.vue";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            // 匹配中文字符的正则表达式
            Pattern pattern = Pattern.compile("\\p{Script=Han}+");

            while ((line = br.readLine()) != null) {
                lineNumber++;
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    System.out.printf("第 %d 行: %s%n", lineNumber, line);
                    int start = matcher.start();
                    int end = matcher.end();
                    String chineseSequence = matcher.group();
                    System.out.printf("  位置 %d-%d: \"%s\" (共%d个汉字)%n", start + 1, end, chineseSequence, chineseSequence.length());
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
        }


        String folderPath = "F:\\workspace\\workspace-security-cloud290\\ui\\src\\views\\soc";
        List<String> files = FileUtil.getAllFiles(folderPath);
        files.forEach(System.out::println);
    }
}
