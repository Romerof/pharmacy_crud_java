/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models.foundation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Lenovo ideapad 330S
 * @param <T>
 */
public abstract class AbstractAcces<T extends DataObject>  
        implements Queryable<T>
{
    protected final SQLConnectionBuilder sqlcb;
    protected Connection link;
    
    public AbstractAcces(SQLConnectionBuilder _sqlcb){
        this.sqlcb = _sqlcb;     
    }
    
    
    protected abstract String insertQuery();
    protected abstract void setInsertStatementParams(PreparedStatement stm, T dto)throws SQLException;
    @Override
    public boolean insert(T dto){
        PreparedStatement stm;        
   
        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement(insertQuery());
            setInsertStatementParams(stm, dto);
            
            stm.execute();
            stm.close();

        }catch (SQLException e){
            System.out.println("error sql"+e.getSQLState()+" -- "+e.getErrorCode()+"  : " +e);
            if (1062 == e.getErrorCode()) 
                javax.swing.JOptionPane.showMessageDialog(null, "esta identificacion ya esta registrada");
            return false;
        }catch (Exception e){
            System.out.println("(AbstractAcces/Insert) error " +e);
            return false;
        }
        return true;
    }
    
    protected abstract String selectQuery();
    protected abstract T setResultData(ResultSet r)throws SQLException;
    @Override
    public ArrayList<T> selectAll(){
        ArrayList<T> all =new ArrayList();
        ResultSet r;
        PreparedStatement stm;
   
        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement(selectQuery());
            r = stm.executeQuery();
            
            while(r.next()){
                all.add( setResultData(r));
            }
            stm.close();
            
        }catch (SQLException e){
            System.out.println("(AbstractAcces/selectAll) error: " +e);
        }
        return all;
    }
    
    
    protected abstract String UpdateQuery();
    protected abstract void setUpdateStatementParams(PreparedStatement stm, T dto, int id)throws SQLException;
    @Override
    public boolean update(int id, T dto){
        PreparedStatement stm;

        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement(UpdateQuery());
            setUpdateStatementParams(stm, dto, id);
            stm.execute();
            stm.close();

        }catch (SQLException e){
            System.out.println("error sql"+e.getSQLState()+" -- "+e.getErrorCode()+"  : " +e);
            if (1062 == e.getErrorCode()) 
                System.out.println("identificacion existente en la base de datos");
            return false;
        }catch (Exception e){
            System.out.println("(AbstractAcces/Update) error: " +e);
            return false;
        }
        return true;
    }
    
    protected abstract String deleteQuery();
    protected abstract void setDeleteStatementParams(PreparedStatement stm, T dto)throws SQLException;

    /**
     * this method returns true if the element is removed, else returns false. 
     * @param dto
     * @return true if element dto is removed, false if an exception occurse
     */
    @Override
    public boolean delete(T dto){
        PreparedStatement stm;

        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement(deleteQuery());
            setDeleteStatementParams(stm, dto); 
            stm.execute();
            stm.close();
            
        }catch (SQLException e){
            System.out.println("error sql: " +e);
            return false;
        }catch (Exception e){
            System.out.println("(AbstractAcces/delete) error " +e);
            return false; 
        }
        return true;
    }
    
    protected String table_name = "purchases";
    @Override
    public int getLastInsertedId(){
        ResultSet r;
        PreparedStatement stm;
        String query = "SELECT max(id) as max FROM pharmacy_database." + this.table_name;
        int id =-1;
        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement(query);
            r = stm.executeQuery();
            r.next();            
            id = r.getInt("max");
                    
            stm.close();
            
        }catch (SQLException e){
            System.out.println("(AbstractAcces/getLastInsertedId) error " +e);
        }
    
        return id;
    }

    @Override
    public T selectThis(T dto){
        T obj=null;
        ResultSet r;
        PreparedStatement stm;
   
        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement(selectThisQuery());
            stm.setInt(1, dto.getId());
            r = stm.executeQuery();
            r.next();
            
            obj = setResultData(r);
            
            stm.close();    
        }catch (SQLException e){
            System.out.println("AbstractAcces/selectThis) error: " +e);
        }
        return obj;
    }

    protected String selectThisQuery(){
        return selectQuery() + " WHERE id = ?";
    }

    @Override
    public ArrayList<T> selectWhere(String field, String param){
        ArrayList<T> allWhere =new ArrayList();
        ResultSet r;
        PreparedStatement stm;
   
        try{
            if (this.link == null || this.link.isClosed() ) this.link = this.sqlcb.getConnection();
            stm = this.link.prepareStatement(selectQuery()+ " WHERE "+field+" = ?");
            stm.setString(1, param);
            r = stm.executeQuery();
            
            while(r.next()){
                allWhere.add( setResultData(r));
            }
            stm.close();
            
        }catch (SQLException e){
            System.out.println("(AbstractAcces/selectAll) error: " +e);
        }
        return allWhere;
    }
    
    
}
