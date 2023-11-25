package farmaciaapp.models.dataobject;

import farmaciaapp.models.foundation.AbstractDataObject;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Customer extends AbstractDataObject{
    private String 
                fullName, //full_name
                address, //address
                telephone, //telephone
                email, //email
                created, //created
                updated,    //updated
                docId
    ;       

    public Customer(int id, String fullName, String address, String telephone, String email, String created, String updated, String docId) {
        super(id, created, updated);
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.docId = docId;
    }

    public Customer() {
        super(-1, "", "");
        this.fullName = "";
        this.address = "";
        this.telephone = "";
        this.email = "";
        this.docId = "";
    }

    public Customer(int id) {
        super(id, "", "");
        this.fullName = "";
        this.address = "";
        this.telephone = "";
        this.email = "";
        this.docId = "";    
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", fullName=" + fullName + ", address=" + address + ", telephone=" + telephone + ", email=" + email + ", created=" + created + ", updated=" + updated + ", docId=" + docId + '}';
    }
    
}
