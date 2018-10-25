package vecdef.org.uy.vecdefTEA.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vecdef.org.uy.vecdefTEA.entidades.Bus;
import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.dto.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.entidades.dto.TEAResponse;
import vecdef.org.uy.vecdefTEA.repository.BusRepository;
import vecdef.org.uy.vecdefTEA.repository.ParadaLineaRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;
import vecdef.org.uy.vecdefTEA.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PosicionService {

    private static final Logger LOG = LoggerFactory.getLogger(PosicionService.class);

    @Autowired
    private ParadaLineaRepository paradaRepository;

    @Autowired
    private SegmentoFisicoRepository segmentoFisicoRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private HistoricoBusService historicoBusService;

    public SegmentoFisico buscarSegmentoFiscoMasCerca(final BusPosicionDTO busPosicionDTO) {
        final List<ParadaLinea> paradasDeLinea = paradaRepository.findByLineaOrderByOrdinal(busPosicionDTO.getLinea());
        if (!CollectionUtils.isEmpty(paradasDeLinea)) {

            final ParadaLinea paradaMasCerca = Collections.min(paradasDeLinea, Comparator.comparing(parada -> Utils.distanciaEntrePuntos(parada.getParadaFisica(), busPosicionDTO)));

            final ParadaFisica paradaSiguiente = paradasDeLinea.stream().filter(siguiente -> siguiente.getOrdinal() > paradaMasCerca.getOrdinal())
                    .min(Comparator.comparing(ParadaLinea::getOrdinal)).map(ParadaLinea::getParadaFisica).orElse(null);


            final ParadaFisica paradaAnterior = paradasDeLinea.stream().filter(siguiente -> siguiente.getOrdinal() < paradaMasCerca.getOrdinal())
                    .max(Comparator.comparing(ParadaLinea::getOrdinal)).map(ParadaLinea::getParadaFisica).orElse(null);

            final ParadaFisica inicioSegmento;
            final ParadaFisica finSegmento;

            if (paradaSiguiente != null && paradaAnterior != null) {
                final double distanciaSegmentoAnterior = Utils.distanciaEntreSegmentoYPunto(paradaAnterior, paradaMasCerca.getParadaFisica(), busPosicionDTO);
                final double distanciaSegmentoSiguiente = Utils.distanciaEntreSegmentoYPunto(paradaMasCerca.getParadaFisica(), paradaSiguiente, busPosicionDTO);

                if (distanciaSegmentoAnterior <= distanciaSegmentoSiguiente) {
                    inicioSegmento = paradaAnterior;
                    finSegmento = paradaMasCerca.getParadaFisica();
                } else {
                    inicioSegmento = paradaMasCerca.getParadaFisica();
                    finSegmento = paradaSiguiente;
                }
            } else if (paradaAnterior != null) {
                inicioSegmento = paradaAnterior;
                finSegmento = paradaMasCerca.getParadaFisica();
            } else if (paradaSiguiente != null) {
                inicioSegmento = paradaMasCerca.getParadaFisica();
                finSegmento = paradaSiguiente;
            } else {
                inicioSegmento = null;
                finSegmento = null;
            }

            if (inicioSegmento != null && finSegmento != null) {
                return segmentoFisicoRepository.findById(SegmentoFisico.construirID(inicioSegmento, finSegmento)).orElse(null);
            } else {
                LOG.warn("No se encontro segmento para la linea " + busPosicionDTO.getLinea() + " - Posicion (x,y) = " + busPosicionDTO.getEjeX() + ", " + busPosicionDTO.getEjeY());
            }

        } else {
            LOG.warn("No se encontraron paradas para la linea " + busPosicionDTO.getLinea());
        }
        return null;
    }

    public TEAResponse calcularTEAAParada(final ParadaLinea paradaLinea) {
        final List<ParadaLinea> paradasPrevias = paradaRepository.findByLineaOrderByOrdinal(paradaLinea.getLinea())
                .stream().filter(p -> p.getOrdinal() <= paradaLinea.getOrdinal())
                .sorted((p1, p2) -> Long.compare(p2.getOrdinal(), p1.getOrdinal())).collect(Collectors.toList());
        final List<Bus> busesDeLaLinea = busRepository.findAllByLinea(paradaLinea.getLinea());

        final List<SegmentoFisico> segmentoFisicos = new ArrayList<>();

        Optional<Bus> estaElOmnibus = Optional.empty();
        for (int i = 0; (i < paradasPrevias.size() - 1) && !estaElOmnibus.isPresent(); i++) {
            final ParadaLinea finSegmento = paradasPrevias.get(i);
            final ParadaLinea inicioSegmento = paradasPrevias.get(i+1);
            final String idSegmento = SegmentoFisico.construirID(inicioSegmento.getParadaFisica(), finSegmento.getParadaFisica());
            estaElOmnibus = busesDeLaLinea.stream().filter(bus -> idSegmento.equals(bus.getSegmentoActual().getId())).findAny();
            segmentoFisicoRepository.findById(idSegmento).ifPresent(segmentoFisicos::add);
        }

        final TEAResponse teaResponse = new TEAResponse();
        estaElOmnibus.ifPresent(bus -> {
            teaResponse.setIdBus(bus.getId());
            teaResponse.setLatitud(bus.getLatitud());
            teaResponse.setLongitud(bus.getLongitud());
            teaResponse.setTea((long) segmentoFisicos.stream()
                    .mapToDouble(historicoBusService::obtenerTiempoEstimadoDeSegmento).sum());
        });

        teaResponse.setIdLinea(paradaLinea.getLinea());
        teaResponse.setIdParada(paradaLinea.getParadaFisica().getCodigoParada());

        return teaResponse;
    }
}
