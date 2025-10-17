package org.zerock.apiserver.product.dto;


import lombok.Data;

@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String sort;
    private String keyword;


    public PageRequestDTO(){
        this.page = 1;
        this.size = 10;
    }

}
