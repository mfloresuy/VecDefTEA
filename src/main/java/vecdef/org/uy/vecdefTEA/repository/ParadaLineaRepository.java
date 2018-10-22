package vecdef.org.uy.vecdefTEA.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vecdef.org.uy.vecdefTEA.entidades.ParadaLinea;

import java.util.List;

public interface ParadaLineaRepository extends CrudRepository<ParadaLinea, Long> {

    List<ParadaLinea> findByLineaOrderByOrdinal(Long linea);

    @Query(value = "SELECT DISTINCT linea FROM ParadaLinea ")
    List<Long> findAllLineas();

}
