package demo;
import demo.config.Config;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileYamlDiff {
    public static void main(String[] args) {
        try (BufferedReader br1 = new BufferedReader(new FileReader(Config.PROJECT_PATH + "ui292\\locales\\zh-CN.yaml"));
             BufferedReader br2 = new BufferedReader(new FileReader(Config.PROJECT_PATH +  "ui292\\locales\\en.yaml"))) {

            String line1, line2;
            int lineNumber = 1;
            boolean isSame = true;

            while (true) {
                line1 = br1.readLine();
                line2 = br2.readLine();

                if (line1 == null && line2 == null) {
                    break;
                }
                String line1Find = line1.contains(":") ? line1.substring(0, line1.indexOf(":")) : line1;
                String line2Find = line2.contains(":") ? line2.substring(0, line2.indexOf(":")) : line2;

                if (line1Find == null || line2Find == null || !line1Find.equals(line2Find)) {
                    System.out.println("第 " + lineNumber + " 行不同");
                    System.out.println(line1);
                    System.out.println(line2);
                    isSame = false;
                    break;
                }

                lineNumber++;
            }

            if (isSame) {
                System.out.println("两个文件完全一致");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
