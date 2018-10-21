package vecdef.org.uy.vecdefTEA.entidades.builders;

import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;

public final class ParadaFisicaBuilder {
    private Long codigoParada;
    private String calle;
    private String esquina;
    private double ejeX;
    private double ejeY;

    private ParadaFisicaBuilder() {
    }

    public static ParadaFisicaBuilder aParadaFisica() {
        return new ParadaFisicaBuilder();
    }

    public ParadaFisicaBuilder withCodigoParada(Long codigoParada) {
        this.codigoParada = codigoParada;
        return this;
    }

    public ParadaFisicaBuilder withCalle(String calle) {
        this.calle = calle;
        return this;
    }

    public ParadaFisicaBuilder withEsquina(String esquina) {
        this.esquina = esquina;
        return this;
    }

    public ParadaFisicaBuilder withEjeX(double ejeX) {
        this.ejeX = ejeX;
        return this;
    }

    public ParadaFisicaBuilder withEjeY(double ejeY) {
        this.ejeY = ejeY;
        return this;
    }

    public ParadaFisica build() {
        ParadaFisica paradaFisica = new ParadaFisica();
        paradaFisica.setCodigoParada(codigoParada);
        paradaFisica.setCalle(calle);
        paradaFisica.setEsquina(esquina);
        paradaFisica.setEjeX(ejeX);
        paradaFisica.setEjeY(ejeY);
        return paradaFisica;
    }
}
