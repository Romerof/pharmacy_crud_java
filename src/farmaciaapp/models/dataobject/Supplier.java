/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.DataObject;
import farmaciaapp.models.foundation.DataObject;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Supplier implements DataObject {
    private int id; //id
    private String 
            name, //full_name
            description, //description
            address, //address
            telephone, //telephone
            email, //email
            city, //city
            created, //created
            updated  //updated
    ;

    public Supplier() {
        this.id = -1;
        this.name = "";
        this.description = "";
        this.address = "";
        this.telephone = "";
        this.email = "";
        this.city = "";
        this.created = "";
        this.updated = "";
    }

    public Supplier(int id, String name, String description, String address, String telephone, String email, String city, String created, String updated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.city = city;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Supplier{" + "id=" + id + ", name=" + name + ", description=" + description + ", address=" + address + ", telephone=" + telephone + ", email=" + email + ", city=" + city + ", created=" + created + ", updated=" + updated + '}';
    }
    
    
    
    
}
