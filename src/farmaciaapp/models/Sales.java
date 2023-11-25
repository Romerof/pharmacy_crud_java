
package farmaciaapp.models;

import farmaciaapp.models.acces.ProductSaleAcces;
import farmaciaapp.models.acces.SaleAcces;
import farmaciaapp.models.dataobject.ProductSale;
import farmaciaapp.models.dataobject.Sale;
import farmaciaapp.models.foundation.AbstractModel;
import farmaciaapp.models.foundation.Queryable;
import java.util.List;

/**
 *
 * @author Lenovo ideapad 330S
 */
public class Sales extends AbstractModel<Sale>{
    
    private Queryable<ProductSale> productSales;
    private List<ProductSale> saleDetails;
    private String [] columnNames = {"Codigo", "Producto", "Cantidad", "Precio","Subtotal","Cliente"};

    public Sales() {
        super(new SaleAcces(SystemConfig.SQL_CONNECTION_PROVIDER));
        this.productSales = new ProductSaleAcces(SystemConfig.SQL_CONNECTION_PROVIDER);
    }

    @Override //innecesario
    public boolean searchIf(String searchParam, Sale dt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String[] getConlumnNames() { return this.columnNames; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value =null;
        
        switch(columnIndex){
            case 0: value = this.saleDetails.get(rowIndex).getProduct().getCode(); break;
            case 1: value = this.saleDetails.get(rowIndex).getProduct().getName(); break;
            case 2: value = this.saleDetails.get(rowIndex).getAmount(); break;
            case 3: value = this.saleDetails.get(rowIndex).getPrice(); break;
            case 4: value = this.saleDetails.get(rowIndex).getPrice() * this.saleDetails.get(rowIndex).getAmount(); break;
            case 5: value = this.saleDetails.get(rowIndex).getSale().getCustomer().getFullName(); break;
            default: System.out.println("Error en el indice de la columna");
        }
        
        return value;
    }

    @Override
    public void pull() {
        super.pull(); 
        this.saleDetails = this.productSales.selectAll();
    }

    @Override
    public int add(Sale dto) {
        dto.setDocument(this.generateCode());
        int idSale =  super.add(dto); 
        dto.setId(idSale);
        for (ProductSale p : dto.getProductSale()) {
            p.setSale(dto);
            this.productSales.insert(p);
        }
        
        return idSale;
    }

    @Override
    public Sale get(int index) { return super.get(index); }

    @Override
    public int getColumnCount() { return this.columnNames.length; }

    @Override
    public int getRowCount() { return this.saleDetails.size(); }
    
    private String generateCode(){
        return ((SaleAcces)this.acces).getDocumentCode();
    }

    
}
