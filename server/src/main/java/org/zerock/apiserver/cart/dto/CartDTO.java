package org.zerock.apiserver.cart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.apiserver.cart.entity.CartEntity;
import org.zerock.apiserver.product.entity.ProductEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CartDTO {

    private Long cno;

    //about product
    private Integer pno;
    private String pname;
    private double price;
    private String fileName;
    private boolean sale;

    private int quantity;

    private String account;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addCartDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateCartDate;

    public CartDTO(CartEntity entity) {
        this.cno = entity.getCno();

        ProductEntity product = entity.getProduct();

        this.pno = product.getPno();
        this.pname = product.getPname();
        this.price = product.getPrice();
        this.fileName = product.getImages().stream().findFirst().orElse(null).getFileName();
        this.quantity = entity.getQuantity();
        this.account = entity.getAccount();
        this.addCartDate = entity.getAddCartDate();
        this.updateCartDate = entity.getUpdateCartDate();
    }
}
