package co.kr.grouppurchace.domain.product.service;

import co.kr.grouppurchace.domain.product.dto.ProductResponse;
import co.kr.grouppurchace.domain.product.dto.ProductSaveRequest;
import co.kr.grouppurchace.domain.product.dto.ProductUpdateRequest;
import co.kr.grouppurchace.domain.product.entity.Product;
import co.kr.grouppurchace.domain.product.repository.ProductRepository;
import co.kr.grouppurchace.global.exception.EntityNotFoundException;
import co.kr.grouppurchace.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long save(ProductSaveRequest request) {
        return productRepository.save(request.toEntity()).getId();
    }

    @Transactional
    public void update(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        product.update(request.getName(), request.getDescription(), request.getPrice(), request.getImageUrl());
    }

    @Transactional
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    public ProductResponse findById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        return new ProductResponse(product);
    }

    public List<ProductResponse> findAll(Long lastId) {
        List<Product> products;
        if (lastId == null) {
            products = productRepository.findTop10ByOrderByIdDesc();
        } else {
            products = productRepository.findTop10ByIdLessThanOrderByIdDesc(lastId);
        }
        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

}
