package vecdef.org.uy.vecdefTEA.servicios;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import vecdef.org.uy.vecdefTEA.entidades.*;
import vecdef.org.uy.vecdefTEA.entidades.builders.*;
import vecdef.org.uy.vecdefTEA.entidades.builders.dto.BusPosicionDTOBuilder;
import vecdef.org.uy.vecdefTEA.entidades.dto.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.repository.BusRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;

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
    private SegmentoFisicoRepository segmentoFisicoRepository;

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

        Assert.assertEquals(bus.getEjeX(), busPosicionDTO.getEjeX(), 0.00001);
        Assert.assertEquals(bus.getEjeY(), busPosicionDTO.getEjeY(), 0.00001);
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

        Mockito.verify(segmentoFisicoRepository).save(segmentoMasCerca);

        Assert.assertEquals(bus.getEjeX(), busPosicionDTO.getEjeX(), 0.00001);
        Assert.assertEquals(bus.getEjeY(), busPosicionDTO.getEjeY(), 0.00001);
        Assert.assertSame(bus.getSegmentoActual(), segmentoMasCerca);
        Assert.assertEquals(bus.getTimestampSegmento(), busPosicionDTO.getTimestamp());
        Assert.assertEquals(1, segmentoMasCerca.getHistorico().size());
        final TiempoBusEnSegmento tiempoBusEnSegmento = segmentoMasCerca.getHistorico().get(0);
        Assert.assertEquals(ahora.minusMinutes(-5L), tiempoBusEnSegmento.getInicio());
        Assert.assertEquals(ahora, tiempoBusEnSegmento.getFin());
        Assert.assertEquals(5*60, tiempoBusEnSegmento.getDuracion());
    }
}