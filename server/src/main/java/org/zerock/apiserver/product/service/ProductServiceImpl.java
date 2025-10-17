package org.zerock.apiserver.product.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.product.dto.PageRequestDTO;
import org.zerock.apiserver.product.dto.ProductDTO;
import org.zerock.apiserver.product.dto.ProductListDTO;
import org.zerock.apiserver.product.dto.PageResponseDTO;
import org.zerock.apiserver.product.entity.ProductEntity;
import org.zerock.apiserver.product.entity.QProductEntity;
import org.zerock.apiserver.product.entity.QProductImage;
import org.zerock.apiserver.product.repository.ProductRepository;
import org.zerock.apiserver.product.util.FileUploader;

import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final JPAQueryFactory queryFactory; // JPAQueryFactory 주입
    private final FileUploader fileUploader;


    @Override
    public ProductDTO register(ProductDTO productDTO) {

        ProductEntity productEntity = productDTO.toEntity();

        productRepository.save(productEntity);

        return new ProductDTO(productEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO getOne(Integer pno) {

        ProductEntity productEntity = productRepository.selectOne(pno);

        return new ProductDTO(productEntity);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.selectOne(productDTO.getPno());

        productEntity.changePname(productDTO.getPname());
        productEntity.changePrice(productDTO.getPrice());
        productEntity.changeSale(productDTO.isSale());

        if(productDTO.getFileNames() != null && productDTO.getFileNames().size() > 0) {
            productEntity.removeImages();
            productDTO.getFileNames().forEach(productEntity::addImage);
        }

        log.info("===============update=====================");
        log.info(productEntity);
        log.info(productEntity.getImages());
        log.info("===============update end====================");

        //drity checki
        return new ProductDTO(productEntity);
    }

    @Override
    public void remove(Integer pno) {
        ProductEntity productEntity = productRepository.selectOne(pno);
        productEntity.changePname("판매중지상품");
        productEntity.changeSale(false);

        //dirty checking
    }

    @Override
    public List<Integer> getTops(int count) {

        QProductEntity productEntity = QProductEntity.productEntity;

        BooleanBuilder builder = new BooleanBuilder();

        JPQLQuery<ProductEntity> query = queryFactory.selectFrom(productEntity);
        query.limit(count);
        query.orderBy(productEntity.pno.desc());

        JPQLQuery<Integer> pnoQuery = query.select(productEntity.pno);

        List<Integer> pnoList = pnoQuery.fetch();

        return pnoList;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO list(PageRequestDTO requestDTO) {

        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;
        JPQLQuery<ProductEntity> query = queryFactory.selectFrom(productEntity);
        query.join(productEntity.images, productImage);

        Pageable pageable = PageRequest.of(requestDTO.getPage() - 1, requestDTO.getSize());

        String keyword = requestDTO.getKeyword();

        query.where(QProductEntity.productEntity.sale.eq(true));

        if(keyword != null && keyword.isEmpty() == false) {

            query.where(QProductEntity.productEntity.pname.contains(keyword));
        }

        query.where(productImage.ord.eq(0));

        OrderSpecifier order =  QProductEntity.productEntity.pno.desc();

        if(requestDTO.getSort() != null) {

            //가격 낮은 순
            if(requestDTO.getSort().equals("pl")) {
                order = QProductEntity.productEntity.price.asc();
            //가격 높은 순
            }else if(requestDTO.getSort().equals("ph")) {
                order = QProductEntity.productEntity.price.desc();
            //출시순
            }else if(requestDTO.getSort().equals("d")) {
                order = QProductEntity.productEntity.createdDate.asc();
            }

        }


        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());
        query.orderBy(order);

        //Integer pno, String pname, double price, String writer, boolean sale, String fileName
        List<ProductListDTO> listDTOList =
                query.select(
                        Projections.bean(
                                ProductListDTO.class,
                                productEntity.pno,
                                productEntity.pname,
                                productEntity.price,
                                productEntity.writer,
                                productEntity.sale,
                                productImage.fileName.as("fileName")
                        )).fetch();

        long total = query.fetchCount();

        return new PageResponseDTO<ProductListDTO>(listDTOList, total, requestDTO);

    }

    @Override
    public int getCatalogPageCount(int size) {
        long count = productRepository.getCatalogPageCount(size);

        return (int)count;
    }


}
