package Repozitorii;

import Config.DatabaseConfiguration;
import Entitati.*;
import Utile.AdaugaDB;
import Utile.CitesteDB;
import Utile.Stare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Utile.Constante.*;

public class OperatoriRepo {
    private static DatabaseConfiguration databaseConfiguration = null;

    public OperatoriRepo(DatabaseConfiguration dbc) {
        databaseConfiguration = dbc;
    }
    private static List<Operator> operatori;
    public static List<Operator> getOperatori(){
        return operatori;
    }

    public static void setOperatori(List<Operator> operatori){
        OperatoriRepo.operatori = operatori;
    }

    public static void addOperator(Operator operator){
        operatori.add(operator);
    }

    public static Operator getOperator(String nume){
        for (Operator op : operatori) {
            if(op.getNume().equals(nume)){
                return op;
            }
        }
        return null;
    }
    public static void afiseaza(){
        for (Operator op : operatori) {
            System.out.println(op + " are permisiunea de a opera trenuri in garile");
            op.listGariPermise();
        }
    }
    public static void adauga() throws IOException, SQLException {
        System.out.println("INTRODUCETI NUMELE SI TARA DE PROVENIENTA SEPARATE PRINTR-UN SINGUR SPATIU");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String[] date = cititor.readLine().split(" ");
        Operator op = new Operator(date[0], date[1]);
        operatori.add(op);
        AdaugaDB.getInstance().insereazaObiecteDB(INSERT_OPERATORI,
                (statement, oper) ->
                {
                    statement.setString(1, oper.getNume());
                    statement.setString(2, oper.getTaraProvenienta());

                }, op
        );
    }
    public static void getOperatoriDB() throws SQLException, IOException {
        operatori = CitesteDB.getInstance().citesteObiecteDB(
                QUERY_GET_OPERATORI,
                rs -> {
                    Operator op = new Operator(rs.getString(1), rs.getString(2));
                    return op;
                }
        );
        operatori.get(0).permiteToate();
        operatori.get(1).permiteToate();
        operatori.get(2).permiteGara(GariRepo.getGara("BucurestiNord"));
        operatori.get(2).permiteGara(GariRepo.getGara("RosioriNord"));
        operatori.get(2).permiteGara(GariRepo.getGara("Craiova"));
        operatori.get(2).permiteGara(GariRepo.getGara("Timisoara"));
    }
    public static void editeaza() throws IOException, SQLException {
        afiseaza();
        System.out.println("INTRODUCETI NUMELE OPERATORULUI PE CARE DORITI SA IL MODIFICATI\n");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String nume = cititor.readLine();
        Operator op = getOperator(nume);
        if(op == null){
            System.out.println("OPERATORUL NU A FOST GASIT, INCERCATI DIN NOU\n");
            editeaza();
        }
        else {
            System.out.println("INTRODUCETI TARA DE PROVENIENTA");
            String nouaTara = cititor.readLine();
            op.setTaraProvenienta(nouaTara);
            PreparedStatement statement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.operatori SET taraProvenienta = ? WHERE nume = ?");
            statement.setString(1, op.getTaraProvenienta());
            statement.setString(2, op.getNume());
            statement.executeUpdate();
        }
    }
    public static void sterge() throws IOException, SQLException {
        afiseaza();
        System.out.println("INTRODUCETI NUMELE OPERATORULUI PE CARE DORITI SA IL STERGETI\n");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String nume = cititor.readLine();
        Operator op = getOperator(nume);
        if (op == null) {
            System.out.println("OPERATORUL NU A FOST GASIT, INCERCATI DIN NOU\n");
            sterge();
        }
        else {
            operatori.remove(op);
            PreparedStatement statement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.trenuri SET operator = null WHERE operator = ?");
            statement.setString(1, op.getNume());
            statement.executeUpdate();
            statement = databaseConfiguration.getDatabaseConnection().prepareStatement("DELETE FROM proiectpaoj.operatori WHERE nume = ?");
            statement.setString(1, op.getNume());
            statement.executeUpdate();
            for (Tren t : TrenuriRepo.getTrenuri()){
                if(t.getOperator().equals(op))
                {
                    t.setOperator(null);
                }
            }
            System.out.println("OPERATORUL A FOST STERS DIN BAZA DE DATE\n");

        }
    }
}
