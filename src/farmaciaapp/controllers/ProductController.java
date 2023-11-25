
package farmaciaapp.controllers;

import farmaciaapp.models.Categories;
import farmaciaapp.models.Products;
import farmaciaapp.models.dataobject.Category;
import farmaciaapp.models.dataobject.Product;
import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.models.foundation.Modelable;
import farmaciaapp.views.SystemView;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class ProductController extends AbstractController<Product>{
    
    private JTextComponent [] fields = 
    {
        view.txtProductId,
        view.txtProductCode,
        view.txtProductName,
        view.txtProductDescription,
        view.txtProductPrice,
        view.txtProductSearch
    };
    private final Modelable<Category> categories;

    public ProductController(SystemView view) {
        super(view, new Products());
        categories = new Categories();
        
        
        view.btnProductNew.addActionListener( e -> clear());
        view.btnProductSave.addActionListener( e -> save());
        view.btnProductDelete.addActionListener( e -> delete());
        view.btnProductUpdate.addActionListener( e -> update());
        view.txtProductSearch.addActionListener( e -> search(view.txtEmploySearch.getText()));
               

    }

    @Override
    protected boolean check() {
        super.errorMsg="Algunos datos no son correctos:\n";

        
        //codigo
        if(!view.txtProductCode.getText().matches("^\\d{1,}$")){
            super.errorMsg += "  Codigo: Solo valores numericos (00122)\n";
            return false;
        }
        
        //precio
        if(!view.txtProductCode.getText().matches("^\\d{1,}$")){
            super.errorMsg += "  Precio: Solo valores numericos (25.5)\n";
            return false;
        }
       
        return true;
    }

    @Override
    protected Product loadDataObject() {
        Product temp =  new Product();
        
        temp.setId(view.txtProductId.getText().trim().isEmpty() ? -1 : Integer.parseInt(view.txtProductId.getText()));
        temp.setCode(view.txtProductCode.getText());
        temp.setName(view.txtProductName.getText());
        temp.setDescription(view.txtProductDescription.getText());
        temp.setPrice(Double.parseDouble(this.view.txtProductPrice.getText()));
        temp.setQuantity(0);
        
        String categoryName = (String) view.cmbProductCategory.getSelectedItem();
        Category tempCategory = categories
                .all()
                .stream()
                .filter(c -> c.getName().equals(categoryName))
                .findAny()
                .get();
        temp.setCategory(tempCategory);
        
        return temp;
    }

    @Override
    protected JTextComponent[] getTextFields() { return this.fields; }

    @Override
    protected JTable getViewTable() { return view.tblProducts; }

    @Override
    protected void loadDataToView(Product d) {
        view.txtProductId.setText(String.valueOf(d.getId()));
        view.txtProductCode.setText(d.getCode());
        view.txtProductName.setText(d.getName());
        view.txtProductDescription.setText(d.getDescription());
        view.txtProductPrice.setText(String.valueOf(d.getPrice()));
        view.cmbProductCategory.setSelectedItem(d.getCategory().getName());
    }
    
    @Override
    public void renew(){
        super.renew();
        categories.pull();
        view.cmbProductCategory.setModel(new DefaultComboBoxModel(categories.all().stream().map(c -> c.getName()).toArray()));

    }
    
    
}
