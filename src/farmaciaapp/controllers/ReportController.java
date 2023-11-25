
package farmaciaapp.controllers;

import farmaciaapp.models.Products;
import farmaciaapp.models.Purchases;
import farmaciaapp.models.Sales;
import farmaciaapp.models.dataobject.Product;
import farmaciaapp.models.dataobject.ProductSale;
import farmaciaapp.models.dataobject.Purchase;
import farmaciaapp.models.dataobject.Sale;
import farmaciaapp.models.foundation.AbstractController;
import farmaciaapp.models.foundation.DataObject;
import farmaciaapp.models.foundation.Modelable;
import farmaciaapp.views.SystemView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class ReportController extends AbstractController{
    
    private Modelable<Product> products;
    private Modelable<Sale> sales;
    private Modelable<Purchase> purchases;
    
    private final TableModel salesTableModel;
    private final TableModel purchasesTableModel;


    public ReportController(SystemView view) {
        super(view, new Sales());
        
        this.products = new Products();
        this.sales = new Sales();
        this.purchases  = new Purchases();
        
        view.tblReportSales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.tblReportProducts.setModel(buildProductModel(sales.get(view.tblReportSales.getSelectedRow())));
            }
        });
        
        view.tblReportPurchases.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(view.tblReportPurchases.getSelectedRow()>0)
                    view.tblReportProducts.setModel(buildProductModel(purchases.get(view.tblReportPurchases.getSelectedRow())));
            }   
        });
        
        //table models
        this.salesTableModel = new AbstractTableModel(){
            String [] columnNames = {"Documento","Cliente","Empleado", "Total", "Fecha"};
            
            @Override
            public int getRowCount() { return sales.all().size(); }
            
            @Override
            public int getColumnCount() { return columnNames.length; }
            
            @Override
            public Object getValueAt(int rowIndex, int columnIndex ) {
                Object value =null;
        
                switch(columnIndex){
                    case 0: value = sales.get(rowIndex).getDocument(); break;
                    case 1: value = sales.get(rowIndex).getCustomer().getDocId(); break;
                    case 2: value = sales.get(rowIndex).getEmployee().getFullName(); break;
                    case 3: value = sales.get(rowIndex).getTotal(); break;
                    case 4: value = sales.get(rowIndex).getCreated(); break;
                    default: System.out.println("Error en el indice de la columna");
                }
       
                return value;
            }
            
            @Override
            public String getColumnName(int column) { 
                return  columnNames[column]; 
            }
        };
        
        this.purchasesTableModel = new AbstractTableModel(){
            String [] columnNames = {"Documento","Proveedor", "Total", "Fecha"};
            
            @Override
            public int getRowCount() { return purchases.all().size(); }
            
            @Override
            public int getColumnCount() { return columnNames.length; }
            
            @Override
            public Object getValueAt(int rowIndex, int columnIndex ) {
                Object value =null;
        
                switch(columnIndex){
                    case 0: value = purchases.get(rowIndex).getDocument(); break;
                    case 1: value = purchases.get(rowIndex).getSupplier().getName(); break;
                    case 2: value = purchases.get(rowIndex).getTotal(); break;
                    case 3: value = purchases.get(rowIndex).getCreated(); break;
                    default: System.out.println("Error en el indice de la columna");
                }
       
                return value;
            }
            
            @Override
            public String getColumnName(int column) { return  columnNames[column]; }
        };
        
        this.purchasesTableModel.addTableModelListener( e -> System.out.println("cambio en la tabla"));
        view.tblReportPurchases.addKeyListener(new KeyAdapter(){
            @Override //38, 40
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 38 || e.getKeyCode() == 40 ){
                    view.tblReportProducts.setModel(buildProductModel(purchases.get(view.tblReportPurchases.getSelectedRow())));
                    view.tblReportProducts.updateUI();
                }
            }

        });
        
        view.tblReportSales.addKeyListener(new KeyAdapter(){
            @Override //38, 40
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 38 || e.getKeyCode() == 40 ){
                    view.tblReportProducts.setModel(buildProductModel(sales.get(view.tblReportSales.getSelectedRow())));
                    view.tblReportProducts.updateUI();
                }
            }

        });
    }
    
    

    @Override
    protected boolean check() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected DataObject loadDataObject() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected JTextComponent[] getTextFields() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected JTable getViewTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void loadDataToView(DataObject d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void renew() {
        //super.renew();
        this.sales.pull();
        this.purchases.pull();
        view.tblReportSales.setModel(salesTableModel);
        view.tblReportSales.updateUI();
        view.tblReportPurchases.setModel(purchasesTableModel);
        view.tblReportPurchases.updateUI();
    }
    
    private   String [] productColumnNames = {"Codigo","Descripcion", "Cantidad"};
    private TableModel buildProductModel(Sale sale){
        return new AbstractTableModel(){
            
            @Override
            public int getRowCount() { return sale.getProductSale().size(); }
            
            @Override
            public int getColumnCount() { return productColumnNames.length; }
            
            @Override
            public Object getValueAt(int rowIndex, int columnIndex ) {
                Object value =null;
        
                switch(columnIndex){
                    case 0: value = sale.getProductSale().get(rowIndex).getProduct().getCode(); break;
                    case 1: value = sale.getProductSale().get(rowIndex).getProduct().getName(); break;
                    case 2: value = sale.getProductSale().get(rowIndex).getAmount(); break;
                    default: System.out.println("Error en el indice de la columna");
                }
       
                return value;
            }
            
            @Override
            public String getColumnName(int column) { return  productColumnNames[column]; }
        };
    }
    
    private TableModel buildProductModel(Purchase purchase){
        return new AbstractTableModel(){
            @Override
            public int getRowCount() { return purchase.getProducts().size(); }
            
            @Override
            public int getColumnCount() { return productColumnNames.length; }
            
            @Override
            public Object getValueAt(int rowIndex, int columnIndex ) {
                Object value =null;
        
                switch(columnIndex){
                    case 0: value = purchase.getProducts().get(rowIndex).getProduct().getCode(); break;
                    case 1: value = purchase.getProducts().get(rowIndex).getProduct().getName(); break;
                    case 2: value = purchase.getProducts().get(rowIndex).getAmount(); break;
                    default: System.out.println("Error en el indice de la columna");
                }
       
                return value;
            }
            
            @Override
            public String getColumnName(int column) { return  productColumnNames[column]; }
        };
    }
}
