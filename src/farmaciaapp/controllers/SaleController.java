
package farmaciaapp.controllers;

import farmaciaapp.models.Customers;
import farmaciaapp.models.Products;
import farmaciaapp.models.Sales;
import farmaciaapp.models.dataobject.Customer;
import farmaciaapp.models.dataobject.Product;
import farmaciaapp.models.dataobject.ProductSale;
import farmaciaapp.models.dataobject.Sale;
import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.models.foundation.Modelable;
import farmaciaapp.views.SystemView;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class SaleController extends AbstractController<Sale>{
    
    private JTextComponent [] fields;
    Modelable<Product> products;
    Modelable<Customer> customers;
    Product actualProduct;
    Sale actualSale;
    
    private DefaultTableModel tableModel;

    public SaleController(SystemView view) {
        super(view, new Sales());
        this.fields = new JTextComponent[]{
            view.txtSaleProductCode, 
            view.txtSaleProductQuantity, 
            view.txtSaleProductId, 
            view.txtSaleProductName,
            view.txtSaleProductPrice,
            view.txtSaleProductStock,
            view.txtSaleProductSub
        };
        this.products =  new Products();
        this.customers = new Customers();
        
        this.actualSale = new Sale();
        this.tableModel = new DefaultTableModel(){
            private String [] columnNames = {"Codigo", "Producto", "Cantidad", "Precio","Subtotal"};
            
            @Override
            public int getRowCount() {
                return actualSale.getProductSale().size();
            }
            
            @Override
            public int getColumnCount() { return this.columnNames.length; }
            
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Object value =null;
                
                switch(columnIndex){
                    case 0: value = actualSale.getProductSale().get(rowIndex).getProduct().getCode(); break;
                    case 1: value = actualSale.getProductSale().get(rowIndex).getProduct().getName(); break;
                    case 2: value = actualSale.getProductSale().get(rowIndex).getAmount(); break;
                    case 3: value = actualSale.getProductSale().get(rowIndex).getPrice(); break;
                    case 4: value = actualSale.getProductSale().get(rowIndex).getPrice() * actualSale.getProductSale().get(rowIndex).getAmount(); break;
                    default: System.out.println("Error en el indice de la columna");
                }
                
                return value;
            }
            
            @Override
            public String getColumnName(int column) { return this.columnNames[column]; }
            
        };
        
        /// eventos
        
        this.view.btnSale.addActionListener(e -> this.sell());
        this.view.btnSaleAdd.addActionListener(e -> {
            this.addProduct();
            view.txtSaleProductCode.requestFocusInWindow();
        });
        this.view.btnSaleDelete.addActionListener(e -> this.deleteProduct());
        this.view.btnSaleNew.addActionListener( e-> this.newSale());
        this.view.txtSaleClientId.addActionListener(e -> {
            this.loadCustomer();
            view.txtSaleProductCode.requestFocusInWindow();
        });
        this.view.txtSaleProductCode.addActionListener(e -> {
            this.loadProduct();
            view.txtSaleProductQuantity.requestFocusInWindow();
        });
        this.view.txtSaleProductQuantity.addActionListener(e -> {
            this.addProduct();
            view.txtSaleProductCode.requestFocusInWindow();
        });
        this.view.txtSaleProductQuantity.addFocusListener(new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e) {
                updateSubTotal();
            }
        });
    }

    @Override
    protected boolean check() {
        super.errorMsg="Algunos datos no son correctos:\n";
        
        if(!view.txtSaleProductQuantity.getText().matches("^\\d{1,}$") || 
                !(Integer.parseInt(view.txtSaleProductQuantity.getText())>0)){
            super.errorMsg += "  cantidad: solo numeros esteros >0\n";
            return false;
        }
       
        return true;
    }

    @Override
    protected Sale loadDataObject() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected JTextComponent[] getTextFields() { return this.fields; }

    @Override
    protected JTable getViewTable() { return this.view.tblSales; }

    @Override
    protected void loadDataToView(Sale d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void sell() {       
        if(!this.actualSale.getProductSale().isEmpty()){ //ingreso al menos un producto ?
            if (this.actualSale.getCustomer()!=null && this.actualSale.getCustomer().getId()!=-1){ //ingreso un cliente registrado ?
                
                this.actualSale.setEmployee(LoginController.user);

                if(!this.model.contains(actualSale)){
                    this.model.add(this.actualSale);
                    
                    for(ProductSale e : this.actualSale.getProductSale()){
                        e.getProduct().setQuantity(e.getProduct().getQuantity() - e.getAmount());
                        products.put(products.indexAt(e.getProduct()), e.getProduct());
                    }
                    
                    this.clearAll();
                    actualSale=new Sale();
                }else{
                    JOptionPane.showMessageDialog(view, "El codigo de documento ya existe");
                }
                
            }else{
                JOptionPane.showMessageDialog(view, "Debe ingresar un cliente registrado");
            }
            
        }else{
            JOptionPane.showMessageDialog(view, "Debe registrar al menos un producto");
        }
    }

    private void addProduct() {
        if(check()){
            if(super.isUpdate){
                //code for update here
                super.isUpdate = false;
                super.updateRow = -1;
                actualProduct = null;
                clear();
            }else
                if(actualProduct != null){
                    int amount = Integer.parseInt(view.txtSaleProductQuantity.getText());
                
                    ProductSale tempProductSale = new ProductSale(
                            -1,
                            amount,
                            actualProduct.getPrice()
                            ,actualProduct, 
                            actualSale,
                            "",
                            ""
                    );
                    
                    this.actualSale.getProductSale().add(tempProductSale);
                    this.actualSale.setTotal(this.actualSale.getTotal() + (tempProductSale.getAmount() * tempProductSale.getPrice()));
                    actualProduct = null;
                    this.view.txtSaleTotal.setText(String.valueOf(this.actualSale.getTotal()));
                    clear();

                }else{
                    JOptionPane.showMessageDialog(view, "El codigo del producto ingresado no se encuantra registrado");
                }
            //view.txtPurchaseTotal.setText(String.valueOf(calculateTotal()));
        }else
            JOptionPane.showMessageDialog(view, super.errorMsg);
    }

    private void newSale() {
        actualSale = new Sale();
        clearAll();
    }

    private void deleteProduct() {
        if(this.getViewTable().getSelectedRow()>=0){
            ProductSale temp = actualSale.getProductSale().remove(this.getViewTable().getSelectedRow());
            this.actualSale.setTotal(this.actualSale.getTotal() - (temp.getPrice()*temp.getAmount()));
            this.view.txtSaleTotal.setText(String.valueOf(this.actualSale.getTotal()));
            this.getViewTable().updateUI();
        }else{
            JOptionPane.showMessageDialog(view, "Debe seleccionar un elemento de la lista");
        }
    }

    private void loadProduct() {
        if(!view.txtSaleProductCode.getText().isEmpty()){
            Optional<Product> product = products.all().stream()
                    .filter( e -> e.getCode().equals(view.txtSaleProductCode.getText()))
                    .findAny();
            if(product.isPresent()){
                actualProduct = product.get();
                view.txtSaleProductId.setText(String.valueOf(actualProduct.getId()));
                view.txtSaleProductName.setText(actualProduct.getName());
                view.txtSaleProductPrice.setText(String.valueOf(actualProduct.getPrice()));
                view.txtSaleProductStock.setText(String.valueOf(actualProduct.getQuantity()));
            }else{
                JOptionPane.showMessageDialog(view, "Este producto no esta registrado");
            }
        }
    }

    private void clearAll() {
        view.txtSaleClientId.setText("");
        view.txtSaleClientName.setText("");
        view.txtSaleTotal.setText("");
        this.actualSale = null;
        this.actualProduct = null;
        clear();
    }

    private void loadCustomer() {
        
        Optional <Customer> temp = this.customers.all().stream()
                .filter( e -> e.getDocId().equals(view.txtSaleClientId.getText()))
                .findAny();
        
        if (temp.isPresent()) {
            this.actualSale.setCustomer(temp.get());
            view.txtSaleClientName.setText(temp.get().getFullName());
        }else
            JOptionPane.showMessageDialog(view, "Este cliente no esta registrado");

        
    }
    
    private void updateSubTotal(){
        if(this.actualProduct!=null){
            double amount = this.check() ? Double.parseDouble(view.txtSaleProductQuantity.getText()) : 1;
            view.txtSaleProductSub.setText(String.valueOf(amount * this.actualProduct.getPrice()));
        }
    }
    
    @Override
    public void renew() {
        this.customers.pull();
        this.products.pull();
        this.model.pull();
        getViewTable().setModel(tableModel);
        getViewTable().updateUI();
    }
}
