package vecdef.org.uy.vecdefTEA.entidades.dto;

public class TEAResponseDTO {

    private Long idLinea;
    private Long idParada;
    private Long idBus;

    private LocationDTO location;

    private long tea;
    private long teaP;

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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(final LocationDTO location) {
        this.location = location;
    }

    public long getTea() {
        return tea;
    }

    public void setTea(final long tea) {
        this.tea = tea;
    }

    public long getTeaP() {
        return teaP;
    }

    public void setTeaP(final long teaP) {
        this.teaP = teaP;
    }

}

