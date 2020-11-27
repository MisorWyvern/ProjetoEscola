package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import br.com.gabrielrosim.projetoescola.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlunoMapperDecorator implements AlunoMapper {

    @Autowired
    @Qualifier("delegate")
    private AlunoMapper delegate;

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProgramaRepository programaRepository;

    @Override
    public Aluno toAluno(AlunoDTO alunoDTO) {
        Aluno aluno = delegate.toAluno(alunoDTO);

        if(aluno.getId() != null){
            Optional<Aluno> alunoFromRepo = alunoRepository.findById(aluno.getId());
            if(alunoFromRepo.isPresent()){
                aluno.setMentorias(List.copyOf(alunoFromRepo.get().getMentorias()));
                aluno.setActive(alunoFromRepo.get().getActive());
            }
        }

        Optional<Programa> programaFromRepo = programaRepository.findById(alunoDTO.getIdPrograma());
        if(programaFromRepo.isPresent()){
            aluno.setPrograma(programaFromRepo.get());
        }

        return aluno;
    }

    @Override
    public AlunoDTO toAlunoDTO(Aluno aluno) {
        AlunoDTO dto = delegate.toAlunoDTO(aluno);

        if(aluno.getId() != null){
            if(aluno.getPrograma() != null) {
                dto.setIdPrograma(aluno.getPrograma().getId());
                dto.setNomePrograma(aluno.getPrograma().getNome());
            }
        }

        return dto;
    }
}
