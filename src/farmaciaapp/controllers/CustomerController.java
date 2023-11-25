

package farmaciaapp.controllers;

import farmaciaapp.models.Customers;
import farmaciaapp.models.dataobject.Customer;
import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.views.SystemView;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class CustomerController extends AbstractController<Customer>{
    
        private final JTextComponent [] fields = {
            view.txtClientId,
            view.txtClientPhone,
            view.txtClientMail,
            view.txtClientName,
            view.txtClientAddress,
            view.txtClientSearch
        };


    public CustomerController(SystemView view) {
        super(view, new Customers());
        
        view.btnClientCancel.addActionListener( e -> super.clear());
        view.btnClientSave.addActionListener( e -> super.save());
        view.btnClientDelete.addActionListener( e -> super.delete());
        view.btnClientEdit.addActionListener( e -> super.update());
        view.txtClientSearch.addActionListener( e -> super.search(view.txtClientSearch.getText()));
    }

    @Override
    protected boolean check() {
        super.errorMsg="Algunos datos no son correctos:\n";
        
        //validacion de id
        if(!view.txtClientId.getText().matches("^[A-Z]{1}\\d{7,}$")){
            super.errorMsg += "  Identificacion: (Ejem) V25393438, J21121733\n";
            return false;
        }
        
        //validacion de telefono
        if(!view.txtClientPhone.getText().matches("^\\d{10,}$")){
            super.errorMsg += "  Telefono: (Ejem) 04249578658\n";
            return false;
        }
        
        //validacion de telefono
        if(!view.txtClientMail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            super.errorMsg += "  Correo: (ejem) mifarma@correo.dom";
            return false;
        }
        
        return true;
    }

    @Override
    protected Customer loadDataObject() {
        return new Customer(
                -1,
                view.txtClientName.getText(),
                view.txtClientAddress.getText(),
                view.txtClientPhone.getText(),
                view.txtClientMail.getText(),
                "",
                "",
                view.txtClientId.getText()
        );
    }

    @Override
    protected JTextComponent[] getTextFields() { return this.fields; }

    @Override
    protected JTable getViewTable() { return view.tblClient; }

    @Override
    protected void loadDataToView(Customer d) {
        view.txtClientId.setText(d.getDocId());
        view.txtClientAddress.setText(d.getAddress());
        view.txtClientMail.setText(d.getEmail());
        view.txtClientName.setText(d.getFullName());
        view.txtClientPhone.setText(d.getTelephone());
    }
    
}
