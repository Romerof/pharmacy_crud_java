
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Category extends AbstractDataObject{
    
    private String name;
    
    public Category(int id, String name, String created, String updated){
        super(id, created, updated);
        this.name = name;
    }
    
    public Category(){
        super(-1, "", "");
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    
}
