package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.ProgramaMapper;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramaService {

    @Autowired
    ProgramaRepository programaRepository;

    @Autowired
    ProgramaMapper programaMapper;

    public List<ProgramaDTO> getProgramas() {
        return programaRepository.findAll()
                                 .parallelStream()
                                 .map(programaMapper::toProgramaDTO)
                                 .collect(Collectors.toList());
    }

    public Optional<ProgramaDTO> getProgramaByIndex(Long id) {
        Optional<Programa> programa = programaRepository.findById(id);
        if(programa.isPresent()){
            return programa.map(programaMapper::toProgramaDTO);
        }
        else{
            return Optional.empty();
        }
    }


    public ProgramaDTO criarPrograma(ProgramaDTO dto) {
        Programa programa = programaMapper.toPrograma(dto);
        if (programa.getDataInicio() == null){
            programa.setDataInicio(LocalDate.now());
        }
        if(programa.getDataTermino() == null){
            programa.setDataTermino(programa.getDataInicio().plusDays(30));
        }
        programa.setMentores(List.of());
        Programa savedPrograma = programaRepository.save(programa);
        return programaMapper.toProgramaDTO(savedPrograma);
    }


}
