package org.zerock.apiserver.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.account.entity.AccountEntity;
import org.zerock.apiserver.account.repository.AccountRepository;

@SpringBootTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository repository;


    @Test
    public void testInsert() {

        for (int i = 1; i <= 100 ; i++) {

            AccountEntity entity = AccountEntity.builder()
                    .email("user"+ i +"@aaa.com")
                    .password("1111")
                    .nickname("USER"+i)
                    .role(  i < 80 ? "USER":"ADMIN")
                    .build();

            repository.save(entity);

        }//end for

    }
}
