package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Programa;

import java.util.Optional;


public class AlunoMapper {

    public static Aluno toAluno(AlunoDTO alunoDTO, Optional<Programa> programa){
        Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getCpf(), null);
        programa.ifPresent(aluno::setPrograma);
        aluno.setId(alunoDTO.getId());
        aluno.setActive(true);

        return aluno;
    }

    public static AlunoDTO toAlunoDTO(Aluno aluno){
        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getCpf(), aluno.getPrograma().getId());
    }
}
