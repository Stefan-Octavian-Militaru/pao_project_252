package Config;

import Utile.CitesteFisier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private final Connection databaseConnection;

    public DatabaseConfiguration(){
        try {
            String[] dbInfo = CitesteFisier.readDbInfoFromCsv("Resurse/dbInfo.csv");
            String url = dbInfo[0];
            String username = dbInfo[1];
            String pass = dbInfo[2];
            databaseConnection = DriverManager.getConnection(url, username, pass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getDatabaseConnection(){
        return databaseConnection;
    }

}
