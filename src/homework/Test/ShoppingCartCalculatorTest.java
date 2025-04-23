package homework.Test;

import homework.shopping.ShoppingCartCalculator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoppingCartCalculatorTest {
    // 有折扣有优惠券
    @Test
    public void case01() {
        String input = "./src/homework/input/case01.txt";
        double expected = 3083.60;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    // 无折扣无优惠券
    @Test
    public void case02() {
        String input = "./src/homework/input/case02.txt";
        double expected = 43.54;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    // 有折扣无优惠券
    @Test
    public void case03() {
        String input = "./src/homework/input/case03.txt";
        double expected = 4453;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    // 优惠券未达门槛
    @Test
    public void case04() {
        String input = "./src/homework/input/case04.txt";
        double expected = 3283.60;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    // 无折扣有优惠券
    @Test
    public void case05() {
        String input = "./src/homework/input/case05.txt";
        double expected = 4343.00;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    // 部分促销信息日期与结算日期不一致
    @Test
    public void case06() {
        String input = "./src/homework/input/case06.txt";
        double expected = 4278.5;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    // 优惠券日期与结算日期不一致
    @Test
    public void case07() {
        String input = "./src/homework/input/case07.txt";
        double expected = 4478.5;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }
}