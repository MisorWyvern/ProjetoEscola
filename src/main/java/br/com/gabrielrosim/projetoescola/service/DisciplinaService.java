package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.DisciplinaDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.DisciplinaMapper;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public DisciplinaDTO criarDisciplina(DisciplinaDTO dto) {
        Disciplina disciplina = disciplinaMapper.toDisciplina(dto);
        disciplina.setMentorias(List.of());
        if(disciplina.getDataInicio() == null){
            disciplina.setDataInicio(LocalDate.now());
        }
        if(disciplina.getDataTermino() == null){
            disciplina.setDataTermino(disciplina.getDataInicio().plusDays(30));
        }
        if(disciplina.getNota() == null){
            disciplina.setNota(Double.valueOf(0));
        }
        Disciplina savedDisciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toDisciplinaDTO(savedDisciplina);
    }


    @Transactional
    public Boolean deletarDisciplina(Long id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);

        if(disciplina.isPresent()){
            //Verificar Ligacao Com Mentoria
            disciplinaRepository.delete(disciplina.get());
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Transactional
    public void atualizarDisciplina(Long id, DisciplinaDTO dto) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);

        if(disciplina.isPresent()){
            disciplina.get().setNome(dto.getNome());
            if(dto.getDataInicio() != null){
                disciplina.get().setDataInicio(dto.getDataInicio());
            }
            if(dto.getDataTermino() != null){
                disciplina.get().setDataTermino(dto.getDataTermino());
            }
            if(dto.getNota() != null){
                disciplina.get().setNota(dto.getNota());
            }
        }else{
            criarDisciplina(dto);
        }
        return;
    }
}
