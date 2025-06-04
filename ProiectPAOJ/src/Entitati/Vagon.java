package Entitati;

public abstract class Vagon {
    protected int idVagon;
    protected boolean esteFolosit;
    protected int greutate;
    public abstract int getGreutate();
    public abstract int getTip();
    public boolean isEsteFolosit() {
        return esteFolosit;
    }

    public void setEsteFolosit(boolean esteFolosit) {
        this.esteFolosit = esteFolosit;
    }

    public int getIdVagon() {
        return idVagon;
    }

    public void setIdVagon(int idVagon) {
        this.idVagon = idVagon;
    }

}
