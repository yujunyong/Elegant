package org.elegant.enumeration;

import com.google.common.collect.Lists;

import java.util.List;

public enum BookFormatEnum {
    PDF(Lists.newArrayList("pdf"), "pdf"),
    EPUB(Lists.newArrayList("epub"), "epub")
    ;

    private List<String> fileExtension;
    private String format;

    BookFormatEnum(List<String> fileExtension, String format) {
        this.fileExtension = fileExtension;
        this.format = format;
    }

    public List<String> getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(List<String> fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
