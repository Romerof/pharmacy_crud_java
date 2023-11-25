
package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class PurchaseDet extends AbstractDataObject {
    
    Product product;
    int amount;
    Purchase purchase;
    Double cost;
    
    public PurchaseDet(int id, Product product, int amount, Purchase purchase, Double cost, String created, String updated) {
        super(id, created, updated);
        this.product = product;
        this.amount = amount;
        this.purchase =  purchase;
        this.cost = cost;
    }
    
    
    public PurchaseDet() {
        super(-1, "", "");
        this.product = null;
        this.amount = -1;
        this.purchase =  null;
        this.cost = 0.0;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "PurchaseDet{" + "product=" + product + ", amount=" + amount + ", purchase=" + purchase + ", cost=" + cost + '}';
    }
    
    
    
    
}
