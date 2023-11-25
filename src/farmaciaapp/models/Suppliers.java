

package farmaciaapp.models;

import farmaciaapp.models.foundation.AbstractModel;
import farmaciaapp.models.dataobject.Supplier;
import farmaciaapp.models.acces.SupplierAcces;


public class Suppliers extends AbstractModel<Supplier>{

    public Suppliers() {
        super(new SupplierAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
    }

    @Override
    public boolean searchIf(String sp, Supplier dto) {
        return dto.getName().contains(sp) || dto.getDescription().contains(sp);
    }

    //metodos y propiedades de AbstractTableModel
    
    String[] columnName = {
        "Identificacion", //0
        "Nombre",         //1
        "Descripcion",    //2
        "Direccion",      //3
        "Telefono",       //4
        "Correo",         //5
        "Ciudad"          //6
    };
    
    @Override
    protected String[] getConlumnNames() {return this.columnName;}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { 
        Object value =null;
        Supplier sup = (Supplier) this.list.get(rowIndex);
        
        switch(columnIndex){
            case 0: value = sup.getId(); break;
            case 1: value = sup.getName(); break;
            case 2: value = sup.getDescription(); break;
            case 3: value = sup.getAddress(); break;
            case 4: value = sup.getTelephone(); break;
            case 5: value = sup.getEmail(); break;
            case 6: value = sup.getCity(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }

    
    
}
