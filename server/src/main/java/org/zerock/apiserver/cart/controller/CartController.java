package org.zerock.apiserver.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.cart.dto.CartDTO;
import org.zerock.apiserver.cart.dto.ChangeCartDTO;
import org.zerock.apiserver.cart.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService service;

//    curl -X POST \
//            -H "Content-Type: application/json" \
//            -d '{
//            "pno": 12,
//            "account": "user@example.com",
//            "quantity": 2
//            }' \
//  http://localhost:8080/api/carts/add

    @PostMapping("change")
    public ResponseEntity<List<CartDTO>> changeCart(@RequestBody ChangeCartDTO changeCartDTO) {

        log.info("addCart...............");
        log.info(changeCartDTO);

        return ResponseEntity.ok(service.addCart(changeCartDTO));

    }

    //curl -X GET "http://localhost:8080/api/carts/list?account=user@example.com"
    @GetMapping("list")
    public ResponseEntity<List<CartDTO>> getCart(@RequestParam("account") String account) {

        log.info("getCart...............");
        log.info(account);
        return ResponseEntity.ok(service.getCart(account));
    }

}
