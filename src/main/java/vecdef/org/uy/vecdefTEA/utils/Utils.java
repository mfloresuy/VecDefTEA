package vecdef.org.uy.vecdefTEA.utils;

public class Utils {

    private static int RADIO = 6371000;

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
        return distanciaEntrePuntos(pto, obtenerPuntMasCerca(seg1, seg2, pto));
    }

    private static double[] obtenerPunto(final IPosicionable posicionable) {
        return new double[]{posicionable.getEjeX(), posicionable.getEjeY()};
    }

    private static double[] obtenerPuntMasCerca(double[] seg1, double[] seg2, double[] pto) {

        final double sx1 = seg1[0];
        final double sy1 = seg1[1];

        final double sx2 = seg2[0];
        final double sy2 = seg2[1];

        final double px = pto[0];
        final double py = pto[1];

        double xDelta = sx2 - sx1;
        double yDelta = sy2 - sy1;

        if ((xDelta == 0) && (yDelta == 0)) {
            throw new IllegalArgumentException("El final del segmento es igual al inicio");
        }

        double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);

        final double[] puntoMasCerca;
        if (u < 0) {
            puntoMasCerca = new double[]{sx1, sy1};
        } else if (u > 1) {
            puntoMasCerca = new double[]{sx2, sy2};
        } else {
            puntoMasCerca = new double[]{sx1 + u * xDelta, sy1 + u * yDelta};
        }

        return puntoMasCerca;
    }


}
