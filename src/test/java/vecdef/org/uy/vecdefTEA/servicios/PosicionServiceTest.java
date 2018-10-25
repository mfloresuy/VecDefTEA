package vecdef.org.uy.vecdefTEA.servicios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import vecdef.org.uy.vecdefTEA.entidades.Bus;
import vecdef.org.uy.vecdefTEA.entidades.builders.BusBuilder;
import vecdef.org.uy.vecdefTEA.entidades.dto.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.builders.dto.BusPosicionDTOBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaFisicaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaLineaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.SegmentoFisicoBuilder;
import vecdef.org.uy.vecdefTEA.entidades.dto.TEAResponse;
import vecdef.org.uy.vecdefTEA.repository.BusRepository;
import vecdef.org.uy.vecdefTEA.repository.ParadaLineaRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PosicionServiceTest {

    @InjectMocks
    private PosicionService posicionService = new PosicionService();

    @Mock
    private ParadaLineaRepository paradaRepository;

    @Mock
    private BusRepository busRepository;

    @Mock
    private HistoricoBusService historicoBusService;

    @Spy
    private SegmentoFisicoRepository segmentoFisicoRepository;


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void buscarSegmentoFiscoMasCerca() {
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

        final ParadaLinea parada4 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(2)
                        .withEjeY(2)
                        .withCodigoParada(4L)
                        .build())
                .withOrdinal(4L)
                .build();

        final String idSegmento = "2#3";
        final SegmentoFisico segmentoFisico = SegmentoFisicoBuilder.aSegmentoFisico()
                .withParadaInicial(parada2.getParadaFisica())
                .withParadaFinal(parada3.getParadaFisica())
                .build();

        final BusPosicionDTO busPosicionDTO = BusPosicionDTOBuilder.aBusPosicionDTO().withEjeX(0.75).withEjeY(0.8).withLinea(1234L).build();
        Mockito.when(paradaRepository.findByLineaOrderByOrdinal(Mockito.eq(1234L))).thenReturn(Arrays.asList(parada1, parada2, parada3, parada4));


        Mockito.doReturn(Optional.of(segmentoFisico)).when(segmentoFisicoRepository).findById(idSegmento);

        final SegmentoFisico segmento = posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO);
        Mockito.verify(segmentoFisicoRepository).findById(idSegmento);
        Assert.assertSame(segmento, segmentoFisico);

    }


    private List<ParadaLinea> crearParadas() {
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
                        .withEjeX(1)
                        .withEjeY(0)
                        .withCodigoParada(2L)
                        .build())
                .withOrdinal(2L)
                .build();

        final ParadaLinea parada3 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(2)
                        .withEjeY(0)
                        .withCodigoParada(3L)
                        .build())
                .withOrdinal(3L)
                .build();

        final ParadaLinea parada4 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(3)
                        .withEjeY(0)
                        .withCodigoParada(4L)
                        .build())
                .withOrdinal(4L)
                .build();

        final ParadaLinea parada5 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(8)
                        .withEjeY(8)
                        .withCodigoParada(5L)
                        .build())
                .withOrdinal(5L)
                .build();

        final ParadaLinea parada6 = ParadaLineaBuilder.aParadaLinea()
                .withParadaFisica(ParadaFisicaBuilder.aParadaFisica()
                        .withEjeX(10)
                        .withEjeY(10)
                        .withCodigoParada(6L)
                        .build())
                .withOrdinal(6L)
                .build();
        return Arrays.asList(parada1, parada2, parada3, parada4, parada5, parada6);
    }


    @Test
    public void buscarSegmentoFiscoMasCerca4EnLineaPrimero() {

        final List<ParadaLinea> paradaLineas = crearParadas();

        final String idSegmento = "1#2";
        final SegmentoFisico segmentoFisico = SegmentoFisicoBuilder.aSegmentoFisico()
                .withParadaInicial(paradaLineas.get(0).getParadaFisica())
                .withParadaFinal(paradaLineas.get(1).getParadaFisica())
                .build();

        final BusPosicionDTO busPosicionDTO = BusPosicionDTOBuilder.aBusPosicionDTO().withEjeX(0.25).withEjeY(0).withLinea(1234L).build();
        Mockito.when(paradaRepository.findByLineaOrderByOrdinal(Mockito.eq(1234L))).thenReturn(paradaLineas);


        Mockito.doReturn(Optional.of(segmentoFisico)).when(segmentoFisicoRepository).findById(idSegmento);

        final SegmentoFisico segmento = posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO);
        Mockito.verify(segmentoFisicoRepository).findById(idSegmento);
        Assert.assertSame(segmento, segmentoFisico);

    }

    @Test
    public void buscarSegmentoFiscoMasCerca4EnLineaTercero() {

        final List<ParadaLinea> paradaLineas = crearParadas();

        final String idSegmento = "3#4";
        final SegmentoFisico segmentoFisico = SegmentoFisicoBuilder.aSegmentoFisico()
                .withParadaInicial(paradaLineas.get(0).getParadaFisica())
                .withParadaFinal(paradaLineas.get(1).getParadaFisica())
                .build();

        final BusPosicionDTO busPosicionDTO = BusPosicionDTOBuilder.aBusPosicionDTO().withEjeX(2.25).withEjeY(0).withLinea(1234L).build();
        Mockito.when(paradaRepository.findByLineaOrderByOrdinal(Mockito.eq(1234L))).thenReturn(paradaLineas);

        Mockito.doReturn(Optional.of(segmentoFisico)).when(segmentoFisicoRepository).findById(idSegmento);

        final SegmentoFisico segmento = posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO);
        Mockito.verify(segmentoFisicoRepository).findById(idSegmento);
        Assert.assertSame(segmento, segmentoFisico);
    }

    @Test
    public void buscarSegmentoFiscoMasCerca4EnLineaSinSiguiente() {

        final List<ParadaLinea> paradaLineas = crearParadas();

        final String idSegmento = "5#6";
        final SegmentoFisico segmentoFisico = SegmentoFisicoBuilder.aSegmentoFisico()
                .withParadaInicial(paradaLineas.get(0).getParadaFisica())
                .withParadaFinal(paradaLineas.get(1).getParadaFisica())
                .build();

        final BusPosicionDTO busPosicionDTO = BusPosicionDTOBuilder.aBusPosicionDTO().withEjeX(12).withEjeY(13).withLinea(1234L).build();
        Mockito.when(paradaRepository.findByLineaOrderByOrdinal(Mockito.eq(1234L))).thenReturn(paradaLineas);

        Mockito.doReturn(Optional.of(segmentoFisico)).when(segmentoFisicoRepository).findById(idSegmento);

        final SegmentoFisico segmento = posicionService.buscarSegmentoFiscoMasCerca(busPosicionDTO);
        Mockito.verify(segmentoFisicoRepository).findById(idSegmento);
        Assert.assertSame(segmento, segmentoFisico);
    }

    @Test
    public void obtenerSegmentosPreviosHastaBus() {
        final List<ParadaLinea> paradaLineas = crearParadas();

        final SegmentoFisico segmento1 = SegmentoFisicoBuilder.aSegmentoFisico().withId("1#2").build();
        final SegmentoFisico segmento2 = SegmentoFisicoBuilder.aSegmentoFisico().withId("2#3").build();
        final SegmentoFisico segmento3 = SegmentoFisicoBuilder.aSegmentoFisico().withId("3#4").build();
        final SegmentoFisico segmento4 = SegmentoFisicoBuilder.aSegmentoFisico().withId("4#5").build();

        final ParadaLinea paradaLinea = paradaLineas.stream().filter(p -> p.getOrdinal().equals(5L)).findAny().get();
        final Bus busCerca = BusBuilder.aBus().withId(56413L).withLinea(paradaLinea.getLinea()).withSegmentoActual(segmento2).build();
        final Bus busLejos = BusBuilder.aBus().withId(56410L).withLinea(paradaLinea.getLinea()).withSegmentoActual(segmento1).build();

        Mockito.when(paradaRepository.findByLineaOrderByOrdinal(paradaLinea.getLinea())).thenReturn(paradaLineas);
        Mockito.when(busRepository.findAllByLinea(paradaLinea.getLinea())).thenReturn(Arrays.asList(busLejos, busCerca));

        Mockito.when(segmentoFisicoRepository.findById("2#3")).thenReturn(Optional.of(segmento2));
        Mockito.when(segmentoFisicoRepository.findById("3#4")).thenReturn(Optional.of(segmento3));
        Mockito.when(segmentoFisicoRepository.findById("4#5")).thenReturn(Optional.of(segmento4));

        Mockito.when(historicoBusService.obtenerTiempoEstimadoDeSegmento(segmento2)).thenReturn(20.3);
        Mockito.when(historicoBusService.obtenerTiempoEstimadoDeSegmento(segmento3)).thenReturn(153.4);
        Mockito.when(historicoBusService.obtenerTiempoEstimadoDeSegmento(segmento4)).thenReturn(451.9);

        final TEAResponse response = posicionService.calcularTEAAParada(paradaLinea);

        Assert.assertEquals(busCerca.getId(), response.getIdBus());
        Assert.assertEquals(busCerca.getLinea(), response.getIdLinea());
        Assert.assertEquals(paradaLinea.getParadaFisica().getCodigoParada(), response.getIdParada());
        //Siempre redondeamos la fraccion de decimal para abajo
        Assert.assertEquals(625, response.getTea());
        final double delta = 0.000005;
        Assert.assertEquals(busCerca.getLatitud(), response.getLatitud(), delta);
        Assert.assertEquals(busCerca.getLongitud(), response.getLatitud(), delta);
    }
}