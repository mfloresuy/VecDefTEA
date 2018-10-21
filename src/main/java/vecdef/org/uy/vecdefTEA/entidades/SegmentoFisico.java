package vecdef.org.uy.vecdefTEA.entidades;

public class SegmentoFisico {

    //SI concatenamos el id de la parada inicial y el de la parada final lo encontramos de toque
    //tipo paradaInicial#paradaFinal
    private String id;

    public SegmentoFisico(final ParadaFisica paradaInicial, final ParadaFisica paradaFinal) {
        this.id = construirID(paradaInicial, paradaFinal);
        this.paradaInicial = paradaInicial;
        this.paradaFinal = paradaFinal;
    }

    private ParadaFisica paradaInicial;
    private ParadaFisica paradaFinal;

    public ParadaFisica getParadaInicial() {
        return paradaInicial;
    }

    public void setParadaInicial(final ParadaFisica paradaInicial) {
        this.paradaInicial = paradaInicial;
    }

    public ParadaFisica getParadaFinal() {
        return paradaFinal;
    }

    public void setParadaFinal(final ParadaFisica paradaFinal) {
        this.paradaFinal = paradaFinal;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public static String construirID(final ParadaFisica paradaInicial, final ParadaFisica paradaFinal) {
        return paradaFinal.getCodigoParada().toString() + "#" + paradaFinal.getCodigoParada().toString();
    }
}
