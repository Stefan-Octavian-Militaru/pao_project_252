package Repozitorii;

import Config.DatabaseConfiguration;
import Entitati.Gara;
import Entitati.Locomotiva;
import Entitati.Tren;
import Utile.AdaugaDB;
import Utile.CitesteDB;
import Utile.Stare;
import Utile.UtileLocomotiva;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static Utile.Constante.*;


public class LocomotiveRepo {
    private static DatabaseConfiguration databaseConfiguration = null;

    public LocomotiveRepo(DatabaseConfiguration dbc) {
        databaseConfiguration = dbc;
    }
    private static List<Locomotiva> locomotive;

    public static List<Locomotiva> getLocomotive() throws SQLException {
        return locomotive;
    }

    public static void setLocomotive(List<Locomotiva> locomotive){
        LocomotiveRepo.locomotive = locomotive;
    }

    public static void addLocomotiva(Locomotiva locomotiva){
        locomotive.add(locomotiva);
    }
    public static Locomotiva getLocomotiva(int index){
        for(Locomotiva locomotiva : locomotive){
            if(locomotiva.getIdLocomotiva() == index){
                return locomotiva;
            }
        }
        return null;
    }
    public static Locomotiva getLocomotivaByIndex(int index) {
        return locomotive.get(index);
    }
    public static void afiseaza(){
        int contor = 1;
        for (Locomotiva locomotiva : locomotive) {
            System.out.println(locomotiva);
        }
    }
    public static void adauga() throws IOException, SQLException {
        System.out.println("INTRODUCETI NUMELE, CAPACITATEA SI VITEZA MEDIE SEPARATE PRINTR-UN SINGUR SPATIU");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String[] date = cititor.readLine().split(" ");
        Locomotiva l = new Locomotiva(date[0], Integer.parseInt(date[1]), Integer.parseInt(date[2]), Stare.NOUA);
        locomotive.add(l);
        AdaugaDB.getInstance().insereazaObiecteDB(INSERT_LOCOMOTIVE,
                (prepStatement, loc) ->{
                    prepStatement.setString(1, loc.getNumeModel());
                    prepStatement.setInt(2, loc.getIdLocomotiva());
                    prepStatement.setInt(3, loc.getCapacitate());
                    prepStatement.setInt(4, loc.getVitezaMedie());
                    prepStatement.setString(5, "NOUA");
                    prepStatement.setBoolean(6, false);
                },l
        );
    }
    public static void getLocomotiveDB() throws SQLException, IOException {
        locomotive = CitesteDB.getInstance().citesteObiecteDB(
                QUERY_GET_LOCOMOTIVE,
                rs -> {
                    Locomotiva l = new Locomotiva(rs.getString("numeModel"), rs.getInt("capacitate"), rs.getInt("vitezaMedie"), Stare.valueOf(rs.getString("stare")), rs.getInt("idLocomotiva"));
                    l.setEsteFolosita(rs.getBoolean("esteFolosita"));
                    return l;
                }
        );
        Statement statement = databaseConfiguration.getDatabaseConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY_GET_LOCOMOTIVA_ID);
        while (resultSet.next()) {
            UtileLocomotiva.seedIdCurent(resultSet.getInt(1) + 1);
        }
        statement.close();
    }
    public static void editeaza() throws IOException, SQLException {
        afiseaza();
        System.out.println("SELECTATI UNA DINTRE LOCOMOTIVE PRIN NUMARUL DIN STANGA SA\n");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        int index = Integer.parseInt(cititor.readLine());
        Locomotiva l = locomotive.get(index);
        if (l == null) {
            System.out.println("LOCOMOTIVA NU A FOST GASITA, INCERCATI DIN NOU!\n");
            editeaza();
        } else {
            System.out.println("INTRODUCETI NUMELE, CAPACITATEA SI VITEZA MEDIE SEPARATE PRINTR-UN SINGUR SPATIU\n");
            String[] date = cititor.readLine().split(" ");
            l.setNumeModel(date[0]);
            l.setCapacitate(Integer.parseInt(date[1]));
            l.setVitezaMedie(Integer.parseInt(date[2]));
            PreparedStatement prepStatement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.locomotive SET numeModel = ?, capacitate = ?, vitezaMedie = ? WHERE idLocomotiva =?");
            prepStatement.setString(1, date[0]);
            prepStatement.setInt(2, Integer.parseInt(date[1]));
            prepStatement.setInt(3, Integer.parseInt(date[2]));
            prepStatement.setInt(4, l.getIdLocomotiva());
            prepStatement.executeUpdate();
        }
    }
    public static void sterge() throws IOException, SQLException {
        afiseaza();
        System.out.println("SELECTATI UNA DINTRE LOCOMOTIVE PRIN NUMARUL DIN STANGA SA\n");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        int index = Integer.parseInt(cititor.readLine());
        Locomotiva l = locomotive.get(index);
        if (l == null) {
            System.out.println("LOCOMOTIVA NU A FOST GASITA, INCERCATI DIN NOU!\n");
            sterge();
        }
        else {
            locomotive.remove(l);
            PreparedStatement statement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.trenuri SET locomotiva = null WHERE locomotiva = ?");
            statement.setInt(1, l.getIdLocomotiva());
            statement.executeUpdate();
            statement = databaseConfiguration.getDatabaseConnection().prepareStatement("DELETE FROM proiectpaoj.locomotive WHERE idLocomotiva = ?");
            statement.setInt(1, l.getIdLocomotiva());
            statement.executeUpdate();
            for (Tren t : TrenuriRepo.getTrenuri()){
                if(t.getLocomotiva().equals(l))
                {
                    t.setLocomotiva(null);
                }
            }
            System.out.println("LOCOMOTIVA A FOST STEARSA DIN BAZA DE DATE\n");
        }
    }
}
