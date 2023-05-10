package com.hoaxify.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hoaxify.ws.configuration.AppConfiguration;
import com.hoaxify.ws.user.User;

@Service
@EnableScheduling
public class FileService {

	AppConfiguration appConfiguration;

	FileAttachmentRepository fileAttachmentRepository;

	public FileService(AppConfiguration appConfiguration, FileAttachmentRepository fileAttachmentRepository) {
		super();
		this.appConfiguration = appConfiguration;
		this.fileAttachmentRepository = fileAttachmentRepository;
	}

	public String writeBase64EncodedStringToFile(String image) throws IOException {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getProfileStoragePath() + "/" + fileName);
		OutputStream outPutStream = new FileOutputStream(target);
		byte[] base64Encoded = Base64.getDecoder().decode(image);
		outPutStream.write(base64Encoded);
		outPutStream.close();
		return fileName;
	}

	public String generateRandomName() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public void deleteProfileImage(String oldImageName) {
		if (oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getProfileStoragePath(), oldImageName));
	}

	public void deleteAttachmentFile(String oldImageName) {
		if (oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), oldImageName));
	}

	private void deleteFile(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + fileName);

		try {
			OutputStream outPutStream = new FileOutputStream(target);
			outPutStream.write(multipartFile.getBytes());
			outPutStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileAttachment attachment = new FileAttachment();
		attachment.setName(fileName);
		attachment.setDate(new Date());
		return fileAttachmentRepository.save(attachment);
	}

	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void cleanupStorage() {
		Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
		List<FileAttachment> fileToBeDeleted = fileAttachmentRepository
				.findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo);
		for (FileAttachment file : fileToBeDeleted) {
			deleteAttachmentFile(file.getName());
			fileAttachmentRepository.deleteById(file.getId());
		}
	}

	public void deleteAllStoredFilesForUser(User inDB) {
		deleteProfileImage(inDB.getImage());
		List<FileAttachment> fileToBeRemoved = fileAttachmentRepository.findByHoaxUser(inDB);
		for (FileAttachment file : fileToBeRemoved) {
			deleteAttachmentFile(file.getName());
		}
	}
}
