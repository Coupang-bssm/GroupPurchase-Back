package co.kr.grouppurchace.domain.product.repository;

import co.kr.grouppurchace.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop10ByOrderByIdDesc();

    List<Product> findTop10ByIdLessThanOrderByIdDesc(Long lastId);
}
