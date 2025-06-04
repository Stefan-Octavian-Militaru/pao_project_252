package Entitati;

import Utile.UtileVagon;

public class VagonMarfa extends Vagon {
    private String tipMarfa;
    public VagonMarfa(String tipMarfa, int greutate) {
        this.tipMarfa = tipMarfa;
        this.greutate = greutate;
        this.esteFolosit = false;
        this.idVagon = UtileVagon.idCurent;
        UtileVagon.idCurent += 1;
    }

    public int getGreutate(){
        return greutate;
    }
    public int getTip() {return 1;}
    @Override
    public String toString() {
        return "MARFA - " + greutate + " kg greutate " + tipMarfa ;
    }
}
