package org.zerock.apiserver.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResponseDTO<E> {

    private List<E> list;
    private long total;

    private PageRequestDTO pageRequestDTO;

    public PageResponseDTO(List<E> list, long total, PageRequestDTO pageRequestDTO) {
        this.list = list;
        this.total = total;
        this.pageRequestDTO = pageRequestDTO;
    }
}
