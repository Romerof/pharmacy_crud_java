
package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Product;
import farmaciaapp.models.dataobject.Purchase;
import farmaciaapp.models.dataobject.PurchaseDet;
import farmaciaapp.models.foundation.AbstractAcces;
import farmaciaapp.models.foundation.Queryable;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class PurchaseDetAcces extends AbstractAcces<PurchaseDet>{
    
    Queryable<Product> products; 
    Queryable<Purchase> purchases; 
    

    public PurchaseDetAcces(SQLConnectionBuilder _sqlcb) {
        super(_sqlcb);
        
        products = new ProductAcces(_sqlcb);
        purchases = new PurchaseAcces(_sqlcb);
    }

    @Override
    protected String insertQuery() {
        return "INSERT INTO `pharmacy_database`.`purchase_details` "
                + "(`purchase_price`, `purchase_amount`, `purchase_sub`, `purchase_id`, `product_id`) "
                + "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, PurchaseDet dto) throws SQLException {
        
        stm.setDouble(1, dto.getCost());
        stm.setInt(2, dto.getAmount());
        stm.setDouble(3, dto.getCost()*dto.getAmount());
        stm.setInt(4,dto.getPurchase().getId());
        stm.setInt(5, dto.getProduct().getId());
    }

    @Override
    protected String selectQuery() {
        return "SELECT * FROM pharmacy_database.purchase_details";
    }

    @Override
    protected PurchaseDet setResultData(ResultSet r) throws SQLException {
        Product tempProd = new Product();
        tempProd.setId(r.getInt("product_id"));
        Purchase tempPur= new Purchase();
        tempPur.setId(r.getInt("purchase_id"));
        
        return new PurchaseDet(
                r.getInt("id"),
                products.selectThis(tempProd),
                r.getInt("purchase_amount"),
                purchases.selectThis(tempPur),
                r.getDouble("purchase_price"),
                "",
                ""
        );
    }

    @Override
    protected String UpdateQuery() {
        return "UPDATE `pharmacy_database`.`purchase_details` SET "
        + "`purchase_price` = ?, "
        + "`purchase_amount` = ?, "
        + "`purchase_sub` = ?, "
        + "`purchase_id` = ?, "
        + "`product_id` = ? "
        + "WHERE (`id` = ?)";
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, PurchaseDet dto, int id) throws SQLException {
        stm.setDouble(1, dto.getCost());
        stm.setInt(2, dto.getAmount());
        stm.setDouble(3, dto.getProduct().getPrice()*dto.getAmount());
        stm.setInt(4,dto.getPurchase().getId());
        stm.setInt(5, dto.getProduct().getId());
        stm.setInt(5, id);
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM `pharmacy_database`.`purchase_details` WHERE (`id` = ?)";
    }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, PurchaseDet dto) throws SQLException {
        stm.setInt(1, dto.getId());
    }
    
}
