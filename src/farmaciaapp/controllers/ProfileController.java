
package farmaciaapp.controllers;

import farmaciaapp.models.dataobject.Employee;
import farmaciaapp.models.Employees;
import farmaciaapp.views.SystemView;
import javax.swing.JOptionPane;

public class ProfileController {
    private Employees employees;
    private final SystemView view;
    private Employee user, tempUser;
    
    private String errorMsg="";
    
    public ProfileController(SystemView _view){
        this.employees = new Employees();
        this.view = _view;
        this.user = LoginController.user;
        
        this.view.btnProfileUpdate.addActionListener(e -> updateUser());
        
    }

    public void start() {
        this.view.txtProfileId.setText(Integer.toString(user.getId()));
        this.view.txtProfileName.setText(user.getFullName());
        this.view.txtProfileAddress.setText(user.getAddress());
        this.view.txtProfilePhone.setText(user.getTelephone());
        this.view.txtProfileMail.setText(user.getEmail());
        
    }
    
    public void updateUser(){
        if(this.checkEmployee()){
            this.employees.put(this.employees.indexAt(user), tempUser);
            JOptionPane.showMessageDialog(view, "Usuario Actualizado", "Actualizacion exitosa", JOptionPane.INFORMATION_MESSAGE);
            this.employees.pull();
            view.txtProfileNewPass.setText("");
            view.txtProfileConfirmPass.setText("");
        }else{
            JOptionPane.showMessageDialog(view, errorMsg, "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
            this.errorMsg="";
        }
    }
    
    public boolean checkEmployee(){
        errorMsg="Algunos datos no son validos:\n";
        
        this.tempUser = new Employee();
        //validacion de id
        if(view.txtProfileId.getText().matches("\\d{4,9}")){
            this.tempUser.setId(Integer.parseInt(view.txtProfileId.getText()));
        }else{
            this.errorMsg += "  Identificacion: numeros 1000 y 999999999\n";
            return false;
        }
        
        //validacion de telefono
        if(view.txtProfilePhone.getText().matches("\\d{10,}")){
            this.tempUser.setTelephone(view.txtProfilePhone.getText());
        }else{
            this.errorMsg += "  Telefono: numeros 10 digitos o mas\n";
            return false;
        }
        
        //validacion de telefono
        if(view.txtProfileMail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            this.tempUser.setEmail(view.txtProfileMail.getText());
        }else{
            this.errorMsg += "  Correo: (ejem) mifarma@correo.dom";
            return false;
        }
        
        String pass = String.copyValueOf(view.txtProfileNewPass.getPassword());
        String confirmPass = String.copyValueOf(view.txtProfileConfirmPass.getPassword());
        if(pass.equals(confirmPass))
            this.tempUser.setPassword(pass);   
        else {
            this.errorMsg += "  Las contraseñas no coindiden";
            return false;
        }
        
        this.tempUser.setUsername(this.user.getUsername());
        this.tempUser.setFullName(view.txtProfileName.getText());
        this.tempUser.setAddress(view.txtProfileAddress.getText());
        this.tempUser.setRol(this.user.getRol());
                
        return true;
    }
}
