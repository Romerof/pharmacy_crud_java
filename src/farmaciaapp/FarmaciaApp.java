
package farmaciaapp;

import farmaciaapp.controllers.LoginController;
import farmaciaapp.controllers.SystemController;
import farmaciaapp.models.Categories;
import farmaciaapp.models.ConnectionDerby;
import farmaciaapp.models.ConnectionMYSQL;
import farmaciaapp.models.SystemConfig;
import farmaciaapp.models.foundation.SQLConnectionBuilder;
import farmaciaapp.views.Login;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class FarmaciaApp {
    
    public static final int  STATE_UNLOGGED = 0;
    public static final int  STATE_LOGGED = 1;
    public static int  state = 0;
    static Login loginView;
    static LoginController login;
    static SystemController system;
    
    
    
    static void AppStateChange(int newState){
            System.out.println("metodo AppStateChange  ejecutado: " + newState);
            switch(newState){
            case STATE_UNLOGGED:
                
                system.showView(false);
                login.showView(true);
                state = newState;
                System.out.println("deslogeado");
                break;
            case STATE_LOGGED: 
                
                state = newState;
                login.showView(false);
                System.out.println("logeado");
                system = new SystemController(i -> AppStateChange(i));
                break;
            }
           // return i;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FarmaciaApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FarmaciaApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FarmaciaApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FarmaciaApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        //Se supone que esto se ejecuta luego de comprobar las configuraciones de la app. 
        SystemConfig.onFinishedConfiguration = d -> {
            loginView = new Login();
            login = new LoginController( i -> AppStateChange(i), loginView); 
        };
        
        SystemConfig.initConfiguration();
   
    }
        
}
