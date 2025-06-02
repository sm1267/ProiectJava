package services;

import models.Supplier;
import persistence.SupplierDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class SupplierService {
    public static final SupplierService INSTANCE = new SupplierService();
    private final SupplierDao dao = SupplierDao.INSTANCE;

    private SupplierService() {}

    public Supplier add(String name, String phone, String email) throws SQLException {
        Supplier s = new Supplier(name, phone, email);
        s.setId(dao.save(s));
        return s;
    }

    public List<Supplier> all()                throws SQLException { return dao.findAll(); }
    public Optional<Supplier> byId(int id)     throws SQLException { return dao.findById(id); }
    public Supplier update(Supplier s)         throws SQLException { dao.save(s); return s; }
    public boolean delete(int id)              throws SQLException { return dao.deleteById(id); }
}

