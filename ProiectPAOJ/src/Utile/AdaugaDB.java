package Utile;

import Config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdaugaDB {
    private static AdaugaDB instanta = null;
    private final Connection databaseConnection;
    private AdaugaDB()
    {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConnection = databaseConfiguration.getDatabaseConnection();
    }
    public static synchronized AdaugaDB getInstance() {
        if (instanta == null)
            instanta = new AdaugaDB();

        return instanta;
    }
    public <T> void insereazaObiecteDB(String query, StatementBinder<T> binder, T object) throws SQLException {
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            binder.bind(preparedStatement, object);
            preparedStatement.executeUpdate();
        }
    }
}
