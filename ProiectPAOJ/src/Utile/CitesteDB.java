package Utile;

import Config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CitesteDB {
    private static CitesteDB instanta = null;
    private final Connection databaseConnection;
    private CitesteDB()
    {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConnection = databaseConfiguration.getDatabaseConnection();
    }
    public static synchronized CitesteDB getInstance() {
        if (instanta == null)
            instanta = new CitesteDB();

        return instanta;
    }
    public <T> List<T> citesteObiecteDB(String query, ResultSetMapper<T> mapper) throws SQLException {
        List<T> results = new ArrayList<>();
        try (Statement statement = databaseConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                results.add(mapper.map(resultSet));
            }
        }
        return results;
    }
}
