package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.DisciplinaDTO;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(DisciplinaMapperDecorator.class)
public interface DisciplinaMapper {
    public Disciplina toDisciplina(DisciplinaDTO disciplinaDTO);
    public DisciplinaDTO toDisciplinaDTO(Disciplina disciplina);
}
