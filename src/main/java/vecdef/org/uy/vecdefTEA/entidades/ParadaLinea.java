package vecdef.org.uy.vecdefTEA.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ParadaLinea {

    @Id
    private Long linea;
    private Long ordinal;

    @ManyToOne
    private ParadaFisica paradaFisica;

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

    public ParadaFisica getParadaFisica() {
        return paradaFisica;
    }

    public void setParadaFisica(final ParadaFisica paradaFisica) {
        this.paradaFisica = paradaFisica;
    }

    @Override
    public String toString() {
        return "ParadaLinea{" +
                "linea=" + linea +
                ", ordinal=" + ordinal +
                ", paradaFisica=" + paradaFisica +
                '}';
    }
}
