package persistence;

import models.Category;
import java.sql.*;

public final class CategoryDao extends AbstractJdbcDao<Category, Integer> {

    public static final CategoryDao INSTANCE = new CategoryDao();

    private CategoryDao() {
        super("INSERT INTO categories(name, description) VALUES (?, ?)",
                "UPDATE categories SET name = ?, description = ? WHERE id = ?",
                Category::getId);
    }

    @Override protected String table() { return "categories"; }

    @Override protected Category map(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"));
    }

    @Override protected void insert(PreparedStatement ps, Category c) throws SQLException {
        ps.setString(1, c.getName());
        ps.setString(2, c.getDescription());
    }

    @Override protected void update(PreparedStatement ps, Category c) throws SQLException {
        ps.setString(1, c.getName());
        ps.setString(2, c.getDescription());
        ps.setInt(3, c.getId());
    }
}

