package homework;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ShoppingCartCalculatorTest {
    String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    @Test
    public void case01() throws IOException {
        String input = "C:\\Users\\ming\\IdeaProjects\\leetcode\\src\\homework\\input\\case01.txt";
        double expected = 3083.60;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }

    @Test
    public void case02() throws IOException {
        String input = "C:\\Users\\ming\\IdeaProjects\\leetcode\\src\\homework\\input\\case03.txt";
        double expected = 43.54;
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        assertEquals(expected, cal.calculate(input), 0.01);
    }
}