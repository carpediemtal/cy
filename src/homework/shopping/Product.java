package homework.shopping;

public class Product {
    String name;  // 产品名称
    int quantity;  // 产品数量
    double price;  // 产品价格

    public Product(String name, int quantity, double price) {
        if (quantity < 0) {
            throw new IllegalArgumentException("商品数量不能为负数");
        }
        if (price < 0) {
            throw new IllegalArgumentException("商品价格不能为负数");
        }
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public double getTotalPrice() {
        return quantity * price;
    }
}
