package vecdef.org.uy.vecdefTEA.repository;

import org.springframework.data.repository.CrudRepository;
import vecdef.org.uy.vecdefTEA.entidades.Bus;

import java.util.List;

public interface BusRepository extends CrudRepository<Bus, Long> {

    List<Bus> findAllByLinea(final Long linea);
}
