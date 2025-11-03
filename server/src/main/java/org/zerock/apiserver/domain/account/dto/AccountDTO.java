package org.zerock.apiserver.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.zerock.apiserver.domain.account.entity.AccountEntity;

@Data
@NoArgsConstructor
public class AccountDTO {

	private String email;

	private String nickname;

	private String role;

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

	public AccountDTO(String email, String nickname, String role, LocalDateTime joinDate,
		LocalDateTime modifiedDate, String accessToken, String refreshToken) {
		this.email = email;
		this.nickname = nickname;
		this.role = role;
		this.joinDate = joinDate;
		this.modifiedDate = modifiedDate;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;

	}

	public AccountDTO(AccountEntity entity) {
		this.email = entity.getEmail();
		this.nickname = entity.getNickname();
		this.role = entity.getRole();
		this.joinDate = entity.getJoinDate();
		this.modifiedDate = entity.getModifiedDate();
		this.accessToken = entity.getAccessToken();
		this.refreshToken = entity.getRefreshToken();

		if (entity.getImages() != null && entity.getImages().size() > 0) {
			this.fileNames = entity.getImages()
				.stream()
				.map(productImage -> productImage.getFileName()).toList();

		}

	}


}
