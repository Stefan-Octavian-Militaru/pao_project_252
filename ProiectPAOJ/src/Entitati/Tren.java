package Entitati;

import Exceptii.ExceptieGreutateVagoane;
import Utile.ScrieAudit;

import java.time.LocalDateTime;
import java.util.List;

public class Tren {
    private Locomotiva locomotiva;
    private List<Vagon> vagoane;
    private Operator operator;
    private Mecanic mecanic;
    private boolean esteFolosit = false;
    private int idTren;

    public Tren(int idTren, Locomotiva locomotiva, List<Vagon> vagoane, Operator operator, Mecanic mecanic) {
        int greutateTotala = 0;
        for (Vagon v : vagoane) {
            greutateTotala += v.getGreutate();
        }
        if(greutateTotala <= locomotiva.getCapacitate())
            this.vagoane = vagoane;
        else {
            throw new ExceptieGreutateVagoane();
        }
        this.locomotiva = locomotiva;
        this.operator = operator;
        this.mecanic = mecanic;
        locomotiva.setEsteFolosita(true);
        vagoane.forEach(v -> v.setEsteFolosit(true));
        mecanic.setEsteFolosit(true);
        this.esteFolosit = false;
        ScrieAudit.scrieFisier("format tren", LocalDateTime.now());
    }

    public boolean isEsteFolosit() {
        return esteFolosit;
    }

    public void setEsteFolosit(boolean esteFolosit) {
        this.esteFolosit = esteFolosit;
    }

    public Locomotiva getLocomotiva() {
        return locomotiva;
    }

    public void setLocomotiva(Locomotiva locomotiva) {
        this.locomotiva = locomotiva;
    }

    public List<Vagon> getVagoane() {
        return vagoane;
    }

    public void setVagoane(List<Vagon> vagoane) {
        this.vagoane = vagoane;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Mecanic getMecanic() {
        return mecanic;
    }

    public void setMecanic(Mecanic mecanic) {
        this.mecanic = mecanic;
    }

    public int getIdTren() {
        return idTren;
    }

    @Override
    public String toString() {
        return "Tren{" +
                "locomotiva=" + locomotiva +
                ", Vagoane=" + vagoane +
                ", operator=" + operator +
                ", mecanic=" + mecanic +
                ", id =" + idTren +
                '}';
    }
}
