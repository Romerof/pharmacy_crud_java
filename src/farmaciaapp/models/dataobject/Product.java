package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Product extends AbstractDataObject {

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", name=" + name + ", description=" + description + ", price=" + price + ", quantity=" + quantity + ", category=" + category + '}';
    }
    
    private String 
            code,
            name,
            description        
    ;
    private double price;
    private int quantity;
    Category category;
    
    public Product(){
        super(-1,"","");
        this.code = "";
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.quantity = 0;
        this.category = null;
    }
    
    public Product(int id){
        super(id,"","");
        this.code = "";
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.quantity = 0;
        this.category = null;
    }

    public Product
        (
            int id,
            String code, 
            String name, 
            String description, 
            double price, 
            int quantity, 
            Category category, 
            String created, 
            String updated
        ) 
    {
        super(id, created, updated);
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
