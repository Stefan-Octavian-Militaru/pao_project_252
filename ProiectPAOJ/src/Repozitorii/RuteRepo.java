package Repozitorii;

import Config.DatabaseConfiguration;
import Entitati.Gara;
import Entitati.Locomotiva;
import Entitati.Ruta;
import Entitati.Tren;
import Servicii.ServiciuRuta;
import Utile.AdaugaDB;
import Utile.Stare;
import Utile.UtileRuta;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Utile.Constante.*;

public class RuteRepo {
    private static DatabaseConfiguration databaseConfiguration = null;

    public RuteRepo(DatabaseConfiguration dbc) {
        databaseConfiguration = dbc;
    }
    private static List<Ruta> rute;
    public static List<Ruta> getRute(){
        return rute;
    }
    public static void setRute(List<Ruta> rute){
        RuteRepo.rute = rute;
    }

    public static void addRuta(Ruta ruta) throws SQLException {
        rute.add(ruta);
        AdaugaDB.getInstance().insereazaObiecteDB(INSERT_RUTE,
                (statement, ru) ->
                {
                    statement.setInt(1, ru.getIdRuta());
                    statement.setInt(2, ru.getOraPlecare());
                    statement.setInt(3, ru.getOraSosire());
                }, ruta
        );
    }
    public static void adaugaTren(int index, Tren t) throws SQLException {
        rute.get(index).setTren(t);
        PreparedStatement statement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.trenuri SET esteFolosit = 1 where idTren = ?");
        statement.setInt(1, t.getIdTren());
        statement.executeUpdate();
        statement.close();
    }

    public static void afiseaza(){
        for (Ruta r : rute) {
            System.out.println(r);
        }
    }
    public static void getRuteDB() throws SQLException, IOException {
        rute = new ArrayList<>();
        Statement statement = databaseConfiguration.getDatabaseConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_GET_RUTE);
        Map <String, Gara> gari = GariRepo.getGari();
        while (resultSet.next()) {
            List<Gara> gariRuta = new ArrayList<>();
            List<Integer> ore = new ArrayList<>();
            int oraPlecare = resultSet.getInt("oraPlecare");
            int oraSosire = resultSet.getInt("oraSosire");
            int idRuta = resultSet.getInt("idRuta");
            Statement gariStatement = databaseConfiguration.getDatabaseConnection().createStatement();
            ResultSet gariSet = gariStatement.executeQuery(QUERY_GET_GARI_FOR_RUTE + resultSet.getInt("idRuta") + " ORDER BY ora ASC");
            while (gariSet.next()) {
                gariRuta.add(gari.get(gariSet.getString("numeGara")));
                int ora = gariSet.getInt("ora");
                if (ora != oraPlecare && ora != oraSosire) {
                    ore.add(ora);
                }
            }
            gariStatement.close();
            Ruta r_aux = new Ruta(gariRuta,oraPlecare, oraSosire, idRuta);
            rute.add(r_aux);
            ServiciuRuta.adaugaRutaLaOrar(r_aux, ore);
        }
        resultSet = statement.executeQuery(QUERY_GET_RUTA_ID);
        while (resultSet.next()) {
            UtileRuta.seedIdCurent(resultSet.getInt(1) + 1);
        }
        statement.close();
    }
}
