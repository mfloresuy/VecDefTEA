package vecdef.org.uy.vecdefTEA.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vecdef.org.uy.vecdefTEA.entidades.Bus;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.TiempoBusEnSegmento;
import vecdef.org.uy.vecdefTEA.entidades.dto.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.repository.BusRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;
import vecdef.org.uy.vecdefTEA.repository.TiempoBusEnSegmentoRepository;

import java.time.Duration;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

@Service
public class HistoricoBusService {

    private static final Logger LOG = LoggerFactory.getLogger(HistoricoBusService.class);

    @Autowired
    private PosicionService posicionService;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private SegmentoFisicoRepository segmentoFisicoRepository;

    @Autowired
    private TiempoBusEnSegmentoRepository tiempoBusEnSegmentoRepository;

    public void procesarPosicionBus(final BusPosicionDTO busPosicionDTO) {
        final SegmentoFisico segmentoActual = posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO);
        if (segmentoActual == null) {
            LOG.error("No se encuentran segmentos para el bus: " + busPosicionDTO);
        } else {
            final Bus bus = busRepository.findById(busPosicionDTO.getIdBus()).orElseGet(() -> {
                final Bus nuevo = new Bus();
                nuevo.setId(busPosicionDTO.getIdBus());
                nuevo.setLinea(busPosicionDTO.getLinea());
                return nuevo;
            });

            bus.setLatitud(busPosicionDTO.getLatitud());
            bus.setLongitud(busPosicionDTO.getLongitud());

            if (bus.getSegmentoActual() == null || !bus.getSegmentoActual().getId().equals(segmentoActual.getId())) {

                if (bus.getSegmentoActual() != null) {
                    final TiempoBusEnSegmento tiempoBusEnSegmento = new TiempoBusEnSegmento();
                    tiempoBusEnSegmento.setBus(bus);
                    tiempoBusEnSegmento.setInicio(bus.getTimestampSegmento());
                    tiempoBusEnSegmento.setFin((busPosicionDTO.getTimestamp()));
                    tiempoBusEnSegmento.setDuracion(Duration.between(tiempoBusEnSegmento.getInicio(), tiempoBusEnSegmento.getFin()).getSeconds());
                    tiempoBusEnSegmento.setSegmentoFisico(segmentoActual);
                    tiempoBusEnSegmentoRepository.save(tiempoBusEnSegmento);
                    segmentoActual.setEta(obtenerTiempoEstimadoDeSegmento(segmentoActual));
                    segmentoFisicoRepository.save(segmentoActual);
                }

                bus.setTimestampSegmento(busPosicionDTO.getTimestamp());
                bus.setSegmentoActual(segmentoActual);

            }

            LOG.info("Guardando: " + bus);
            busRepository.save(bus);
        }
    }

    public double obtenerTiempoEstimadoDeSegmento(final SegmentoFisico segmentoFisico) {
        final List<TiempoBusEnSegmento> tiempos = tiempoBusEnSegmentoRepository.findBySegmentoFisico(segmentoFisico);
        return tiempos.stream().mapToDouble(TiempoBusEnSegmento::getDuracion).average().orElse(Double.MAX_VALUE);
    }
}
