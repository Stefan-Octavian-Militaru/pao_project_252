package Entitati;

import Repozitorii.GariRepo;
import Utile.Stare;

import java.util.ArrayList;
import java.util.List;

public class Operator {
    private String nume;
    private String taraProvenienta;
    private List<Gara> gariPermise;
    private List<Tren> trenuri;

    public Operator(String nume, String taraProvenienta) {
        this.nume = nume;
        this.taraProvenienta = taraProvenienta;
        this.gariPermise = new ArrayList<>();
        this.trenuri = new ArrayList<>();
    }

    public void permiteGara(Gara g){
        gariPermise.add(g);
    }
    public void permiteToate(){
        gariPermise = GariRepo.getGariList();
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTaraProvenienta() {
        return taraProvenienta;
    }

    public void setTaraProvenienta(String taraProvenienta) {
        this.taraProvenienta = taraProvenienta;
    }

    public List<Gara> getGariPermise() {
        return gariPermise;
    }

    public void setGariPermise(List<Gara> gariPermise) {
        this.gariPermise = gariPermise;
    }
    public void listGariPermise(){
        for(Gara g : gariPermise){
            System.out.print(g.getNume() + " ");
        }
        System.out.println();
    }
    public List<Tren> getTrenuri(){
        return trenuri;
    }
    @Override
    public String toString() {
        return nume + " - " + taraProvenienta;
    }
}
