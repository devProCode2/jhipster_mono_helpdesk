package br.com.davefernandes.jh.helpdesk.web.rest;

import br.com.davefernandes.jh.helpdesk.repository.ChamadoRepository;
import br.com.davefernandes.jh.helpdesk.service.ChamadoQueryService;
import br.com.davefernandes.jh.helpdesk.service.ChamadoService;
import br.com.davefernandes.jh.helpdesk.service.criteria.ChamadoCriteria;
import br.com.davefernandes.jh.helpdesk.service.dto.ChamadoDTO;
import br.com.davefernandes.jh.helpdesk.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.davefernandes.jh.helpdesk.domain.Chamado}.
 */
@RestController
@RequestMapping("/api")
public class ChamadoResource {

    private final Logger log = LoggerFactory.getLogger(ChamadoResource.class);

    private static final String ENTITY_NAME = "chamado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChamadoService chamadoService;

    private final ChamadoRepository chamadoRepository;

    private final ChamadoQueryService chamadoQueryService;

    public ChamadoResource(ChamadoService chamadoService, ChamadoRepository chamadoRepository, ChamadoQueryService chamadoQueryService) {
        this.chamadoService = chamadoService;
        this.chamadoRepository = chamadoRepository;
        this.chamadoQueryService = chamadoQueryService;
    }

    /**
     * {@code POST  /chamados} : Create a new chamado.
     *
     * @param chamadoDTO the chamadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chamadoDTO, or with status {@code 400 (Bad Request)} if the chamado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chamados")
    public ResponseEntity<ChamadoDTO> createChamado(@Valid @RequestBody ChamadoDTO chamadoDTO) throws URISyntaxException {
        log.debug("REST request to save Chamado : {}", chamadoDTO);
        if (chamadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new chamado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChamadoDTO result = chamadoService.save(chamadoDTO);
        return ResponseEntity
            .created(new URI("/api/chamados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chamados/:id} : Updates an existing chamado.
     *
     * @param id the id of the chamadoDTO to save.
     * @param chamadoDTO the chamadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chamadoDTO,
     * or with status {@code 400 (Bad Request)} if the chamadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chamadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chamados/{id}")
    public ResponseEntity<ChamadoDTO> updateChamado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChamadoDTO chamadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Chamado : {}, {}", id, chamadoDTO);
        if (chamadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chamadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chamadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChamadoDTO result = chamadoService.update(chamadoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chamadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chamados/:id} : Partial updates given fields of an existing chamado, field will ignore if it is null
     *
     * @param id the id of the chamadoDTO to save.
     * @param chamadoDTO the chamadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chamadoDTO,
     * or with status {@code 400 (Bad Request)} if the chamadoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the chamadoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the chamadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chamados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChamadoDTO> partialUpdateChamado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChamadoDTO chamadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Chamado partially : {}, {}", id, chamadoDTO);
        if (chamadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chamadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chamadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChamadoDTO> result = chamadoService.partialUpdate(chamadoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chamadoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /chamados} : get all the chamados.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chamados in body.
     */
    @GetMapping("/chamados")
    public ResponseEntity<List<ChamadoDTO>> getAllChamados(
        ChamadoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Chamados by criteria: {}", criteria);
        Page<ChamadoDTO> page = chamadoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chamados/count} : count all the chamados.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/chamados/count")
    public ResponseEntity<Long> countChamados(ChamadoCriteria criteria) {
        log.debug("REST request to count Chamados by criteria: {}", criteria);
        return ResponseEntity.ok().body(chamadoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /chamados/:id} : get the "id" chamado.
     *
     * @param id the id of the chamadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chamadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chamados/{id}")
    public ResponseEntity<ChamadoDTO> getChamado(@PathVariable Long id) {
        log.debug("REST request to get Chamado : {}", id);
        Optional<ChamadoDTO> chamadoDTO = chamadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chamadoDTO);
    }

    /**
     * {@code DELETE  /chamados/:id} : delete the "id" chamado.
     *
     * @param id the id of the chamadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chamados/{id}")
    public ResponseEntity<Void> deleteChamado(@PathVariable Long id) {
        log.debug("REST request to delete Chamado : {}", id);
        chamadoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
