package Entitati;

import Utile.UtileVagon;

public class VagonCalatori extends Vagon{
    private int capacitate;
    private int locuriOcupate;

    public VagonCalatori(int capacitate, int greutate, int locuriOcupate) {
        this.capacitate = capacitate;
        this.greutate = greutate;
        this.locuriOcupate = locuriOcupate;
        this.esteFolosit = false;
        this.idVagon = UtileVagon.idCurent;
        UtileVagon.idCurent += 1;
    }

    public int getGreutate(){
        return greutate + locuriOcupate * 80;
    }
    public int getTip() {return 0;}
    @Override
    public String toString() {
        return "CALATORI - " + capacitate + " locuri " + greutate + " kg greutate";
    }
}
