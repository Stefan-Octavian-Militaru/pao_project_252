package Repozitorii;

import Config.DatabaseConfiguration;
import Entitati.Locomotiva;
import Entitati.Mecanic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

import Entitati.Tren;
import Utile.AdaugaDB;
import Utile.CitesteDB;
import Utile.SortMecanici;
import Utile.Stare;

import static Utile.Constante.*;

public class MecaniciRepo {
    private static DatabaseConfiguration databaseConfiguration = null;

    public MecaniciRepo(DatabaseConfiguration dbc) {
        databaseConfiguration = dbc;
    }
    private static List<Mecanic> mecanici;
    public static List<Mecanic> getMecanici(){
        return mecanici;
    }

    public static void setMecanici(List<Mecanic> mecanici){
        MecaniciRepo.mecanici = mecanici;
    }

    public static void addMecanic(Mecanic mecanic){
        mecanici.add(mecanic);
    }

    public static Mecanic getMecanic(String nume){
        for (Mecanic op : mecanici) {
            if(op.getNume().equals(nume)){
                return op;
            }
        }
        return null;
    }
    public static void afiseaza(){
        for (Mecanic m : mecanici) {
            System.out.println(m);
        }
    }
    public static void adauga() throws IOException, SQLException {
        System.out.println("INTRODUCETI NUMELE, DATA NASTERII SI DATA ANGAJARII SEPARATE PRINTR-UN SINGUR SPATIU");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String[] date = cititor.readLine().split(" ");
        Mecanic m = new Mecanic(date[0] + " " + date[1], LocalDate.parse(date[2]), LocalDate.parse(date[3]));
        mecanici.add(m);
        AdaugaDB.getInstance().insereazaObiecteDB(INSERT_MECANICI,
                (statement, mec) ->
                {
                    statement.setString(1, mec.getNume());
                    statement.setDate(2, java.sql.Date.valueOf(mec.getDataNasterii()));
                    statement.setDate(3, java.sql.Date.valueOf(mec.getDataNasterii()));
                    statement.setBoolean(4, false);
                }, m
        );
    }
    public static void getMecaniciDB() throws SQLException, IOException {
        mecanici = CitesteDB.getInstance().citesteObiecteDB(
                QUERY_GET_MECANICI,
                rs -> {
                    Mecanic m = new Mecanic(rs.getString("nume"), LocalDate.parse(rs.getString("dataNasterii")), LocalDate.parse(rs.getString("dataAngajarii")));
                    m.setEsteFolosit(rs.getBoolean("esteFolosit"));
                    return m;
                }
        );
    }
    public static void editeaza() throws IOException, SQLException {
        afiseaza();
        System.out.println("INTRODUCETI NUMELE COMPLET AL MECANICULUI PE CARE DORITI SA IL MODIFICATI");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String nume = cititor.readLine();
        Mecanic m = getMecanic(nume);
        if(m == null){
            System.out.println("MECANICUL NU A FOST GASIT, INCERCATI DIN NOU!\n");
            editeaza();
        }
        else {
            System.out.println("INTRODUCETI DATA NASTERII SI DATA ANGAJARII SEPARATE PRINTR-UN SINGUR SPATIU");
            String[] date = cititor.readLine().split(" ");
            m.setDataNasterii(LocalDate.parse(date[0]));
            m.setDataAngajarii(LocalDate.parse(date[1]));
            PreparedStatement statement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.mecanici dataNasterii = ?, dataAngajarii = ? WHERE nume = ?");
            statement.setDate(1, java.sql.Date.valueOf(date[1]));
            statement.setDate(2, java.sql.Date.valueOf(date[2]));
            statement.setString(3, m.getNume());
            statement.executeUpdate();
        }
    }
    public static void sterge() throws IOException, SQLException {
        afiseaza();
        System.out.println("INTRODUCETI NUMELE COMPLET AL MECANICULUI PE CARE DORITI SA IL STERGETI");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String nume = cititor.readLine();
        Mecanic m = getMecanic(nume);
        if (m == null) {
            System.out.println("MECANICUL NU A FOST GASIT, INCERCATI DIN NOU!\n");
            sterge();
        } else {
            mecanici.remove(m);
            PreparedStatement statement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.trenuri SET mecanic = null WHERE mecanic = ?");
            statement.setString(1, m.getNume());
            statement.executeUpdate();
            statement = databaseConfiguration.getDatabaseConnection().prepareStatement("DELETE FROM proiectpaoj.mecanici WHERE nume = ?");
            statement.setString(1, m.getNume());
            statement.executeUpdate();
            for (Tren t : TrenuriRepo.getTrenuri()){
                if(t.getMecanic().equals(m))
                {
                    t.setMecanic(null);
                }
            }
            System.out.println("MECANICUL A FOST STERS DIN BAZA DE DATE\n");
        }
    }
}
