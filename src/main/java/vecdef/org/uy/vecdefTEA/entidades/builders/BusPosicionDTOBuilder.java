package vecdef.org.uy.vecdefTEA.entidades.builders;

import vecdef.org.uy.vecdefTEA.entidades.BusPosicionDTO;

import java.time.LocalDateTime;

public final class BusPosicionDTOBuilder {
    private Long idBus;
    private Long linea;
    private double ejeX;
    private double ejeY;
    private LocalDateTime timestamp;

    private BusPosicionDTOBuilder() {
    }

    public static BusPosicionDTOBuilder aBusPosicionDTO() {
        return new BusPosicionDTOBuilder();
    }

    public BusPosicionDTOBuilder withIdBus(Long idBus) {
        this.idBus = idBus;
        return this;
    }

    public BusPosicionDTOBuilder withLinea(Long linea) {
        this.linea = linea;
        return this;
    }

    public BusPosicionDTOBuilder withEjeX(double ejeX) {
        this.ejeX = ejeX;
        return this;
    }

    public BusPosicionDTOBuilder withEjeY(double ejeY) {
        this.ejeY = ejeY;
        return this;
    }

    public BusPosicionDTOBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public BusPosicionDTO build() {
        BusPosicionDTO busPosicionDTO = new BusPosicionDTO();
        busPosicionDTO.setIdBus(idBus);
        busPosicionDTO.setLinea(linea);
        busPosicionDTO.setEjeX(ejeX);
        busPosicionDTO.setEjeY(ejeY);
        busPosicionDTO.setTimestamp(timestamp);
        return busPosicionDTO;
    }
}
