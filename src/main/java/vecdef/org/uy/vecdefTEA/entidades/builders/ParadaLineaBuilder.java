package vecdef.org.uy.vecdefTEA.entidades.builders;

import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;

public final class ParadaLineaBuilder {
    private Long linea;
    private Long ordinal;
    private ParadaFisica paradaFisica;

    private ParadaLineaBuilder() {
    }

    public static ParadaLineaBuilder aParadaLinea() {
        return new ParadaLineaBuilder();
    }

    public ParadaLineaBuilder withLinea(Long linea) {
        this.linea = linea;
        return this;
    }

    public ParadaLineaBuilder withOrdinal(Long ordinal) {
        this.ordinal = ordinal;
        return this;
    }

    public ParadaLineaBuilder withParadaFisica(ParadaFisica paradaFisica) {
        this.paradaFisica = paradaFisica;
        return this;
    }

    public ParadaLinea build() {
        ParadaLinea paradaLinea = new ParadaLinea();
        paradaLinea.setLinea(linea);
        paradaLinea.setOrdinal(ordinal);
        paradaLinea.setParadaFisica(paradaFisica);
        return paradaLinea;
    }
}
