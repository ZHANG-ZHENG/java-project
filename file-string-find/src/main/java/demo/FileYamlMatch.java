package demo;
import demo.config.Config;
import demo.util.YamlUtil;
import java.io.*;


public class FileYamlMatch {


    public static void main(String[] args) {
        try {
            String filePath = Config.PROJECT_PATH + "ui292\\locales\\zh-CN.yaml";
            String key = "eventSubscription.emailPushType1Contiditon1";
            Integer line = YamlUtil.getLineNumberOfKey(filePath, key);
            System.out.println("Key 在第 " + line + " 行");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}