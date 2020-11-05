package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.TipoAvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.TipoAvaliacao;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoAvaliacaoMapper {
    public TipoAvaliacao toTipoAvaliacao(TipoAvaliacaoDTO tipoAvaliacaoDTO);
    public TipoAvaliacaoDTO toTipoAvaliacaoDTO(TipoAvaliacao tipoAvaliacao);
}
