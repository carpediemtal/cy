package homework.Test;

import homework.shopping.FullReductionCoupon;
import homework.shopping.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FullReductionCouponTest {

    @Test
    void testTotalPriceAfterPromotion() {
        Date date = new Date(122, 0, 1);
        Date SettlementDate = new Date(122, 0, 1);
        FullReductionCoupon promotion = new FullReductionCoupon(date, 50, 5);

        // 正常满减
        double expected = 95.0;
        Assertions.assertEquals(expected, promotion.TotalPriceAfterPromotion(100, date), 0.01);

        // 折扣与结算日期不同，不满减
        double expected2 = 100.0;
        Date SettlementDate2 = new Date(122, 1, 1);
        Assertions.assertEquals(expected2, promotion.TotalPriceAfterPromotion(100, SettlementDate2), 0.01);

        // 未达到满减门槛，不满减
        double expected3 = 49.0;
        Assertions.assertEquals(expected3, promotion.TotalPriceAfterPromotion(49, SettlementDate), 0.01);

        // 满减门槛为负的异常
        assertThrows(IllegalArgumentException.class, () -> {
            FullReductionCoupon promotion3 = new FullReductionCoupon(date, -50, 5);
        });

        // 满减金额为负的异常
        assertThrows(IllegalArgumentException.class, () -> {
            FullReductionCoupon promotion3 = new FullReductionCoupon(date, 50, -5);
        });

    }
}