package org.zerock.apiserver.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.account.entity.AccountEntity;
import org.zerock.apiserver.product.util.FileUploader;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountImageService {

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



}
