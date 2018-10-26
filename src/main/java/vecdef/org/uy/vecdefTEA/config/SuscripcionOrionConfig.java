package vecdef.org.uy.vecdefTEA.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class SuscripcionOrionConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SuscripcionOrionConfig.class);

    private static String URL_SUSCRIPCION = System.getenv("ORION_URL");
    private static String URL_CALLBACK = System.getenv("ORION_CALLBACK_URL");

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
        LOG.info("Suscricpion a Orion exitosa");
    }

}
