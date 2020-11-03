package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Programa;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
@DecoratedWith(AlunoMapperDecorator.class)
public interface AlunoMapper {
    public Aluno toAluno(AlunoDTO alunoDTO);
    public AlunoDTO toAlunoDTO(Aluno aluno);
}
