package org.zerock.apiserver.domain.account.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.domain.account.entity.AccountEntity;
import org.zerock.apiserver.domain.product.util.FileUploader;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountImageService {

	private static final Logger log = LogManager.getLogger(AccountImageService.class);
	private final FileUploader fileUploader;

	public AccountEntity uploadAccountImage(AccountEntity account, MultipartFile[] files) {

		if (files == null || files.length == 0) {
			throw new IllegalArgumentException("No files to upload");
		}
		account.removeImages();
		List<String> uploadfileNames = fileUploader.upload(files);
		account.addImages(uploadfileNames);
		return account;
	}

	public AccountEntity uploadProfileImage(AccountEntity account, MultipartFile file) {

		account.removeImages();
		String uploadfileName = fileUploader.uploadSingle(file);
		log.info("Uploaded profile image: " + uploadfileName);
		account.addImage(uploadfileName);
		return account;
	}



}
