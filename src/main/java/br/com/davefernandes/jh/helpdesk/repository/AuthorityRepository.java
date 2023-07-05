package br.com.davefernandes.jh.helpdesk.repository;

import br.com.davefernandes.jh.helpdesk.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
