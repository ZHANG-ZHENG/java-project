package demo.bean;

import java.util.List;

public class FileBean {

    private String filePath;

    private String fileName;

    private List<FindStringBean> findStringList;

    private String fileNameFrom;

    private List<FileBean> fileFromList;

    private int fromFileLineIndex;

    private String fromType;

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

    public String getFileNameFrom() {
        return fileNameFrom;
    }

    public void setFileNameFrom(String fileNameFrom) {
        this.fileNameFrom = fileNameFrom;
    }

    public List<FileBean> getFileFromList() {
        return fileFromList;
    }

    public void setFileFromList(List<FileBean> fileFromList) {
        this.fileFromList = fileFromList;
    }

    public int getFromFileLineIndex() {
        return fromFileLineIndex;
    }

    public void setFromFileLineIndex(int fromFileLineIndex) {
        this.fromFileLineIndex = fromFileLineIndex;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }
}
