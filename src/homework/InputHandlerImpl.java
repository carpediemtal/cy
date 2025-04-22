package homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandlerImpl {
    private static final String DATE_CATEGORY_PATTERN_STR = "(\\d{4}\\.\\d{2}\\.\\d{2}) \\| (\\d+\\.?\\d*) \\| (\\S+)";
    private static final String PRODUCT_PATTERN_STR = "(\\d+) \\* (\\S+) : (\\d+\\.?\\d*)";
    private static final String SETTLEMENT_DATE_PATTERN_STR = "(\\d{4}\\.\\d{2}\\.\\d{2})";
    private static final String COUPON_PATTERN_STR = "(\\d{4}\\.\\d{1,2}\\.\\d{1,2}) (\\d+) (\\d+)";


    private static final Pattern DATE_CATEGORY_PATTERN = Pattern.compile(DATE_CATEGORY_PATTERN_STR);
    private static final Pattern PRODUCT_PATTERN = Pattern.compile(PRODUCT_PATTERN_STR);
    private static final Pattern SETTLEMENT_DATE_PATTERN = Pattern.compile(SETTLEMENT_DATE_PATTERN_STR);
    private static final Pattern COUPON_PATTERN = Pattern.compile(COUPON_PATTERN_STR);

    private List<Product> products;
    private List<PromotionStrategy> promotions;
    private Date settlementDate;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public InputHandlerImpl(String filePath) {
        inputParser(filePath);
        // TODO：解析并校验文件
    }

    public void inputParser(String inputFilePath) {
        products = new ArrayList<>();
        promotions = new ArrayList<>();
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            parseContent(fileContent.toString());
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void parseContent(String content) {
        parseDateCategoryPromotions(content);  // 解析促销信息
        parseProducts(content);   // 解析产品
        parseSettlementDate(content);  // 解析结算日期
        parseCoupons(content);  // 解析优惠券信息
    }

    private void parseDateCategoryPromotions(String content) {
        Matcher matcher = DATE_CATEGORY_PATTERN.matcher(content);
        while (matcher.find()) {
            try {
                Date date = dateFormat.parse(matcher.group(1));
                double discount = Double.parseDouble(matcher.group(2));
                String category = matcher.group(3);
                promotions.add(new DateCategoryPromotion(date, discount, category));
            } catch (ParseException e) {
                // todo
            }
        }
    }

    private void parseProducts(String content) {
        Matcher matcher = PRODUCT_PATTERN.matcher(content);
        while (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            double price = Double.parseDouble(matcher.group(3));
            products.add(new Product(name, quantity, price));
        }
    }

    private void parseSettlementDate(String content) {
        Matcher matcher = SETTLEMENT_DATE_PATTERN.matcher(content);
        if (matcher.find()) {
            try {
                settlementDate = dateFormat.parse(matcher.group(1));
            } catch (ParseException e) {
                // todo
            }
        }
    }

    private void parseCoupons(String content) {
        Matcher matcher = COUPON_PATTERN.matcher(content);
        while (matcher.find()) {
            try {
                Date expirationDate = dateFormat.parse(matcher.group(1));
                double threshold = Double.parseDouble(matcher.group(2));
                double reduction = Double.parseDouble(matcher.group(3));
                promotions.add(new FullReductionCoupon(expirationDate, threshold, reduction));
            } catch (ParseException e) {
                // todo
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<PromotionStrategy> getPromotions() {
        return promotions;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }
}
