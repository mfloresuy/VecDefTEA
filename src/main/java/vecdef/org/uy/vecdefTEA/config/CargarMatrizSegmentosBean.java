package vecdef.org.uy.vecdefTEA.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaFisicaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaLineaBuilder;
import vecdef.org.uy.vecdefTEA.repository.ParadaRepository;
import vecdef.org.uy.vecdefTEA.utils.Utils;

import javax.annotation.PostConstruct;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class CargarMatrizSegmentosBean {

    private static final Logger LOG = LoggerFactory.getLogger(CargarMatrizSegmentosBean.class);
    private static final String URL_TRAYECTOS = "http://192.168.1.52/api/trayectosporlinea";

    private final RestTemplate restTemplate;
    private final ParadaRepository paradaRepository;

    @Autowired
    public CargarMatrizSegmentosBean(final ParadaRepository paradaRepository, final RestTemplateBuilder restTemplateBuilder) {
        this.paradaRepository = paradaRepository;
        this.restTemplate = restTemplateBuilder.setConnectTimeout(10000).setReadTimeout(10000).build();
    }

    @PostConstruct
    private void init() {
        final ResponseEntity<JsonNode> jsonTrayectos = restTemplate.getForEntity(URL_TRAYECTOS, JsonNode.class);
        if (HttpStatus.OK.equals(jsonTrayectos.getStatusCode()) && jsonTrayectos.getBody() != null) {
            final Stream<JsonNode> sTrayectos = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(jsonTrayectos.getBody().get("trayectos").elements(), Spliterator.ORDERED),
                    false);

            final ParadaLinea unaParada = sTrayectos.map(t -> {
                final double[] coordenadas = Utils.aplanar(t.get("long").asDouble(), t.get("lat").asDouble());
                final ParadaFisica paradaFisica = ParadaFisicaBuilder.aParadaFisica().
                        withCodigoParada(t.get("codigoParada").asLong()).
                        withCalle(t.get("calle").textValue()).
                        withEsquina(t.get("esquina").textValue()).
                        withEjeX(coordenadas[0]).
                        withEjeY(coordenadas[1]).
                        build();
                return ParadaLineaBuilder.aParadaLinea().
                        withParadaFisica(paradaFisica).
                        withLinea(t.get("linea").asLong()).
                        withOrdinal(t.get("ordinal").asLong()).
                        build();
            }).findAny().orElseThrow(RuntimeException::new);

            LOG.info("Un objeto: " + unaParada.toString());

            LOG.info("Trayectos iniciados");
        } else {
            LOG.info("No se encontraron trayectos");
        }
    }

}
