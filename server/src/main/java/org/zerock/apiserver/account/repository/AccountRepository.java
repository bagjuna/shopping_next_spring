package org.zerock.apiserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.account.entity.AccountEntity;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByRefreshToken(String refreshToken);

}
