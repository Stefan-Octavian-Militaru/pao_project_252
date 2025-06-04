package Utile;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementBinder<T> {
    void bind(PreparedStatement ps, T object) throws SQLException;
}
