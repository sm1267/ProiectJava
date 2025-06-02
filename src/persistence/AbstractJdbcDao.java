package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractJdbcDao<T, ID> implements CrudRepository<T, ID> {

    protected abstract String table();
    protected abstract T map(ResultSet rs) throws SQLException;
    protected abstract void insert(PreparedStatement ps, T t) throws SQLException;
    protected abstract void update(PreparedStatement ps, T t) throws SQLException;

    private final String insertSql;
    private final String updateSql;
    private final Function<T, ID> idExtractor;

    protected AbstractJdbcDao(String insertSql, String updateSql,
                              Function<T, ID> idExtractor) {
        this.insertSql   = insertSql;
        this.updateSql   = updateSql;
        this.idExtractor = idExtractor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ID save(T entity) throws SQLException {
        ID id = idExtractor.apply(entity);
        if (id == null || (id instanceof Number n && n.intValue() == 0)) {
            try (PreparedStatement ps = DBManager.getConnection()
                    .prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insert(ps, entity);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return (ID) Integer.valueOf(keys.getInt(1));
                    }
                }
            }
        } else {
            try (PreparedStatement ps = DBManager.getConnection().prepareStatement(updateSql)) {
                update(ps, entity);
                ps.executeUpdate();
            }
        }
        return id;
    }

    @Override
    public Optional<T> findById(ID id) throws SQLException {
        String sql = "SELECT * FROM " + table() + " WHERE id = ?";
        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public List<T> findAll() throws SQLException {
        String sql = "SELECT * FROM " + table();
        List<T> list = new ArrayList<>();
        try (Statement st = DBManager.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public boolean deleteById(ID id) throws SQLException {
        String sql = "DELETE FROM " + table() + " WHERE id = ?";
        try (PreparedStatement ps = DBManager.getConnection().prepareStatement(sql)) {
            ps.setObject(1, id);
            return ps.executeUpdate() == 1;
        }
    }
}

