package org.zerock.apiserver.product.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images")
@Getter

public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pno;

    private String pname;

    private double price;

    private String writer;

    private LocalDateTime createdDate;

    private boolean sale;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tbl_product_images", // 상품 이미지 정보를 저장할 테이블 이름
            joinColumns = @JoinColumn(name = "pno"), // Product 엔티티의 ID를 참조하는 FK 컬럼
            indexes =  {
                    @Index(name = "idx_product_images_pno", columnList = "pno")
            }
    )
    @Builder.Default
    private Set<ProductImage> images = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    public void addImage(String fileName) {

        this.images.add(new ProductImage(fileName, this.images.size()));
    }

    public void removeImages() {
        this.images.clear();
    }

    public void changeSale(boolean sale) {
        this.sale = sale;
    }

    public void changePrice(double price) {
        this.price = price;
    }
    public void changePname(String pname) {
        this.pname = pname;
    }
}
