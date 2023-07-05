package br.com.davefernandes.jh.helpdesk.service;

import br.com.davefernandes.jh.helpdesk.domain.Chamado;
import br.com.davefernandes.jh.helpdesk.repository.ChamadoRepository;
import br.com.davefernandes.jh.helpdesk.service.dto.ChamadoDTO;
import br.com.davefernandes.jh.helpdesk.service.mapper.ChamadoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Chamado}.
 */
@Service
@Transactional
public class ChamadoService {

    private final Logger log = LoggerFactory.getLogger(ChamadoService.class);

    private final ChamadoRepository chamadoRepository;

    private final ChamadoMapper chamadoMapper;

    public ChamadoService(ChamadoRepository chamadoRepository, ChamadoMapper chamadoMapper) {
        this.chamadoRepository = chamadoRepository;
        this.chamadoMapper = chamadoMapper;
    }

    /**
     * Save a chamado.
     *
     * @param chamadoDTO the entity to save.
     * @return the persisted entity.
     */
    public ChamadoDTO save(ChamadoDTO chamadoDTO) {
        log.debug("Request to save Chamado : {}", chamadoDTO);
        Chamado chamado = chamadoMapper.toEntity(chamadoDTO);
        chamado = chamadoRepository.save(chamado);
        return chamadoMapper.toDto(chamado);
    }

    /**
     * Update a chamado.
     *
     * @param chamadoDTO the entity to save.
     * @return the persisted entity.
     */
    public ChamadoDTO update(ChamadoDTO chamadoDTO) {
        log.debug("Request to update Chamado : {}", chamadoDTO);
        Chamado chamado = chamadoMapper.toEntity(chamadoDTO);
        chamado = chamadoRepository.save(chamado);
        return chamadoMapper.toDto(chamado);
    }

    /**
     * Partially update a chamado.
     *
     * @param chamadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChamadoDTO> partialUpdate(ChamadoDTO chamadoDTO) {
        log.debug("Request to partially update Chamado : {}", chamadoDTO);

        return chamadoRepository
            .findById(chamadoDTO.getId())
            .map(existingChamado -> {
                chamadoMapper.partialUpdate(existingChamado, chamadoDTO);

                return existingChamado;
            })
            .map(chamadoRepository::save)
            .map(chamadoMapper::toDto);
    }

    /**
     * Get all the chamados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChamadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chamados");
        return chamadoRepository.findAll(pageable).map(chamadoMapper::toDto);
    }

    /**
     * Get one chamado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChamadoDTO> findOne(Long id) {
        log.debug("Request to get Chamado : {}", id);
        return chamadoRepository.findById(id).map(chamadoMapper::toDto);
    }

    /**
     * Delete the chamado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Chamado : {}", id);
        chamadoRepository.deleteById(id);
    }
}
