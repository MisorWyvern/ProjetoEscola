package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.DisciplinaDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.DisciplinaMapper;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    DisciplinaMapper disciplinaMapper;

    public List<DisciplinaDTO> getDisciplinas() {
        return disciplinaRepository.findAll()
                                   .parallelStream()
                                   .map(disciplinaMapper::toDisciplinaDTO)
                                   .collect(Collectors.toList());
    }

    public Optional<DisciplinaDTO> getDisciplinaByIndex(Long id) {
        return disciplinaRepository.findById(id)
                                   .map(disciplinaMapper::toDisciplinaDTO);
    }
}
