package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 2000);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 2000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 2000);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 2000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("가장 마지막에 저장된 상품번호를 불러온다.")
    @Test
    public void getLatestProductNumber() {
        // given
        String latestProductNumber = "003";
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, SELLING, "아메리카노", 4000);
        Product product3 = createProduct(latestProductNumber, HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String latestProduct = productRepository.findLatestProduct();

        // then
        assertThat(latestProduct).isEqualTo(latestProductNumber);
    }

    @DisplayName("가장 마지막에 저장된 상품번호를 불러온다. 단 하나도 없는경우 null을 불러온다.")
    @Test
    public void getLatestProductNumberToNullWhenProductIsEmpty() {
        // given
        // when
        String latestProduct = productRepository.findLatestProduct();

        // then
        assertThat(latestProduct).isEqualTo(null);
    }

    private static Product createProduct(String productNumber, ProductType type, ProductSellingStatus productSellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(productSellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}