package vecdef.org.uy.vecdefTEA.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vecdef.org.uy.vecdefTEA.entidades.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.servicios.HistoricoBusService;
import vecdef.org.uy.vecdefTEA.utils.Utils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Path("bus_evento")
public class SuscripcionBus {

    private HistoricoBusService historicoBusService;

    @Autowired
    public SuscripcionBus(final HistoricoBusService historicoBusService) {
        this.historicoBusService = historicoBusService;
    }

    @POST
    public Response suscripcion(@RequestBody final String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final JsonNode jsonNode = objectMapper.readTree(body).get("data").get(0);

            final BusPosicionDTO busPosicionDTO = new BusPosicionDTO();
            busPosicionDTO.setIdBus(jsonNode.get("id").longValue());
            busPosicionDTO.setLinea(jsonNode.get("linea").get("value").asLong());

            double[] coordenadas = Utils.aplanar(jsonNode.get("location").get("value").get("coordinates"));

            busPosicionDTO.setEjeX(coordenadas[0]);
            busPosicionDTO.setEjeY(coordenadas[1]);

            busPosicionDTO.setTimestamp(LocalDateTime.parse(jsonNode.get("timestamp").get("value").asText(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")));

            historicoBusService.procesarPosicionBus(busPosicionDTO);

            return Response.status(Response.Status.OK).build();
        } catch (final IOException e) {
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

}
