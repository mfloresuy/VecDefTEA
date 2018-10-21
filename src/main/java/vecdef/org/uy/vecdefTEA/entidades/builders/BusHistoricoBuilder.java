package vecdef.org.uy.vecdefTEA.entidades.builders;

import vecdef.org.uy.vecdefTEA.entidades.BusHistorico;

public final class BusHistoricoBuilder {
    private Long idBus;
    private String linea;
    private double ejeX;
    private double ejeY;
    private String timestamp;

    private BusHistoricoBuilder() {
    }

    public static BusHistoricoBuilder aBusHistorico() {
        return new BusHistoricoBuilder();
    }

    public BusHistoricoBuilder withIdBus(Long idBus) {
        this.idBus = idBus;
        return this;
    }

    public BusHistoricoBuilder withLinea(String linea) {
        this.linea = linea;
        return this;
    }

    public BusHistoricoBuilder withEjeX(double ejeX) {
        this.ejeX = ejeX;
        return this;
    }

    public BusHistoricoBuilder withEjeY(double ejeY) {
        this.ejeY = ejeY;
        return this;
    }

    public BusHistoricoBuilder withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public BusHistorico build() {
        BusHistorico busHistorico = new BusHistorico();
        busHistorico.setIdBus(idBus);
        busHistorico.setLinea(linea);
        busHistorico.setEjeX(ejeX);
        busHistorico.setEjeY(ejeY);
        busHistorico.setTimestamp(timestamp);
        return busHistorico;
    }
}
