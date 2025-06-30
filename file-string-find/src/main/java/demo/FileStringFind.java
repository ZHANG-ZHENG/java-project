package demo;

import demo.bean.FileBean;
import demo.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileStringFind {
    public static void main(String[] args) {
        System.out.println("FileStringFind");


        String folderPath = "F:\\workspace\\workspace-security-cloud290\\ui\\src\\views\\soc";
        List<String> filesPath = FileUtil.getAllFiles(folderPath);
        //files.forEach(System.out::println);
        Map<String, FileBean> fileBeanMap = new HashMap<>();
        for(String fPath : filesPath) {
            FileBean fileBean = new FileBean();
            fileBean.setFilePath(fPath);
            fileBeanMap.put(fPath, fileBean);

            FileUtil.fingString(fileBean);
        }
    }
}
