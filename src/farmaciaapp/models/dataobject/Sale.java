/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Sale extends AbstractDataObject{
    private String document;
    private double total;
    private Employee employee;
    private Customer customer;
    private List<ProductSale> productSale;
    
    public Sale() {
        super(-1,"", "");
        this.document = "";
        this.total = 0.0;
        this.employee = null;
        this.customer = null;
        this.productSale = new ArrayList();
    }

    public Sale(int id) {
        super(id,"", "");
        this.document = "";
        this.total = 0.0;
        this.employee = null;
        this.customer = null;
        this.productSale = new ArrayList();
    }
    
    public Sale(int id, String document, double total, Employee emploeey, Customer customer, String created, String updated) {
        super(id, created, updated);
        this.document = document;
        this.total = total;
        this.employee = emploeey;
        this.customer = customer;
        this.productSale = new ArrayList();
    }
    
    public Sale(int id, String document, double total, Employee emploeey, Customer customer, String created, String updated, ArrayList productSale) {
        super(id, created, updated);
        this.document = document;
        this.total = total;
        this.employee = emploeey;
        this.customer = customer;
        this.productSale = productSale;
    }


    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee emploeey) {
        this.employee = emploeey;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductSale> getProductSale() {
        return productSale;
    }

    public void setProductSale(List<ProductSale> productSale) {
        this.productSale = productSale;
    }

    @Override
    public String toString() {
        return "Sale{"+ super.toString() + ", document=" + document + ", total=" + total + ", emploeey=" + employee + ", customer=" + customer + ", productSale=" + productSale + '}';
    }
    
    
    
}
