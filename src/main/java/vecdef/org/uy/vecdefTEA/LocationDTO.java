package vecdef.org.uy.vecdefTEA;

import java.math.BigDecimal;

public class LocationDTO {

    private BigDecimal[] coordinates;
    private String type;


    public BigDecimal[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(final BigDecimal[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
