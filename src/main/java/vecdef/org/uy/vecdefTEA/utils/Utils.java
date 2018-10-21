package vecdef.org.uy.vecdefTEA.utils;

import com.fasterxml.jackson.databind.JsonNode;

public class Utils {

    private static int RADIO = 6371000;

    public static double[] aplanar(final JsonNode jsonNode) {
        final double longitud = jsonNode.get(0).asDouble();
        final double latitud = jsonNode.get(1).asDouble();

        return aplanar(longitud, latitud);
    }

    private static double[] aplanar(double longitud, double latitud) {
        return new double[]{longitud * RADIO * Math.cos(latitud), latitud * RADIO};
    }


}
