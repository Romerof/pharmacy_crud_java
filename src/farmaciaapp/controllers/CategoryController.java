
package farmaciaapp.controllers;

import farmaciaapp.models.Categories;
import farmaciaapp.models.dataobject.Category;
import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.views.SystemView;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class CategoryController extends AbstractController<Category>{
    
    private final JTextComponent [] fields = {
        view.txtCategoryId,
        view.txtCategoryName,
        view.txtCategorySearch
    };
    
    public CategoryController(SystemView view) {
        super(view, new Categories());
        
        view.btnCategoryCancel.addActionListener( e -> super.clear() );
        view.btnCategoryDelete.addActionListener( e -> super.delete() );
        view.btnCategorySave.addActionListener( e -> super.save());
        view.btnCategoryUpdate.addActionListener( e -> super.update());
        view.txtCategorySearch.addActionListener( e -> super.search(view.txtCategorySearch.getText()));
    }

    @Override
    protected boolean check() { 
        if(view.txtCategoryName.getText().trim().isEmpty()){
            super.errorMsg = "Debe introducir el nombre de la categoria";
            return false;
        }
        
        return !view.txtCategoryName.getText().trim().isEmpty(); 
    }

    @Override
    protected Category loadDataObject() {
        return new Category(
                view.txtCategoryId.getText().trim().isEmpty() 
                        ? -1 
                        : Integer.parseInt(view.txtCategoryId.getText()),
                view.txtCategoryName.getText(),
                "",
                ""
        );
    }

    @Override
    protected JTextComponent[] getTextFields() { return this.fields; }

    @Override
    protected JTable getViewTable() { return this.view.tblCategory; }

    @Override
    protected void loadDataToView(Category d) {
        view.txtCategoryId.setText(String.valueOf(d.getId()));
        view.txtCategoryName.setText(d.getName());
    }
}
