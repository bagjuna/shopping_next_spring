package org.zerock.apiserver.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDTO {

    private Integer pno;
    private String pname;
    private double price;
    private String writer;
    private boolean sale;

    //Each Product has a image file
    private String fileName;

    public ProductListDTO(Integer pno, String pname, double price, String writer, boolean sale, String fileName) {
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.writer = writer;
        this.sale = sale;
        this.fileName = fileName;
    }

}
