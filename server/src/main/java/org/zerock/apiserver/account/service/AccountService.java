package org.zerock.apiserver.account.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.account.dto.AccountDTO;
import org.zerock.apiserver.account.dto.RefreshDTO;
import org.zerock.apiserver.account.dto.request.SigninRequest;
import org.zerock.apiserver.account.dto.reaspone.SigninResponse;
import org.zerock.apiserver.account.entity.AccountEntity;

@Transactional
public interface AccountService {

    SigninResponse getOne(String email, String password);

    AccountEntity register(SigninRequest accountDTO);

    SigninResponse checkSocial(String email);

    SigninResponse update(AccountDTO accountDTO, @RequestParam(value = "file", required = false) MultipartFile file);

    SigninResponse refresh(RefreshDTO refreshDTO);
}
