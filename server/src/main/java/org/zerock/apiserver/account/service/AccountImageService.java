package org.zerock.apiserver.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.account.dto.AccountDTO;
import org.zerock.apiserver.product.util.FileUploader;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountImageService {

	private final FileUploader fileUploader;

	public AccountDTO uploadAccountImage(AccountDTO accountDTO, MultipartFile[] files) {
		if(files == null || files.length <= 0) {
			throw new IllegalArgumentException("No files to upload");
		}
		List<String> uploadfileNames = fileUploader.upload(files);
		uploadfileNames.forEach(newFileName -> accountDTO.addFileName(newFileName));
		return accountDTO;
	}


}
