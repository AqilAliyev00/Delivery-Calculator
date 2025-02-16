import aliyev.Delivery;
import aliyev.DeliveryCostCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeliveryCostCalculatorTest {
    private final DeliveryCostCalculator calculator = new DeliveryCostCalculator();

    @Test
    @Tag("Exception")
    @DisplayName("Проверка исключения для хрупких грузов на расстоянии более 30 км")
    void testFragileOver30KmThrowsException() {
        Delivery delivery = new Delivery(31, "большой", true, "обычная");
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateCost(delivery));
    }

    @ParameterizedTest
    @Tag("Calculation")
    @DisplayName("Проверка расчёта стоимости доставки")
    @CsvSource({
            "1, маленький, false, обычная, 400",
            "5, большой, false, повышенная, 400",
            "15, большой, true, высокая, 980",
            "25, маленький, false, очень высокая, 480"
    })
    void testCalculateCost(double distance, String size, boolean fragile, String load, int expected) {
        Delivery delivery = new Delivery(distance, size, fragile, load);
        assertEquals(expected, calculator.calculateCost(delivery));
    }

    @Test
    @Tag("MinimumCost")
    @DisplayName("Проверка минимальной стоимости доставки")
    void testMinimumCost() {
        Delivery delivery = new Delivery(1, "маленький", false, "обычная");
        assertEquals(400, calculator.calculateCost(delivery));
    }

    @Test
    @Tag("Multiplier")
    @DisplayName("Проверка корректного применения коэффициента загруженности")
    void testLoadMultiplier() {
        Delivery delivery = new Delivery(10, "большой", false, "очень высокая");
        assertEquals(480, calculator.calculateCost(delivery));
    }

    @Test
    @Tag("EdgeCase")
    @DisplayName("Проверка на предельное расстояние для хрупких грузов")
    void testFragileEdgeCase() {
        Delivery delivery = new Delivery(30, "маленький", true, "обычная");
        assertEquals(600, calculator.calculateCost(delivery));
    }
}
