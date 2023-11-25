/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models;

import farmaciaapp.models.foundation.SQLConnectionBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class ConnectionDerby implements SQLConnectionBuilder {
    private static final String driver ="org.apache.derby.jdbc.EmbeddedDriver";
    private static final String protocol = "jdbc:derby:/";
    private final String database;
    private final String user;
    private final String password;
    private static Connection conn;
    private static File source;
    
    public ConnectionDerby( String database, String user, String password, File source){
        this.database = database;
        this.user = user;
        this.password = password;
        ConnectionDerby.source =  source;
    }
    
    @Override
    public Connection getConnection() {
        if(this.conn == null)
            try{
                Class.forName(driver); //org.apache.derby.jdbc.EmbeddedDriver
                this.conn = DriverManager.getConnection(protocol + database + "; create = true;" , user, password);

                System.out.println("derby ok");
            }catch(SQLException e){
                System.out.println("problema sql: " + e.getErrorCode()+" -> " + e.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionDerby.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return this.conn;
    }

    @Override
    /**
     * This method take file configured in constructor method and make a 
     * database connection and build database using that file. 
     */
    public void Build() {
        try{
            BufferedReader br = new BufferedReader(new FileReader(source));
            String line = br.readLine();
            StringBuffer query= new StringBuffer();
              
            while(line !=null){
                if(line.length()>0){
                    char firstChar = line.charAt(0);
                    if(firstChar != '/' && firstChar!='-'){
                        query.append(line);
                        query.append(" ");
                        if(line.charAt(line.length() - 1) == ';'){ //ejecurar sentencia
                            System.out.println(query);
                            query.charAt(query.length() - 1);
                            System.out.println("    ->" + query.charAt(query.indexOf(";"))+"<---");
                            conn.prepareStatement(query.deleteCharAt(query.indexOf(";")).toString()).executeUpdate();
                            query= new StringBuffer();
                        }
                    }
                }
                
                line = br.readLine();
            }
            
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problema sql: " + ex.getErrorCode()+" -> " + ex.getMessage());
            switch (ex.getErrorCode()){
                case 1049: //base de datos no encontrada
                    System.out.println("Error de tipo SQLException: base de datos no encontrada ");
                    break;
                case 1045: //base de datos no encontrada
                    System.out.println("Error de tipo SQLException: el usuario o contraseña de la base de datos es incorrecto ");
                    break;
                default:

            }

        }
    }

    @Override
    public boolean checkDB() {
        try{
            String query = "Select * from " + database + ".opt";
            ResultSet rs = getConnection().prepareStatement(query).executeQuery();
            if(rs.next()) return true;
        }catch(SQLException e){
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("problema sql: " + e.getErrorCode()+" -> " + e.getMessage());
            switch (e.getErrorCode()){
                case 1049: //base de datos no encontrada
                    System.out.println("Error de tipo SQLException: base de datos no encontrada ");
                    break;
                case 1045: //base de datos no encontrada
                    System.out.println("Error de tipo SQLException: el usuario o contraseña de la base de datos es incorrecto ");
                    break;
                case 30000: //tabla/vista no existe
                    String msg= "tabla opt no enconstrada, la base de datos cfg no esta construida";
                    Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, msg, e);
                    return false;
                default:

            }
        }
        return true;
    }
    
}
