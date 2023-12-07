


package farmaciaapp.models;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import farmaciaapp.views.DBConfigView;
import farmaciaapp.views.InstalationWindow;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class SystemConfig {
    public static SQLConnectionBuilder SQL_CONNECTION_PROVIDER;
    
    private CfgData data;
    
    private static SQLConnectionBuilder configConnection = new ConnectionDerby(
            "pharmacy_cfg",
            "root",
            "root",
            new File(System.getProperty("user.dir") +"\\src\\farmaciaapp\\resources\\cfg.sql")
    );
    
    public static boolean checkDatabase(){
        int dbSystem;
        String dbUser, dbName, dbPassword, dbHost;
        //localhost:3306
        
        // chequear si existe la base de datos de configuracion
        if (configConnection.checkDB()){
            System.out.println("Cargando configuración...");
        
            
        }else{ //si existe, costruir la base de datos de configuracion
            
            JOptionPane.showMessageDialog(null, "No se ha encontrado un archivo de configuracion previo, "
                    + "se creara uno nuevo", "Instalacion", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Creando cfg_pharmacy...");
            configConnection.Build();
            //registrar la informacion si no existe
            JDialog a = new DBConfigView(null, false);
            a.setVisible(true);
        }
        
        //chequear que la base de datos de configuracion contiene datos
        boolean containData = false;
        try{
            String query = "SELECT * FROM pharmacy_cfg.opt";
            ResultSet rs = configConnection.getConnection().prepareStatement(query).executeQuery();
            rs.next();
            dbSystem = rs.getInt("dbsystem");
            dbName = rs.getString("dbname");
            dbUser = rs.getString("dbuser");
            dbPassword = rs.getString("dbpassword");
            containData=true;
        }catch (SQLException ex) {
            if (ex.getErrorCode() == 20000) System.out.println("no hay info");
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problema sql SystemConfig.checkDataBase: " + ex.getErrorCode()+" -> " + ex.getMessage());
        }
        
        //sino registrar los datos
        if(!containData){
            JFrame install = new InstalationWindow();
            System.out.println("ahora debemos registrar los datos");
            DBConfigView a = new DBConfigView(install, true);
            CfgData d = new  CfgData();
            
            a.btnConfigSave.addActionListener( (ActionEvent e) -> {
                d.dbSystem = a.cbmConfigAdmin.getSelectedIndex();
                d.dbName = a.txtConfigName.getText();
                d.dbhost = a.txtConfigHost.getText();
                d.dbUser = a.txtConfigUser.getText();
                d.dbPassword = String.copyValueOf(a.txtConfigPass.getPassword());
            });
            
            try{
                String query = "INSERT INTO pharmacy_cfg.opt (dbsystem, dbhost, dbname, dbuser, dbpassword) VALUES (?,?,?,?,?) ";
                PreparedStatement pr = configConnection.getConnection().prepareStatement(query);
                pr.setInt(1, d.dbSystem);
                pr.setString(2, d.dbName);
                pr.setString(3, d.dbhost);
                pr.setString(4, d.dbUser);
                pr.setString(5, d.dbPassword);
                pr.execute();
            }catch (SQLException ex) {
                if (ex.getErrorCode() == 20000) System.out.println("no hay info");
                Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("problema sql SystemConfig.checkDataBase: " + ex.getErrorCode()+" -> " + ex.getMessage());
            }
        }
        
        
        //obtener los datos para conectar a la bd del sistema
        System.out.println("obteniendo datos");
        try{
            String query = "SELECT * FROM pharmacy_cfg.opt";
            ResultSet rs = configConnection.getConnection().prepareStatement(query).executeQuery();
            rs.next();
            dbSystem = rs.getInt("dbsystem");
            dbHost = rs.getString("dbhost");
            dbName = rs.getString("dbname");
            dbUser = rs.getString("dbuser");
            dbPassword = rs.getString("dbpassword");
            System.out.println("datos obtenidos: " + dbSystem + ", " + dbHost + ", " +dbName+ ", " +dbUser+ ", " +dbPassword);
        }catch (SQLException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problema sql: " + ex.getErrorCode()+" -> " + ex.getMessage());
        }
        
        
        //chequear la bd del sistema si existe
        
            // si no existe costruir la bd del sistema si no existe
            
        //conectarse a la bd del sistema
        return false;
    }

    public static void installConfig(){
            
    } 
    
    private static class CfgData {
        
        int dbSystem;
        String dbUser, dbName, dbPassword, dbhost;
        
        public CfgData() {
        }
        public CfgData(int dbSystem, String dbUser, String dbName, String dbPassword, String dbhost) {
            this.dbSystem = dbSystem;
            this.dbUser = dbUser;
            this.dbName = dbName;
            this.dbPassword = dbPassword;
            this.dbhost = dbhost;
        }
    }
    
    
}


