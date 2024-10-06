package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductSellingTypeIn(List<ProductSellingStatus> sellingType);

    List<Product> findAllByProductNumberIn(List<String> productNumber);
}
