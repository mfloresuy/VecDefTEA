package vecdef.org.uy.vecdefTEA.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import vecdef.org.uy.vecdefTEA.entidades.BusHistorico;
import vecdef.org.uy.vecdefTEA.utils.Utils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("algo")
public class SuscripcionBus {

    private static Logger LOGGER = LoggerFactory.getLogger(SuscripcionBus.class);

    @POST
    @Path("sucri")
    public Response susucripcion(@RequestBody final String body) {
        final BusHistorico historico = new BusHistorico();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LOGGER.info(body);
            final JsonNode jsonNode = objectMapper.readTree(body).get("data").get(0);
            final BusHistorico busHistorico = new BusHistorico();
            busHistorico.setIdBus(jsonNode.get("id").longValue());
            busHistorico.setLinea(jsonNode.get("linea").get("value").asText());

            double[] coordenadas = Utils.aplanar(jsonNode.get("location").get("value").get("coordinates"));

            busHistorico.setEjeX(coordenadas[0]);
            busHistorico.setEjeY(coordenadas[1]);

            busHistorico.setTimestamp(jsonNode.get("timestamp").get("value").asText());

            ObjectMapper mapper = new ObjectMapper();
            LOGGER.info(mapper.writeValueAsString(busHistorico));

            return Response.status(Response.Status.OK).build();
        } catch (IOException e) {
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }

    }

}
