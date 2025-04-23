package homework;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoppingCartCalculatorTest {
    @Test
    public void case01() {
        String input = "./src/homework/input/case01.txt";
        double expected = 3083.60;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    @Test
    public void case02() {
        String input = "./src/homework/input/case02.txt";
        double expected = 43.54;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    @Test
    public void case03() {
        String input = "./src/homework/input/case03.txt";
        double expected = 4253;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }
}