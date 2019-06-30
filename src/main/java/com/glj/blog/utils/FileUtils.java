package com.glj.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author guolongjie
 * @since 2019/5/3
 */

public class FileUtils {


    private static String getDirPath(){
        String dirPath = null;
        if (System.getProperty("os.name").toLowerCase().startsWith("win")){
            dirPath = "D:/blog/images/";
        }else {
            dirPath = "/usr/data/blog/images/";
        }
        return dirPath;
    }

    public static String saveFile(MultipartFile file) throws IOException {
        String dirPath = getDirPath();
        File dir = new File(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        Path path = Paths.get(dirPath+fileName);
        Files.write(path,file.getBytes());
        return fileName;
    }

    public static void deleteFile(String filePath){
        File file = new File(getDirPath(),filePath);
        if (file.exists()) {
            file.deleteOnExit();
        }
    }


}
