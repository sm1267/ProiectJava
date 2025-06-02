package services;

import models.StockChangeLog;
import persistence.StockChangeLogDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class StockChangeLogService {
    public static final StockChangeLogService INSTANCE = new StockChangeLogService();
    private final StockChangeLogDao dao = StockChangeLogDao.INSTANCE;

    private StockChangeLogService() {}

    public StockChangeLog add(StockChangeLog log) throws SQLException {
        log.setId(dao.save(log));
        return log;
    }

    public List<StockChangeLog> all()                 throws SQLException { return dao.findAll(); }
    public Optional<StockChangeLog> byId(int id)      throws SQLException { return dao.findById(id); }
    public StockChangeLog update(StockChangeLog log)  throws SQLException { dao.save(log); return log; }
    public boolean delete(int id)                     throws SQLException { return dao.deleteById(id); }
}

