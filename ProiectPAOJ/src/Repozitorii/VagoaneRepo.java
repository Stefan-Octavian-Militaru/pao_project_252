package Repozitorii;

import Config.DatabaseConfiguration;
import Entitati.*;
import Utile.AdaugaDB;
import Utile.CitesteDB;
import Utile.UtileLocomotiva;

import javax.swing.plaf.metal.MetalDesktopIconUI;
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

public class VagoaneRepo {
    private static DatabaseConfiguration databaseConfiguration = null;

    public VagoaneRepo(DatabaseConfiguration dbc) {
        databaseConfiguration = dbc;
    }
    private static List<Vagon> vagoane;

    public static List<Vagon> getVagoane(){
        return vagoane;
    }

    public static void setVagoane(List<Vagon> vagoane){
        VagoaneRepo.vagoane = vagoane;
    }

    public static void addVagon(Vagon vagon){
        vagoane.add(vagon);
    }
    public static Vagon getVagon(int index){
        return vagoane.get(index);
    }
    public static void afiseaza(){
        for (Vagon v : vagoane) {
            System.out.println(v);
        }
    }
    public static void adauga() throws IOException, SQLException {
        System.out.println("INTRODUCETI GREUTATEA SI TIPUL DE MARFA SAU GREUTATEA, CAPACITATEA SI NUMARUL DE LOCURI OCUPATE (DACA DORITI VAGON MARFAR/DE CALATORI) SEPARATE PRINTR-UN SINGUR SPATIU");
        BufferedReader cititor = new BufferedReader(new InputStreamReader(System.in));
        String[] date = cititor.readLine().split(" ");
        Vagon v;
        if(date.length == 2)
            v = new VagonMarfa(date[1], Integer.parseInt(date[0]));
        else
            v = new VagonCalatori(Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(date[2]));
        vagoane.add(v);
        if(date.length == 2){
            AdaugaDB.getInstance().insereazaObiecteDB(INSERT_VAGOANE_MARFA,
                    (statement, vm) ->{
                        statement.setInt(1, vm.getGreutate());
                        statement.setString(2, date[1]);
                        statement.setBoolean(3, false);
                        statement.setInt(4, vm.getIdVagon());
                    }, v
            );
        }
        else{
            AdaugaDB.getInstance().insereazaObiecteDB(INSERT_VAGOANE_CALATORI,
                    (statement, vc) -> {
                        statement.setInt(1, Integer.parseInt(date[1]));
                        statement.setInt(2, Integer.parseInt(date[2]));
                        statement.setInt(3, Integer.parseInt(date[0]));
                        statement.setBoolean(4, false);
                        statement.setInt(5, vc.getIdVagon());
                    }, v
            );
        }

    }
    public static void getVagoaneDB() throws SQLException, IOException {
        vagoane = new ArrayList<>();
        List<Vagon> vagoaneCalatori = CitesteDB.getInstance().citesteObiecteDB(
                QUERY_GET_VAGOANE_CALATORI,
                rs -> {
                    VagonCalatori vc = new VagonCalatori(rs.getInt("capacitate"), rs.getInt("greutate"), rs.getInt("locuriOcupate"));
                    vc.setEsteFolosit(rs.getBoolean("esteFolosit"));
                    vc.setIdVagon(rs.getInt("idVagon"));
                    return vc;
                }
        );
        List<Vagon> vagoaneMarfa = CitesteDB.getInstance().citesteObiecteDB(
                QUERY_GET_VAGOANE_MARFA,
                rs -> {
                    VagonMarfa vm = new VagonMarfa(rs.getString("tipMarfa"), rs.getInt("greutate"));
                    vm.setEsteFolosit(rs.getBoolean("esteFolosit"));
                    vm.setIdVagon(rs.getInt("idVagon"));
                    return vm;
                }
        );
        vagoane.addAll(vagoaneCalatori);
        vagoane.addAll(vagoaneMarfa);
    }
    public static Vagon getVagonById(int id){
        for (Vagon v : vagoane) {
            if(v.getIdVagon() == id){
                return v;
            }
        }
        return null;
    }
}
