package vecdef.org.uy.vecdefTEA.servicios;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import vecdef.org.uy.vecdefTEA.entidades.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.builders.BusPosicionDTOBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaFisicaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaLineaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.SegmentoFisicoBuilder;
import vecdef.org.uy.vecdefTEA.repository.BusRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class HistoricoBusServiceTest {

    @InjectMocks
    private HistoricoBusService historicoBusService = new HistoricoBusService();

    @Mock
    private PosicionService posicionService;

    @Mock
    private BusRepository busRepository;

    @Test
    public void procesarPosicionBus() {

        final BusPosicionDTO busPosicionDTO = BusPosicionDTOBuilder.aBusPosicionDTO()
                .withIdBus(1L)
                .withEjeX(5)
                .withEjeY(9)
                .withLinea("linea")
                .withTimestamp(LocalDateTime.now())
                .build();

        final ParadaLinea parada2 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(0)
                        .withEjeY(1)
                        .withCodigoParada(2L)
                        .build())
                .withOrdinal(2L)
                .build();

        final ParadaLinea parada3 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(1)
                        .withEjeY(1)
                        .withCodigoParada(3L)
                        .build())
                .withOrdinal(3L)
                .build();

        final SegmentoFisico segmentoFisico = SegmentoFisicoBuilder.aSegmentoFisico()
                .withParadaInicial(parada2.getParadaFisica())
                .withParadaFinal(parada3.getParadaFisica())
                .build();


        Mockito.when(posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO)).thenReturn(segmentoFisico);

        Mockito.when(busRepository.findById(busPosicionDTO.getIdBus())).thenReturn(Optional.empty());

        historicoBusService.procesarPosicionBus(busPosicionDTO);


    }
}