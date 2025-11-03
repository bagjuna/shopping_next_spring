package org.zerock.apiserver.domain.account.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String role;

    private String accessToken;

    private String refreshToken;

    private String profileImageUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "tbl_account_image", // 회원 이미지 정보를 저장할 테이블 이름
        joinColumns = @JoinColumn(name = "ano"), // Account 엔티티의 ID를 참조하는 FK 컬럼
        indexes =  {
            @Index(name = "idx_", columnList = "ano")
        }
    )
    @Builder.Default
    private Set<AccountImage> images = new HashSet<>();


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

    public void addImage(String fileName) {
        this.images.add(new AccountImage(fileName, this.images.size()));
        this.profileImageUrl = fileName;
    }

    public void removeImages() {
        this.images.clear();
    }

    public void addImages(List<String> uploadfileNames) {
        uploadfileNames.forEach(
            uploadfileName -> this.images.add(new AccountImage(uploadfileName, this.images.size())));
        this.profileImageUrl = uploadfileNames.get(0);
    }

    public void addMainImage(String fileName) {
        this.profileImageUrl = fileName;
        this.images.add(new AccountImage(fileName, images.size()));
    }

}
