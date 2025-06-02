package persistence;

import models.*;
import java.sql.*;

public final class ProductDao extends AbstractJdbcDao<Product, Integer> {

    public static final ProductDao INSTANCE = new ProductDao();

    private ProductDao() {
        super("""
              INSERT INTO products(name, price, stock_qty, best_before,
                                   category_id, supplier_id, dtype)
              VALUES (?,?,?,?,?,?,?)""",
                """
                UPDATE products SET name=?, price=?, stock_qty=?, best_before=?,
                                     category_id=?, supplier_id=?, dtype=? WHERE id=?""",
                Product::getId);
    }

    @Override protected String table() { return "products"; }

    @Override protected Product map(ResultSet rs) throws SQLException {
        Category  cat = CategoryDao.INSTANCE.findById(rs.getInt("category_id")).orElse(null);
        Supplier  sup = SupplierDao.INSTANCE.findById(rs.getInt("supplier_id")).orElse(null);

        String dtype = rs.getString("dtype");
        if ("PERISHABLE".equals(dtype)) {
            return new PerishableProduct(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock_qty"),
                    rs.getDate("best_before") != null
                            ? rs.getDate("best_before").toLocalDate() : null,
                    cat, sup);
        }
        return new NonPerishableProduct(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock_qty"),
                cat, sup);
    }

    @Override protected void insert(PreparedStatement ps, Product p) throws SQLException {
        ps.setString(1, p.getName());
        ps.setDouble(2, p.getPrice());
        ps.setInt(3, p.getStockQty());

        if (p instanceof PerishableProduct per && per.getBestBefore() != null) {
            ps.setDate(4, Date.valueOf(per.getBestBefore()));
        } else {
            ps.setNull(4, Types.DATE);
        }
        ps.setInt(5, p.getCategory().getId());
        ps.setInt(6, p.getSupplier().getId());
        ps.setString(7, p.getDtype());
    }

    @Override protected void update(PreparedStatement ps, Product p) throws SQLException {
        ps.setString(1, p.getName());
        ps.setDouble(2, p.getPrice());
        ps.setInt(3, p.getStockQty());
        if (p instanceof PerishableProduct per && per.getBestBefore() != null) {
            ps.setDate(4, Date.valueOf(per.getBestBefore()));
        } else {
            ps.setNull(4, Types.DATE);
        }
        ps.setInt(5, p.getCategory().getId());
        ps.setInt(6, p.getSupplier().getId());
        ps.setString(7, p.getDtype());
        ps.setInt(8, p.getId());
    }
}

