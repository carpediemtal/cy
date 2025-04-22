package homework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

// 促销策略接口
interface PromotionStrategy {
    double applyPromotion(Product product, Date settlementDate);
}

// 品类折扣策略
class CategoryPromotion implements PromotionStrategy {
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

    public CategoryPromotion(Date date, double discount, String category) {
        this.date = date;
        this.discount = discount;
        this.category = category;
    }

    @Override
    public double applyPromotion(Product product, Date settlementDate) {
        String productCategory = CATEGORY_MAP.get(product.name);
        if (productCategory != null && productCategory.equals(category) && date.compareTo(settlementDate) <= 0) {
            return product.getTotalPrice() * discount;
        }
        return product.getTotalPrice();
    }
}

// 优惠券接口
interface CouponStrategy {
    double applyCoupon(double total, Date settlementDate);
}

// 满减优惠券策略
class FullReductionCoupon implements CouponStrategy {
    Date expirationDate;
    double threshold;
    double reduction;

    public FullReductionCoupon(Date expirationDate, double threshold, double reduction) {
        this.expirationDate = expirationDate;
        this.threshold = threshold;
        this.reduction = reduction;
    }

    @Override
    public double applyCoupon(double total, Date settlementDate) {
        if (expirationDate.after(settlementDate) && total >= threshold) {
            return total - reduction;
        }
        return total;
    }
}

public class ShoppingCartCalculator {
    public static double calculateTotal(List<Product> products, List<PromotionStrategy> promotions, CouponStrategy coupon, Date settlementDate) {
        double total = 0;
        for (Product product : products) {
            double productTotal = product.getTotalPrice();
            for (PromotionStrategy promotion : promotions) {
                productTotal = promotion.applyPromotion(product, settlementDate);
            }
            total += productTotal;
        }
        if (coupon != null) {
            total = coupon.applyCoupon(total, settlementDate);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        List<PromotionStrategy> promotions = new ArrayList<>();
        CouponStrategy coupon = null;
        Date settlementDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Scanner scanner = new Scanner(System.in);

        // 读取所有输入直到 EOF（Ctrl+Z 或 Ctrl+D）
        List<String> allLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            allLines.add(scanner.nextLine());
        }
        scanner.close();

        // 按空行分割为四部分：促销、产品、日期+优惠券（合并）
        List<String> sections = new ArrayList<>();
        StringBuilder currentSection = new StringBuilder();
        for (String line : allLines) {
            if (line.trim().isEmpty()) { // 空行作为分隔符
                if (currentSection.length() > 0) {
                    sections.add(currentSection.toString().trim());
                    currentSection = new StringBuilder();
                }
            } else {
                currentSection.append(line).append("\n");
            }
        }
        // 添加最后一个部分（可能包含日期和优惠券）
        if (currentSection.length() > 0) {
            sections.add(currentSection.toString().trim());
        }

        // 解析四部分（允许前两部分为空，后两部分合并为日期+优惠券）
        String promotionSection = sections.size() > 0 ? sections.get(0) : "";
        String productSection = sections.size() > 1 ? sections.get(1) : "";
        String dateAndCouponSection = sections.size() > 2 ? sections.get(2) : "";

        // 解析促销信息（第一部分）
        parsePromotions(promotionSection, promotions, dateFormat);
        // 解析产品信息（第二部分）
        parseProducts(productSection, products);
        // 解析日期和优惠券（第三部分）
        Object[] result = parseDateAndCoupon(dateAndCouponSection, dateFormat);
        settlementDate = (Date) result[0];
        coupon = (CouponStrategy) result[1];

        if (settlementDate != null) {
            double total = calculateTotal(products, promotions, coupon, settlementDate);
            System.out.printf("%.2f%n", total);
        }
    }

    private static void parsePromotions(String section, List<PromotionStrategy> promotions, SimpleDateFormat dateFormat) {
        for (String line : section.split("\n")) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(" \\| ");
            if (parts.length == 3) {
                try {
                    promotions.add(new CategoryPromotion(
                            dateFormat.parse(parts[0]),
                            Double.parseDouble(parts[1]),
                            parts[2]
                    ));
                } catch (Exception e) { /* 忽略格式错误的行 */ }
            }
        }
    }

    private static void parseProducts(String section, List<Product> products) {
        for (String line : section.split("\n")) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(" \\* ");
            if (parts.length == 2) {
                try {
                    String[] namePrice = parts[1].split(" : ");
                    products.add(new Product(
                            namePrice[0].trim(),
                            Integer.parseInt(parts[0]),
                            Double.parseDouble(namePrice[1])
                    ));
                } catch (Exception e) { /* 忽略格式错误的行 */ }
            }
        }
    }

    private static Object[] parseDateAndCoupon(String section, SimpleDateFormat dateFormat) {
        Date settlementDate = null;
        CouponStrategy coupon = null;
        String[] lines = section.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            if (i == 0) { // 第一行为结算日期
                try {
                    settlementDate = dateFormat.parse(line);
                } catch (ParseException e) { /* 日期格式错误则跳过 */ }
            } else { // 后续行为优惠券信息
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    try {
                        coupon = new FullReductionCoupon(
                                dateFormat.parse(parts[0]),
                                Double.parseDouble(parts[1]),
                                Double.parseDouble(parts[2])
                        );
                    } catch (Exception e) { /* 忽略格式错误的优惠券 */ }
                }
            }
        }
        return new Object[]{settlementDate, coupon};
    }
}