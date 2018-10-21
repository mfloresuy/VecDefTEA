package vecdef.org.uy.vecdefTEA.repository;

import org.springframework.data.repository.CrudRepository;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;

import java.util.List;

public interface ParadaRepository extends CrudRepository<ParadaLinea, Long> {

    List<ParadaLinea> findByLineaOrderByOrdinal(final String linea);
}
