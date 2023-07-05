package br.com.davefernandes.jh.helpdesk.service.mapper;

import br.com.davefernandes.jh.helpdesk.domain.Pessoa;
import br.com.davefernandes.jh.helpdesk.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {}
