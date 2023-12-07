
package farmaciaapp.controllers;

import farmaciaapp.FarmaciaApp;
import farmaciaapp.models.dataobject.Employee;
import farmaciaapp.models.Employees;
import farmaciaapp.views.Login;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginController {
    private final Login view;
    private final Employees employees;
    public static Employee user;
    
 
    public LoginController(Consumer<Integer> changeState, Login view){
        this.view = view;
        this.user = new Employee();
        this.employees = new Employees();
        
        
        //podria reducir esta instruccion devolviendo un simple resultado buleano
        //si embargo tendria que cambiar la estructura del consumer y sus afectados
        this.view.btnEnter.addActionListener((ActionEvent e) -> {
            
            if (checkUser()) {
                changeState.accept(FarmaciaApp.STATE_LOGGED);
            }else{
                JOptionPane.showMessageDialog(view, "Datos del usuario incorrectos", "Inicio de sesion fallido", 0);
            }
// si el usuario es incorrecto el mensaje se envia en el observador
        });           
    }  
    
    public void showView(boolean flag){
        this.view.setVisible(flag);
        this.employees.pull();
    }
    
    public boolean checkUser(){
        Employee temUser = new Employee();
        temUser.setUsername(view.txtUser.getText());
        temUser.setPassword(String.valueOf(view.txtPassword.getPassword()));
        
        for (Employee e :this.employees.all()) {
            if(e.getUsername().equals(temUser.getUsername()))
                if(e.getPassword().equals(temUser.getPassword())){
                    SetActualUser(e);
                    return true;
                }
        }
        return false;
    }
    
    void SetActualUser(Employee e){
        LoginController.user = e;
    }
    
    
    
    
}
