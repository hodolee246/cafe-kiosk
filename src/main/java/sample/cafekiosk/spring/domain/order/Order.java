package sample.cafekiosk.spring.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.BaseEntity;
import sample.cafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;
    private LocalDateTime registerDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList;

    public Order(List<Product> products, LocalDateTime registerDateTime) {
        this.orderStatus = OrderStatus.INIT;
        this.totalPrice = calculateTotalPrice(products);
        this.registerDateTime = registerDateTime;
        this.orderProductList = products.stream()
                .map(product -> new OrderProduct(this, product))
                .toList();
    }

    private static int calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }

    public static Order create(List<Product> products, LocalDateTime registerDateTime) {
        return new Order(products, registerDateTime);
    }

}
