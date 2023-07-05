package br.com.davefernandes.jh.helpdesk.repository;

import br.com.davefernandes.jh.helpdesk.domain.Chamado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Chamado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long>, JpaSpecificationExecutor<Chamado> {}
