package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.DisciplinaDTO;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DisciplinaMapperDecorator implements DisciplinaMapper{

    @Autowired
    @Qualifier("delegate")
    private DisciplinaMapper delegate;

    @Autowired
    private DisciplinaRepository disciplinaRepository;


    @Override
    public Disciplina toDisciplina(DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = delegate.toDisciplina(disciplinaDTO);

        if(disciplina.getId() != null){
            Optional<Disciplina> disciplinaFromRepo = disciplinaRepository.findById(disciplina.getId());
            disciplinaFromRepo.ifPresent(value -> disciplina.setMentorias(List.copyOf(value.getMentorias())));
            disciplinaFromRepo.ifPresent(dfr -> disciplina.setAvaliacoes(List.copyOf(dfr.getAvaliacoes())));
        }

        return disciplina;
    }

    @Override
    public DisciplinaDTO toDisciplinaDTO(Disciplina disciplina) {
        DisciplinaDTO dto = delegate.toDisciplinaDTO(disciplina);
        return dto;
    }
}
