package com.zno.heed.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ImageDownloadUtil {
    private Path foundFile;
    
    public Resource getFileAsResource(String fileCode) throws IOException {
        String homeDirectory=System.getProperty("user.home");
		Path dirPath = Paths.get(homeDirectory+"/Images-Upload");
        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });
 
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
         
        return null;
    }
}