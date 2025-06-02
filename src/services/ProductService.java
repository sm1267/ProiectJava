package services;

import models.Product;
import persistence.ProductDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class ProductService {
    public static final ProductService INSTANCE = new ProductService();
    private final ProductDao dao = ProductDao.INSTANCE;

    private ProductService() {}

    public Product add(Product p) throws SQLException {
        p.setId(dao.save(p));
        return p;
    }

    public List<Product> all()                throws SQLException { return dao.findAll(); }
    public Optional<Product> byId(int id)     throws SQLException { return dao.findById(id); }
    public Product update(Product p)          throws SQLException { dao.save(p); return p; }
    public boolean delete(int id)             throws SQLException { return dao.deleteById(id); }
}

