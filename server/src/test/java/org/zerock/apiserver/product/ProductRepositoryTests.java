package org.zerock.apiserver.product;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.zerock.apiserver.product.dto.ProductDTO;
import org.zerock.apiserver.product.entity.ProductEntity;
import org.zerock.apiserver.product.repository.ProductRepository;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void testInsert() {

        for (int i = 1; i <= 10 ; i++) {

            ProductEntity entity = ProductEntity.builder()
                    .pname("Test Product " + i)
                    .price(10000 + i)
                    .writer("user00")
                    .sale(true)
                    .build();

            entity.addImage("test1.webp");
            entity.addImage("test2.webp");

            repository.save(entity);
            log.info(entity);


        }

    }

    @Test
    public void testList() {

        PageRequest pageRequest = PageRequest.of(0, 4);

        repository.list(pageRequest).forEach(log::info);

    }

    @Test
    public void testFindOne() {

        Integer pno = 1;

        ProductEntity productEntity = repository.selectOne(pno);

        log.info(productEntity);
        log.info(productEntity.getImages());

        ProductDTO productDTO = new ProductDTO(productEntity);
        log.info(productDTO);
    }

}
