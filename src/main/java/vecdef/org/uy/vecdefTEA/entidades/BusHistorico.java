package vecdef.org.uy.vecdefTEA.entidades;

import vecdef.org.uy.vecdefTEA.utils.IPosicionable;

public class BusHistorico implements IPosicionable {

    private Long idBus;
    private String linea;
    private double ejeX;
    private double ejeY;
    private String timestamp;

    public Long getIdBus() {
        return idBus;
    }

    public void setIdBus(Long idBus) {
        this.idBus = idBus;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
