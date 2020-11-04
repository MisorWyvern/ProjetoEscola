package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.model.Programa;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(ProgramaMapperDecorator.class)
public interface ProgramaMapper {
    public Programa toPrograma(ProgramaDTO programaDTO);
    public ProgramaDTO toProgramaDTO(Programa programa);
}
