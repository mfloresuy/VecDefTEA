package vecdef.org.uy.vecdefTEA.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Parada {

    @Id
    private Long codigoParada;
    private Long linea;
    private Long ordinal;
    private String calle;
    private String esquina;
    private double ejeX;
    private double ejeY;

    public Long getCodigoParada() {
        return codigoParada;
    }

    public void setCodigoParada(final Long codigoParada) {
        this.codigoParada = codigoParada;
    }

    public Long getLinea() {
        return linea;
    }

    public void setLinea(final Long linea) {
        this.linea = linea;
    }

    public Long getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(final Long ordinal) {
        this.ordinal = ordinal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(final String calle) {
        this.calle = calle;
    }

    public String getEsquina() {
        return esquina;
    }

    public void setEsquina(final String esquina) {
        this.esquina = esquina;
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

//{"codigoParada":"4836","linea":"217","ordinal":"1","calle":"AV DRA MA L SALDUN DE RODRIGUEZ","esquina":"AV BOLIVIA","long":"-56.0833009323971","lat":"-34.8818168390103"}