package sample.cafekiosk.spring.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus productSellingType;

    @Column
    private String name;

    @Column
    private int price;

    @Builder
    public Product(String productNumber, ProductType productType, ProductSellingStatus productSellingType, String name, int price) {
        this.productNumber = productNumber;
        this.productType = productType;
        this.productSellingType = productSellingType;
        this.name = name;
        this.price = price;
    }

}
