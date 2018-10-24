package vecdef.org.uy.vecdefTEA.entidades.builders;

import vecdef.org.uy.vecdefTEA.entidades.Bus;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;

import java.time.LocalDateTime;

public final class BusBuilder {
    private Long id;
    private Long linea;
    private SegmentoFisico segmentoActual;
    private LocalDateTime timestampSegmento;
    private double latitud;
    private double longitud;

    private BusBuilder() {
    }

    public static BusBuilder aBus() {
        return new BusBuilder();
    }

    public BusBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BusBuilder withLinea(Long linea) {
        this.linea = linea;
        return this;
    }

    public BusBuilder withSegmentoActual(SegmentoFisico segmentoActual) {
        this.segmentoActual = segmentoActual;
        return this;
    }

    public BusBuilder withTimestampSegmento(LocalDateTime timestampSegmento) {
        this.timestampSegmento = timestampSegmento;
        return this;
    }

    public BusBuilder withLatitud(double latitud) {
        this.latitud = latitud;
        return this;
    }

    public BusBuilder withLongitud(double longitud) {
        this.longitud = longitud;
        return this;
    }

    public Bus build() {
        Bus bus = new Bus();
        bus.setId(id);
        bus.setLinea(linea);
        bus.setSegmentoActual(segmentoActual);
        bus.setTimestampSegmento(timestampSegmento);
        bus.setLatitud(latitud);
        bus.setLongitud(longitud);
        return bus;
    }
}
