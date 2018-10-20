package vecdef.org.uy.vecdefTEA.servicios.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import vecdef.org.uy.vecdefTEA.LocationDTO;
import vecdef.org.uy.vecdefTEA.NextBusTEADTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;

@Path("nextBus")
public class TeaServiceImpl {

    private final ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("/{id_linea}/{id_parada}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response nextBus(@PathParam("id_linea") int idLinea, @PathParam("id_parada") int idParada) {
        final NextBusTEADTO entity = mockLocation();
        try {
            return Response.status(Response.Status.OK).entity(mapper.writeValueAsString(entity)).type(MediaType.APPLICATION_JSON_TYPE).build();
        } catch (IOException e) {
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }

    }

    private NextBusTEADTO mockLocation() {
        final LocationDTO location = new LocationDTO();
        location.setType("Point");

        final BigDecimal[] coordinates = {BigDecimal.valueOf(-56.19539), BigDecimal.valueOf(-34.90608)};
        location.setCoordinates(coordinates);

        final NextBusTEADTO nextBusTEADTO = new NextBusTEADTO();
        nextBusTEADTO.setIdBus(1234);
        nextBusTEADTO.setIdLinea("1234");
        nextBusTEADTO.setIdParada("1234");
        nextBusTEADTO.setLocation(location);
        return nextBusTEADTO;
    }
}
