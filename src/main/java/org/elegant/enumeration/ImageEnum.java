package org.elegant.enumeration;

import com.google.common.collect.Lists;

import java.util.List;

public enum ImageEnum {
    JPG(Lists.newArrayList("jpg")),
    PNG(Lists.newArrayList("png"))
    ;

    private List<String> fileExtension;

    ImageEnum(List<String> fileExtension) {
        this.fileExtension = fileExtension;
    }

    public List<String> getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(List<String> fileExtension) {
        this.fileExtension = fileExtension;
    }
}
