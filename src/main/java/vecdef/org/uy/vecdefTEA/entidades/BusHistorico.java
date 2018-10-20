package vecdef.org.uy.vecdefTEA.entidades;

import java.math.BigDecimal;
import java.util.Date;

public class BusHistorico {

    private Long idBus;
    private String linea;
    private BigDecimal[] coordenadas;
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

    public BigDecimal[] getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(BigDecimal[] coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
