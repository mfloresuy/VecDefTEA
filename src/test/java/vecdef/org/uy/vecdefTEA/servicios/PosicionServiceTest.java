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
import vecdef.org.uy.vecdefTEA.entidades.BusHistorico;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.builders.BusHistoricoBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaFisicaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.ParadaLineaBuilder;
import vecdef.org.uy.vecdefTEA.entidades.builders.SegmentoFisicoBuilder;
import vecdef.org.uy.vecdefTEA.repository.ParadaRepository;
import vecdef.org.uy.vecdefTEA.repository.SegmentoFisicoRepository;

import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PosicionServiceTest {

    @InjectMocks
    private PosicionService posicionService = new PosicionService();

    @Mock
    private ParadaRepository paradaRepository;

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

        final BusHistorico busHistorico = BusHistoricoBuilder.aBusHistorico().withEjeX(0.75).withEjeY(0.8).withLinea("1234").build();
        Mockito.when(paradaRepository.findByLineaOrderByOrdinal(Mockito.eq("1234"))).thenReturn(Arrays.asList(parada1, parada2, parada3, parada4));


        Mockito.doReturn(Optional.of(segmentoFisico)).when(segmentoFisicoRepository).findById(idSegmento);

        final SegmentoFisico segmento = posicionService.buscarSegmentoFiscoMasCerca(busHistorico);
        Mockito.verify(segmentoFisicoRepository).findById(idSegmento);
        Assert.assertSame(segmento, segmentoFisico);

    }
}