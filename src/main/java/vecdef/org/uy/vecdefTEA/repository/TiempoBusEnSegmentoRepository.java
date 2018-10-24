package vecdef.org.uy.vecdefTEA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vecdef.org.uy.vecdefTEA.entidades.SegmentoFisico;
import vecdef.org.uy.vecdefTEA.entidades.TiempoBusEnSegmento;

import java.util.List;

public interface TiempoBusEnSegmentoRepository extends JpaRepository<TiempoBusEnSegmento, Long> {

    List<TiempoBusEnSegmento> findBySegmentoFisico(SegmentoFisico segmentoFisico);

}
