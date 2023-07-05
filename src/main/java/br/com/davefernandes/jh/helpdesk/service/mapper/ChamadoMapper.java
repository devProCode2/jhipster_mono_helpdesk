package br.com.davefernandes.jh.helpdesk.service.mapper;

import br.com.davefernandes.jh.helpdesk.domain.Chamado;
import br.com.davefernandes.jh.helpdesk.service.dto.ChamadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chamado} and its DTO {@link ChamadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChamadoMapper extends EntityMapper<ChamadoDTO, Chamado> {}
