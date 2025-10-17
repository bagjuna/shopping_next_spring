package org.zerock.apiserver.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
public class AccountEntity {

    @Id
    private String email;

    private String password;

    private String nickname;

    private String role;

    private String accessToken;

    private String refreshToken;

    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate

    @CreatedDate
    private LocalDateTime joinDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
    public void changeRole(String role) {
        this.role = role;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    public void changeAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
