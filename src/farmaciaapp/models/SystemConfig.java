


package farmaciaapp.models;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import farmaciaapp.views.DBConfigView;
import farmaciaapp.views.InstalationWindow;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;
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
    
    private static CData data;

    public static void initConfiguration() {
        if (checkConfig() && checkDatabase()){
            onFinishedConfiguration.accept(data);
        }else{
            Build();
        }
    }

    //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    private static SQLConnectionBuilder configConnection = new ConnectionDerby(
            "pharmacy_cfg",
            "root",
            "root",
            new File(System.getProperty("user.dir") +"\\src\\farmaciaapp\\resources\\cfg.sql")
    );
    
    private static boolean checkConfig(){
         // chequear si existe la base de datos de configuracion
        if (configConnection.checkDB()){
            System.out.println("Cargando configuración...");
            CData tempData = selectData();
            //System.out.println(tempData);           
            if(tempData != null) data = tempData;
            else    return false;
        }else   return false;
            
        return true;
    }
    
            
    private static boolean checkDatabase(){
        if (SQL_CONNECTION_PROVIDER == null){
            if(data == null) throw new UnsupportedOperationException("datos de configuracion no cargados"); 
            SQL_CONNECTION_PROVIDER = new ConnectionMYSQL(
                    data.dbName, 
                    data.dbHost, 
                    data.dbUser, 
                    data.dbPassword
            );
        }
        return SQL_CONNECTION_PROVIDER.checkDB();
    }

    private static CData selectData(){
        CData d =  null;
        try{
            String query = "SELECT * FROM pharmacy_cfg.opt";
            ResultSet rs = configConnection.getConnection().prepareStatement(query).executeQuery();
            if(rs.next()){
                d = new CData();
                d.dbSystem = rs.getInt("dbsystem");
                d.dbHost = rs.getString("dbhost");
                d.dbName = rs.getString("dbname");
                d.dbUser = rs.getString("dbuser");
                d.dbPassword = rs.getString("dbpassword");
            }else System.out.println("no hay registros");
        }catch (SQLException ex) {
            if (ex.getErrorCode() == 20000) System.out.println("no hay info");
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problema sql SystemConfig.selectData: " + ex.getErrorCode()+" -> " + ex.getMessage());
        }
        return d;
    }
    
    //La vista y las ventanas de instalacion podrian estar mejor elaboradas, sin
    //embargo, por causas practicas se hizo la mas sencillo podible.
    
    
    private static void Build(){
        JOptionPane.showMessageDialog(
                null, 
                "No se ha encontrado un archivo de configuracion previo, o esta"
                + " datañado, crearemos uno nuevo",
                "Instalacion",
                JOptionPane.INFORMATION_MESSAGE
        );
        
        JFrame install = new InstalationWindow();   
        DBConfigView a = new DBConfigView(install, false);
        CData d = new  CData();

        configConnection.Build();
        a.btnConfigSave.addActionListener( (ActionEvent e) -> {
            d.dbSystem = a.cbmConfigAdmin.getSelectedIndex();
            d.dbName = a.txtConfigName.getText();
            d.dbHost = a.txtConfigHost.getText();
            d.dbUser = a.txtConfigUser.getText();
            d.dbPassword = String.copyValueOf(a.txtConfigPass.getPassword());
            
            SQL_CONNECTION_PROVIDER = new ConnectionMYSQL(
                    d.dbName, 
                    d.dbHost, 
                    d.dbUser, 
                    d.dbPassword
            );
                        
            if(SQL_CONNECTION_PROVIDER.checkDB()){
                insertData(d);
                a.lblFeedback.setForeground(Color.GREEN);
                a.lblFeedback.setText("Conexion correcta!");
                JOptionPane.showMessageDialog(a, "Configuracuion Establecida, se iniciara el sistema");
                a.dispose();
                install.dispose();
                if (onFinishedConfiguration != null) onFinishedConfiguration.accept(d);
                
            }else{
                a.lblFeedback.setForeground(Color.red);
                
                //TODO hay errores por filtrar; host link,port, manejador o servidor, driver etc..
                switch(SQL_CONNECTION_PROVIDER.getError()){
                    case SQLConnectionBuilder.ERROR_INCORRECT_USER:
                        a.lblFeedback.setText("Usuario o contraseña incorrecto.");
                        break;
                    case SQLConnectionBuilder.ERROR_BAD_DATABASE:
                        if(a.cbCreate.isSelected()){
                            a.lblFeedback.setText("Creando base de datos...");
                            SQL_CONNECTION_PROVIDER.Build();
                            JOptionPane.showMessageDialog(a, "Base de datos creada!");
                            insertData(d);
                            
                        }else{
                            a.lblFeedback.setText("Base de datos \""+d.dbName+"\" no encontrada");
                        }
                        break;
                    default:
                        a.lblFeedback.setText("Conexion fallida");
                }
            }
        });
    }
    
    private static void insertData(CData cd){
        try{
            String query = "INSERT INTO pharmacy_cfg.opt (dbsystem, dbhost, dbname, dbuser, dbpassword) VALUES (?,?,?,?,?) ";
            PreparedStatement pr = configConnection.getConnection().prepareStatement(query);
            pr.setInt(1, cd.dbSystem);
            pr.setString(2, cd.dbHost);
            pr.setString(3, cd.dbName);
            pr.setString(4, cd.dbUser);
            pr.setString(5, cd.dbPassword);
            pr.execute();
        }catch (SQLException ex) {
            Logger.getLogger(ConnectionMYSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("problema sql SystemConfig.inserData: " + ex.getErrorCode()+" -> " + ex.getMessage());
        }
    }
    
    
    private static void BuildDatabase(){
        SQL_CONNECTION_PROVIDER.Build();
    }
    
    private static class CData {
        
        int dbSystem;
        String dbUser, dbName, dbPassword, dbHost;
        
        //MySQL = localhost:3306
        
        public CData() {
        }
        public CData(int dbSystem, String dbUser, String dbName, String dbPassword, String dbhost) {
            this.dbSystem = dbSystem;
            this.dbUser = dbUser;
            this.dbName = dbName;
            this.dbPassword = dbPassword;
            this.dbHost = dbhost;
        }

        @Override
        public String toString() {
            return "CData{" + "dbSystem=" + dbSystem + ", dbUser=" + dbUser + ", dbName=" + dbName + ", dbPassword=" + dbPassword + ", dbHost=" + dbHost + '}';
        }
        
    }
    
    public static Consumer onFinishedConfiguration;
    //public static Function onFinishedConfiguration2;
    
}


