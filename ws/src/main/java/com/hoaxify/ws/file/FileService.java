package com.hoaxify.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class FileService {

	public String writeBase64EncodedStringToFile(String image) throws IOException {
		String fileName = generateRandomName();
		File target = new File("picture-storage/"+fileName);
		OutputStream outPutStream = new FileOutputStream(target);
		byte [] base64Encoded = Base64.getDecoder().decode(image);
		outPutStream.write(base64Encoded);
		outPutStream.close();
		return fileName;
	}
	public String generateRandomName() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
