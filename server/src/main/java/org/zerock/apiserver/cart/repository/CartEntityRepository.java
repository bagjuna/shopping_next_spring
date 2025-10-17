package org.zerock.apiserver.cart.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.zerock.apiserver.cart.entity.CartEntity;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;

public interface CartEntityRepository extends JpaRepository<CartEntity, Long>    {


    @EntityGraph(attributePaths = {"product", "product.images"})
    @Query("select c from CartEntity c where c.account = :account")
    List<CartEntity> findByAccount(String account);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // DB에 Write Lock을 걸어 동시 접근 방지
    @Query("SELECT ce FROM CartEntity ce WHERE ce.account = :account AND ce.product.pno = :pno")
    Optional<CartEntity> findByAccountAndPnoWithLock(String account, Integer pno);
}
