package org.zerock.apiserver.domain.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.cart.dto.CartDTO;
import org.zerock.apiserver.domain.cart.dto.ChangeCartDTO;
import org.zerock.apiserver.domain.cart.entity.CartEntity;
import org.zerock.apiserver.domain.cart.repository.CartEntityRepository;
import org.zerock.apiserver.domain.product.entity.ProductEntity;
import org.zerock.apiserver.domain.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartServiceImpl implements CartService {

	private final CartEntityRepository repository;

	private final ProductRepository productRepository;

	// @Override
	public List<CartDTO> addCartOld(ChangeCartDTO changeCartDTO) {

		List<CartEntity> cartEntityList = repository.findByAccount(changeCartDTO.getAccount());

		CartEntity target = cartEntityList.stream()
			.filter(entity -> entity.getProduct().getPno().equals(changeCartDTO.getPno()))
			.findFirst()
			.orElse(null);

		if (target != null) {
			target.changeQuantity(target.getQuantity() + changeCartDTO.getQuantity());

			if (target.getQuantity() <= 0) {
				repository.deleteById(target.getCno());
				cartEntityList.remove(target);
			}
		} else {

			ProductEntity productEntity = productRepository.selectOne(changeCartDTO.getPno());

			CartEntity entity = CartEntity.builder()
				.account(changeCartDTO.getAccount())
				.product(productEntity)
				.quantity(changeCartDTO.getQuantity())
				.build();

			repository.save(entity);
			cartEntityList.add(entity);
		}
		return cartEntityList.stream().map(entity -> new CartDTO(entity)).collect(Collectors.toList());
	}

	@Override
	public List<CartDTO> addCart(ChangeCartDTO changeCartDTO) {

		// 1. Lock을 걸고 특정 상품의 CartEntity를 조회
		Optional<CartEntity> targetOpt = repository.findByAccountAndPnoWithLock(
			changeCartDTO.getAccount(), changeCartDTO.getPno());

		// 2. 존재하면 수량 변경, 존재하지 않으면 새로 추가
		if (targetOpt.isPresent()) {
			// 존재하는 경우 수량 변경
			CartEntity target = targetOpt.get();

			// 수량 변경
			target.changeQuantity(target.getQuantity() + changeCartDTO.getQuantity());

			// 수량이 0 이하이면 삭제
			if (target.getQuantity() <= 0) {
				repository.deleteById(target.getCno());
			}
		}
		// 존재하지 않는 경우 새로 추가
		else {

			// 수량이 0 이하이면 예외 처리
			if (changeCartDTO.getQuantity() <= 0) {
				throw new IllegalArgumentException("장바구니에 없는 상품에 대해 수량 감소 요청이 들어왔습니다.");
			}
			// 상품 정보 조회
			ProductEntity productEntity = productRepository.selectOne(changeCartDTO.getPno());
			// 새 CartEntity 생성 및 저장
			CartEntity entity = CartEntity.builder()
				.account(changeCartDTO.getAccount())
				.product(productEntity)
				.quantity(changeCartDTO.getQuantity())
				.build();
			repository.save(entity);
		}

		// 3. 최종적으로 해당 사용자의 모든 CartEntity를 조회하여 반환
		return repository.findByAccount(changeCartDTO.getAccount())
			.stream()
			.map(entity -> new CartDTO(entity))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public List<CartDTO> getCart(String account) {

		List<CartEntity> cartEntityList = repository.findByAccount(account);

		if (cartEntityList != null && cartEntityList.size() > 0) {
			return cartEntityList.stream().map(entity -> new CartDTO(entity)).collect(Collectors.toList());
		}

		return new ArrayList<>();
	}
}
