package farmaciaapp.models;

import farmaciaapp.models.acces.PurchaseAcces;
import farmaciaapp.models.dataobject.Purchase;
import farmaciaapp.models.foundation.AbstractModel;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Purchases extends AbstractModel<Purchase> {
    
    private static final String [] columnNames = { "Documento", "Proveedor", "Total", "Fecha" };

    public Purchases() {
        super(new PurchaseAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
    }

    @Override
    public boolean searchIf(String searchParam, Purchase dt) {
        return dt.getDocument().contains(searchParam)
                || dt.getProducts().stream().allMatch(e -> e.getProduct().getName().contains(searchParam));
    }

    @Override
    protected String[] getConlumnNames() { return columnNames; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value =null;
        
        switch(columnIndex){
            case 0: value = this.list.get(rowIndex).getDocument(); break;
            case 1: value = this.list.get(rowIndex).getSupplier().getName(); break;
            case 2: value = this.list.get(rowIndex).getTotal(); break;
            case 3: value = this.list.get(rowIndex).getCreated(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }

    @Override
    public boolean contains(Purchase dto) {
        pull();
        for (Purchase e : this.list) {
            if (e.getId() == dto.getId() || 
                    e.getDocument().equalsIgnoreCase(dto.getDocument())) {
                return true;
            }
        }
        return false;
    }
    
    
    
}
