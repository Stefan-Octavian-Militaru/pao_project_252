package Exceptii;

public class ExceptieGreutateVagoane extends RuntimeException {
    public ExceptieGreutateVagoane() {
        super("TRENUL NU A PUTUT FI FORMAT, VAGOANELE DEPASESC GREUTATEA ADMISA DE LOCOMOTIVA");
    }
}
