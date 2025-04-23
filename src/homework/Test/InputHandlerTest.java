package homework.Test;

import homework.shopping.FullReductionCoupon;
import homework.shopping.InputHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InputHandlerTest {

    @Test
    void testInputParser() {
        // 商品列表为空的异常
//        assertThrows(IllegalArgumentException.class, () -> {
//            InputHandler handler = new InputHandler("./src/homework/input/case08.txt");
//        });

        // 结算日期为空的异常
        assertThrows(IllegalArgumentException.class, () -> {
            InputHandler handler = new InputHandler("./src/homework/input/case09.txt");
        });
    }
}