package vecdef.org.uy.vecdefTEA.entidades.dto;

public class TEAResponse {

    private Long idLinea;
    private Long idParada;
    private Long idBus;

    private double latitud;
    private double longitud;

    private long tea;

    public Long getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(final Long idLinea) {
        this.idLinea = idLinea;
    }

    public Long getIdParada() {
        return idParada;
    }

    public void setIdParada(final Long idParada) {
        this.idParada = idParada;
    }

    public Long getIdBus() {
        return idBus;
    }

    public void setIdBus(final Long idBus) {
        this.idBus = idBus;
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

    public long getTea() {
        return tea;
    }

    public void setTea(final long tea) {
        this.tea = tea;
    }
}
