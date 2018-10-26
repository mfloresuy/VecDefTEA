package vecdef.org.uy.vecdefTEA.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaFisicaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaLineaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.SegmentoFisicoBuilder;
import vecdef.org.uy.vecdefTEA.repository.ParadaFisicaRepository;
import vecdef.org.uy.vecdefTEA.repository.ParadaLineaRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;
import vecdef.org.uy.vecdefTEA.utils.Utils;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class CargarMatrizSegmentosConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CargarMatrizSegmentosConfig.class);

    private static String URL_TRAYECTOS = System.getenv("TRAYECTOS_URL");

    private final RestTemplate restTemplate;
    private final ParadaLineaRepository paradaLineaRepository;
    private final ParadaFisicaRepository paradaFisicaRepository;
    private final SegmentoFisicoRepository segmentoFisicoRepository;

    @Autowired
    public CargarMatrizSegmentosConfig(final RestTemplateBuilder restTemplateBuilder, final ParadaLineaRepository paradaLineaRepository,
                                       final ParadaFisicaRepository paradaFisicaRepository, final SegmentoFisicoRepository segmentoFisicoRepository) {
        this.restTemplate = restTemplateBuilder.setConnectTimeout(10000).setReadTimeout(10000).build();
        this.paradaLineaRepository = paradaLineaRepository;
        this.paradaFisicaRepository = paradaFisicaRepository;
        this.segmentoFisicoRepository = segmentoFisicoRepository;
    }

    @PostConstruct
    private void init() {
        final ResponseEntity<JsonNode> jsonTrayectos = restTemplate.getForEntity(URL_TRAYECTOS, JsonNode.class);
        if (HttpStatus.OK.equals(jsonTrayectos.getStatusCode()) && jsonTrayectos.getBody() != null) {
            final Stream<JsonNode> sTrayectos = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(jsonTrayectos.getBody().get("trayectos").elements(), Spliterator.ORDERED),
                    false);

            sTrayectos.filter(t -> StringUtils.isNotEmpty(t.get("codigoParada").asText())).forEach(t -> {
                final double[] coordenadas = Utils.aplanar(t.get("long").asDouble(), t.get("lat").asDouble());
                final ParadaFisica paradaFisica = ParadaFisicaBuilder.aParadaFisica().
                        withCodigoParada(t.get("codigoParada").asLong()).
                        withCalle(t.get("calle").textValue()).
                        withEsquina(t.get("esquina").textValue()).
                        withEjeX(coordenadas[0]).
                        withEjeY(coordenadas[1]).
                        build();
                paradaFisicaRepository.save(paradaFisica);

                final ParadaLinea paradaLinea = ParadaLineaBuilder.aParadaLinea().
                        withParadaFisica(paradaFisica).
                        withLinea(t.get("linea").asLong()).
                        withOrdinal(t.get("ordinal").asLong()).
                        build();
                paradaLineaRepository.save(paradaLinea);
            });

            paradaLineaRepository.findAllLineas().forEach(l -> {
                final Iterator<ParadaLinea> iterLinea = paradaLineaRepository.findByLineaOrderByOrdinal(l).iterator();
                ParadaFisica paradaInicio;
                ParadaFisica paradaFinal = iterLinea.next().getParadaFisica();
                do {
                    paradaInicio = paradaFinal;
                    paradaFinal = iterLinea.next().getParadaFisica();
                    final SegmentoFisico segmentoFisico = SegmentoFisicoBuilder.aSegmentoFisico().
                            withId(SegmentoFisico.construirID(paradaInicio, paradaFinal)).
                            withParadaInicial(paradaInicio).withParadaFinal(paradaFinal).build();
                    segmentoFisico.setLecturas(0);
                    segmentoFisicoRepository.save(segmentoFisico);
                } while (iterLinea.hasNext());
            });

            LOG.info("Trayectos iniciados");
        } else {
            LOG.info("No se encontraron trayectos");
        }
    }

}
