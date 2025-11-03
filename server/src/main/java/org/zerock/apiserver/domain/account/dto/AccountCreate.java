package org.zerock.apiserver.domain.account.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.zerock.apiserver.domain.account.entity.AccountEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AccountCreate {

	private String email;

	private String nickname;

	private String role;

	private String password;

	private List<String> fileNames = new ArrayList<>();

	private String accessToken;

	private String refreshToken;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime joinDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;


	public void addFileName(String fileName) {
		this.fileNames.add(fileName);

	}

	public AccountEntity toEntity() {
		AccountEntity entity = AccountEntity.builder()
			.email(this.email)
			.nickname(this.nickname)
			.role("USER")
			.build();

		if (this.fileNames != null && this.fileNames.size() > 0) {
			this.fileNames.forEach(entity::addImage);
		}
		return entity;
	}

	public AccountCreate(String email, String password, String nickname, String role, LocalDateTime joinDate,
		LocalDateTime modifiedDate, String accessToken, String refreshToken) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
		this.joinDate = joinDate;
		this.modifiedDate = modifiedDate;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;

	}

}
