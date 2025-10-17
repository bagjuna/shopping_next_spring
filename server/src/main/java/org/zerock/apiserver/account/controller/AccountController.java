package org.zerock.apiserver.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.account.dto.AccountDTO;
import org.zerock.apiserver.account.dto.RefreshDTO;
import org.zerock.apiserver.account.dto.SigninDTO;
import org.zerock.apiserver.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@Log4j2
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping("signin")
    public AccountDTO login(@RequestBody SigninDTO signinDTO) {

        log.info("signin...............");
        log.info(signinDTO);
        return service.getOne(signinDTO.getUsername(), signinDTO.getPassword());

    }

    @PostMapping("register")
    public AccountDTO register(AccountDTO accountDTO) {

        log.info("register...............");
        log.info(accountDTO);

        service.register(accountDTO);

        return accountDTO;
    }


    @PostMapping("social")
    public AccountDTO social(String email) {

        log.info("social...............");
        log.info(email);

        AccountDTO accountDTO =  service.checkSocial(email);

        log.info("accountDTO................");
        log.info(accountDTO);

        return accountDTO;
    }

    @PutMapping("modify")
    public AccountDTO modify(AccountDTO accountDTO) {

        log.info("modify................");
        log.info(accountDTO);

        return service.update(accountDTO);

    }


    @PostMapping("refresh")
    public AccountDTO refresh( @RequestBody RefreshDTO refreshDTO) {

        log.info("refresh................");
        log.info(refreshDTO);

        return service.refresh(refreshDTO);
    }
}
