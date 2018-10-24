package vecdef.org.uy.vecdefTEA.servicios;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import vecdef.org.uy.vecdefTEA.entidades.Bus;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.TiempoBusEnSegmento;
import vecdef.org.uy.vecdefTEA.entidades.builders.BusBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaFisicaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaLineaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.SegmentoFisicoBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.dto.BusPosicionDTOBuilder;
import vecdef.org.uy.vecdefTEA.entidades.dto.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.repository.BusRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;
import vecdef.org.uy.vecdefTEA.repository.TiempoBusEnSegmentoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class HistoricoBusServiceTest {

    @InjectMocks
    private HistoricoBusService historicoBusService = new HistoricoBusService();

    @Mock
    private PosicionService posicionService;

    @Spy
    private BusRepository busRepository;

    @Spy
    private TiempoBusEnSegmentoRepository tiempoBusEnSegmentoRepository;

    @Test
    public void procesarPosicionBusNuevo() {

        final BusPosicionDTO busPosicionDTO = BusPosicionDTOBuilder.aBusPosicionDTO()
                .withIdBus(1L)
                .withEjeX(5)
                .withEjeY(9)
                .withLinea(1234L)
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

        Mockito.when(busRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        historicoBusService.procesarPosicionBus(busPosicionDTO);

        final ArgumentCaptor<Bus> busPosicionCaptor = ArgumentCaptor.forClass(Bus.class);


        Mockito.verify(busRepository, Mockito.times(2)).save(busPosicionCaptor.capture());
        final Bus bus = busPosicionCaptor.getValue();

        Assert.assertEquals(bus.getLatitud(), busPosicionDTO.getLatitud(), 0.00001);
        Assert.assertEquals(bus.getLongitud(), busPosicionDTO.getLongitud(), 0.00001);
        Assert.assertSame(bus.getSegmentoActual(), segmentoFisico);
        Assert.assertEquals(bus.getTimestampSegmento(), busPosicionDTO.getTimestamp());
    }

    @Test
    public void procesarPosicionBusExisteNuevoSegmento() {

        final LocalDateTime ahora = LocalDateTime.now();

        final BusPosicionDTO busPosicionDTO = BusPosicionDTOBuilder.aBusPosicionDTO()
                .withIdBus(1L)
                .withEjeX(5)
                .withEjeY(9)
                .withLinea(62L)
                .withTimestamp(ahora)
                .build();

        final ParadaLinea parada1 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(0.0)
                        .withEjeY(0.0)
                        .withCodigoParada(1L)
                        .build())
                .withOrdinal(1L)
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

        final SegmentoFisico segmentoActual = SegmentoFisicoBuilder.aSegmentoFisico()
                .withParadaInicial(parada1.getParadaFisica())
                .withParadaFinal(parada2.getParadaFisica())
                .withId("1#2")
                .build();


        final SegmentoFisico segmentoMasCerca = SegmentoFisicoBuilder.aSegmentoFisico()
                .withParadaInicial(parada2.getParadaFisica())
                .withParadaFinal(parada3.getParadaFisica())
                .withId("2#3")
                .build();

        final Bus bus = BusBuilder.aBus()
                .withId(6853L)
                .withLatitud(36.6)
                .withLongitud(7894.6)
                .withLinea(231L)
                .withSegmentoActual(segmentoActual)
                .withTimestampSegmento(busPosicionDTO.getTimestamp().minusMinutes(-5L))
                .build();


        Mockito.when(posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO)).thenReturn(segmentoMasCerca);

        Mockito.when(busRepository.findById(busPosicionDTO.getIdBus())).thenReturn(Optional.of(bus));

        Mockito.when(busRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        historicoBusService.procesarPosicionBus(busPosicionDTO);

//        Assert.assertEquals(bus.getLatitud(), busPosicionDTO.getLatitud(), 0.00001);
//        Assert.assertEquals(bus.getLongitud(), busPosicionDTO.getLongitud(), 0.00001);
//        Assert.assertSame(bus.getSegmentoActual(), segmentoMasCerca);
//        Assert.assertEquals(bus.getTimestampSegmento(), busPosicionDTO.getTimestamp());
//        Assert.assertEquals(1, tiempoBusEnSegmentoRepository.findBySegmentoFisico(segmentoMasCerca).size());
//
//        final TiempoBusEnSegmento tiempoBusEnSegmento = tiempoBusEnSegmentoRepository.findBySegmentoFisico(segmentoMasCerca).get(0);
//        Assert.assertEquals(ahora.minusMinutes(-5L), tiempoBusEnSegmento.getInicio());
//        Assert.assertEquals(ahora, tiempoBusEnSegmento.getFin());
//        Assert.assertEquals(5 * 60, tiempoBusEnSegmento.getDuracion());
    }
}