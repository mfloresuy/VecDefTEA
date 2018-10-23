package vecdef.org.uy.vecdefTEA.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class TiempoBusEnSegmento {

    @Id
    private Long id;

    @ManyToOne
    private Bus bus;
    private long duracion;
    private LocalDateTime inicio;
    private LocalDateTime fin;

    public Bus getBus() {
        return bus;
    }

    public void setBus(final Bus bus) {
        this.bus = bus;
    }

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(final long duracion) {
        this.duracion = duracion;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(final LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(final LocalDateTime fin) {
        this.fin = fin;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}