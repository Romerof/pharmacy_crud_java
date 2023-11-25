
package farmaciaapp.models.foundation;

import farmaciaapp.views.SystemView;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import javax.swing.table.TableModel;

/**
 *
 * @author Lenovo ideapad 330S
 * @param <T>
 */
public abstract class AbstractController <T extends DataObject> {
    
    protected Modelable<T> model;
    protected boolean isUpdate=false;
    protected int updateRow = -1;
    protected SystemView view;    
    protected String errorMsg;
    

    public AbstractController(SystemView view, Modelable<T> model) {
        this.model = model;
        this.view =  view;       
    }
    
    
    protected abstract boolean check();
    protected abstract T loadDataObject();
    protected abstract JTextComponent[] getTextFields();
    protected abstract javax.swing.JTable getViewTable();
    protected abstract void  loadDataToView(T d);

    
    public void renew(){
        this.model.pull();
        getViewTable().setModel( (TableModel) model);
        getViewTable().updateUI();
    }
    public void release(){
        //aqui deberia escribir instrucciones para liberar los recursos usados
        getViewTable().removeAll();
    }
    

    protected void clear() {
        
        for (JTextComponent t : getTextFields()){ 
            t.setText("");
        }
        
        this.model.pull();
        getViewTable().clearSelection();
        getViewTable().updateUI();
        this.updateRow=-1;
        this.isUpdate=false;
    }

    protected void save() {
        if(this.check()){
            
            T temp = loadDataObject();
            if( !isUpdate) {
                if(!this.model.contains(temp))
                    this.model.add(temp);
                else
                    JOptionPane.showMessageDialog(
                            view, 
                            "La identificacion especificada ya existe", 
                            "Error", 
                            JOptionPane.WARNING_MESSAGE
                    );
            }else {
                this.model.pull();
                int index = model.indexAt(temp); //en el caso claves unicas no hay verificacion
                if(index == updateRow || index == -1){
                    this.model.put(this.updateRow, temp);
                    this.isUpdate=false;
                    this.updateRow=-1;
                }else 
                    JOptionPane.showMessageDialog(
                            view, 
                            "La identificacion especificada ya existe en otro registro", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE
                    );
            }
            this.clear();
            getViewTable().updateUI();

        }else{
            JOptionPane.showMessageDialog(view, errorMsg);
        }
    }

    protected void delete() {
        int row = getViewTable().getSelectedRow(); 
        if(row >= 0){
            this.model.remove(row);
            this.getViewTable().updateUI();
        }else{
            JOptionPane.showMessageDialog(view, "Debe seleccionar un elemento de la lista");
        }
    }

    protected void update() {
        this.updateRow = getViewTable().getSelectedRow(); 
        this.isUpdate =true;
        if(updateRow >= 0){
            loadDataToView( this.model.get( updateRow));
            this.getViewTable().clearSelection();
        }else{
            JOptionPane.showMessageDialog(view, "Debe seleccionar un elemento de la lista");
        }
    }
    
    protected void search(String param) {
        this.model.pull();
        if(!param.trim().equals("")){
            this.model.filterList(param);
        }
        this.getViewTable().updateUI();   
    }
    
    
    
}
