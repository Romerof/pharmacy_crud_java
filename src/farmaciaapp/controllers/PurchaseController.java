
package farmaciaapp.controllers;

import farmaciaapp.models.Products;
import farmaciaapp.models.PurchaseDetails;
import farmaciaapp.models.Purchases;
import farmaciaapp.models.Suppliers;
import farmaciaapp.models.dataobject.Product;
import farmaciaapp.models.dataobject.Purchase;
import farmaciaapp.models.dataobject.PurchaseDet;
import farmaciaapp.models.dataobject.Supplier;
import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.models.foundation.Modelable;
import farmaciaapp.views.SystemView;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class PurchaseController extends AbstractController<PurchaseDet>{
    
    Modelable<Product> products;
    Modelable<Purchase> purchases;
    Modelable<Supplier> suppliers;
    Purchase actualPurchase;
    Product actualProduct;
    List<PurchaseDet> tempPurchaseDetail;

    private final AbstractTableModel tempPurchaseDetailTableModel;
    
    private final javax.swing.text.JTextComponent [] fields = { 
        view.txtPurchaseCode,
        view.txtPurchaseId,
        view.txtPurchaseName,
        view.txtPurchasePrice,
        view.txtPurchaseQuantity,
        view.txtPurchaseSub,
        view.txtPurchaseTotal
    };

    public PurchaseController(SystemView view) {
        super(view, new PurchaseDetails());
        products = new Products();
        purchases = new Purchases();
        suppliers =  new Suppliers();
        actualPurchase = new Purchase();
        actualProduct =  null;
        tempPurchaseDetail = new ArrayList(20);
        
        view.btnToBuy.addActionListener( e -> save());
        view.btnAddProduct.addActionListener( e -> addProduct());
        view.btnPurchaseNew.addActionListener( e -> clear());
        view.btnPurchaseModify.addActionListener( e -> update());
        view.btnPurchaseDelete1.addActionListener( e -> delete());
        view.txtPurchaseCode.addFocusListener( new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e); 
                loadProduct();
            }
            
        });
        view.txtPurchaseCode.addActionListener( e -> loadProduct() );
        view.txtPurchaseQuantity.addFocusListener( new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e); 
                view.txtPurchaseSub.setText(calculateSub());
            }
            
        });
        view.txtPurchasePrice.addFocusListener( new FocusAdapter(){
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e); 
                view.txtPurchaseSub.setText(calculateSub());
            }
            
        });
        
        this.tempPurchaseDetailTableModel = new AbstractTableModel(){
            @Override
            public int getRowCount() { return tempPurchaseDetail.size(); }
            
            @Override
            public int getColumnCount() { return ((TableModel) model).getColumnCount(); }
            
            @Override
            public Object getValueAt(int rowIndex, int columnIndex ) {
                Object value =null;
        
                switch(columnIndex){
                    case 0: value = tempPurchaseDetail.get(rowIndex).getPurchase().getDocument(); break;
                    case 1: value = tempPurchaseDetail.get(rowIndex).getProduct().getName(); break;
                    case 2: value = tempPurchaseDetail.get(rowIndex).getAmount(); break;
                    case 3: value = tempPurchaseDetail.get(rowIndex).getCost() * tempPurchaseDetail.get(rowIndex).getAmount(); break;
                    default: System.out.println("Error en el indice de la columna");
                }
        
                return value;
            }
            
            @Override
            public String getColumnName(int column) { 
                String [] columnNames = {"Documento","Producto","Cantidad", "Subtotal"};
                return  columnNames[column]; 
            }
        };
        
        
        //UpdateListener = 
        
    }

    @Override
    protected boolean check() {
        super.errorMsg="Algunos datos no son correctos:\n";
        
        //validacion de id
        if(!view.txtPurchaseQuantity.getText().matches("^\\d{1,}$") || 
                !(Integer.parseInt(view.txtPurchaseQuantity.getText())>0)){
            super.errorMsg += "  cantidad: solo numeros esteros >0\n";
            return false;
        }
        
        //validacion de telefono
        if(!view.txtPurchasePrice.getText().matches("^\\d{1,}|(\\d{1,}\\.{0,1}\\d{1,})$")){
            super.errorMsg += "  Precio: (Ejem) 25.0";
            return false;
        }
                
        return true;
    }

    @Override
    protected PurchaseDet loadDataObject() {
        
        PurchaseDet det = new PurchaseDet();
        
        det.setPurchase(actualPurchase);
        det.setAmount(Integer.parseInt(view.txtPurchaseQuantity.getText()));
        det.setCost(Double.valueOf(view.txtPurchasePrice.getText()));
        det.setProduct(actualProduct);
        
        actualProduct.setQuantity(products.get(products.indexAt(actualProduct)).getQuantity() + det.getAmount());
        
        return det;
    }

    @Override
    protected JTextComponent[] getTextFields() { return fields; }

    @Override
    protected JTable getViewTable() { return view.tblPurchases; }

    @Override
    protected void loadDataToView(PurchaseDet d) {
        view.txtPurchaseCode.setText(d.getProduct().getCode());
        view.txtPurchaseId.setText(String.valueOf(d.getProduct().getId()));
        view.txtPurchaseName.setText(d.getProduct().getName());
        view.txtPurchasePrice.setText(String.valueOf(d.getCost()));
        view.txtPurchaseQuantity.setText(String.valueOf(d.getAmount()));
        view.txtPurchaseSub.setText(calculateSub());
    }
    
    @Override
    protected void clear(){
        if(this.getViewTable().getModel() == model) getViewTable().setModel(tempPurchaseDetailTableModel);
        super.clear();
    }
    
    protected void clearPurchase(){
        view.txtPurchaseDocument.setText("");
        this.tempPurchaseDetail.removeAll(tempPurchaseDetail);
        this.getViewTable().setModel((TableModel) model);
        super.clear();
    }
    
    @Override
    protected void delete(){
        if(this.getViewTable().getModel() == model) {
            JOptionPane.showMessageDialog(view, "Estos registros no pueden ser borrados");
        }else if(this.getViewTable().getSelectedRow()>=0){
            tempPurchaseDetail.remove(this.getViewTable().getSelectedRow());
            this.getViewTable().updateUI();
        }else{
            JOptionPane.showMessageDialog(view, "Debe seleccionar un elemento de la lista");
        }
    }
    
    @Override
    protected void update(){
        if(this.getViewTable().getModel() == model) {
            JOptionPane.showMessageDialog(view, "Estos registros no pueden ser editados");
        }else if(updateRow <0) {
            super.updateRow = getViewTable().getSelectedRow(); 
            super.isUpdate = true;
            this.actualProduct = tempPurchaseDetail.get(updateRow).getProduct();
            loadDataToView(tempPurchaseDetail.get(updateRow));
            this.getViewTable().clearSelection();
        }else{
            JOptionPane.showMessageDialog(view, "Debe seleccionar un elemento de la lista");
        }
    }


    private void addProduct() {
        
        if(check()){
                PurchaseDet tempPD = this.loadDataObject();
            if(super.isUpdate){
                this.tempPurchaseDetail.set(updateRow, tempPD);
                super.isUpdate = false;
                super.updateRow = -1;
                actualProduct = null;
                clear();
            }else
                if(actualProduct != null){
                    this.tempPurchaseDetail.add(tempPD);
                    actualProduct = null;
                    clear();

                }else{
                    JOptionPane.showMessageDialog(view, "El codigo del producto ingresado no se encuantra registrado");
                }
            view.txtPurchaseTotal.setText(String.valueOf(calculateTotal()));
        }else
            JOptionPane.showMessageDialog(view, super.errorMsg);

    }
    
    private void loadProduct(){
        if(this.getViewTable().getModel() == model) getViewTable().setModel(tempPurchaseDetailTableModel);
        if(!view.txtPurchaseCode.getText().isEmpty()){
            Optional<Product> product = products.all().stream().filter( e -> e.getCode().equals(view.txtPurchaseCode.getText())).findAny();
            if(product.isPresent()){
                actualProduct = product.get();
                view.txtPurchaseId.setText(String.valueOf(actualProduct.getId()));
                view.txtPurchaseName.setText(actualProduct.getName());
            }
        }
    }

    @Override
    public void renew() {
        super.renew();
        this.products.pull();
        this.purchases.pull();
        this.suppliers.pull();        
        view.cmbPurchaseSupplier.setModel(new DefaultComboBoxModel(this.suppliers.all().stream().map(s -> s.getName()).toArray()));
    }

    @Override
    public void release() {
        super.release();
        this.tempPurchaseDetail.removeAll(tempPurchaseDetail);
    }

    private String calculateSub() {
        if(check()){
            
            return String.valueOf(
            Double.parseDouble(view.txtPurchasePrice.getText()) *
            Double.parseDouble(view.txtPurchaseQuantity.getText()));
        }else 
            return "0";
    }

    @Override
    protected void save() {
        if(!this.tempPurchaseDetail.isEmpty()){
            loadPurchase();
            if(!purchases.contains(actualPurchase)){
                actualPurchase.setId(this.purchases.add(this.actualPurchase));
                for (PurchaseDet e : this.tempPurchaseDetail){
                    this.model.add(e);
                    products.put(products.indexAt(e.getProduct()), e.getProduct());
                }
                this.clearPurchase();
                actualPurchase=null;
            }else{
                JOptionPane.showMessageDialog(view, "El codigo de documento ya existe");
            }
            
        }else{
            JOptionPane.showMessageDialog(view, "Debe registrar al menos un producto en la compra");
        }
    }

    private void loadPurchase() {
        this.actualPurchase.setDocument(view.txtPurchaseDocument.getText());
        
        //to do: deberia verificar que el proveedor no sea nulo, en caso de que no se encuentre el nombre seleccionado.
        //si embargo, para efectos practicos omitire esto
        this.actualPurchase.setSupplier(this.suppliers.all().stream().filter( e -> e.getName().equals(view.cmbPurchaseSupplier.getSelectedItem())).findAny().get()) ;
        
        this.actualPurchase.setEmployee(LoginController.user);
        this.actualPurchase.setTotal(calculateTotal());
    }

    private double calculateTotal() {
        double total= 0.0;
        if(!this.tempPurchaseDetail.isEmpty()){
            total = this.tempPurchaseDetail.stream()
                .mapToDouble( e -> e.getAmount() * e.getCost()).reduce(0.0, ( a, e) -> a + e);
        }

                
        return total;
    }
    
    
    
}
