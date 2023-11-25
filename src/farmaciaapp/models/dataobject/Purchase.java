package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Purchase extends AbstractDataObject {
    
    private double total;
    private Supplier supplier; //supplier_id
    private Employee employee; // empliye_id
    private List<PurchaseDet> details;
    private String document; //num_document

    public List<PurchaseDet> getProducts() {
        return details;
    }

    public void setProducts(List<PurchaseDet> products) {
        this.details = products;
    }
    
    public Purchase(int id, double total, Supplier supplier, Employee employee, String document, String created, String updated, List<PurchaseDet> products) {
        super(id, created, updated);
        
        this.total = total;
        this.supplier =  supplier;
        this.employee = employee;
        this.details = products;
        this.document = document;
    }

    public Purchase(){
        super(-1, "", "");
        this.total = 0.0;
        this.supplier =  null;
        this.employee = null;
        this.details = new ArrayList();
        this.document = "";

    }
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
        
        return "Purchase{" + "id" + super.id + ", total=" + total + ", supplier=" + supplier + ", employee=" + employee + ", details=" + details + ", document=" + document + '}';
    }



    
    
    
}
