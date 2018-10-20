package vecdef.org.uy.vecdefTEA;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class NextBusTEADTO implements Serializable {

    @JsonProperty("id_linea")
    private String idLinea;

    @JsonProperty("id_parada")
    private String idParada;

    @JsonProperty("id_bus")
    private Integer idBus;

    private LocationDTO location;

    public String getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(final String idLinea) {
        this.idLinea = idLinea;
    }

    public String getIdParada() {
        return idParada;
    }

    public void setIdParada(final String idParada) {
        this.idParada = idParada;
    }

    public Integer getIdBus() {
        return idBus;
    }

    public void setIdBus(final Integer idBus) {
        this.idBus = idBus;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(final LocationDTO location) {
        this.location = location;
    }
}
