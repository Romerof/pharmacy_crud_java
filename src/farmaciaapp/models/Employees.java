
package farmaciaapp.models;

import farmaciaapp.models.dataobject.Employee;
import farmaciaapp.models.acces.EmployeeAcces;
import farmaciaapp.models.foundation.AbstractModel;
import java.util.ArrayList;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Employees extends AbstractModel<Employee> {

    private final String[] ColumnName = {
        "Identificacion", //0
        "Nombre",         //1
        "Usuario",        //2
        "Rol",            //3
        "Direccion",      //4
        "Telefono",       //5
        "Correo"          //6
    };
    public Employees() {
        super(new EmployeeAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
    }
    

    @Override
    public boolean searchIf(String searchParam, Employee dt) {
        return Integer.toString(dt.getId()).contains(searchParam) ||
                    dt.getFullName().contains(searchParam) ||
                    dt.getUsername().contains(searchParam);
    }

    @Override
    protected String[] getConlumnNames() { return ColumnName; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value =null;
        
        switch(columnIndex){
            case 0: value = this.list.get(rowIndex).getId(); break;
            case 1: value = this.list.get(rowIndex).getFullName(); break;
            case 2: value = this.list.get(rowIndex).getUsername(); break;
            case 3: value = this.list.get(rowIndex).getRol(); break;
            case 4: value = this.list.get(rowIndex).getAddress(); break;
            case 5: value = this.list.get(rowIndex).getTelephone(); break;
            case 6: value = this.list.get(rowIndex).getEmail(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }

    public ArrayList<Employee> all () { return super.list; }

}
