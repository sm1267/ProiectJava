package services;

import models.Category;
import persistence.CategoryDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class CategoryService {
    public static final CategoryService INSTANCE = new CategoryService();
    private final CategoryDao dao = CategoryDao.INSTANCE;

    private CategoryService() {}

    public Category add(String name, String desc) throws SQLException {
        Category c = new Category(name, desc);
        c.setId(dao.save(c));
        return c;
    }

    public List<Category> all()                throws SQLException { return dao.findAll(); }
    public Optional<Category> byId(int id)     throws SQLException { return dao.findById(id); }
    public Category update(Category c)         throws SQLException { dao.save(c); return c; }
    public boolean delete(int id)              throws SQLException { return dao.deleteById(id); }
}
