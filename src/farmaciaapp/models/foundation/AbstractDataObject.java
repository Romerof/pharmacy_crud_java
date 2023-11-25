/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package farmaciaapp.models.foundation;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class AbstractDataObject implements DataObject{
    protected int id;
    protected String created, updated;
    
    public AbstractDataObject(int id, String created, String updated){
        this.id = id;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public int getId() {return this.id;}

    @Override
    public void setId(int id) {this.id=id;}

    public String getCreated() {
        return created;
    }

    public void setCreated(String Created) {
        this.created = Created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String Updated) {
        this.updated = Updated;
    }
    
    
}
