
package farmaciaapp.models;

import farmaciaapp.models.acces.CategoryAcces;
import farmaciaapp.models.dataobject.Category;
import farmaciaapp.models.foundation.AbstractModel;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Categories extends AbstractModel<Category>{
    
    private static final String [] ColumnNames = {"id","name"};
    
    public Categories() {
        super(new CategoryAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
    }

    @Override
    public boolean searchIf(String searchParam, Category dt) { return dt.getName().contains(searchParam.trim());}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value =null;
        Category category = this.list.get(rowIndex);
        
        switch(columnIndex){
            case 0: value = category.getId(); break;
            case 1: value = category.getName(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }

    @Override
    protected String[] getConlumnNames() { return Categories.ColumnNames; }
    
}
