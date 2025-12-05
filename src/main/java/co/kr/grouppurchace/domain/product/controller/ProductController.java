package co.kr.grouppurchace.domain.product.controller;

import co.kr.grouppurchace.domain.product.dto.ProductResponse;
import co.kr.grouppurchace.domain.product.dto.ProductSaveRequest;
import co.kr.grouppurchace.domain.product.dto.ProductUpdateRequest;
import co.kr.grouppurchace.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createProduct(@RequestBody ProductSaveRequest request) {
        Long productId = productService.save(request);
        URI location = URI.create("/api/products/" + productId);
        return ResponseEntity.created(location).body(Map.of("message", "상품이 등록되었습니다."));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request) {
        productService.update(productId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(Map.of("productId", productId, "message", "상품이 삭제되었습니다."));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.findById(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam(required = false) Long lastId) {
        List<ProductResponse> response = productService.findAll(lastId);
        return ResponseEntity.ok(response);
    }

}
