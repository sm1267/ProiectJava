package services;

import models.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class InventoryService {
    public static final InventoryService INSTANCE = new InventoryService();
    private InventoryService() {}

    // --- sub-servicii singletons -----------------------------------------
    private final CategoryService      categories = CategoryService.INSTANCE;
    private final SupplierService      suppliers  = SupplierService.INSTANCE;
    private final ProductService       products   = ProductService.INSTANCE;
    private final StockChangeLogService logs      = StockChangeLogService.INSTANCE;

    // accesori -------------------------------------------------------------
    public CategoryService      getCategories() { return categories; }
    public SupplierService      getSuppliers()  { return suppliers; }
    public ProductService       getProducts()   { return products; }
    public StockChangeLogService getLogs()      { return logs; }

    // faţade scurte --------------------------------------------------------
    public Product addProduct(Product p)             throws SQLException { return products.add(p); }
    public List<Product> listProducts()              throws SQLException { return products.all(); }
    public Optional<Product> findProduct(int id)     throws SQLException { return products.byId(id); }
}
