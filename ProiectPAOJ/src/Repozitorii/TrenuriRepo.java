package Repozitorii;

import Config.DatabaseConfiguration;
import Entitati.*;
import Exceptii.ExceptieGreutateVagoane;
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
import java.util.ArrayList;
import java.util.List;

import static Utile.Constante.*;

public class TrenuriRepo {
    private static DatabaseConfiguration databaseConfiguration = null;

    public TrenuriRepo(DatabaseConfiguration dbc) {
        databaseConfiguration = dbc;
    }
    private static List<Tren> trenuri;

    public static Tren getTren(int idTren) {
        for(Tren t: trenuri) {
            if(t.getIdTren() == idTren) {
                return t;
            }
        }
        return null;
    }
    public static Tren getTrenByIndex(int index){
        return trenuri.get(index);
    }
    public static List<Tren> getTrenuri() {
        return trenuri;
    }

    public static void formeazaTren(Operator operator, Locomotiva locomotiva, List<Vagon> vagoane, Mecanic mecanic) throws SQLException {
        if(locomotiva.getStare() == Stare.DEZAFECTATA){
            System.out.println("Locomotiva nu poate fi pusa in operatie!");
        }
        else{
            Statement statement = databaseConfiguration.getDatabaseConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_GET_TREN_ID);
            int maxId = 0;
            while(resultSet.next()) {
                maxId = resultSet.getInt(1) + 1;
            }
            try {
                Tren t = new Tren(maxId, locomotiva, vagoane, operator, mecanic);
                trenuri.add(t);
                AdaugaDB.getInstance().insereazaObiecteDB(INSERT_TRENURI,
                        (prepStatement, tren) -> {
                            prepStatement.setInt(1, tren.getIdTren());
                            prepStatement.setInt(2, tren.getLocomotiva().getIdLocomotiva());
                            prepStatement.setString(3, tren.getMecanic().getNume());
                            prepStatement.setString(4, tren.getOperator().getNume());
                            prepStatement.setBoolean(5, false);
                        }, t
                );
                PreparedStatement prepStatement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.locomotive SET esteFolosita = 1 WHERE idLocomotiva = ?");
                prepStatement.setInt(1, locomotiva.getIdLocomotiva());
                prepStatement.executeUpdate();
                prepStatement = databaseConfiguration.getDatabaseConnection().prepareStatement("UPDATE proiectpaoj.mecanici SET esteFolosit = 1 WHERE nume = ?");
                prepStatement.setString(1, mecanic.getNume());
                prepStatement.executeUpdate();
                for (Vagon v : vagoane) {
                    String insertCommand;
                    if (v.getTip() == 0)
                        insertCommand = "UPDATE proiectpaoj.vagoanecalatori SET esteFolosit = 1, tren = " + Integer.toString(t.getIdTren()) + "WHERE nume = ?";
                    else
                        insertCommand = "UPDATE proiectpaoj.vagoanemarfa SET esteFolosit = 1, tren = " + Integer.toString(t.getIdTren()) + "WHERE nume = ?";
                    prepStatement = databaseConfiguration.getDatabaseConnection().prepareStatement(insertCommand);
                    prepStatement.setInt(1, v.getIdVagon());
                    prepStatement.executeUpdate();
                }
            }
            catch (ExceptieGreutateVagoane e){
                throw e;
            }
        }
    }
    public static void afiseaza(){
        for (Tren t : trenuri) {
            System.out.println(t);
        }
    }
    public static List<Tren> getTrenuriDisponibile(){
        List<Tren> trenuriDisponibile = new ArrayList<>();
            for(Tren t : trenuri){
                if(!t.isEsteFolosit()){
                    trenuriDisponibile.add(t);
                }
            }
        return trenuriDisponibile;
    }
    public static void getTrenuriDB() throws SQLException, IOException {
        trenuri = CitesteDB.getInstance().citesteObiecteDB(
                QUERY_GET_TRENURI,
                rs -> {
                    int idTren = rs.getInt("idTren");
                    List<Vagon> vagoaneTren = CitesteDB.getInstance().citesteObiecteDB(
                        QUERY_GET_VCALATORI_TRENURI + idTren,
                            rs2 -> VagoaneRepo.getVagonById(rs2.getInt(1)));
                    vagoaneTren.addAll(CitesteDB.getInstance().citesteObiecteDB(
                            QUERY_GET_VMARFA_TRENURI + idTren,
                                     rs3 -> VagoaneRepo.getVagonById(rs3.getInt(1))));
                    Tren t_aux = new Tren(rs.getInt("idTren"), LocomotiveRepo.getLocomotiva(rs.getInt("locomotiva")), vagoaneTren, OperatoriRepo.getOperator(rs.getString("operator")), MecaniciRepo.getMecanic(rs.getString("mecanic")));
                    return t_aux;
                }
        );
    }
}
