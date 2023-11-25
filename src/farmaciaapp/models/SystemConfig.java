
package farmaciaapp.models;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import java.io.File;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class SystemConfig {
    public static SQLConnectionBuilder SQL_CONNECTION_PROVIDER = new ConnectionMYSQL();
    
    public static SQLConnectionBuilder ConfigConnection = new ConnectionDerby(
            "pharmacy_cfg",
            "root",
            "root",
            new File(System.getProperty("user.dir") +"\\src\\farmaciaapp\\resources\\cfg.sql")
    );
    
    public static boolean checkDatabase(){
        if (ConfigConnection.checkDB()){
            System.out.println("todo fine");
        }else{
            System.out.println("ahora construir la bd de configuracion");
            ConfigConnection.Build();
        }
        return false;
    }
    public static void BuildDatabase(){
    }
}

