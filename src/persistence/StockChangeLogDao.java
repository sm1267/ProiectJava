package persistence;

import models.*;
import java.sql.*;
import java.time.LocalDateTime;

public final class StockChangeLogDao extends AbstractJdbcDao<StockChangeLog, Integer> {

    public static final StockChangeLogDao INSTANCE = new StockChangeLogDao();

    private StockChangeLogDao() {
        super("INSERT INTO stock_change_logs(product_id, delta_qty, reason) VALUES (?,?,?)",
                "UPDATE stock_change_logs SET product_id=?, delta_qty=?, reason=? WHERE id=?",
                StockChangeLog::getId);
    }

    @Override protected String table() { return "stock_change_logs"; }

    @Override protected StockChangeLog map(ResultSet rs) throws SQLException {
        Product p = ProductDao.INSTANCE.findById(rs.getInt("product_id")).orElse(null);
        return new StockChangeLog(
                rs.getInt("id"),
                p,
                rs.getInt("delta_qty"),
                rs.getString("reason"),
                rs.getTimestamp("ts").toLocalDateTime());
    }

    @Override protected void insert(PreparedStatement ps, StockChangeLog log) throws SQLException {
        ps.setInt(1, log.getProduct().getId());
        ps.setInt(2, log.getDeltaQty());
        ps.setString(3, log.getReason());
    }

    @Override protected void update(PreparedStatement ps, StockChangeLog log) throws SQLException {
        ps.setInt(1, log.getProduct().getId());
        ps.setInt(2, log.getDeltaQty());
        ps.setString(3, log.getReason());
        ps.setInt(4, log.getId());
    }
}
