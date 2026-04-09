package demo.util;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.ScannerImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class YamlUtil {

    public static Map<String, Object> getYamlMap(String filePath) {
        Yaml yaml = new Yaml();
        File file = new File(filePath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> yamlMap = yaml.load(inputStream);
        return yamlMap;
    }

    public static String getVal(Map<String, Object> data, String keysString) {
        String[] keys = keysString.split("\\.");
        Object current = data;
        if (current == null) {
            return null;
        }
        for (String key : keys) {
            if (current instanceof Map) {
                current = ((Map<?, ?>) current).get(key);
            } else {
                current = null;
                break;
            }
        }
        if (current == null) {
            return null;
        }
        return current.toString();
    }

    public static Integer getLineNumberOfKey(String filePath, String keyPath) throws IOException {
        String[] targetPath = keyPath.split("\\.");
        if (targetPath.length == 0) {
            return null;
        }

        Deque<String> currentPath = new ArrayDeque<>();

        // 读取文件内容
        String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        StreamReader streamReader = new StreamReader(content);

        // 关键修复：先创建 Scanner，再传给 ParserImpl
        LoaderOptions loaderOptions = new LoaderOptions();
        ScannerImpl scanner = new ScannerImpl(streamReader, loaderOptions);
        ParserImpl parser = new ParserImpl(scanner);

        while (parser.peekEvent() != null) {
            Event event = parser.getEvent();

            if (event.is(Event.ID.Scalar)) {
                ScalarEvent scalarEvent = (ScalarEvent) event;
                String scalarValue = scalarEvent.getValue();

                if (currentPath.size() == targetPath.length - 1) {
                    if (scalarValue.equals(targetPath[targetPath.length - 1])) {
                        return scalarEvent.getStartMark().getLine() + 1;
                    }
                } else if (currentPath.size() < targetPath.length - 1) {
                    if (scalarValue.equals(targetPath[currentPath.size()])) {
                        currentPath.addLast(scalarValue);
                    }
                }
            } else if (event.is(Event.ID.MappingEnd)) {
                if (!currentPath.isEmpty()) {
                    currentPath.removeLast();
                }
            }
        }
        return null;
    }

}
