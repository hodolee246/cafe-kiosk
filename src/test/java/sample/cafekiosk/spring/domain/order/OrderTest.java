package sample.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다")
    @Test
    void calculatorOrderTotalPrice() {
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000));

        Order order = Order.create(products, LocalDateTime.now());

        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }


    @DisplayName("주문 생성 시 주문상태는 INIT 이다")
    @Test
    void init() {
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000));

        Order order = Order.create(products, LocalDateTime.now());

        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 생성 시간이 들어간다")
    @Test
    void registerdDateTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000));

        Order order = Order.create(products, now);

        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
        assertThat(order.getRegisterDateTime()).isEqualTo(now);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .productType(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .productSellingType(ProductSellingStatus.SELLING)
                .name("name of menu")
                .build();
    }

}