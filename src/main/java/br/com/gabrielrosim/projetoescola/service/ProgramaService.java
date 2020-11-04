package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.ProgramaMapper;
import br.com.gabrielrosim.projetoescola.exception.ProgramaCurrentlyInUseException;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    protected ProgramaMapper programaMapper;

    @Autowired
    private AlunoService alunoService;

    public List<ProgramaDTO> getProgramas() {
        return programaRepository.findAll()
                .parallelStream()
                .map(programaMapper::toProgramaDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProgramaDTO> getProgramaByIndex(Long id) {
        Optional<Programa> programa = programaRepository.findById(id);
        if (programa.isPresent()) {
            return programa.map(programaMapper::toProgramaDTO);
        } else {
            return Optional.empty();
        }
    }


    public ProgramaDTO criarPrograma(ProgramaDTO dto) {
        Programa programa = programaMapper.toPrograma(dto);
        if (programa.getDataInicio() == null) {
            programa.setDataInicio(LocalDate.now());
        }
        if (programa.getDataTermino() == null) {
            programa.setDataTermino(programa.getDataInicio().plusDays(30));
        }
        programa.setMentores(List.of());
        Programa savedPrograma = programaRepository.save(programa);
        return programaMapper.toProgramaDTO(savedPrograma);
    }

    @Transactional
    public void atualiarPrograma(Long id, ProgramaDTO dto) {
        Optional<Programa> programa = programaRepository.findById(id);
        if (programa.isPresent()) {
            programa.get().setNome(dto.getNome());
            if (dto.getDataInicio() != null) {
                programa.get().setDataInicio(dto.getDataInicio());
            }
            if (dto.getDataTermino() != null) {
                programa.get().setDataTermino(dto.getDataTermino());
            }
        } else {
            criarPrograma(dto);
        }
    }

    @Transactional
    public boolean deletarPrograma(Long id) {
        Optional<Programa> programa = programaRepository.findById(id);
        if (programa.isPresent()) {
            List<AlunoDTO> alunos = alunoService.getAlunosByPrograma(programa.get());
            //Verificar relacao entre programa e mentor...
            if (!(alunos.isEmpty())) {
                throw new ProgramaCurrentlyInUseException("Program em uso. Existem alunos vinculados a esse programa.");
            } else {
                programaRepository.delete(programa.get());
                return true;
            }
        }
        return false;
    }
}
