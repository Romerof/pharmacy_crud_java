package farmaciaapp.controllers;

import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.models.Suppliers;
import farmaciaapp.models.dataobject.Supplier;
import farmaciaapp.views.SystemView;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;


public class SupplierController extends AbstractController<Supplier>{
    
    private final JTextComponent [] fields = {
        view.txtSupplierId,
        view.txtSupplierName,
        view.txtSupplierDesc,
        view.txtSupplierAddress,
        view.txtSupplierMail,
        view.txtSupplierPhone,
        view.txtSupplierSearch
    };

    public SupplierController(SystemView view) {
        super(view, new Suppliers());
        
        view.btnSupplierCancel.addActionListener( e -> super.clear() );
        view.btnSupplierDelete0.addActionListener( e -> super.delete() );
        view.btnSupplierSave.addActionListener( e -> super.save());
        view.btnSupplierUpdate.addActionListener( e -> super.update());
        view.txtSupplierSearch.addActionListener( e -> super.search(view.txtSupplierSearch.getText()));
    }

    @Override
    public boolean check() {
        super.errorMsg="Algunos datos no son correctos:\n";
        
        //validacion de telefono
        if(!view.txtSupplierPhone.getText().matches("^\\d{10,}$")){
            super.errorMsg += "  Telefono: (Ejem) 04249578658\n";
            return false;
        }
        
        //validacion de correo
        if(!view.txtSupplierMail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            super.errorMsg += "  Correo: (ejem) mifarma@correo.dom";
            return false;
        }      
        super.errorMsg = "";
        return true;
    }

    @Override
    public Supplier loadDataObject(){
        Supplier so = new Supplier();
        so.setName(errorMsg);
        so.setName(view.txtSupplierName.getText());
        so.setDescription(view.txtSupplierDesc.getText());
        so.setAddress(view.txtSupplierAddress.getText());
        so.setEmail(view.txtSupplierMail.getText());
        so.setTelephone(view.txtSupplierPhone.getText());
        so.setCity((String) view.cmbSupplierCity.getSelectedItem());
        if (!view.txtSupplierId.getText().trim().isEmpty()) 
            so.setId(Integer.parseInt(view.txtSupplierId.getText()));
        return so;
    }

    @Override
    protected JTextComponent[] getTextFields() { return this.fields; }

    @Override
    public JTable getViewTable() { return this.view.tblSuppliers; }
 
    @Override
    protected void loadDataToView(Supplier supplier) {
        view.txtSupplierId.setText(String.valueOf(supplier.getId()));
        view.txtSupplierName.setText(supplier.getName());
        view.txtSupplierDesc.setText(supplier.getDescription());
        view.txtSupplierAddress.setText(supplier.getAddress());
        view.txtSupplierMail.setText(supplier.getEmail());
        view.txtSupplierPhone.setText(supplier.getTelephone());
        view.cmbSupplierCity.setSelectedItem(supplier.getCity());
    }


    
}
