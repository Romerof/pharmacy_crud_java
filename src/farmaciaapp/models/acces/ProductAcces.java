 package farmaciaapp.models.acces;

import farmaciaapp.models.dataobject.Category;
import farmaciaapp.models.dataobject.Product;
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
public class ProductAcces extends AbstractAcces<Product>{

    public ProductAcces(SQLConnectionBuilder _sqlcb) {
        super(_sqlcb);
    }

    @Override
    protected String insertQuery() {
        return "INSERT INTO `pharmacy_database`.`products` "
                + "(`code`, `name`, `description`, `price`, `quantity`, `created`, `id_category`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setInsertStatementParams(PreparedStatement stm, Product dto) throws SQLException {
        stm.setString(1, dto.getCode());
        stm.setString(2, dto.getName());
        stm.setString(3, dto.getDescription());
        stm.setDouble(4, dto.getPrice());
        stm.setInt(5, dto.getQuantity());
        stm.setTimestamp(6, Timestamp.from(Instant.now()));
        stm.setInt(7, dto.getCategory().getId());
    }

    @Override
    protected String selectQuery() {
        return "SELECT \n" +
            "`pharmacy_database`.`products`.`id`, \n" +
            "`pharmacy_database`.`products`.`code`, \n" +
            "`pharmacy_database`.`products`.`name`, \n" +
            "`pharmacy_database`.`products`.`description`,  \n" +
            "`pharmacy_database`.`products`.`price`,  \n" +
            "`pharmacy_database`.`products`.`quantity`,  \n" +
            "`pharmacy_database`.`products`.`created`,  \n" +
            "`pharmacy_database`.`products`.`updated`,  \n" +
            "`pharmacy_database`.`products`.`id_category`,  \n" +
            "`pharmacy_database`.`categories`.`name` as `name_category` " + 
            "FROM `pharmacy_database`.`products` \n" +
            "inner join `pharmacy_database`.`categories`\n" +
            "on `id_category` = `pharmacy_database`.`categories`.`id`";
    }

    @Override
    protected Product setResultData(ResultSet r) throws SQLException {
        Product product =  new Product();
        Category category= new Category();
        
        product.setId(r.getInt("id"));//id
        product.setCode(r.getString("code"));//code
        product.setName(r.getString("name"));//name
        product.setDescription(r.getString("description"));//description
        product.setPrice(r.getDouble("price"));//price
        product.setQuantity(r.getInt("quantity"));//quantity
        product.setCreated(r.getString("created"));//created
        product.setUpdated(r.getString("updated"));//updated
        
        category.setId(r.getInt("id_category"));
        category.setName(r.getString("name_category"));
        product.setCategory(category); //id_category
                
        return product;
    }

    @Override
    protected String UpdateQuery() {
        return "UPDATE `pharmacy_database`.`products` SET "
                + "`code` = ?, "
                + "`name` = ?, "
                + "`description` = ?, "
                + "`price` = ?, "
                + "`quantity` = ?, "
                + "`updated` = ?, "
                + "`id_category` = ? "
                + "WHERE ( `id` = ? )";
    }

    @Override
    protected void setUpdateStatementParams(PreparedStatement stm, Product dto, int id) throws SQLException {
        stm.setString(1, dto.getCode());
        stm.setString(2, dto.getName());
        stm.setString(3, dto.getDescription());
        stm.setDouble(4, dto.getPrice());
        stm.setInt(5, dto.getQuantity());
        stm.setTimestamp(6, Timestamp.from(Instant.now()));
        stm.setInt(7, dto.getCategory().getId());
        stm.setInt(8, id);
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM `pharmacy_database`.`products` WHERE (`id` = ?);";
    }

    @Override
    protected void setDeleteStatementParams(PreparedStatement stm, Product dto) throws SQLException {
        stm.setInt(1, dto.getId());
    }

    @Override
    protected String selectThisQuery() {
        return "SELECT \n" +
            "`pharmacy_database`.`products`.`id`, \n" +
            "`pharmacy_database`.`products`.`code`, \n" +
            "`pharmacy_database`.`products`.`name`, \n" +
            "`pharmacy_database`.`products`.`description`,  \n" +
            "`pharmacy_database`.`products`.`price`,  \n" +
            "`pharmacy_database`.`products`.`quantity`,  \n" +
            "`pharmacy_database`.`products`.`created`,  \n" +
            "`pharmacy_database`.`products`.`updated`,  \n" +
            "`pharmacy_database`.`products`.`id_category`,  \n" +
            "`pharmacy_database`.`categories`.`name` as `name_category` " + 
            "FROM `pharmacy_database`.`products` \n" +
            "inner join `pharmacy_database`.`categories`\n" +
            "on `id_category` = `pharmacy_database`.`categories`.`id` \n" +
            "WHERE `pharmacy_database`.`products`.`id` = ? ;";
    }
    
    
    
}
