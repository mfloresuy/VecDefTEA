package vecdef.org.uy.vecdefTEA.entidades.dto;

import vecdef.org.uy.vecdefTEA.utils.IPosicionable;

import java.time.LocalDateTime;

public class BusPosicionDTO implements IPosicionable {

    private Long idBus;
    private Long linea;
    private double ejeX;
    private double ejeY;
    private double latitud;
    private double longitud;

    private LocalDateTime timestamp;

    public Long getIdBus() {
        return idBus;
    }

    public void setIdBus(final Long idBus) {
        this.idBus = idBus;
    }

    public Long getLinea() {
        return linea;
    }

    public void setLinea(final Long linea) {
        this.linea = linea;
    }

    @Override
    public double getEjeX() {
        return ejeX;
    }

    public void setEjeX(final double ejeX) {
        this.ejeX = ejeX;
    }

    @Override
    public double getEjeY() {
        return ejeY;
    }

    public void setEjeY(final double ejeY) {
        this.ejeY = ejeY;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BusPosicionDTO{" +
                "idBus=" + idBus +
                ", linea=" + linea +
                ", ejeX=" + ejeX +
                ", ejeY=" + ejeY +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", timestamp=" + timestamp +
                '}';
    }

}
