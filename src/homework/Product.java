package homework;

public class Product {
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
