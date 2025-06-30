package demo.bean;

import java.util.List;

public class FileBean {

    private String filePath;

    private String fileName;

    private List<FindStringBean> findStringList;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<FindStringBean> getFindStringList() {
        return findStringList;
    }

    public void setFindStringList(List<FindStringBean> findStringList) {
        this.findStringList = findStringList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
