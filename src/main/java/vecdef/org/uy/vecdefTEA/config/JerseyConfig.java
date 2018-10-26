package vecdef.org.uy.vecdefTEA.config;


import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import vecdef.org.uy.vecdefTEA.controller.ConsultaBusController;
import vecdef.org.uy.vecdefTEA.controller.SuscripcionBusController;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(SuscripcionBusController.class);
        register(ConsultaBusController.class);
    }

}
