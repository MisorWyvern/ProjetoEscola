package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.AlunoMapper;
import br.com.gabrielrosim.projetoescola.dto.mapper.ProgramaMapper;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {
    //private List<Aluno> alunos = new ArrayList<>();


    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private AlunoMapper alunoMapper;
    @Autowired
    private ProgramaService programaService;
    @Autowired
    private ProgramaMapper programaMapper;


    public List<AlunoDTO> getAlunos() {
        if (alunoRepository.findByActive(true).isPresent()) {
            return alunoRepository.findByActive(true).get()
                    .parallelStream()
                    .map(alunoMapper::toAlunoDTO)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    public List<AlunoDTO> getAlunosByPrograma(Programa programa){
        if(alunoRepository.findByPrograma(programa).isPresent()){
            return alunoRepository.findByPrograma(programa).get()
                    .parallelStream()
                    .map(alunoMapper::toAlunoDTO)
                    .collect(Collectors.toList());
        } else{
            return List.of();
        }
    }

    public Optional<AlunoDTO> getAlunoByIndex(Long id) {
        return alunoRepository.findById(id)
                .map(alunoMapper::toAlunoDTO);
    }

    public Optional<AlunoDTO> criarAluno(AlunoDTO alunoDTO) {
        if(programaService.getProgramaByIndex(alunoDTO.getProgramaId()).isPresent()){
            Aluno aluno = alunoMapper.toAluno(alunoDTO);
            aluno.setMentorias(List.of());
            aluno.setActive(true);

            Aluno savedAluno = alunoRepository.save(aluno);
            return Optional.of(alunoMapper.toAlunoDTO(savedAluno));
        }
        else{
            return Optional.empty();
        }








    }


    public void atualizarAluno(Long id, AlunoDTO alunoDTO) {
        Optional<Aluno> aluno = alunoRepository.findById(id);

        if (aluno.isPresent()) {
            Aluno alunoAtualizado = aluno.get();
            alunoAtualizado.setNome(alunoDTO.getNome());
            alunoAtualizado.setCpf(alunoDTO.getCpf());

            Optional<Programa> programa = programaService.getProgramaByIndex(alunoDTO.getProgramaId())
                                                         .map(programaMapper::toPrograma);
            programa.ifPresent(alunoAtualizado::setPrograma);
            alunoAtualizado.setActive(true);
            alunoRepository.save(alunoAtualizado);
        } else {
            criarAluno(alunoDTO);
        }
    }

    @Transactional
    public Boolean deletarAluno(Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if(aluno.isPresent()){
            aluno.get().setActive(false);
            return true;
        }
        return false;
    }


}
