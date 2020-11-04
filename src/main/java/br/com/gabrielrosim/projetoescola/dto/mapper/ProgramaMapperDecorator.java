package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import br.com.gabrielrosim.projetoescola.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProgramaMapperDecorator implements ProgramaMapper {

    @Autowired
    @Qualifier("delegate")
    private ProgramaMapper delegate;

    @Autowired
    private ProgramaRepository programaRepository;

    @Override
    public Programa toPrograma(ProgramaDTO programaDTO) {
        Programa programa = delegate.toPrograma(programaDTO);
        if(programa.getId() != null){
            Optional<Programa> progRepo = programaRepository.findById(programa.getId());
            progRepo.ifPresent(value -> programa.setMentores(value.getMentores()));
        }
        return programa;
    }

    @Override
    public ProgramaDTO toProgramaDTO(Programa programa) {
        ProgramaDTO dto = delegate.toProgramaDTO(programa);
        return dto;
    }
}
