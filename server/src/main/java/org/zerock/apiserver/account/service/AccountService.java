package org.zerock.apiserver.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.account.dto.AccountDTO;
import org.zerock.apiserver.account.dto.RefreshDTO;
import org.zerock.apiserver.account.repository.AccountRepository;

import java.util.Optional;


@Transactional
public interface AccountService {

    AccountDTO getOne(String email, String password);

    void register(AccountDTO accountDTO);

    AccountDTO checkSocial(String email);

    AccountDTO update(AccountDTO accountDTO);

    AccountDTO refresh(RefreshDTO refreshDTO);
}
