
package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Employee;
import farmaciaapp.models.dataobject.Purchase;
import farmaciaapp.models.dataobject.PurchaseDet;
import farmaciaapp.models.dataobject.Supplier;
import farmaciaapp.models.foundation.AbstractAcces;
import farmaciaapp.models.foundation.Queryable;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Lenovo ideapad 330S
 */

public class PurchaseAcces extends AbstractAcces<Purchase>{
    
    Queryable<PurchaseDet> products;
    Queryable<Employee> employees;
    Queryable<Supplier> suppliers;

    
    public PurchaseAcces(SQLConnectionBuilder _sqlcb) {
        super(_sqlcb);
        this.products = new ProductPurchaseAcces(_sqlcb);
        this.suppliers = new SupplierAcces(_sqlcb);
        this.employees = new EmployeeAcces(_sqlcb);
        this.table_name = "`purchases`";

    }

    @Override
    protected String insertQuery() {
        return "INSERT INTO `pharmacy_database`.`purchases` (`total`, `created`, `supplier_id`, `employee_id`, `num_document`)"
                + " VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, Purchase dto) throws SQLException {
        stm.setDouble(1,dto.getTotal());
        stm.setTimestamp(2, Timestamp.from(Instant.now()));
        stm.setInt(3, dto.getSupplier().getId());
        stm.setInt(4, dto.getEmployee().getId());
        stm.setString(5, dto.getDocument());
    }

    @Override
    protected String selectQuery() {
        return "SELECT * FROM pharmacy_database.purchases";
    }

    @Override
    protected Purchase setResultData(ResultSet r) throws SQLException {
        Employee employee = new Employee();
        employee.setId(r.getInt("employee_id"));
        Supplier supplier = new Supplier();
        supplier.setId(r.getInt("supplier_id"));
        Purchase tempPurchase = new Purchase(
                r.getInt("id"),
                r.getDouble("total"),
                suppliers.selectThis(supplier),
                employees.selectThis(employee),
                r.getString("num_document"),
                r.getString("created"),
                "",
                products.selectWhere("purchase_id", r.getString("id"))
        );
        for(PurchaseDet p : tempPurchase.getProducts()) p.setPurchase(tempPurchase);
        
        return tempPurchase;
    }

    @Override
    protected String UpdateQuery() { 
        return "UPDATE `pharmacy_database`.`purchases` SET "
                + "`total` = ?, "
                + "`supplier_id` = ?, "
                + "`employee_id` = ?, "
                + "`num_document` = ? "
                + "WHERE (`id` = ?)"; 
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, Purchase dto, int id) throws SQLException {
        stm.setDouble(1,dto.getTotal());
        stm.setTimestamp(2, Timestamp.from(Instant.now()));
        stm.setInt(3, dto.getSupplier().getId());
        stm.setInt(4, dto.getEmployee().getId());
        stm.setString(5, dto.getDocument());
        stm.setInt(5, id);
    }

    @Override
    protected String deleteQuery() { return "DELETE FROM `pharmacy_database`.`purchases` WHERE (`id` = ?);"; }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, Purchase dto) throws SQLException {
        stm.setInt(1, dto.getId());
    }
    
    
}
