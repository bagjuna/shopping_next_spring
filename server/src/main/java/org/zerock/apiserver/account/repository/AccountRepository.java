package org.zerock.apiserver.account.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.apiserver.account.entity.AccountEntity;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByRefreshToken(String refreshToken);

	Optional<AccountEntity> findByEmail(String email);

	@EntityGraph(attributePaths = {"images"})
	@Query("select a from AccountEntity a where a.email = :email")
	AccountEntity selectOne(String email);

}
