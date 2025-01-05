package sample.cafekiosk.spring.api.service.product;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.product.dto.request.ProductCreateRequest;

import java.util.List;

import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근상품의 상품번호에서 1 증가한 상품번호이다.")
    @Test
    void createProduct() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 2500);
        productRepository.save(product1);

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(3500)
                .build();

        // when
        ProductResponse product = productService.createProduct(request);

        // then
        Assertions.assertThat(product)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("002", HANDMADE, SELLING, "카푸치노", 3500);

        List<Product> productList = productRepository.findAll();
        Assertions.assertThat(productList).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", HANDMADE, SELLING, "아메리카노", 2500),
                        Tuple.tuple("002", HANDMADE, SELLING, "카푸치노", 3500)
                );
    }

    @DisplayName("신규 상품을 등록할때 신규상품이 하나 도 없는경우 상품번호는 001이다.")
    @Test
    void createProductWhenProductIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(3500)
                .build();

        // when
        ProductResponse product = productService.createProduct(request);

        // then
        Assertions.assertThat(product)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("001", HANDMADE, SELLING, "카푸치노", 3500);

        List<Product> productList = productRepository.findAll();
        Assertions.assertThat(productList).hasSize(1)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", HANDMADE, SELLING, "카푸치노", 3500)
                );
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
