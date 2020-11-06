package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.model.Avaliacao;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(AvaliacaoMapperDecorator.class)
public interface AvaliacaoMapper {
    public Avaliacao toAvaliacao(AvaliacaoDTO avaliacaoDTO);
    public AvaliacaoDTO toAvaliacaoDTO(Avaliacao avaliacao);
}
