package demo.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    /**
     * 加载properties文件
     */
    public static Properties getProperty(String filePath) {
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            prop.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

    /**
     * 根据多级路径获取属性值
     * @param prop Properties对象
     * @param path 多级路径如"xxx.xxx.xxx"
     * @return 属性值，未找到返回null
     */
    public static String getVal(Properties prop, String path) {
        // 直接尝试完整路径查找
        String value = prop.getProperty(path);
        if (value != null) {
            return value;
        }

        // 尝试将点替换为下划线等常见替代符号
        String[] alternatives = {"_", "-", ""};
        for (String alt : alternatives) {
            String altPath = path.replace(".", alt);
            value = prop.getProperty(altPath);
            if (value != null) {
                return value;
            }
        }

        return null;
    }
}
