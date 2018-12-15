package org.elegant.enumeration;

import com.google.common.collect.Lists;

import java.nio.file.Path;
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

    public boolean isMatch(String filename) {
        return getFileExtension().stream().anyMatch(filename::endsWith);
    }

    public boolean isMatch(Path path) {
        return getFileExtension().stream().anyMatch(extention -> path.toString().endsWith(extention));
    }
}
