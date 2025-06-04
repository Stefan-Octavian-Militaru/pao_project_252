package Repozitorii;
import Config.DatabaseConfiguration;
import Entitati.Gara;
import Entitati.Locomotiva;
import Entitati.Ruta;
import Utile.AdaugaDB;
import Utile.Stare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utile.Constante.*;

public class GariRepo {
    private static DatabaseConfiguration databaseConfiguration = null;

    public GariRepo(DatabaseConfiguration dbc) {
        databaseConfiguration = dbc;
    }
    private static Map<String, Gara> gari;

    public static Map<String, Gara> getGari(){
        return gari;
    }

    public static void setGari(Map<String, Gara> gari){
        GariRepo.gari = gari;
    }

    public static Gara getGara(String nume){
        return gari.get(nume);
    }
    public static List<Gara> getGariList(){
        return new ArrayList<Gara>(gari.values());
    }
    public static void afiseaza(){
        for (Gara g : gari.values()) {
            System.out.println(g);
        }
    }
    public static void adauga() throws IOException, SQLException {
        System.out.println("INTRODUCETI NUMELE SI NUMARUL DE LINII, SEPARATE PRINTR-UN SINGUR SPATIU");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String[] date = cititor.readLine().split(" ");
        Gara g = new Gara(Integer.parseInt(date[1]), date[0]);
        gari.put(date[0], g);
        AdaugaDB.getInstance().insereazaObiecteDB(INSERT_GARI,
                (statement, gara) -> {
                    statement.setString(1, gara.getNume());
                    statement.setInt(2, gara.getNrLinii());
                }, g
        );
    }
    public static void getGariDB() throws SQLException, IOException {
        gari = new HashMap<>();
        Statement statement = databaseConfiguration.getDatabaseConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_GET_GARI);
        while (resultSet.next()) {
            Gara g_aux = new Gara(resultSet.getInt("nrLinii"), resultSet.getString("nume"));
            gari.put(resultSet.getString("nume"), g_aux);
        }
    }
    public static void editeaza() throws IOException, SQLException {
        afiseaza();
        System.out.printf("INTRODUCETI NUMELE GARII PE CARE DORITI SA O EDITATI");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String nume = cititor.readLine();
        Gara g = gari.get(nume);
        if(g == null){
            System.out.println("GARA NU A FOST GASITA, INCERCATI DIN NOU");
            editeaza();
        }
        else {
            System.out.println("INTRODUCETI NUMARUL DE LINII PE CARE DORITI SA IL AIBA GARA");
            int nouNrLinii = Integer.parseInt(cititor.readLine());
            g.setNrLinii(nouNrLinii);
            PreparedStatement statement= databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.gari SET nrLinii = ? WHERE nume = ?");
            statement.setInt(1, nouNrLinii);
            statement.setString(2, g.getNume());
            statement.executeUpdate();
        }
    }
    public static void sterge() throws IOException, SQLException {
        afiseaza();
        System.out.printf("INTRODUCETI NUMELE GARII PE CARE DORITI SA O STERGETI");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String nume = cititor.readLine();
        Gara g = gari.get(nume);
        if(g == null){
            System.out.println("GARA NU A FOST GASITA, INCERCATI DIN NOU");
            sterge();
        }
        else {
            gari.remove(nume);
            PreparedStatement statement = databaseConfiguration.getDatabaseConnection().prepareStatement("DELETE FROM proiectpaoj.rute_gari WHERE numeGara = ?");
            statement.setString(1, nume);
            statement.executeUpdate();
            statement = databaseConfiguration.getDatabaseConnection().prepareStatement("DELETE FROM proiectpaoj.gari WHERE nume = ?");
            statement.setString(1, nume);
            statement.executeUpdate();
            for(Ruta r : RuteRepo.getRute()){
                r.stergeDestintatie(g);
            }
            System.out.println("GARA A FOST STEARSA DIN BAZA DE DATE\n");
        }
    }

}
