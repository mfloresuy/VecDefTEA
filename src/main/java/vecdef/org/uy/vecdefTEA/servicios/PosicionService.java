package vecdef.org.uy.vecdefTEA.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.dto.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.repository.ParadaLineaRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;
import vecdef.org.uy.vecdefTEA.utils.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PosicionService {

    private static final Logger LOG = LoggerFactory.getLogger(PosicionService.class);

    @Autowired
    private ParadaLineaRepository paradaRepository;

    @Autowired
    private SegmentoFisicoRepository segmentoFisicoRepository;

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
                LOG.warn("No se encontro segmento para la linea " + busPosicionDTO.getLinea());
            }

        } else {
            LOG.warn("No se encontro segmento para la linea " + busPosicionDTO.getLinea());
        }
        return null;
    }

}
