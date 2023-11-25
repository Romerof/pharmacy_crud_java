
package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class ProductSale extends AbstractDataObject{
    private int amount;
    private double price;
    private Product product;
    private Sale sale;
    
    public ProductSale() {
        super(-1, "", "");
        this.amount = 0;
        this.price = 0.0;
        this.product = null;
        this.sale = null;
    }

    public ProductSale(int id, int amount, double price, Product product, Sale sale, String created, String updated) {
        super(id, created, updated);
        this.amount = amount;
        this.price = price;
        this.product = product;
        this.sale = sale;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "ProductSale{ id=" +id+ ", amount=" + amount + ", price=" + price + ", product=" + product + ", sale=" + '}';
    }

    
    
    
}
