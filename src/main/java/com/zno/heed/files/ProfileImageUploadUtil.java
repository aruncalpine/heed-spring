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

import com.datastax.oss.driver.shaded.guava.common.util.concurrent.Service;
import com.zno.heed.user.UserService;
@Component
public class ProfileImageUploadUtil {
@Autowired
UserService userService;
	public  String saveProfileImage(String fileName, MultipartFile multipartFile, String bearerToken) throws IOException {
		Path uploadPath = Paths.get("ProfileImages-Upload");
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		String fileCode = RandomStringUtils.randomAlphanumeric(8);
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
			
		//	UserService userService = new UserService();
		//	userService.saveProfileImage(bearerToken, filePath);
			userService.saveProfileImage(bearerToken, filePath);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save file: " + fileName, ioe);
		}
		return fileCode;
	}
}


