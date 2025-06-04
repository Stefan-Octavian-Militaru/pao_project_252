import Config.DatabaseConfiguration;
import Entitati.*;
import Repozitorii.*;
import Utile.Constante;
import Utile.Menu;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import Servicii.ServiciuRuta;
import Servicii.ServiciuClient;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        //functie care initializeaza cateva valori (place holder pana se vor implementa bazele de date)
        Utile.Initializari.initializeazaRepo();
        Utile.Initializari.initializeazaTot();
        List<Locomotiva> locomotive = LocomotiveRepo.getLocomotive();
        List<Vagon> vagoane = VagoaneRepo.getVagoane();
        List<Operator> operatori = OperatoriRepo.getOperatori();
        List<Mecanic> mecanici = MecaniciRepo.getMecanici();
        List<Ruta> rute = RuteRepo.getRute();
        //functia care implementeaza formarea trenurilor si verifica daca depasesc greutatea admisa
//        TrenuriRepo.formeazaTren(operatori.get(0), locomotive.get(7), List.of(new Vagon[]{vagoane.get(1), vagoane.get(3), vagoane.get(4)}), mecanici.get(1));
        Tren t1 = TrenuriRepo.getTrenByIndex(0);
        Ruta r1 = rute.get(0);
        Client c1 = new Client("Marius Adrian", "madrian@gmail.com", "sajfbokafs", "0773189900", LocalDate.parse("1997-09-14"));
        RuteRepo.adaugaTren(0, t1);
        //functia care programeaza ruta
        List<Integer> l1 = new ArrayList<>();
        l1.add(13);
        ServiciuRuta.adaugaRutaLaOrar(r1, l1);
        //functia care cauta rute dupa destinatii
        Ruta r2 = ServiciuClient.gasesteRuta("BucurestiNord", "Videle");
//        TrenuriRepo.formeazaTren(operatori.get(2), locomotive.get(8), new ArrayList<>(), mecanici.get(3));
        Tren t2 = TrenuriRepo.getTrenByIndex(1);
//        RuteRepo.adaugaTren(0, t2);
        //afisarea orarului in gara bucuresti nord
//        GariRepo.getGari().get("BucurestiNord").afiseazaOrar();
        Menu.arataOptiuni();
    }
}