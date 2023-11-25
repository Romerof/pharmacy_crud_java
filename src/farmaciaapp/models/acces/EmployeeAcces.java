package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Employee;
import farmaciaapp.models.foundation.AbstractAcces;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import farmaciaapp.models.foundation.SQLConnectionBuilder;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class EmployeeAcces extends AbstractAcces<Employee>{
    
    public EmployeeAcces(SQLConnectionBuilder _sqlcb){
        super(_sqlcb); 
        this.table_name = "`employees`";
    }
    
    @Override
    protected String insertQuery() {
        return "INSERT INTO pharmacy_database.employees \n" +
                "(id, full_name, username, address, telephone, email, password, rol, created) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        ;
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, Employee dto) throws SQLException {
        stm.setInt(1, dto.getId());
        stm.setString(2, dto.getFullName());
        stm.setString(3, dto.getUsername());
        stm.setString(4, dto.getAddress());
        stm.setString(5, dto.getTelephone());
        stm.setString(6, dto.getEmail());
        stm.setString(7, dto.getPassword());
        stm.setString(8, dto.getRol());
        stm.setTimestamp(9, Timestamp.from(Instant.now()));
    }

    @Override
    protected String selectQuery() { return "SELECT * FROM employees"; }

    @Override
    protected Employee setResultData(ResultSet r) throws SQLException {
        return new Employee(
            r.getInt("id"),
            r.getString("full_name"),
            r.getString("username"),
            r.getString("address"),
            r.getString("telephone"),
            r.getString("email"),
            r.getString("password"),
            r.getString("rol"),
            r.getString("created"),
            r.getString("updated")
        );
    }

    @Override
    protected String UpdateQuery() {
        return "UPDATE pharmacy_database.employees SET "
                + "id = ?, "
                + "full_name = ?, "
                + "username = ?, "
                + "address = ?, "
                + "telephone = ?, "
                + "email = ?, "
                + "password = ?, "
                + "rol = ?, "
                + "updated = ? "
                + "WHERE id = ?"
        ;
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, Employee dto, int id) throws SQLException {
        stm.setInt(1,    dto.getId());
        stm.setString(2, dto.getFullName());
        stm.setString(3, dto.getUsername());
        stm.setString(4, dto.getAddress());
        stm.setString(5, dto.getTelephone());
        stm.setString(6, dto.getEmail());
        stm.setString(7, dto.getPassword());
        stm.setString(8, dto.getRol());
        stm.setTimestamp(9, Timestamp.from(Instant.now()));
        stm.setString(10, String.valueOf(id));
    }

    @Override
    protected String deleteQuery() { return "DELETE FROM pharmacy_database.employees WHERE id = ?;"; }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, Employee dto) throws SQLException {
        stm.setInt(1, dto.getId());
    }
}
