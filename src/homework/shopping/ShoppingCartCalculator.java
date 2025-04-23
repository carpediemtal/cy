package homework.shopping;

import java.util.*;


public class ShoppingCartCalculator {
    public double calculate(String filePath) {
        InputHandler inputParser = new InputHandler(filePath);
        List<Product> products = inputParser.getProducts();
        List<SinglePromotion> singlePromotions = inputParser.getSinglePromotions();
        Date settlementDate = inputParser.getSettlementDate();

        return calculateTotal(products, singlePromotions, inputParser.getTotalPromotions(), settlementDate);
    }

    // 按照各类优惠规则计算总价格
    public double calculateTotal(List<Product> products, List<SinglePromotion> singlePromotions, List<TotalPromotion> totalPromotions, Date settlementDate) {
        double total = 0;
        for (var product : products) {
            total += getPriceAfterPromotion(product, singlePromotions, settlementDate);
        }

        for (var promotion : totalPromotions) {
            total = promotion.TotalPriceAfterPromotion(total, settlementDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    // 计算单个商品优惠后的价格
    private static double getPriceAfterPromotion(Product product, List<SinglePromotion> promotions, Date settlementDate) {
        for (SinglePromotion promotion : promotions) {
            double result = promotion.PriceAfterPromotion(product, settlementDate);
            if (result == product.getTotalPrice()) {
                continue;
            }
            // 当前商品只会满足一个优惠条件
            return promotion.PriceAfterPromotion(product, settlementDate);
        }

        // 无优惠条件返回原件
        return product.getTotalPrice();
    }
}