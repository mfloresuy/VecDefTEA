package vecdef.org.uy.vecdefTEA.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SegmentoFisico {

    @Id
    private String idSegmento;

    @ManyToOne
    private ParadaFisica paradaInicial;

    @ManyToOne
    private ParadaFisica paradaFinal;

    private double eta;

    public ParadaFisica getParadaInicial() {
        return paradaInicial;
    }

    public void setParadaInicial(final ParadaFisica paradaInicial) {
        this.paradaInicial = paradaInicial;
    }

    public ParadaFisica getParadaFinal() {
        return paradaFinal;
    }

    public void setParadaFinal(final ParadaFisica paradaFinal) {
        this.paradaFinal = paradaFinal;
    }

    public String getId() {
        return idSegmento;
    }

    public void setId(final String id) {
        this.idSegmento = id;
    }

    public double getEta() {
        return eta;
    }

    public void setEta(final double eta) {
        this.eta = eta;
    }

    public static String construirID(final ParadaFisica paradaInicial, final ParadaFisica paradaFinal) {
        return paradaInicial.getCodigoParada().toString() + "#" + paradaFinal.getCodigoParada().toString();
    }

    @Override
    public String toString() {
        return "SegmentoFisico{" +
                "idSegmento='" + idSegmento + '\'' +
                ", paradaInicial=" + paradaInicial +
                ", paradaFinal=" + paradaFinal +
                '}';
    }

}
