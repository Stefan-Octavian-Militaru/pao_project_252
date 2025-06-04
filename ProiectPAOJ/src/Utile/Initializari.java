package Utile;

import Config.DatabaseConfiguration;
import Repozitorii.*;

import java.io.IOException;
import java.sql.SQLException;

public class Initializari {
    public static void initializeazaTot() throws SQLException, IOException {
        GariRepo.getGariDB();
        LocomotiveRepo.getLocomotiveDB();
        VagoaneRepo.getVagoaneDB();
        MecaniciRepo.getMecaniciDB();
        OperatoriRepo.getOperatoriDB();
        RuteRepo.getRuteDB();
        TrenuriRepo.getTrenuriDB();
    }
    public static void initializeazaRepo() throws SQLException, IOException {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        GariRepo gariRepo = new GariRepo(databaseConfiguration);
        LocomotiveRepo locomotiveRepo = new LocomotiveRepo(databaseConfiguration);
        VagoaneRepo vagoaneRepo = new VagoaneRepo(databaseConfiguration);
        MecaniciRepo mecaniciRepo = new MecaniciRepo(databaseConfiguration);
        OperatoriRepo operatoriRepo = new OperatoriRepo(databaseConfiguration);
        RuteRepo ruteRepo = new RuteRepo(databaseConfiguration);
        TrenuriRepo trenuriRepo = new TrenuriRepo(databaseConfiguration);

    }
}
