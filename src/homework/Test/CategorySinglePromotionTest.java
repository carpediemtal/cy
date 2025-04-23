package homework.Test;

import homework.shopping.CategorySinglePromotion;
import homework.shopping.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;


class CategorySinglePromotionTest {

    @Test
    void testPriceAfterPromotion() {
        Date date = new Date(122, 0, 1);
        Product productNotInList = new Product("牛奶", 1, 20.0);
        Product productInList = new Product("蔬菜", 1, 20.0);
        CategorySinglePromotion promotion = new CategorySinglePromotion(date, 0.7, "食品");

        double expected = 14.0;
        // 测试正常情况
        Assertions.assertEquals(expected, promotion.PriceAfterPromotion(productInList, date), 0.01);

        // 测试折扣非法的异常
        assertThrows(IllegalArgumentException.class, () -> {
            CategorySinglePromotion promotion2 = new CategorySinglePromotion(date, -0.7, "电子");
        });

        // 当前商品不在已有的商品品类中
        CategorySinglePromotion promotion2 = new CategorySinglePromotion(date, 0.7, "百货");
        assertThrows(IllegalArgumentException.class, () -> {
            promotion.PriceAfterPromotion(productNotInList, date);
        });
    }
}