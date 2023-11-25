
package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;

/**
 *
 * @author Lenovo ideapad 330S
 * dto object of employees table
 */
public class Employee extends AbstractDataObject{
    private String 
        fullName, //full_name
        username, //username
        address, //address
        telephone, //telephone
        email, //email
        password, //password
        rol = "" //rol
    ;       

    public Employee() {
        super(-1, "", "");
        this.fullName = "";
        this.username = "";
        this.address = "";
        this.telephone = "";
        this.email = "";
        this.password = "";
    }

    public Employee(int id, String fullName, String username, String address, 
            String telephone, String email, String password, String rol, String created, String updated) {
        super(id, created, updated);
        this.fullName = fullName;
        this.username = username;
        this.address = address;
        this.telephone = telephone;
        this.rol = rol;
        this.email = email;
        this.password = password;
    }

    public Employee(int id) {
        super(id, "", "");
        this.fullName = "";
        this.username = "";
        this.address = "";
        this.telephone = "";
        this.email = "";
        this.password = "";    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", fullName=" + fullName + ", username=" + username + ", address=" + address + ", telephone=" + telephone + ", email=" + email + ", password=" + password + ", rol=" + rol + ", created=" + created + ", updated=" + updated + '}';
    }
    
    
}
