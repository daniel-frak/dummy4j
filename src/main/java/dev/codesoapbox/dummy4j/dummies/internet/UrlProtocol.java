package dev.codesoapbox.dummy4j.dummies.internet;

public enum UrlProtocol {

    HTTP("http"),
    HTTPS("https"),
    FTP("ftp"),
    FILE("file"),
    JAR("jar");

    private String value;

    UrlProtocol(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
