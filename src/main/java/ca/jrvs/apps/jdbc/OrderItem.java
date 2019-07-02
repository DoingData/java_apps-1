package ca.jrvs.apps.jdbc;

public class OrderItem {

    private long quantity;
    private String product_name;
    private String product_code;
    private long product_size;
    private String product_variety;
    private double product_price;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public long getProduct_size() {
        return product_size;
    }

    public void setProduct_size(long product_size) {
        this.product_size = product_size;
    }

    public String getProduct_variety() {
        return product_variety;
    }

    public void setProduct_variety(String product_variety) {
        this.product_variety = product_variety;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "quantity=" + quantity +
                ", product_name='" + product_name + '\'' +
                ", product_code='" + product_code + '\'' +
                ", product_size=" + product_size +
                ", product_variety='" + product_variety + '\'' +
                ", product_price=" + product_price +
                '}';
    }
}
