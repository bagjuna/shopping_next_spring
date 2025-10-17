package org.zerock.apiserver.account.dto.request;


import lombok.Data;

@Data
public class SigninRequest {

    private String email;
    private String password;

}
