package org.zerock.apiserver.product.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.apiserver.product.dto.ProductDTO;
import org.zerock.apiserver.product.dto.ProductListDTO;
import org.zerock.apiserver.product.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "select new org.zerock.apiserver.product.dto.ProductListDTO(" +
            "p.pno, p.pname, p.price, p.writer, p.sale, pi.fileName) " +
            "from ProductEntity p left join p.images pi " +
            "where pi.ord = 0 or pi.ord is null " +
            "order by p.pno desc",
            countQuery = "select count(p) from ProductEntity p")
    Page<ProductListDTO> list(Pageable pageable);

    @EntityGraph(attributePaths = {"images"})
    @Query("select p from ProductEntity p where p.pno = :pno")
    ProductEntity selectOne(Integer pno);

    @Query("select count(p) from ProductEntity  p where p.sale = true")
    Long getCatalogPageCount(int size);


}
