package persistence;

import models.Supplier;
import java.sql.*;

public final class SupplierDao extends AbstractJdbcDao<Supplier, Integer> {

    public static final SupplierDao INSTANCE = new SupplierDao();

    private SupplierDao() {
        super("INSERT INTO suppliers(name, phone, email) VALUES (?,?,?)",
                "UPDATE suppliers SET name=?, phone=?, email=? WHERE id=?",
                Supplier::getId);
    }

    @Override protected String table() { return "suppliers"; }

    @Override protected Supplier map(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getInt("id"), rs.getString("name"),
                rs.getString("phone"), rs.getString("email"));
    }

    @Override protected void insert(PreparedStatement ps, Supplier s) throws SQLException {
        ps.setString(1, s.getName());
        ps.setString(2, s.getPhone());
        ps.setString(3, s.getEmail());
    }

    @Override protected void update(PreparedStatement ps, Supplier s) throws SQLException {
        ps.setString(1, s.getName());
        ps.setString(2, s.getPhone());
        ps.setString(3, s.getEmail());
        ps.setInt(4, s.getId());
    }
}

