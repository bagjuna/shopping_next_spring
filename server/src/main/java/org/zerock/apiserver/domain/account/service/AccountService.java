package org.zerock.apiserver.domain.account.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.domain.account.dto.AccountDTO;
import org.zerock.apiserver.domain.account.dto.RefreshDTO;
import org.zerock.apiserver.domain.account.dto.request.SigninRequest;
import org.zerock.apiserver.domain.account.dto.reaspone.SigninResponse;
import org.zerock.apiserver.domain.account.entity.AccountEntity;

@Transactional
public interface AccountService {

    AccountEntity getById(Long id);
    AccountEntity getByEmail(String email);
    SigninResponse getOne(String email, String password);

    AccountEntity register(SigninRequest accountDTO);

    SigninResponse checkSocial(String email);

    SigninResponse update(AccountDTO accountDTO, @RequestParam(value = "file", required = false) MultipartFile file);

    SigninResponse refresh(RefreshDTO refreshDTO);
}
