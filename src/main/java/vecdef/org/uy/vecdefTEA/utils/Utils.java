package vecdef.org.uy.vecdefTEA.utils;

import com.fasterxml.jackson.databind.JsonNode;

public class Utils {

    private static int RADIO = 6371000;

    public static double[] aplanar(final JsonNode jsonNode) {
        final double longitud = jsonNode.get(0).asDouble();
        final double latitud = jsonNode.get(1).asDouble();

        return aplanar(longitud, latitud);
    }

    public static double[] aplanar(double longitud, double latitud) {
        return new double[]{longitud * RADIO * Math.cos(latitud), latitud * RADIO};
    }

    public static double distanciaEntrePuntos(final IPosicionable pto1, final IPosicionable pto2) {
        return distanciaEntrePuntos(obtenerPunto(pto1), obtenerPunto(pto2));
    }

    private static double distanciaEntrePuntos(double[] pto1, double[] pto2) {
        return Math.sqrt(Math.pow(pto1[0] - pto2[0], 2) + Math.pow(pto1[1] - pto2[1], 2));
    }

    public static double distanciaEntreSegmentoYPunto(final IPosicionable seg1, final IPosicionable seg2, final IPosicionable pto) {
        return distanciaEntreSegmentoYPunto(obtenerPunto(seg1), obtenerPunto(seg2), obtenerPunto(pto));
    }

    private static double distanciaEntreSegmentoYPunto(double[] seg1, double[] seg2, double[] pto) {
        return Math.abs((seg2[1] - seg1[1])*pto[0] - (seg2[0] - seg1[0])*pto[1] + seg2[0]*seg1[1] - seg2[1]*seg1[0]) /
                Math.sqrt(Math.pow(seg2[0] - seg1[0], 2) + Math.pow(seg2[1] - seg1[1], 2));
    }

    private static double[] obtenerPunto(final IPosicionable posicionable) {
        return new double[]{posicionable.getEjeX(), posicionable.getEjeX()};
    }


}
