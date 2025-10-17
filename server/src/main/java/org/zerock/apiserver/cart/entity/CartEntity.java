package org.zerock.apiserver.cart.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.apiserver.product.entity.ProductEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_account_cart", indexes = {
        @Index(name = "idx_account_cart_account", columnList = "account")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@EqualsAndHashCode(of = "cno")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private ProductEntity product;
    private int quantity;

    private String account;

    @CreatedDate
    private LocalDateTime addCartDate;

    @LastModifiedDate
    private LocalDateTime updateCartDate;

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }




}
