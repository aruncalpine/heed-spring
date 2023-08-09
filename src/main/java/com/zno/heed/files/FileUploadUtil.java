package com.zno.heed.files;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zno.heed.nettySocket.model.Message;
import com.zno.heed.user.UserService;
@Component
public class FileUploadUtil {
	@Autowired
	UserService userservice;
	public  String saveFile(String fileName, MultipartFile multipartFile, Message message) throws IOException {
		String homeDirectory=System.getProperty("user.home");
		Path uploadPath = Paths.get(homeDirectory+"/Files-Upload");
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		String fileCode = RandomStringUtils.randomAlphanumeric(8);
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
	        userservice.saveFile(message, filePath,fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save file: " + fileName, ioe);
		}
		return fileCode;
	}
}
