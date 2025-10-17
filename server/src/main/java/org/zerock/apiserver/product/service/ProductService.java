package org.zerock.apiserver.product.service;

import org.zerock.apiserver.product.dto.PageRequestDTO;
import org.zerock.apiserver.product.dto.ProductDTO;
import org.zerock.apiserver.product.dto.PageResponseDTO;

import java.util.List;

public interface ProductService {

    ProductDTO register(ProductDTO productDTO);

    ProductDTO getOne(Integer pno);

    ProductDTO update(ProductDTO productDTO);

    void remove(Integer pno);

    List<Integer> getTops(int count);

    PageResponseDTO list(PageRequestDTO pageRequestDTO);

    int getCatalogPageCount(int size);

}
