package br.com.davefernandes.jh.helpdesk.repository;

import br.com.davefernandes.jh.helpdesk.domain.Chamado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Chamado entity.
 */
@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long>, JpaSpecificationExecutor<Chamado> {
    default Optional<Chamado> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Chamado> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Chamado> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct chamado from Chamado chamado left join fetch chamado.cliente left join fetch chamado.tecnico",
        countQuery = "select count(distinct chamado) from Chamado chamado"
    )
    Page<Chamado> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct chamado from Chamado chamado left join fetch chamado.cliente left join fetch chamado.tecnico")
    List<Chamado> findAllWithToOneRelationships();

    @Query("select chamado from Chamado chamado left join fetch chamado.cliente left join fetch chamado.tecnico where chamado.id =:id")
    Optional<Chamado> findOneWithToOneRelationships(@Param("id") Long id);
}
