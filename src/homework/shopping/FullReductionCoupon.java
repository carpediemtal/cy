package homework.shopping;

import java.util.Date;


// 满减优惠券策略类
public class FullReductionCoupon implements TotalPromotion {
    Date expirationDate;
    double threshold;
    double reduction;

    public FullReductionCoupon(Date expirationDate, double threshold, double reduction) {
        if (threshold < 0) {
            throw new IllegalArgumentException("满减门槛不能为负数");
        }
        if (reduction < 0) {
            throw new IllegalArgumentException("满减金额不能为负数");
        }
        this.expirationDate = expirationDate;
        this.threshold = threshold;
        this.reduction = reduction;
    }

    public double TotalPriceAfterPromotion(double total, Date settlementDate) {
        if (expirationDate.equals(settlementDate) && total >= threshold) {
            return total - reduction;
        }
        return total;
    }
}
