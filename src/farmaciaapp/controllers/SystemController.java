
package farmaciaapp.controllers;

import farmaciaapp.FarmaciaApp;
import farmaciaapp.views.SystemView;
import java.util.function.Consumer;

public class SystemController {
    SystemView view;
    EmployeeController employee;
    ProfileController profile;
    CustomerController customer;
    SupplierController supplier;
    CategoryController category;
    ProductController product;
    PurchaseController purchase;
    SaleController sale;
    ReportController report;
    int actualTab;

    
    public SystemController(Consumer<Integer> changeState){
        
        this.view = new SystemView();
        view.setVisible(true);
        
        this.view.btnLogout.addActionListener( i -> changeState.accept(FarmaciaApp.STATE_UNLOGGED));
        
        employee = new EmployeeController(view);
        profile = new ProfileController(view);
        customer = new CustomerController(view);
        supplier = new SupplierController(view);
        category =  new CategoryController(view);
        purchase = new PurchaseController(view);
        sale = new SaleController(view);
        report =  new ReportController(view);
        
        
        product =  new ProductController(view);
        product.renew();
        actualTab = 0;
        view.jTabbedPane1.setSelectedIndex(0);
        
        view.jTabbedPane1.addChangeListener(e -> changeTab(view.jTabbedPane1.getSelectedIndex()));
        

    }
    
    private void changeTab(int tabIndex){
        
        switch(tabIndex){
            case 0: //productos
                product.renew();
                
                break;
            case 1: 
                purchase.renew();
                break;
            case 2: //ventas
                sale.renew();
                break;
            case 3: //clientes
                customer.renew();
                
                break;
            case 4: //empleados
                employee.renew();


                break;
            case 5: //proveedor
                supplier.renew();

                break;
            case 6: //categorias
                category.renew();

                break;
            case 7: //reportes
                report.renew();
                break;
            case 8: //perfil
                profile.start();
                break;
            default:
        }
        
        switch(this.actualTab){
            case 0: //productos
                product.release();
                
                break;
            case 1: 
                purchase.release();
                break;
            case 2: //ventas
                break;
            case 3: //clientes
                customer.release();
                break;
            case 4: //empleados
                break;
            case 5: //proveedor
                supplier.release();
                break;
            case 6: //categorias
                category.release();
                break;
            case 7: //reportes
                //report.r
                break;
            case 8: //perfil
                //profile.release();
                break;
            default:
        }
        
        this.actualTab = tabIndex;
    }
    
    public void showView(boolean flag){
        this.view.setVisible(flag);
    }
    
}
