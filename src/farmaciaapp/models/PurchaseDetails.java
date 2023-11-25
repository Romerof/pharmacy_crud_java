package farmaciaapp.models;

import farmaciaapp.models.acces.PurchaseDetAcces;
import farmaciaapp.models.dataobject.PurchaseDet;
import farmaciaapp.models.foundation.AbstractModel;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class PurchaseDetails extends AbstractModel<PurchaseDet>{
    
    private static final String [] columnNames= {"Documento","Producto","Cantidad", "Subtotal"};

    public PurchaseDetails() {
        super(new PurchaseDetAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
    }

    @Override
    public boolean searchIf(String searchParam, PurchaseDet dt) {
        return false;
    }

    @Override
    protected String[] getConlumnNames() { return columnNames; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value =null;
        
        switch(columnIndex){
            case 0: value = this.list.get(rowIndex).getPurchase().getDocument(); break;
            case 1: value = this.list.get(rowIndex).getProduct().getName(); break;
            case 2: value = this.list.get(rowIndex).getAmount(); break;
            case 3: value = this.list.get(rowIndex).getCost()*this.list.get(rowIndex).getAmount(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }
    
}
