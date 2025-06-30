package demo.util;

import demo.bean.FileBean;
import demo.bean.FindStringBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileUtil {

    /**
     * 遍历文件夹下所有文件
     */
    public static List<String> getAllFiles(String folderPath) {
        try {
            return Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("遍历文件夹出错: " + e.getMessage());
            return null;
        }
    }

    /**
     * 遍历文件查找字符
     */
    public static void fingString(FileBean fileBean) {
        //String filePath = "F:\\workspace\\workspace-security-cloud290\\ui\\src\\views\\soc\\periodicReport-test.vue";
        List<FindStringBean> findStringList = new ArrayList<>();
        fileBean.setFindStringList(findStringList);

        String filePath = fileBean.getFilePath();
        File file = new File(filePath);
        fileBean.setFileName(file.getName());
//        if(!file.getName().equals("CustomDashboard.vue")) {
//            return;
//        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            // 匹配中文字符的正则表达式
            Pattern patternHan = Pattern.compile("\\p{Script=Han}+");

            while ((line = br.readLine()) != null) {
                lineNumber++;
                Matcher matcherHan = patternHan.matcher(line);

                if (line.contains("// ") || (line.contains("<!-- ") && line.contains(" -->"))) {
                    continue;
                }

                while (matcherHan.find()) {
                    int start = matcherHan.start();
                    int end = matcherHan.end();
                    String chineseSequence = matcherHan.group();
                    System.out.printf(file.getName() + " 第 %d 行: %s%n", lineNumber, line);
                    System.out.printf("  位置 %d-%d: \"%s\" (共%d个汉字)%n", start + 1, end, chineseSequence, chineseSequence.length());

                    FindStringBean findStringBean = new FindStringBean();
                    findStringBean.setStart(start + 1);
                    findStringBean.setEnd(end);
                    findStringBean.setLineIndex(lineNumber);
                    findStringBean.setLineString(line);
                    findStringBean.setFindString(chineseSequence);
                    findStringList.add(findStringBean);
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
        }
    }
}
