package demo.util;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

}
