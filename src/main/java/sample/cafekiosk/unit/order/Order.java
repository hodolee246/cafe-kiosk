package sample.cafekiosk.unit.order;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.unit.bererage.Beverage;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class Order {

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;

}
