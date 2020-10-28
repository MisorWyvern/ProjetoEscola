package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.AlunoMapper;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import br.com.gabrielrosim.projetoescola.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {
    //private List<Aluno> alunos = new ArrayList<>();


    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProgramaRepository programaRepository;


    public List<AlunoDTO> getAlunos() {
        return alunoRepository.findByActive(true).get()
                              .parallelStream()
                              .map(AlunoMapper::toAlunoDTO)
                              .collect(Collectors.toList());
    }

    public Optional<AlunoDTO> getAlunoByIndex(Long id) {
        return alunoRepository.findById(id)
                              .map(AlunoMapper::toAlunoDTO);
//        Optional<Aluno> aluno = alunoRepository.findById(id);
//        if(aluno.isPresent()){
//            return AlunoMapper.toAlunoDTO(aluno.get());
//        }
//        return null;
    }

    public AlunoDTO criarAluno(AlunoDTO alunoDTO) {
        Aluno aluno = AlunoMapper.toAluno(alunoDTO, programaRepository.findById(alunoDTO.getProgramaId()));
        Aluno savedAluno = alunoRepository.save(aluno);
        return AlunoMapper.toAlunoDTO(savedAluno);
    }


    public void atualizarAluno(Long id, AlunoDTO alunoDTO) {
        Optional<Aluno> aluno = alunoRepository.findById(id);

        if(aluno.isPresent()){
            Aluno alunoAtualizado = aluno.get();
            alunoAtualizado.setNome(alunoDTO.getNome());
            alunoAtualizado.setCpf(alunoDTO.getCpf());
            Optional<Programa> programa = programaRepository.findById(alunoDTO.getProgramaId());
            programa.ifPresent(alunoAtualizado::setPrograma);

            alunoRepository.save(alunoAtualizado);
        }
        else{
            criarAluno(alunoDTO);
        }
    }

    public void deletarAluno(Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        aluno.ifPresent(value -> alunoRepository.delete(value));
    }


}
