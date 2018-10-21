package vecdef.org.uy.vecdefTEA.entidades;

public class ParadaLinea {

    private Long linea;
    private Long ordinal;

    private ParadaFisica paradaFisica;


    public Long getLinea() {
        return linea;
    }

    public void setLinea(Long linea) {
        this.linea = linea;
    }

    public Long getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Long ordinal) {
        this.ordinal = ordinal;
    }

    public ParadaFisica getParadaFisica() {
        return paradaFisica;
    }

    public void setParadaFisica(ParadaFisica paradaFisica) {
        this.paradaFisica = paradaFisica;
    }
}
