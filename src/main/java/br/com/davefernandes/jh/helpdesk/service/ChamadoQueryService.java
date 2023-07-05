package br.com.davefernandes.jh.helpdesk.service;

import br.com.davefernandes.jh.helpdesk.domain.*; // for static metamodels
import br.com.davefernandes.jh.helpdesk.domain.Chamado;
import br.com.davefernandes.jh.helpdesk.repository.ChamadoRepository;
import br.com.davefernandes.jh.helpdesk.service.criteria.ChamadoCriteria;
import br.com.davefernandes.jh.helpdesk.service.dto.ChamadoDTO;
import br.com.davefernandes.jh.helpdesk.service.mapper.ChamadoMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Chamado} entities in the database.
 * The main input is a {@link ChamadoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChamadoDTO} or a {@link Page} of {@link ChamadoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChamadoQueryService extends QueryService<Chamado> {

    private final Logger log = LoggerFactory.getLogger(ChamadoQueryService.class);

    private final ChamadoRepository chamadoRepository;

    private final ChamadoMapper chamadoMapper;

    public ChamadoQueryService(ChamadoRepository chamadoRepository, ChamadoMapper chamadoMapper) {
        this.chamadoRepository = chamadoRepository;
        this.chamadoMapper = chamadoMapper;
    }

    /**
     * Return a {@link List} of {@link ChamadoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChamadoDTO> findByCriteria(ChamadoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chamado> specification = createSpecification(criteria);
        return chamadoMapper.toDto(chamadoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChamadoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChamadoDTO> findByCriteria(ChamadoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chamado> specification = createSpecification(criteria);
        return chamadoRepository.findAll(specification, page).map(chamadoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChamadoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Chamado> specification = createSpecification(criteria);
        return chamadoRepository.count(specification);
    }

    /**
     * Function to convert {@link ChamadoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Chamado> createSpecification(ChamadoCriteria criteria) {
        Specification<Chamado> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Chamado_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Chamado_.titulo));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Chamado_.status));
            }
            if (criteria.getPrioridade() != null) {
                specification = specification.and(buildSpecification(criteria.getPrioridade(), Chamado_.prioridade));
            }
            if (criteria.getDataAbertura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAbertura(), Chamado_.dataAbertura));
            }
            if (criteria.getDataFechamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataFechamento(), Chamado_.dataFechamento));
            }
            if (criteria.getValorOrcamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorOrcamento(), Chamado_.valorOrcamento));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Chamado_.descricao));
            }
        }
        return specification;
    }
}
