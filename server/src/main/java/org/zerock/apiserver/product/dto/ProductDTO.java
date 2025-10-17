package org.zerock.apiserver.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.apiserver.product.entity.ProductEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDTO {


    private Integer pno;
    private String pname;
    private double price;
    private String writer;
    private boolean sale;

    private List<String> fileNames = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    public ProductDTO(ProductEntity entity) {

        this.pno = entity.getPno();
        this.pname = entity.getPname();
        this.price = entity.getPrice();
        this.writer = entity.getWriter();
        this.sale = entity.isSale();
        this.createdDate = entity.getCreatedDate();

        if(entity.getImages() != null && entity.getImages().size() > 0){
            this.fileNames = entity.getImages()
                    .stream()
                    .map(productImage -> productImage.getFileName()).toList();

        }


    }

    public void addFileName(String fileName) {

        this.fileNames.add(fileName);

    }

    public ProductEntity toEntity() {
        ProductEntity entity = ProductEntity.builder()
                .pno(this.pno)
                .pname(this.pname)
                .price(this.price)
                .writer(this.writer)
                .sale(this.sale)
                .build();

        if(this.fileNames != null && this.fileNames.size() > 0) {
            this.fileNames.forEach(entity::addImage);
        }

        return entity;
    }

}
