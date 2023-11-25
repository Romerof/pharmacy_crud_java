

package farmaciaapp.models.acces;

import farmaciaapp.models.foundation.DataObject;
import farmaciaapp.models.foundation.AbstractAcces;
import farmaciaapp.models.dataobject.Supplier;
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
public class SupplierAcces extends AbstractAcces{

    public SupplierAcces(SQLConnectionBuilder _sqlcb) {
        super(_sqlcb);
    }

    @Override
    protected String insertQuery(){
        return "INSERT INTO `pharmacy_database`.`suppliers` "
                + "(`name`, `description`, `telephone`, `address`, `email`, `city`, `created`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);"; 
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, DataObject dt) throws SQLException {
        Supplier sup = (Supplier) dt;
        stm.setString(1,sup.getName());
        stm.setString(2,sup.getDescription());
        stm.setString(3,sup.getTelephone());
        stm.setString(4,sup.getAddress());
        stm.setString(5,sup.getEmail());
        stm.setString(6,sup.getCity());
        stm.setTimestamp(7, Timestamp.from(Instant.now()));
    }

    @Override
    protected String selectQuery() { return "SELECT * FROM pharmacy_database.suppliers"; }

    @Override
    protected DataObject setResultData(ResultSet r) throws SQLException{
        return new Supplier(
            r.getInt("id"),
            r.getString("name"),               
            r.getString("description"),
            r.getString("address"),
            r.getString("telephone"),
            r.getString("email"),
            r.getString("city"),
            r.getString("created"),
            r.getString("updated")
        );
    }

    @Override
    protected String UpdateQuery() {
        return "UPDATE `pharmacy_database`.`suppliers` "
                + "SET `name` = ?, "
                + "`description` = ?, "
                + "`telephone` = ?, "
                + "`address` = ?, "
                + "`email` = ?, "
                + "`city` = ?, "
                + "`updated` = ? "
                + "WHERE (`id` = ?)";
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, DataObject dt, int id)throws SQLException {
        Supplier sup = (Supplier) dt;
        stm.setString(1,sup.getName());
        stm.setString(2,sup.getDescription());
        stm.setString(3,sup.getTelephone());
        stm.setString(4,sup.getAddress());
        stm.setString(5,sup.getEmail());
        stm.setString(6,sup.getCity());
        stm.setTimestamp(7, Timestamp.from(Instant.now()));
        stm.setInt(8, id);
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM `pharmacy_database`.`suppliers` WHERE (`id` = ?);";
    }
    
    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, DataObject dto)throws SQLException {
        Supplier sup = (Supplier) dto;
        stm.setInt(1, sup.getId());
    }
}
