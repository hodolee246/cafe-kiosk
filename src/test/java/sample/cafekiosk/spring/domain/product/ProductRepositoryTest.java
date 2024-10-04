package sample.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품 조회")
    @Test
    void findAllByProductSellingTypeIn() {
        //given
        Product product1 = Product.builder()
                .productNumber("001")
                .productType(ProductType.HANDMADE)
                .productSellingType(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .productType(ProductType.HANDMADE)
                .productSellingType(ProductSellingStatus.HOLD)
                .name("아아")
                .price(4200)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .productType(ProductType.HANDMADE)
                .productSellingType(ProductSellingStatus.STOP_SELLING)
                .name("팥빙수")
                .price(7000)
                .build();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productRepository.saveAll(productList);
        //when
        productRepository.findAllByProductSellingTypeIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));
        //then
        Assertions.assertThat(productList).hasSameClassAs(2)
                .extracting("productNumber", "name", "sellingType")
                //순서 상관없이 체크
                .containsExactlyInAnyOrder(Tuple.tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        Tuple.tuple("002", "아메리카노", ProductSellingStatus.HOLD));
    }

}