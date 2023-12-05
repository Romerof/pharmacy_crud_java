


package farmaciaapp.models;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class SystemConfig {
    public static SQLConnectionBuilder SQL_CONNECTION_PROVIDER;
    
    private static SQLConnectionBuilder configConnection = new ConnectionDerby(
            "pharmacy_cfg",
            "root",
            "root",
            new File(System.getProperty("user.dir") +"\\src\\farmaciaapp\\resources\\cfg.sql")
    );
    
    public static boolean checkDatabase(){
        int dbSystem;
        String dbUser, dbName, dbPassword, dbhost;
        //localhost:3306
        
        // chequear la base de datos de configuracion
        if (configConnection.checkDB()){
            System.out.println("Cargando configuración...");
        
            //costruir la base de datos de configuracion en caso de no tenerla.
        }else{ 
            
            JOptionPane.showMessageDialog(null, "No se ha encontrado un archivo de configuracion previo, "
                    + "se creara uno nuevo", "Instalacion", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Creando cfg_pharmacy...");
            configConnection.Build();
            //registrar la informacion si no existe
            
        }
        //obtener la informacion para conectar a la bd del sistema
        try{
            String query = "SELECT * FROM pharmacy_cfg.opt";
            ResultSet rs = configConnection.getConnection().prepareStatement(query).executeQuery();
            rs.next();
            dbSystem = rs.getInt("dbsystem");
            dbName = rs.getString("dbname");
            dbUser = rs.getString("dbuser");
            dbPassword = rs.getString("dbpassword");
        }catch (SQLException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problema sql: " + ex.getErrorCode()+" -> " + ex.getMessage());
        }
        
        
        //chequear la bd del sistema
        
            // costruir la bd del sistema si no existe
        //conectarse a la bd del sistema
        return false;
    }
}

