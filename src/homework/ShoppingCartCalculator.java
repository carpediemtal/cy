package homework;

import java.util.*;


public class ShoppingCartCalculator {

    public double calculate(String filePath) {
        InputHandlerImpl inputParser = new InputHandlerImpl(filePath);
        List<Product> products = inputParser.getProducts();
        List<PromotionStrategy> promotions = inputParser.getPromotions();
        Date settlementDate = inputParser.getSettlementDate();

        return calculateTotal(products, promotions, settlementDate);
    }

    // 按照各类优惠规则计算总价格
    public double calculateTotal(List<Product> products, List<PromotionStrategy> promotions, Date settlementDate) {
        double total = 0;
        for (Product product : products) {
            total += getPriceAfterPromotion(product, promotions, settlementDate);
        }

        for (PromotionStrategy promotion : promotions) {
            total = promotion.FullReduction(total, settlementDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    private static double getPriceAfterPromotion(Product product, List<PromotionStrategy> promotions, Date settlementDate) {
        for (PromotionStrategy promotion : promotions) {
            double result = promotion.CategoryDiscount(product, settlementDate);
            if (result == product.getTotalPrice()) {
                continue;
            }
            return promotion.CategoryDiscount(product, settlementDate);
        }
        return product.getTotalPrice();
    }

    public static void main(String[] args) {
        ShoppingCartCalculator cal = new ShoppingCartCalculator();
        double res = cal.calculate("C:\\Users\\ming\\IdeaProjects\\leetcode\\src\\homework\\input\\case03.txt");
        System.out.printf("%.2f%n", res);
    }
}