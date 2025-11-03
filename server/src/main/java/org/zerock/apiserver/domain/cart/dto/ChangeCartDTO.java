package org.zerock.apiserver.domain.cart.dto;

import lombok.Data;

@Data
public class ChangeCartDTO {

    private Integer pno;
    private int quantity;
    private String account;
}
