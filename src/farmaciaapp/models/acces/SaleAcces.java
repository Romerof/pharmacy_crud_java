package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Customer;
import farmaciaapp.models.dataobject.Employee;
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
 * @author Lenovo ideapad 330S
 */
public class SaleAcces extends AbstractAcces <Sale>{
    
    Queryable <Employee> employees;
    Queryable <Customer> customers;
    Queryable <ProductSale> products;
    

    public SaleAcces(SQLConnectionBuilder _sqlcb) {
        super(_sqlcb);
        this.employees = new EmployeeAcces(_sqlcb);
        this.customers = new CustomerAcces(_sqlcb);
        this.products = new ProductSaleAcces(_sqlcb);
        this.table_name = "`Sales`";

    }

    @Override
    protected String insertQuery() {
        return "INSERT INTO `pharmacy_database`.`sales` (`document`, `id_employee`, `id_customer`, `created`, `total`) "
                + "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, Sale dto) throws SQLException {
        stm.setString(1, dto.getDocument());
        stm.setInt(2, dto.getEmployee().getId());
        stm.setInt(3, dto.getCustomer().getId());
        stm.setTimestamp(4, Timestamp.from(Instant.now()));
        stm.setDouble(5,dto.getTotal());
    }

    @Override
    protected String selectQuery() {
        return "SELECT * FROM pharmacy_database.sales";
    }

    @Override
    protected Sale setResultData(ResultSet r) throws SQLException {
        Employee tempEmp = new Employee(r.getInt("id_employee"));
        Customer tempCus = new Customer(r.getInt("id_customer"));
        
        
        Sale tempSale =  new Sale(
                r.getInt("id"),
                r.getString("document"),
                r.getDouble("total"),
                this.employees.selectThis(tempEmp),//tempEmp, //TODO: este metodo no es eficiente, se 
                this.customers.selectThis(tempCus), // ejecuta una consulta con ada registro de venta.
                r.getString("created"),
                r.getString("updated"),
                this.products.selectWhere("id_sale", r.getString("id"))
        );
        
        for(ProductSale p : tempSale.getProductSale()) p.setSale(tempSale);
        
        return tempSale;
    }

    @Override
    protected String UpdateQuery() {
        return "UPDATE `pharmacy_database`.`sales` SET "
                + "`updated` = ?, "
                + "`total` = ? "
                + "WHERE (`id` = ?);";
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, Sale dto, int id) throws SQLException {
        stm.setTimestamp(1, Timestamp.from(Instant.now()));
        stm.setDouble(2, dto.getTotal());
        stm.setInt(2, id);
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM `pharmacy_database`.`sales` WHERE (`id` = ?)";
    }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, Sale dto) throws SQLException {
        stm.setInt(2, dto.getId());
    }
    
    public String getDocumentCode(){ 
        ResultSet r;
        PreparedStatement stm;
        int code = -1;
        
        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement("SELECT max(document + 1) as document FROM pharmacy_database.sales");
            r = stm.executeQuery();
            
            r.next();
            code = r.getInt("document");
            
            stm.close();
            
        }catch (SQLException e){
            System.out.println("SaleAcces/getDocument) error: " +e);
        }
        String temp = "000000000" + code;
        return temp.substring(temp.length() - 9);
    } 
}
