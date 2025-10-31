package org.zerock.apiserver.account.dto.reaspone;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SigninResponse {

	private String email;

	private String password;

	private String nickname;

	private String role;

	private String accessToken;

	private String refreshToken;

	private String profileImageURL;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime joinDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;


	@Builder
	public SigninResponse(String email, String password, String nickname, String role, LocalDateTime joinDate,
		LocalDateTime modifiedDate,  String accessToken, String refreshToken,String profileImageURL) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
		this.joinDate = joinDate;
		this.modifiedDate = modifiedDate;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.profileImageURL = profileImageURL;
	}


}
