package services;

import models.Product;
import persistence.ProductDao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ProductService {
    public static final ProductService INSTANCE = new ProductService();
    private final ProductDao dao = ProductDao.INSTANCE;

    private ProductService() {}

    public Product add(Product p) throws SQLException {
        int generatedId = dao.save(p);
        p.setId(generatedId);
        return p;
    }

    public List<Product> all() throws SQLException {
        List<Product> list = dao.findAll();
        Collections.sort(list);
        return list;
    }

    public Optional<Product> byId(int id) throws SQLException {
        return dao.findById(id);
    }

    public Product update(Product p) throws SQLException {
        dao.save(p);
        return p;
    }

    public boolean delete(int id) throws SQLException {
        return dao.deleteById(id);
    }

    public Map<Integer, Product> allAsMap() throws SQLException {
        List<Product> list = dao.findAll();
        Map<Integer, Product> result = new HashMap<>();
        for (Product p : list) {
            result.put(p.getId(), p);
        }
        return result;
    }
}
