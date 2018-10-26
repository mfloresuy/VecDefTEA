package vecdef.org.uy.vecdefTEA.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vecdef.org.uy.vecdefTEA.entidades.dto.BusPosicionDTO;
import vecdef.org.uy.vecdefTEA.servicios.HistoricoBusService;
import vecdef.org.uy.vecdefTEA.utils.Utils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Path("bus_evento")
public class SuscripcionBusController {

    private HistoricoBusService historicoBusService;

    @Autowired
    public SuscripcionBusController(final HistoricoBusService historicoBusService) {
        this.historicoBusService = historicoBusService;
    }

    @POST
    public Response suscripcion(@RequestBody final String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final JsonNode jsonNode = objectMapper.readTree(body).get("data").get(0);

            final BusPosicionDTO busPosicionDTO = new BusPosicionDTO();
            busPosicionDTO.setIdBus(jsonNode.get("id").asLong());
            busPosicionDTO.setLinea(jsonNode.get("linea").get("value").asLong());

            final JsonNode jsonCoordenadas = jsonNode.get("location").get("value").get("coordinates");
            double longitud = jsonCoordenadas.get(0).asDouble();
            double latituud = jsonCoordenadas.get(1).asDouble();
            double[] coordenadas = Utils.aplanar(longitud, latituud);

            busPosicionDTO.setLongitud(longitud);
            busPosicionDTO.setLatitud(latituud);
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
