package homework;

import java.util.*;


// 产品类
class Product {
    String name;  // 产品名称
    int quantity;  // 产品数量
    double price;  // 产品价格

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public double getTotalPrice() {
        return quantity * price;
    }
}


// 统一优惠策略接口
interface PromotionStrategy {
    double CategoryDiscount(Product products, Date settlementDate);

    double FullReduction(double total, Date settlementDate);
}


class defaultPromotion implements PromotionStrategy {
    public double CategoryDiscount(Product product, Date settlementDate) {
        return product.getTotalPrice();
    }

    public double FullReduction(double total, Date settlementDate) {
        return total;
    }
}


// 品类折扣策略
class DateCategoryPromotion extends defaultPromotion {
    Date date;
    double discount;
    String category;
    private static final Map<String, String> CATEGORY_MAP = new java.util.HashMap<>();

    static {
        CATEGORY_MAP.put("ipad", "电子");
        CATEGORY_MAP.put("iphone", "电子");
        CATEGORY_MAP.put("显示器", "电子");
        CATEGORY_MAP.put("笔记本电脑", "电子");
        CATEGORY_MAP.put("键盘", "电子");
        CATEGORY_MAP.put("面包", "食品");
        CATEGORY_MAP.put("饼干", "食品");
        CATEGORY_MAP.put("蛋糕", "食品");
        CATEGORY_MAP.put("牛肉", "食品");
        CATEGORY_MAP.put("鱼", "食品");
        CATEGORY_MAP.put("蔬菜", "食品");
        CATEGORY_MAP.put("餐巾纸", "日用品");
        CATEGORY_MAP.put("收纳箱", "日用品");
        CATEGORY_MAP.put("咖啡杯", "日用品");
        CATEGORY_MAP.put("雨伞", "日用品");
        CATEGORY_MAP.put("啤酒", "酒类");
        CATEGORY_MAP.put("白酒", "酒类");
        CATEGORY_MAP.put("伏特加", "酒类");
    }

    public DateCategoryPromotion(Date date, double discount, String category) {
        this.date = date;
        this.discount = discount;
        this.category = category;
    }

    @Override
    public double CategoryDiscount(Product product, Date settlementDate) {
        String productCategory = CATEGORY_MAP.get(product.name);
        if (productCategory != null && productCategory.equals(category) && date.compareTo(settlementDate) <= 0) {
            return product.getTotalPrice() * discount;
        } else {
            return product.getTotalPrice();
        }
    }
}


// 满减优惠券策略类
class FullReductionCoupon extends defaultPromotion {
    Date expirationDate;
    double threshold;
    double reduction;

    public FullReductionCoupon(Date expirationDate, double threshold, double reduction) {
        this.expirationDate = expirationDate;
        this.threshold = threshold;
        this.reduction = reduction;
    }


    @Override
    public double FullReduction(double total, Date settlementDate) {
        if (expirationDate.after(settlementDate) && total >= threshold) {
            return total - reduction;
        }
        return total;
    }
}

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