package vecdef.org.uy.vecdefTEA.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Bus {

    @Id
    private Long id;
    private Long linea;

    @ManyToOne
    private SegmentoFisico segmentoActual;

    private LocalDateTime timestampSegmento;

    private double latitud;
    private double longitud;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getLinea() {
        return linea;
    }

    public void setLinea(final Long linea) {
        this.linea = linea;
    }

    public SegmentoFisico getSegmentoActual() {
        return segmentoActual;
    }

    public void setSegmentoActual(final SegmentoFisico segmentoActual) {
        this.segmentoActual = segmentoActual;
    }

    public LocalDateTime getTimestampSegmento() {
        return timestampSegmento;
    }

    public void setTimestampSegmento(final LocalDateTime timestampSegmento) {
        this.timestampSegmento = timestampSegmento;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(final double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(final double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", linea=" + linea +
                ", segmentoActual=" + segmentoActual +
                ", timestampSegmento=" + timestampSegmento +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }

}
