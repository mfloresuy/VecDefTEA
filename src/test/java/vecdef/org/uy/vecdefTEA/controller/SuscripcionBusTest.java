package vecdef.org.uy.vecdefTEA.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuscripcionBusTest {




    final SuscripcionBus suscripcionBus = new SuscripcionBus();

    @Test
    public void testSuscripcion() {
        final String body = "{\"subscriptionId\":\"5bcba7cc276b064e029f84ad\",\"data\":[{\"id\":\"254\",\"type\":\"Bus\",\"codigoBus\":{\"type\":\"Number\",\"value\":254,\"metadata\":{}},\"linea\":{\"type\":\"Text\",\"value\":\"2392\",\"metadata\":{}},\"location\":{\"type\":\"geo:json\",\"value\":{\"type\":\"Point\",\"coordinates\":[-56.08219,-34.892803]},\"metadata\":{}},\"timestamp\":{\"type\":\"DateTime\",\"value\":\"2018-10-02T14:11:37.00Z\",\"metadata\":{}}}]}";

        suscripcionBus.susucripcion(body);



    }


}
