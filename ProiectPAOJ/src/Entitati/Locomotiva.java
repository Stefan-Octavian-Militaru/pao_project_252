package Entitati;

import Utile.Stare;
import Utile.UtileLocomotiva;

public class Locomotiva {
    private String numeModel;
    private int capacitate;
    private int vitezaMedie;
    private Stare stare;
    private boolean esteFolosita;
    private int idLocomotiva;

    public Locomotiva(String numeModel, int capacitate, int vitezaMedie, Stare stare) {
        this.numeModel = numeModel;
        this.capacitate = capacitate;
        this.vitezaMedie = vitezaMedie;
        this.stare = stare;
        this.esteFolosita = false;
        this.idLocomotiva = UtileLocomotiva.idCurent;
        UtileLocomotiva.idCurent += 1;
    }
    public Locomotiva(String numeModel, int capacitate, int vitezaMedie, Stare stare, int idLocomotiva) {
        this.numeModel = numeModel;
        this.capacitate = capacitate;
        this.vitezaMedie = vitezaMedie;
        this.stare = stare;
        this.esteFolosita = false;
        this.idLocomotiva = idLocomotiva;
    }

    public boolean isEsteFolosita() {
        return esteFolosita;
    }

    public void setEsteFolosita(boolean esteFolosita) {
        this.esteFolosita = esteFolosita;
    }

    public String getNumeModel() {
        return numeModel;
    }

    public void setNumeModel(String numeModel) {
        this.numeModel = numeModel;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }

    public int getVitezaMedie() {
        return vitezaMedie;
    }

    public void setVitezaMedie(int vitezaMedie) {
        this.vitezaMedie = vitezaMedie;
    }

    public Stare getStare() {
        return stare;
    }

    public void setStare(Stare stare) {
        this.stare = stare;
    }

    @Override
    public String toString() {
        return numeModel + " capacitate: " + capacitate + " kg viteza medie: " + vitezaMedie + " km/h stare: " + stare;
    }

    public int getIdLocomotiva() {
        return idLocomotiva;
    }
}
