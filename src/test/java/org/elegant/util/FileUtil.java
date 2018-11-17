package org.elegant.util;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileUtil {
    public static Path getPath(String uri) {
        try {
            return Paths.get(ClassLoader.getSystemResource(uri).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
