package org.zerock.apiserver.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class AccountDTO{

    private String email;

    private String password;

    private String nickname;

    private String role;

    private String accessToken;

    private String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    public AccountDTO(String email, String password, String nickname, String role, LocalDateTime joinDate, LocalDateTime modifiedDate, String accessToken, String refreshToken) {
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
