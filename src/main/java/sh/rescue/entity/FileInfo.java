package sh.rescue.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfo {
    private String filename;
    private String hash;
    private String sign;

    public FileInfo() {
    }

    public FileInfo(String filename, String hash) {
        this.filename = filename;
        this.hash = hash;
    }

    public FileInfo(String filename, String hash, String sign) {
        this.filename = filename;
        this.hash = hash;
        this.sign = sign;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
