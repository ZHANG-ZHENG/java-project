package demo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

}
