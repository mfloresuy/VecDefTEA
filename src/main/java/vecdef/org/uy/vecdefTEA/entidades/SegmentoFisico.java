package vecdef.org.uy.vecdefTEA.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SegmentoFisico {

    @Id
    private String idSegmento;

    @ManyToOne
    private ParadaFisica paradaInicial;

    @ManyToOne
    private ParadaFisica paradaFinal;

    @OneToMany
    private List<TiempoBusEnSegmento> historico = new ArrayList<>();

    public SegmentoFisico(final ParadaFisica paradaInicial, final ParadaFisica paradaFinal) {
        this.id = construirID(paradaInicial, paradaFinal);
        this.paradaInicial = paradaInicial;
        this.paradaFinal = paradaFinal;
    }

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

    public List<TiempoBusEnSegmento> getHistorico() {
        return historico;
    }

    public void setHistorico(final List<TiempoBusEnSegmento> historico) {
        this.historico = historico;
    }

    public static String construirID(final ParadaFisica paradaInicial, final ParadaFisica paradaFinal) {
        return paradaInicial.getCodigoParada().toString() + "#" + paradaFinal.getCodigoParada().toString();
    }

}
