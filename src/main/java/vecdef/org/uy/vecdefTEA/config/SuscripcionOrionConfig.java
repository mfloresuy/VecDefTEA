package vecdef.org.uy.vecdefTEA.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class SuscripcionOrionConfig {

    private static final String URL_SUSCRIPCION = "http://192.168.1.44:1026/v2/subscriptions";
    private static final String URL_CALLBACK = "http://192.168.1.48:8080/bus_evento";

    private final RestTemplate restTemplate;

    @Autowired
    public SuscripcionOrionConfig(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.setConnectTimeout(10000).setReadTimeout(10000).build();
    }


    @PostConstruct
    private void init() throws IOException {
        restTemplate.postForEntity(URL_SUSCRIPCION, new ObjectMapper().readTree("{\n" +
                "  \"subject\": {\n" +
                "    \"entities\": [\n" +
                "      {\n" +
                "        \"type\": \"Bus\",\n" +
                "        \"idPattern\": \".*\"\n" +
                "      }\n" +
                "    ]\n" +
                "    },\n" +
                "  \"notification\": {\n" +
                "    \"http\": {\n" +
                "      \"url\": \"" + URL_CALLBACK + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}"), JsonNode.class);
    }

}
