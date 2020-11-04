package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
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

    @Override
    public Aluno toAluno(AlunoDTO alunoDTO) {
        Aluno aluno = delegate.toAluno(alunoDTO);

        if(aluno.getId() != null){
            Optional<Aluno> alunoFromRepo = alunoRepository.findById(aluno.getId());
            alunoFromRepo.ifPresent(afr -> aluno.setPrograma(afr.getPrograma()));
            alunoFromRepo.ifPresent(afr -> aluno.setMentorias(afr.getMentorias()));
            alunoFromRepo.ifPresent(afr -> aluno.setActive(afr.getActive()));
        }

        return aluno;
    }

    @Override
    public AlunoDTO toAlunoDTO(Aluno aluno) {
        AlunoDTO dto = delegate.toAlunoDTO(aluno);

        if(aluno.getId() != null){
            if(aluno.getPrograma() != null) {
                dto.setProgramaId(aluno.getPrograma().getId());
            }
        }

        return dto;
    }
}
