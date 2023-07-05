package br.com.davefernandes.jh.helpdesk.service.mapper;

import br.com.davefernandes.jh.helpdesk.domain.Chamado;
import br.com.davefernandes.jh.helpdesk.domain.Pessoa;
import br.com.davefernandes.jh.helpdesk.service.dto.ChamadoDTO;
import br.com.davefernandes.jh.helpdesk.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chamado} and its DTO {@link ChamadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChamadoMapper extends EntityMapper<ChamadoDTO, Chamado> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "pessoaNome")
    @Mapping(target = "tecnico", source = "tecnico", qualifiedByName = "pessoaNome")
    ChamadoDTO toDto(Chamado s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);
}
