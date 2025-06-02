package persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    ID          save(T entity)         throws SQLException;  // insert sau update
    Optional<T> findById(ID id)        throws SQLException;
    List<T>     findAll()              throws SQLException;
    boolean     deleteById(ID id)      throws SQLException;
}

