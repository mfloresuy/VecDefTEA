package vecdef.org.uy.vecdefTEA.servicios.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import vecdef.org.uy.vecdefTEA.entidades.BusHistorico;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;


@Path("algo")
public class SuscripcionBus {

    private static Logger LOGGER = LoggerFactory.getLogger(SuscripcionBus.class);

    @POST
    @Path("sucri")
    public Response susucripcion(@RequestBody final String body) {
        final BusHistorico historico = new BusHistorico();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final JsonNode jsonNode = objectMapper.readTree(body);
            BusHistorico busHistorico = new BusHistorico();
            busHistorico.setIdBus(jsonNode.get("id").longValue());
            busHistorico.setLinea(jsonNode.get("linea").get("value").asText());
            JsonNode coordinatesNode = jsonNode.get("location").get("value").get("coordinates");
            BigDecimal[] coordinates = {coordinatesNode.get(0).decimalValue(), coordinatesNode.get(1).decimalValue()};
            busHistorico.setCoordenadas(coordinates);
            busHistorico.setTimestamp(jsonNode.get("timestamp").get("value").asText());
            return Response.status(Response.Status.OK).build();
        } catch (IOException e) {
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }

    }

}
