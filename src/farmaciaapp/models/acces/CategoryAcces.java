/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Category;
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
public class CategoryAcces extends AbstractAcces<Category>{

    public CategoryAcces(SQLConnectionBuilder _sqlcb) {
        super(_sqlcb);
    }

    @Override
    protected String insertQuery() {
        return "INSERT INTO `pharmacy_database`.`categories` (`name`,`created` ) "
                + "VALUES (?, ?)";
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, Category dto) throws SQLException {
        stm.setString(1, dto.getName());
        stm.setTimestamp(2, Timestamp.from(Instant.now()));
    }

    @Override
    protected String selectQuery() {
        return "SELECT * FROM pharmacy_database.categories";
    }

    @Override
    protected Category setResultData(ResultSet r) throws SQLException {
        return new Category(
                r.getInt("id"),
                r.getString("name"),
                r.getString("created"),
                r.getString("updated")
        );
    }

    @Override
    protected String UpdateQuery() {
        return "UPDATE `pharmacy_database`.`categories` SET "
                + "`name` = ?, "
                + "`updated` = ? "
                + "WHERE (`id` = ?)";
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, Category dto, int id) throws SQLException {
        stm.setString(1, dto.getName());
        stm.setTimestamp(2, Timestamp.from(Instant.now()));
        stm.setInt(3, id);
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM `pharmacy_database`.`categories` WHERE (`id` = ?)";
    }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, Category dto) throws SQLException {
        stm.setInt(1, dto.getId());
    }
    
}
