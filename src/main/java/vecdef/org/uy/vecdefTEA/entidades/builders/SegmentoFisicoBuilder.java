package vecdef.org.uy.vecdefTEA.entidades.builders;

import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;

public final class SegmentoFisicoBuilder {
    //SI concatenamos el id de la parada inicial y el de la parada final lo encontramos de toque
    //tipo paradaInicial#paradaFinal
    private String id;
    private ParadaFisica paradaInicial;
    private ParadaFisica paradaFinal;

    private SegmentoFisicoBuilder() {
    }

    public static SegmentoFisicoBuilder aSegmentoFisico() {
        return new SegmentoFisicoBuilder();
    }

    public SegmentoFisicoBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public SegmentoFisicoBuilder withParadaInicial(ParadaFisica paradaInicial) {
        this.paradaInicial = paradaInicial;
        return this;
    }

    public SegmentoFisicoBuilder withParadaFinal(ParadaFisica paradaFinal) {
        this.paradaFinal = paradaFinal;
        return this;
    }

    public SegmentoFisico build() {
        final SegmentoFisico segmentoFisico = new SegmentoFisico();
        segmentoFisico.setParadaInicial(paradaInicial);
        segmentoFisico.setParadaFinal(paradaFinal);
        segmentoFisico.setId(id);
        return segmentoFisico;
    }
}
