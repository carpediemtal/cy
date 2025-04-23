package homework.shopping;

import java.util.Date;
import java.util.Map;


// 品类折扣策略
public class CategorySinglePromotion implements SinglePromotion {
    Date date;  // 促销日期
    double discount;  // 折扣
    String category;  // 商品品类
    private static final Map<String, String> CATEGORY_MAP = new java.util.HashMap<>();
    public static final String ELECTRONICS = "电子";
    public static final String FOOD = "食品";
    public static final String DAILY_NECESSITIES = "日用品";
    public static final String ALCOHOL = "酒类";

    static {
        CATEGORY_MAP.put("ipad", ELECTRONICS);
        CATEGORY_MAP.put("iphone", ELECTRONICS);
        CATEGORY_MAP.put("显示器", ELECTRONICS);
        CATEGORY_MAP.put("笔记本电脑", ELECTRONICS);
        CATEGORY_MAP.put("键盘", ELECTRONICS);
        CATEGORY_MAP.put("面包", FOOD);
        CATEGORY_MAP.put("饼干", FOOD);
        CATEGORY_MAP.put("蛋糕", FOOD);
        CATEGORY_MAP.put("牛肉", FOOD);
        CATEGORY_MAP.put("鱼", FOOD);
        CATEGORY_MAP.put("蔬菜", FOOD);
        CATEGORY_MAP.put("餐巾纸", DAILY_NECESSITIES);
        CATEGORY_MAP.put("收纳箱", DAILY_NECESSITIES);
        CATEGORY_MAP.put("咖啡杯", DAILY_NECESSITIES);
        CATEGORY_MAP.put("雨伞", DAILY_NECESSITIES);
        CATEGORY_MAP.put("啤酒", ALCOHOL);
        CATEGORY_MAP.put("白酒", ALCOHOL);
        CATEGORY_MAP.put("伏特加", ALCOHOL);
    }

    public CategorySinglePromotion(Date date, double discount, String category) {
        if (discount <= 0 || discount > 1) {
            throw new IllegalArgumentException("折扣必须在 0 到 1 之间");
        }
        this.date = date;
        this.discount = discount;
        this.category = category;
    }

    // 判断当前商品是否满足当前的促销策略，返回计算价格
    @Override
    public double PriceAfterPromotion(Product product, Date settlementDate) {
        if (!CATEGORY_MAP.containsKey(product.name)) {
            throw new IllegalArgumentException("当前商品不在已有的商品种类中");
        }
        String productCategory = CATEGORY_MAP.get(product.name);
        if (productCategory != null && productCategory.equals(category) && date.compareTo(settlementDate) == 0) {
            return product.getTotalPrice() * discount;
        } else {
            return product.getTotalPrice();
        }
    }
}