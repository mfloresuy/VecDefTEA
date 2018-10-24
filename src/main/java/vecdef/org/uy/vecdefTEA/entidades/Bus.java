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

    private double ejeX;
    private double ejeY;

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

    public double getEjeX() {
        return ejeX;
    }

    public void setEjeX(final double ejeX) {
        this.ejeX = ejeX;
    }

    public double getEjeY() {
        return ejeY;
    }

    public void setEjeY(final double ejeY) {
        this.ejeY = ejeY;
    }

}
