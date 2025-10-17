package org.zerock.apiserver.cart.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.cart.dto.CartDTO;
import org.zerock.apiserver.cart.dto.ChangeCartDTO;

import java.util.List;

@Transactional
public interface CartService {

    List<CartDTO> addCart(ChangeCartDTO changeCartDTO);

    List<CartDTO> getCart(String account);
}
