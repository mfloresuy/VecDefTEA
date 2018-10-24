package vecdef.org.uy.vecdefTEA.entidades.dto;

import vecdef.org.uy.vecdefTEA.utils.IPosicionable;

import java.time.LocalDateTime;

public class BusPosicionDTO implements IPosicionable {

    private Long idBus;
    private Long linea;
    private double ejeX;
    private double ejeY;

    private LocalDateTime timestamp;

    public Long getIdBus() {
        return idBus;
    }

    public void setIdBus(Long idBus) {
        this.idBus = idBus;
    }

    public Long getLinea() {
        return linea;
    }

    public void setLinea(final Long linea) {
        this.linea = linea;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getEjeX() {
        return ejeX;
    }

    public void setEjeX(double ejeX) {
        this.ejeX = ejeX;
    }

    public double getEjeY() {
        return ejeY;
    }

    public void setEjeY(double ejeY) {
        this.ejeY = ejeY;
    }

}
