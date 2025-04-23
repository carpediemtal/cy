package homework.shopping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private static final String DATE_CATEGORY_PATTERN_STR = "(\\d{4}\\.\\d{2}\\.\\d{2}) \\| (\\d+\\.?\\d*) \\| (\\S+)";
    private static final String PRODUCT_PATTERN_STR = "(\\d+) \\* (\\S+) : (\\-?\\d+\\.?\\d*)";
    private static final String SETTLEMENT_DATE_PATTERN_STR = "\\b(\\d{4})\\.(\\d{2})\\.(\\d{2})\\b(?! \\|)";
    private static final String COUPON_PATTERN_STR = "(\\d{4}\\.\\d{1,2}\\.\\d{1,2}) (\\d+) (\\d+)";


    private static final Pattern DATE_CATEGORY_PATTERN = Pattern.compile(DATE_CATEGORY_PATTERN_STR);
    private static final Pattern PRODUCT_PATTERN = Pattern.compile(PRODUCT_PATTERN_STR);
    private static final Pattern SETTLEMENT_DATE_PATTERN = Pattern.compile(SETTLEMENT_DATE_PATTERN_STR);
    private static final Pattern COUPON_PATTERN = Pattern.compile(COUPON_PATTERN_STR);

    private List<Product> products;
    private List<SinglePromotion> singlePromotions;
    private List<TotalPromotion> totalPromotions;
    private Date settlementDate = null;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public InputHandler(String filePath) {
        inputParser(filePath);
    }

    // 获取文件中全部的输入
    public void inputParser(String inputFilePath) {
        products = new ArrayList<>();
        singlePromotions = new ArrayList<>();
        totalPromotions = new ArrayList<>();
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            parseContent(fileContent.toString());
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw new IllegalArgumentException("文件读取错误");
        }
    }

    // 按照正则表达式解析信息
    private void parseContent(String content) {
        parseCategoryPromotions(content);
        parseProducts(content);
        parseSettlementDate(content);
        parseCoupons(content);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("商品列表不能为空");
        }
        if (settlementDate == null) {
            throw new IllegalArgumentException("结算日期不能为空");
        }
    }

    // 解析促销信息
    private void parseCategoryPromotions(String content) {
        Matcher matcher = DATE_CATEGORY_PATTERN.matcher(content);
        while (matcher.find()) {
            try {
                Date date = dateFormat.parse(matcher.group(1));
                double discount = Double.parseDouble(matcher.group(2));
                String category = matcher.group(3);
                singlePromotions.add(new CategorySinglePromotion(date, discount, category));
            } catch (ParseException e) {
                throw new IllegalArgumentException("促销信息格式错误");
            }
        }
    }

    // 解析产品
    private void parseProducts(String content) {
        Matcher matcher = PRODUCT_PATTERN.matcher(content);
        while (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            double price = Double.parseDouble(matcher.group(3));
            products.add(new Product(name, quantity, price));
        }
    }

    // 解析结算日期
    private void parseSettlementDate(String content) {
        Matcher matcher = SETTLEMENT_DATE_PATTERN.matcher(content);
        if (matcher.find()) {
            try {
                settlementDate = dateFormat.parse(matcher.group());
            } catch (ParseException e) {
                throw new IllegalArgumentException("日期格式错误");
            }
        }
    }

    // 解析优惠券信息
    private void parseCoupons(String content) {
        Matcher matcher = COUPON_PATTERN.matcher(content);
        while (matcher.find()) {
            try {
                Date expirationDate = dateFormat.parse(matcher.group(1));
                double threshold = Double.parseDouble(matcher.group(2));
                double reduction = Double.parseDouble(matcher.group(3));
                totalPromotions.add(new FullReductionCoupon(expirationDate, threshold, reduction));
            } catch (ParseException e) {
                throw new IllegalArgumentException("优惠券信息格式错误");
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<SinglePromotion> getSinglePromotions() {
        return singlePromotions;
    }

    public List<TotalPromotion> getTotalPromotions() {
        return totalPromotions;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }
}
