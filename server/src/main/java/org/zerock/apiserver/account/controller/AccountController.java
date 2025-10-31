package org.zerock.apiserver.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.account.dto.AccountDTO;
import org.zerock.apiserver.account.dto.RefreshDTO;
import org.zerock.apiserver.account.dto.request.SigninRequest;
import org.zerock.apiserver.account.dto.reaspone.SigninResponse;
import org.zerock.apiserver.account.entity.AccountEntity;
import org.zerock.apiserver.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@Log4j2
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping("signin")
    public SigninResponse login(@RequestBody SigninRequest signinRequest) {

        log.info("signin...............");
        log.info(signinRequest);
        return service.getOne(signinRequest.getEmail(), signinRequest.getPassword());

    }

    @PostMapping("register")
    public SigninResponse register(SigninRequest accountDTO) {

        log.info("register...............");
        log.info(accountDTO);

        AccountEntity register = service.register(accountDTO);

        return SigninResponse.builder()
            .email(register.getEmail())
            .password(register.getPassword())
            .nickname(register.getNickname())
            .role(register.getRole())
            .joinDate(register.getJoinDate())
            .modifiedDate(register.getJoinDate())
            .accessToken(register.getAccessToken())
            .refreshToken(register.getRefreshToken())
            .build();

    }

    @PostMapping("social")
    public SigninResponse social(String email) {

        log.info("social...............");
        log.info(email);

        SigninResponse accountDTO =  service.checkSocial(email);

        log.info("accountDTO................");
        log.info(accountDTO);

        return accountDTO;
    }

    @PutMapping("modify")
    public SigninResponse modify(AccountDTO accountDTO, @RequestParam(value = "file", required = false) MultipartFile file) {

        log.info("modify................");
        log.info(accountDTO);

        return service.update(accountDTO, file);

    }


    @PostMapping("refresh")
    public SigninResponse refresh(@RequestBody RefreshDTO refreshDTO) {

        log.info("refresh................");
        log.info(refreshDTO);

        return service.refresh(refreshDTO);
    }

    // @GetMapping("/test")
    // public String test(@AuthenticationPrincipal String email) {
    //     return "Account Controller Test Success";
    // }
}
