
package farmaciaapp.models.foundation;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lenovo ideapad 330S
 * @param <T>
 */
public abstract class AbstractModel <T extends DataObject> 
        extends AbstractTableModel 
        implements Modelable<T>
{
    protected final Queryable<T> acces;
    protected ArrayList <T> list;

    public AbstractModel(Queryable acces){
        this.acces = acces;
        this.list = acces.selectAll();
    }
    
    @Override
    public T get(int index) { return this.list.get(index); }

    @Override
    public int add(T dto) {
        if(this.acces.insert(dto)){
            this.list.add(dto);
            return this.acces.getLastInsertedId();
        }else return -1;
    }

    @Override
    public boolean put(int index, T dto) {
        if(this.acces.update(this.list.get(index).getId(), dto)){
            this.list.set(index, dto);
            return true;
        }else return false;
    }

    @Override
    public T remove(int index) {
        T temp = this.list.get(index);
        if (this.acces.delete(temp)){ 
            this.list.remove(index);
            return temp;
        }else return null;
    }

    @Override
    public void pull() {
        this.list = this.acces.selectAll();
    }

    @Override
    public boolean contains(T dto) {
        pull();
        for (T e : this.list) {
            if (checkElement(e, dto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexAt(T dto) {
        for (int i = 0; i<this.list.size(); i++){
            if (checkElement(this.list.get(i), dto)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public List<T> all(){ return this.list; }

    @Override
    public void filterList(String filterParam) {
        this.list.removeIf(c -> !searchIf(filterParam, c));
    }
    
    public abstract boolean searchIf(String searchParam, T dt);
    
    //AbstractTableModel
    @Override
    public int getRowCount() { return this.list.size(); }
    
    @Override
    public String getColumnName(int column) { return getConlumnNames()[column]; }
    
    @Override
    public int getColumnCount() {return getConlumnNames().length;}
    
    protected abstract String [] getConlumnNames();
    
    protected boolean checkElement(T dt1, T dt2){
        return dt1.getId() == dt2.getId();
    }

    
}
