package vecdef.org.uy.vecdefTEA.entidades.dto;

public class LocationDTO {
    private String type = "Point";
    private double[] coordinates;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(final double[] coordinates) {
        this.coordinates = coordinates;
    }

}
