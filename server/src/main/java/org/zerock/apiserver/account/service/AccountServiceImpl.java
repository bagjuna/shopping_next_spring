package org.zerock.apiserver.account.service;

import static java.rmi.server.LogStream.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.account.dto.AccountDTO;
import org.zerock.apiserver.account.dto.RefreshDTO;
import org.zerock.apiserver.account.dto.request.SigninRequest;
import org.zerock.apiserver.account.dto.reaspone.SigninResponse;
import org.zerock.apiserver.account.entity.AccountEntity;
import org.zerock.apiserver.account.repository.AccountRepository;
import org.zerock.apiserver.account.util.JWTUtil;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final AccountImageService accountImageService;

    @Override
    public SigninResponse getOne(String email, String password) {

        Optional<AccountEntity> result = accountRepository.findByEmail(email);

        if(result.isEmpty()){
            throw new IllegalArgumentException("Not Found");
        }

        AccountEntity entity = result.get();

        if(!entity.getPassword().equals(password)){
            throw new IllegalArgumentException("Password is not correct");
        }

        String accessToken = JWTUtil.createToken(Map.of("email", email), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email", email), 60 * 24);

        entity.changeAccessToken(accessToken);
        entity.changeRefreshToken(refreshToken);
        log("entity" + entity);

        //String email, String password, String nickname, String role, LocalDateTime joinDate, LocalDateTime modifiedDate
        SigninResponse accountDTO = SigninResponse.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .role(entity.getRole())
                .joinDate(entity.getJoinDate())
                .modifiedDate(entity.getModifiedDate())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .profileImageURL(entity.getProfileImageUrl())
                .build();
        log("accountDTO: " + accountDTO);
        return accountDTO;
    }

    @Override
    public AccountEntity register(SigninRequest accountDTO) {

        String accessToken = JWTUtil.createToken(Map.of("email", accountDTO.getEmail()), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email", accountDTO.getEmail()), 60*24);

        AccountEntity entity  = AccountEntity.builder()
                .email(accountDTO.getEmail())
                .password(accountDTO.getPassword())
                .role("USER")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return accountRepository.save(entity);
    }

    @Override
    public SigninResponse checkSocial(String email) {
        // 1. 해당 이메일로 등록된 계정을 찾거나, 없으면 새로 생성합니다.
        AccountEntity account = accountRepository.findById(email)
                .orElseGet(() -> createNewAccount(email));

        // 2. JWT 토큰을 생성하고 엔티티에 업데이트합니다.
        updateTokens(account, email);

        // 3. 엔티티를 DTO로 변환하여 반환합니다.
        return SigninResponse.builder()
                .email(account.getEmail())
                .password(account.getPassword())
                .nickname(account.getNickname())
                .role(account.getRole())
                .joinDate(account.getJoinDate())
                .modifiedDate(LocalDateTime.now())
                .accessToken(account.getAccessToken())
                .refreshToken(account.getRefreshToken())
                .build();
    }

    // 새로운 소셜 계정을 생성하는 메서드
    private AccountEntity createNewAccount(String email) {
        AccountEntity newAccount = AccountEntity.builder()
                .email(email)
                .password("1111")
                .nickname("Social")
                .role("USER")
                .build();

        return accountRepository.save(newAccount);
    }

    // JWT 토큰을 생성하고 엔티티에 업데이트하는 메서드
    private void updateTokens(AccountEntity account, String email) {
        String accessToken = JWTUtil.createToken(Map.of("email", email), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email", email), 60 * 24);

        account.changeAccessToken(accessToken);
        account.changeRefreshToken(refreshToken);
        accountRepository.save(account); // 토큰 변경 후 저장
    }

    @Override
    public SigninResponse update(AccountDTO accountDTO, MultipartFile file) {

        AccountEntity entity = accountRepository.selectOne(accountDTO.getEmail());

        entity.changeNickname(accountDTO.getNickname());

        AccountEntity updatedEntity = accountImageService.uploadProfileImage(entity, file);

        return SigninResponse.builder()
                .email(updatedEntity.getEmail())
                .password(updatedEntity.getPassword())
                .nickname(updatedEntity.getNickname())
                .role(updatedEntity.getRole())
                .joinDate(updatedEntity.getJoinDate())
                .modifiedDate(LocalDateTime.now())
                .accessToken(updatedEntity.getAccessToken())
                .refreshToken(updatedEntity.getRefreshToken())
                .profileImageURL(updatedEntity.getProfileImageUrl())
                .build();
    }

    @Override
    public SigninResponse refresh(RefreshDTO refreshDTO) {

        //해당 email로 등록된 엔티티가 있는지 살펴본다.
        Optional<AccountEntity> result = accountRepository.findByRefreshToken(refreshDTO.getRefreshToken());

        //있다면 AccountDTO로 만들어서 반환
        //String email, String password, String nickname, String role, LocalDateTime joinDate, LocalDateTime modifiedDate
        if(result.isEmpty()){
            throw new IllegalArgumentException("Not Found");
        }

        AccountEntity entity = result.get();

        //새로운 Access Token과 Refresh Token 생성
        String accessToken = JWTUtil.createToken(Map.of("email", entity.getEmail()), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email",  entity.getEmail()), 60*24);

        AccountServiceImpl.log.info("new access token: " + accessToken);
        AccountServiceImpl.log.info("new refresh token: " + refreshToken);

        entity.changeAccessToken(accessToken);
        entity.changeRefreshToken(refreshToken);

        return SigninResponse.builder()
            .email(entity.getEmail())
            .password(entity.getPassword())
            .nickname(entity.getNickname())
            .role(entity.getRole())
            .joinDate(entity.getJoinDate())
            .modifiedDate(LocalDateTime.now())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .profileImageURL(entity.getProfileImageUrl())
            .build();

    }
}

























