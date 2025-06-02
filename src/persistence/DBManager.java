package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBManager {
    private static final String URL  = "jdbc:mysql://localhost:3306/inventory";
    private static final String USER = "root";
    private static final String PASS = "admin";

    private static Connection connection;

    private DBManager() {}

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }
}

