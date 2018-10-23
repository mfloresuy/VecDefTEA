package vecdef.org.uy.vecdefTEA.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vecdef.org.uy.vecdefTEA.entidades.Bus;
import vecdef.org.uy.vecdefTEA.entidades.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.TiempoBusEnSegmento;
import vecdef.org.uy.vecdefTEA.repository.BusRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;

import java.time.Duration;

@Service
public class HistoricoBusService {

    @Autowired
    private PosicionService posicionService;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private SegmentoFisicoRepository segmentoFisicoRepository;

    public void procesarPosicionBus(final BusPosicionDTO busPosicionDTO) {
        final SegmentoFisico segmentoActual = posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO);

        final Bus bus = busRepository.findById(busPosicionDTO.getIdBus()).orElseGet(() -> {
                final Bus nuevo = new Bus();
                nuevo.setId(busPosicionDTO.getIdBus());
                nuevo.setLinea(busPosicionDTO.getLinea());
                return nuevo;
        });

        bus.setLatitud(busPosicionDTO.getLatitud());
        bus.setLongitud(busPosicionDTO.getLongitud());

        if ((bus.getSegmentoActual() == null) || (bus.getSegmentoActual().getId().equals(segmentoActual.getId()))) {

            if (bus.getSegmentoActual() != null) {
                final TiempoBusEnSegmento tiempoBusEnSegmento = new TiempoBusEnSegmento();
                tiempoBusEnSegmento.setBus(bus);
                tiempoBusEnSegmento.setInicio(bus.getTimestampSegmento());
                tiempoBusEnSegmento.setFin((busPosicionDTO.getTimestamp()));
                tiempoBusEnSegmento.setDuracion(Duration.between(tiempoBusEnSegmento.getInicio(), tiempoBusEnSegmento.getFin()).getSeconds());
                segmentoActual.getHistorico().add(tiempoBusEnSegmento);
                segmentoFisicoRepository.save(segmentoActual);
            }

            bus.setTimestampSegmento(busPosicionDTO.getTimestamp());
            bus.setSegmentoActual(segmentoActual);

        }

        busRepository.save(bus);
    }
}
