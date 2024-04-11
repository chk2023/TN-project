package com._3dhs.tnproject.post.util;

import java.io.File;

public class FileUtil {
    public static String getFilePath(String directory) {
        String baseDir = System.getProperty("user.dir");
        return baseDir + File.separator + "src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "static" + File.separator + directory;
    }
}
