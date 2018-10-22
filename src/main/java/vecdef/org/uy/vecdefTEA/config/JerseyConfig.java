package vecdef.org.uy.vecdefTEA.config;


import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import vecdef.org.uy.vecdefTEA.controller.SuscripcionBus;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(SuscripcionBus.class);
    }
}
