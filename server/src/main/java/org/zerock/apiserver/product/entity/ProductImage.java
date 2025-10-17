package org.zerock.apiserver.product.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "fileName")
public class ProductImage {

    private String fileName;

    private int ord;

    public ProductImage(String fileName, int ord) {
        this.fileName = fileName;
        this.ord = ord;
    }

}
