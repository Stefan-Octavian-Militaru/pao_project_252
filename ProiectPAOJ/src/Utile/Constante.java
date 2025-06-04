package Utile;

public class Constante {
    public static final String FISIER_GARI = "src/Resurse/gari.txt";
    public static final String FISIER_LOCOMOTIVE = "src/Resurse/locomotive.txt";
    public static final String FISIER_VAGOANE = "src/Resurse/vagoane.txt";
    public static final String QUERY_GET_LOCOMOTIVE = "SELECT * FROM proiectpaoj.locomotive";
    public static final String QUERY_GET_GARI = "SELECT * FROM proiectpaoj.gari";
    public static final String QUERY_GET_VAGOANE_CALATORI = "SELECT * FROM proiectpaoj.vagoanecalatori";
    public static final String QUERY_GET_VAGOANE_MARFA = "SELECT * FROM proiectpaoj.vagoanemarfa";
    public static final String QUERY_GET_MECANICI = "SELECT * FROM proiectpaoj.mecanici";
    public static final String QUERY_GET_OPERATORI = "SELECT * FROM proiectpaoj.operatori";
    public static final String QUERY_GET_RUTE = "SELECT * FROM proiectpaoj.rute";
    public static final String INSERT_GARI = "INSERT INTO proiectpaoj.gari (nume, nrLinii) VALUES (?, ?)";
    public static final String INSERT_LOCOMOTIVE = "INSERT INTO proiectpaoj.locomotive (numeModel, idLocomotiva, capacitate, vitezaMedie, stare, esteFolosita) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_MECANICI = "INSERT INTO proiectpaoj.mecanici (nume, dataNasterii, dataAngajarii, esteFolosit) VALUES (?, ?, ?, ?)";
    public static final String INSERT_OPERATORI = "INSERT INTO proiectpaoj.operatori (nume, taraProvenienta) VALUES (?, ?)";
    public static final String INSERT_RUTE = "INSERT INTO proiectpaoj.rute (idRuta, oraPlecare, oraSosire) VALUES (?, ?, ?)";
    public static final String QUERY_GET_LOCOMOTIVA_ID = "SELECT max(idLocomotiva) FROM proiectpaoj.locomotive";
    public static final String INSERT_VAGOANE_CALATORI = "INSERT INTO proiectpaoj.vagoanecalatori(capacitate, locuriOcupate, greutate, esteFolosit) VALUES (?, ?, ?, ?)";
    public static final String INSERT_VAGOANE_MARFA = "INSERT INTO proiectpaoj.vagoanemarfa(greutate, esteFolosit, tipMarfa) VALUES (?, ?, ?)";
    public static final String QUERY_GET_RUTA_ID = "SELECT max(idRuta) FROM proiectpaoj.rute";
    public static final String QUERY_GET_GARI_FOR_RUTE = "SELECT numeGara, ora FROM rute_gari WHERE idRuta =";
    public static final String INSERT_TRENURI = "INSERT INTO proiectpaoj.trenuri (idTren, locomotiva, mecanic, operator, esteFolosit) VALUES (? , ? , ?, ?, ?)";
    public static final String QUERY_GET_TREN_ID = "SELECT max(idTren) FROM proiectpaoj.trenuri";
    public static final String QUERY_GET_TRENURI = "SELECT * FROM proiectpaoj.trenuri";
    public static final String QUERY_GET_VCALATORI_TRENURI = "SELECT idVagon from proiectpaoj.vagoanecalatori WHERE tren = ";
    public static final String QUERY_GET_VMARFA_TRENURI = "SELECT idVagon from proiectpaoj.vagoanemarfa WHERE tren = ";
}
