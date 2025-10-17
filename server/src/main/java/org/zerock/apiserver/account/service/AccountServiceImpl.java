package org.zerock.apiserver.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.account.dto.AccountDTO;
import org.zerock.apiserver.account.dto.RefreshDTO;
import org.zerock.apiserver.account.entity.AccountEntity;
import org.zerock.apiserver.account.repository.AccountRepository;
import org.zerock.apiserver.account.util.JWTUtil;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService{

    private final AccountRepository repository;


    @Override
    public AccountDTO getOne(String email, String password) {

        Optional<AccountEntity> result = repository.findById(email);

        if(result.isEmpty()){
            throw new IllegalArgumentException("Not Found");
        }

        AccountEntity entity = result.get();

        if(entity.getPassword().equals(password) == false){
            throw new IllegalArgumentException("Password is not correct");
        }

        String accessToken = JWTUtil.createToken(Map.of("email", email), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email", email), 60*24);

        entity.changeAccessToken(accessToken);
        entity.changeRefreshToken(refreshToken);

        //String email, String password, String nickname, String role, LocalDateTime joinDate, LocalDateTime modifiedDate
        AccountDTO accountDTO = new AccountDTO(
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname(),
                entity.getRole(),
                entity.getJoinDate(),
                entity.getModifiedDate(),
                entity.getAccessToken(),
                entity.getRefreshToken()
        );
        return accountDTO;
    }

    @Override
    public void register(AccountDTO accountDTO) {

        String accessToken = JWTUtil.createToken(Map.of("email", accountDTO.getEmail()), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email", accountDTO.getEmail()), 60*24);

        AccountEntity entity  = AccountEntity.builder()
                .email(accountDTO.getEmail())
                .password(accountDTO.getPassword())
                .nickname(accountDTO.getNickname())
                .role("USER")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        repository.save(entity);
    }

    @Override
    public AccountDTO checkSocial(String email) {
        // 1. 해당 이메일로 등록된 계정을 찾거나, 없으면 새로 생성합니다.
        AccountEntity account = repository.findById(email)
                .orElseGet(() -> createNewAccount(email));

        // 2. JWT 토큰을 생성하고 엔티티에 업데이트합니다.
        updateTokens(account, email);

        // 3. 엔티티를 DTO로 변환하여 반환합니다.
        return convertToDto(account);
    }

    // 새로운 소셜 계정을 생성하는 메서드
    private AccountEntity createNewAccount(String email) {
        AccountEntity newAccount = AccountEntity.builder()
                .email(email)
                .password("1111")
                .nickname("Social")
                .role("USER")
                .build();

        return repository.save(newAccount);
    }

    // JWT 토큰을 생성하고 엔티티에 업데이트하는 메서드
    private void updateTokens(AccountEntity account, String email) {
        String accessToken = JWTUtil.createToken(Map.of("email", email), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email", email), 60 * 24);

        account.changeAccessToken(accessToken);
        account.changeRefreshToken(refreshToken);
        repository.save(account); // 토큰 변경 후 저장
    }

    // AccountEntity를 AccountDTO로 변환하는 메서드
    private AccountDTO convertToDto(AccountEntity entity) {
        return new AccountDTO(
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname(),
                entity.getRole(),
                entity.getJoinDate(),
                entity.getModifiedDate(),
                entity.getAccessToken(),
                entity.getRefreshToken());
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {

        AccountEntity entity = AccountEntity.builder()
                .email(accountDTO.getEmail())
                .password(accountDTO.getPassword())
                .nickname(accountDTO.getNickname())
                .role("USER")
                .build();

        repository.save(entity);

       return convertToDto(entity);
    }

    @Override
    public AccountDTO refresh(RefreshDTO refreshDTO) {

        //해당 email로 등록된 엔티티가 있는지 살펴본다.
        Optional<AccountEntity> result = repository.findByRefreshToken(refreshDTO.getRefreshToken());

        //있다면 AccountDTO로 만들어서 반환
        //String email, String password, String nickname, String role, LocalDateTime joinDate, LocalDateTime modifiedDate
        if(result.isEmpty()){
            throw new IllegalArgumentException("Not Found");
        }

        AccountEntity entity = result.get();

        //새로운 Access Token과 Refresh Token 생성
        String accessToken = JWTUtil.createToken(Map.of("email", entity.getEmail()), 60);
        String refreshToken = JWTUtil.createToken(Map.of("email",  entity.getEmail()), 60*24);

        log.info("new access token: " + accessToken);
        log.info("new refresh token: " + refreshToken);

        entity.changeAccessToken(accessToken);
        entity.changeRefreshToken(refreshToken);


        return convertToDto(entity);

    }
}

























