package com.hoaxify.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;


import org.springframework.stereotype.Service;
import com.hoaxify.ws.configuration.AppConfiguration;


@Service
public class FileService {


	AppConfiguration appConfiguration;

	public FileService(AppConfiguration appConfiguration) {
		super();
		this.appConfiguration = appConfiguration;
	}

	public String writeBase64EncodedStringToFile(String image) throws IOException {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getUploadPath() + "/" + fileName);
		OutputStream outPutStream = new FileOutputStream(target);
		byte[] base64Encoded = Base64.getDecoder().decode(image);	
		outPutStream.write(base64Encoded);
		outPutStream.close();
		return fileName;
	}

	public String generateRandomName() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public void deleteImage(String oldImageName) {
		if (oldImageName == null) {
			return;
		}
		try {
			Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(), oldImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
