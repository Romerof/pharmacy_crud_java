/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Product;
import farmaciaapp.models.dataobject.ProductSale;
import farmaciaapp.models.dataobject.Sale;
import farmaciaapp.models.foundation.AbstractAcces;
import farmaciaapp.models.foundation.Queryable;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class ProductSaleAcces extends AbstractAcces <ProductSale>{
    
    Queryable<Product> products;
    //Queryable<Sale> sales;

    public ProductSaleAcces(SQLConnectionBuilder _sqlcb) {
        super(_sqlcb);
        this.products =  new ProductAcces(_sqlcb);
        //this.sales =  new SaleAcces(_sqlcb);
    }

    @Override
    protected String insertQuery() {
        return "INSERT INTO `pharmacy_database`.`products_sales` (`id_product`, `amount`, `price`, `created`, `id_sale`) "
                + "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, ProductSale dto) throws SQLException {
        stm.setInt(1, dto.getProduct().getId());
        stm.setInt(2, dto.getAmount());
        stm.setDouble(3, dto.getPrice());
        stm.setTimestamp(4, Timestamp.from(Instant.now()));
        stm.setInt(5, dto.getSale().getId());
    }

    @Override
    protected String selectQuery() {
        return "SELECT * FROM pharmacy_database.products_sales";
    }

    @Override
    protected ProductSale setResultData(ResultSet r) throws SQLException {
        Product tempProduct = new Product(r.getInt("id_product"));
        Sale tempSale =  new Sale(r.getInt("id_sale"));
        return new ProductSale(
                r.getInt("id"),
                r.getInt("amount"),
                r.getDouble("price"),
                this.products.selectThis(tempProduct),
                tempSale,
                r.getString("created"),
                r.getString("updated")
        );
    }
    

    @Override
    protected String UpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, ProductSale dto, int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String deleteQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, ProductSale dto) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
