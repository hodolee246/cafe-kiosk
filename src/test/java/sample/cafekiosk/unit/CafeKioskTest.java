package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.bererage.Americano;

public class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> count of drink : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> name of drink : " + cafeKiosk.getBeverages().get(0).getName());
    }

}
