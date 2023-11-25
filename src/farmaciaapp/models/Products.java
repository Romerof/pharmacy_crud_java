
package farmaciaapp.models;

import farmaciaapp.models.acces.ProductAcces;
import farmaciaapp.models.dataobject.Product;
import farmaciaapp.models.foundation.AbstractModel;

/**
 *
 * @author Lenovo ideapad 330S
 */
public  class Products extends AbstractModel<Product>{
    
    private static final String [] columnNames = {"Id", "Codigo","Nombre","Precio","Categoria","Cantidad"}; 
    
    public Products() {
        super(new ProductAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
    }
    
    @Override
    public boolean searchIf(String searchParam, Product dt) {
        return dt.getCode().contains(searchParam)
                || dt.getName().contains(searchParam);
    }

    @Override
    protected String[] getConlumnNames() { return Products.columnNames; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value =null;
        Product prod = this.list.get(rowIndex);
        
        switch(columnIndex){
            case 0: value = prod.getId(); break;
            case 1: value = prod.getCode(); break;
            case 2: value = prod.getName(); break;
            case 3: value = prod.getPrice(); break;
            case 4: value = prod.getCategory().getName(); break;
            case 5: value = prod.getQuantity(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }
    
}
