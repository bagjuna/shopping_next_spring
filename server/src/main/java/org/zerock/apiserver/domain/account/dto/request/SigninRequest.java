package org.zerock.apiserver.domain.account.dto.request;


import lombok.Data;

@Data
public class SigninRequest {

    private String email;
    private String password;

}
