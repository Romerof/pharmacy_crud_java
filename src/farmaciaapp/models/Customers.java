

package farmaciaapp.models;

import farmaciaapp.models.acces.CustomerAcces;
import farmaciaapp.models.dataobject.Customer;
import farmaciaapp.models.foundation.AbstractModel;

/**
 *
 * @author Lenovo ideapad 330S
 */

public class Customers extends AbstractModel<Customer>{

    private static final String [] columnNames = {
        "Identificacion", //0
        "Nombre",         //1
        "Direccion",      //2
        "Telefono",       //3
        "Correo"          //4
    };
    
    public Customers() {
        super(new CustomerAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
    }

    @Override
    public boolean searchIf(String searchParam, Customer dt) {
        return dt.getDocId().contains(searchParam) || dt.getFullName().contains(searchParam);
    }

    @Override
    protected String[] getConlumnNames() { return Customers.columnNames; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value =null;
        
        switch(columnIndex){
            case 0: value = this.list.get(rowIndex).getDocId(); break;
            case 1: value = this.list.get(rowIndex).getFullName(); break;
            case 2: value = this.list.get(rowIndex).getAddress(); break;
            case 3: value = this.list.get(rowIndex).getTelephone(); break;
            case 4: value = this.list.get(rowIndex).getEmail(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }
    
}
