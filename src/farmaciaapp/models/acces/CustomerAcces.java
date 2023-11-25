
package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Customer;
import farmaciaapp.models.foundation.AbstractAcces;
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
public class CustomerAcces extends AbstractAcces<Customer>{
    
    public CustomerAcces(SQLConnectionBuilder _sqlcb){
        super(_sqlcb);    
        this.table_name = "`customers`";
        
    }

    @Override
    protected String insertQuery() {
        return "INSERT INTO pharmacy_database.customers \n" +
                "(doc_id, full_name, address, telephone, email, created) \n" +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, Customer dto) throws SQLException {
        stm.setString(1, dto.getDocId());
        stm.setString(2, dto.getFullName());
        stm.setString(3, dto.getAddress());
        stm.setString(4, dto.getTelephone());
        stm.setString(5, dto.getEmail());
        stm.setTimestamp(6, Timestamp.from(Instant.now()));
    }

    @Override
    protected String selectQuery() { return "SELECT * FROM customers"; }

    @Override
    protected Customer setResultData(ResultSet r) throws SQLException {
        return new Customer(
            r.getInt("id"),
            r.getString("full_name"),
            r.getString("address"),
            r.getString("telephone"),
            r.getString("email"),
            r.getString("created"),
            r.getString("updated"),
            r.getString("doc_id")          
        );
    }

    @Override
    protected String UpdateQuery() {
        return "UPDATE pharmacy_database.customers SET "
                + "doc_id = ?, "
                + "full_name = ?, "
                + "address = ?, "
                + "telephone = ?, "
                + "email = ?, "
                + "updated = ? "
                + "WHERE id = ?"
        ;
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, Customer dto, int id) throws SQLException {
        stm.setString(1, dto.getDocId());
        stm.setString(2, dto.getFullName());
        stm.setString(3, dto.getAddress());
        stm.setString(4, dto.getTelephone());
        stm.setString(5, dto.getEmail());
        stm.setTimestamp(6, Timestamp.from(Instant.now()));
        stm.setString(7, String.valueOf(id));
    }

    @Override
    protected String deleteQuery() { return "DELETE FROM pharmacy_database.customers WHERE id = ?"; }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, Customer dto) throws SQLException {
        stm.setInt(1, dto.getId());
    }

}
