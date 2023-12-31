package farmaciaapp.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo ideapad 330S
 */
public final class ConnectionMYSQL implements SQLConnectionBuilder{
    private static final String DRIVER = "jdbc:mysql://";
    private Connection conn;
    
    private int error=0;
    private final String dbName, dbHost, dbUser, dbPass;
    
    public ConnectionMYSQL(String dbName, String dbHost, String dbUser, String dbPass){
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }
    
    
    @Override
    public Connection getConnection() {
        
        if(conn == null){
        
            try{
                //obtener el valor del driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = DRIVER + dbHost + "/" + dbName;
                //obtener la conexion
                conn = DriverManager.getConnection(url, dbUser, dbPass);
                System.out.println("Connection ok");
                this.error = 0;
            }catch(ClassNotFoundException e){
                System.err.println("Error de tipo ClassNotFoundException: " +e);
            }catch(SQLException e){
                        System.out.println("(" +e.getErrorCode()+") "+e.getMessage());
                switch (e.getErrorCode()){
                    case 1049: //base de datos no encontrada
                        System.out.println("Error de tipo SQLException: base de datos no encontrada ");
                        this.error = SQLConnectionBuilder.ERROR_BAD_DATABASE;
                        break;
                    case 1045: //usurio o contraseña
                        System.out.println("Error de tipo SQLException: el usuario o contraseña de la base de datos es incorrecto ");
                        this.error = SQLConnectionBuilder.ERROR_INCORRECT_USER;
                        break;
                    default:
                        System.out.println("Error de tipo SQLException: (" +e.getErrorCode()+") "+e.getMessage());
                        
                }
            }
        }//else 
        
        return conn;
    }

    @Override
    public void Build() {
        
        BufferedReader br = null;
        try { 
            File file = new File(System.getProperty("user.dir") +"\\src\\farmaciaapp\\resources\\pharmacy_mysql.sql");
            br = new BufferedReader(new FileReader(file));
            String query = "", linea;
            linea = br.readLine();
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(""
                    + ""
                    + "jdbc:mysql://localhost:3306", dbUser, dbPass);
            
            while(linea!= null){
                //System.out.println(linea);
                if(linea.length()>0){
                    char firstChar = linea.charAt(0);
                    if(firstChar != '/' && firstChar!='-'){
                        query +=  linea + " ";
                        if(linea.charAt(linea.length() - 1) == ';'){ //ejecurar sentencia
                            System.out.println(query);
                            conn.prepareStatement(query).executeUpdate();
                            query="";
                        }
                    }
                }
                linea = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean checkDB() {
        return this.getConnection() != null;
    }

    @Override
    public int getError() {
        return this.error;
    }
    
    

}
