
package farmaciaapp.controllers;

import farmaciaapp.models.dataobject.Employee;
import farmaciaapp.models.Employees;
import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.views.SystemView;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;


public class EmployeeController extends AbstractController<Employee>{
            
    private final JTextComponent [] fields = {
        view.txtEmployId,
        view.txtEmployAddress,
        view.txtEmployMail,
        view.txtEmployName,
        view.txtEmployPass,
        view.txtEmployPhone,
        view.txtEmployUser,
        view.txtEmploySearch
    };
            
    public EmployeeController(SystemView _view){
        super(_view, new Employees());
        
        view.btnEmployCancel.addActionListener( e -> clear());
        view.btnEmploySave.addActionListener( e -> save());
        view.btnEmployDelete.addActionListener( e -> delete());
        view.btnEmployUpdate.addActionListener( e -> update());
        view.txtEmploySearch.addActionListener( e -> search(view.txtEmploySearch.getText()));
        
        //eliminar, esto es solo para pruebas
        
    }

    @Override
    protected boolean check() {
        super.errorMsg="Algunos datos no son correctos:\n";
        boolean checked = true;

        if(!view.txtEmployId.getText().matches("\\d{4,9}")){
            super.errorMsg += "  Identificacion: (Ejem) V25393438, J21121733\n";
            checked = false;
        }
        
        //validacion de telefono
        if(!view.txtEmployPhone.getText().matches("\\d{10,}")){
            super.errorMsg += "  Identificacion: (Ejem) V25393438, J21121733\n";
            checked = false;
        }
        
        //validacion de telefono
        if(!view.txtEmployMail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            super.errorMsg += "  Correo: (ejem) mifarma@correo.dom";
            checked = false;
        }
        return  checked;
    }

    @Override
    protected Employee loadDataObject() {
        Employee employee =  new Employee();
        employee.setId(Integer.parseInt(view.txtEmployId.getText()));
        employee.setTelephone(view.txtEmployPhone.getText());
        employee.setEmail(view.txtEmployMail.getText());
        employee.setFullName(view.txtEmployName.getText());
        employee.setUsername(view.txtEmployUser.getText());
        employee.setRol(view.cbmEmployRol.getSelectedItem().toString());
        employee.setAddress(view.txtEmployAddress.getText());
        employee.setPassword(String.copyValueOf(view.txtEmployPass.getPassword()));
        return employee;
    }

    @Override
    protected JTextComponent[] getTextFields() { return this.fields; }

    @Override
    protected JTable getViewTable() { return view.tblEmploy; }

    @Override
    protected void loadDataToView(Employee d) {
        view.txtEmployId.setText(Integer.toString(d.getId()));
        view.txtEmployAddress.setText(d.getAddress());
        view.txtEmployMail.setText(d.getEmail());
        view.txtEmployName.setText(d.getFullName());
        view.txtEmployPass.setText(d.getPassword());
        view.txtEmployPhone.setText(d.getTelephone());
        view.txtEmployUser.setText(d.getUsername());
        view.cbmEmployRol.setSelectedItem(d.getRol());
    }
}
