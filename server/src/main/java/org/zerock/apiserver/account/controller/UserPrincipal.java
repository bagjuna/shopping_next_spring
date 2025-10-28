package org.zerock.apiserver.account.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserPrincipal {
    private String id; // NextAuth의 token.id (email)
    private String role; // NextAuth의 token.role
    private String email;
    private String name; // NextAuth의 token.name

}
