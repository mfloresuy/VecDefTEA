package vecdef.org.uy.vecdefTEA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import vecdef.org.uy.vecdefTEA.entidades.ParadaFisica;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;
import vecdef.org.uy.vecdefTEA.repository.ParadaFisicaRepository;
import vecdef.org.uy.vecdefTEA.repository.ParadaLineaRepository;
import vecdef.org.uy.vecdefTEA.servicios.PosicionService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("nextBus")
public class ConsultaBusController {

    private PosicionService posicionService;

    private ParadaLineaRepository paradaLineaRepository;

    private ParadaFisicaRepository paradaFisicaRepository;

    @Autowired
    public ConsultaBusController(final PosicionService posicionService, final ParadaLineaRepository paradaLineaRepository, final ParadaFisicaRepository paradaFisicaRepository) {
        this.posicionService = posicionService;
        this.paradaLineaRepository = paradaLineaRepository;
        this.paradaFisicaRepository = paradaFisicaRepository;
    }

    @GET
    @Path("{idLinea}/{idParada}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response suscripcion(final @PathParam("idLinea") Long idLinea, final @PathParam("idParada") Long idParada) {
        try {
            final ParadaFisica paradaFisica = paradaFisicaRepository.findById(idParada).orElseThrow(RuntimeException::new);
            final ParadaLinea paradaLinea = paradaLineaRepository.findByParadaFisicaAndLinea(paradaFisica, idLinea);
            return Response.status(Response.Status.OK).entity(posicionService.calcularTEAAParada(paradaLinea)).build();
        } catch (final Exception e) {
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

}
