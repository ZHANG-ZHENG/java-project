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
            // 查找 t('xxxx.xxxxx'） 或t("xxxx.xxxxx"）
            Pattern patternT = Pattern.compile("t\\(['\"]([^'\"]*\\.[^'\"]*)['\"]\\)");
            // 查找包引用
            Pattern patternFrom = Pattern.compile("from\\s+['\"](.*?)['\"]");

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (line.contains("// ") || (line.contains("<!-- ") && line.contains(" -->"))) {
                    continue;
                }
                Matcher matcherHan = patternHan.matcher(line);
                while (matcherHan.find()) {
                    int start = matcherHan.start();
                    int end = matcherHan.end();
                    String chineseSequence = matcherHan.group();
//                    System.out.printf(file.getName() + " 第 %d 行: %s%n", lineNumber, line);
//                    System.out.printf("  位置 %d-%d: \"%s\" (共%d个汉字)%n", start + 1, end, chineseSequence, chineseSequence.length());

                    FindStringBean findStringBean = new FindStringBean();
                    findStringBean.setStart(start + 1);
                    findStringBean.setEnd(end);
                    findStringBean.setLineIndex(lineNumber);
                    findStringBean.setLineString(line);
                    findStringBean.setFindString(chineseSequence);
                    findStringList.add(findStringBean);
                }

                Matcher matcherT = patternT.matcher(line);
                while (matcherT.find()) {
                    int start = matcherT.start();
                    int end = matcherT.end();
                    String chineseSequence = matcherT.group();
//                    System.out.printf(file.getName() + " 第 %d 行: %s%n", lineNumber, line);
//                    System.out.printf("  位置 %d-%d: \"%s\" (共%d个)%n", start + 1, end, chineseSequence, chineseSequence.length());

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

    /**
     * 遍历文件查找依赖
     */
    public static void fingFrom(FileBean fileBean) {
        List<FileBean> fileFromList = new ArrayList<>();
        fileBean.setFileFromList(fileFromList);

        String filePath = fileBean.getFilePath();
        File file = new File(filePath);
        fileBean.setFileName(file.getName());

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            // 查找包引用
            Pattern patternFrom = Pattern.compile("from\\s+['\"](.*?)['\"]");

            while ((line = br.readLine()) != null) {
                lineNumber++;

                Matcher matcherFrom = patternFrom.matcher(line);

                while (matcherFrom.find()) {
                    String fromContent = matcherFrom.group(1);
                    //System.out.println(file.getName()+"引用: " + fromContent);
                    FileBean fromFileBean = new FileBean();
                    fromFileBean.setFileNameFrom(fromContent);
                    fromFileBean.setFromFileLineIndex(lineNumber);

                    String nodeModulesPath = "F:\\workspace\\workspace-security-cloud290\\ui\\node_modules\\" +fromContent;
                    if(nodeModulesPath.contains("cloneDeep")){
                        System.out.println("cloneDeep");
                    }
                    //System.out.println("nodeModulesPath: " + nodeModulesPath);
                    String checkNodeModulesPath = checkFileExistsWithoutExtension(nodeModulesPath);
                    if (checkNodeModulesPath != null) {
                        //System.out.println(file.getName()+"引用库文件: " + nodeModulesFile.getPath());
                        fromFileBean.setFromType("公共依赖");
                        fromFileBean.setFilePath(checkNodeModulesPath);
                        fileBean.getFileFromList().add(fromFileBean);
                        continue;
                    }
                    System.out.println(file.getName()+"引用: " + fromContent);
                    if (fromContent.startsWith(".")) {
                        Path absolutePath = resolveRelativePath(fileBean.getFilePath(), fromContent);
                        System.out.println(file.getName()+"引用路径: " + absolutePath.toAbsolutePath());
                        fromFileBean.setFromType("内部依赖");
                        fromFileBean.setFilePath(absolutePath.toString());
                        fileBean.getFileFromList().add(fromFileBean);
                        continue;

                    }
                    if (fromContent.startsWith("@")) {
                        String fromContentAt = fromContent.replace("@", "F:\\workspace\\workspace-security-cloud290\\ui\\src\\");
                        System.out.println(file.getName()+"引用路径: " + fromContentAt);
                        String filePathCheck = checkFileExistsWithoutExtension(fromContentAt);
                        if (filePathCheck != null) {
                            fromFileBean.setFromType("内部依赖");
                            fromFileBean.setFilePath(filePathCheck);
                            fileBean.getFileFromList().add(fromFileBean);
                            // continue;
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
        }
    }

    private static Path resolveRelativePath(String baseFilePath, String relativeReference) {
        // 获取已知文件的父目录路径
        Path basePath = Paths.get(baseFilePath).getParent();
        // 解析相对路径
        Path resolvedPath = basePath.resolve(relativeReference).normalize();
        return resolvedPath.toAbsolutePath();
    }

    public static String checkFileExistsWithoutExtension(String fullPath) {
        File file = new File(fullPath);
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        String directory = file.getParent();
        String fileNameWithoutExtension = file.getName();
        return checkFileExistsWithoutExtension(directory, fileNameWithoutExtension);
    }
    public static String checkFileExistsWithoutExtension(String directory, String fileNameWithoutExtension) {
        File dir = new File(directory);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String filename = file.getName();
                    // 检查文件名是否匹配（不考虑后缀）
                    if (filename.startsWith(fileNameWithoutExtension + ".")
                            || filename.equals(fileNameWithoutExtension)) {
                        return file.getAbsolutePath();
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String filePath = "F:\\workspace\\workspace-security-cloud290\\ui\\src\\api\\soc\\scene";
        File file = new File(filePath);
        System.out.println(file.getName());
        System.out.println(file.getAbsoluteFile());
        System.out.println(checkFileExistsWithoutExtension("F:\\workspace\\workspace-security-cloud290\\ui\\src\\api\\soc\\","scene"));
        System.out.println(checkFileExistsWithoutExtension("F:\\workspace\\workspace-security-cloud290\\ui\\src\\api\\soc\\","scene.ts"));

        System.out.println(checkFileExistsWithoutExtension(filePath));
    }
}
