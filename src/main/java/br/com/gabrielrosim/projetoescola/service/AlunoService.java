package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.AlunoMapper;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
import br.com.gabrielrosim.projetoescola.repository.ProgramaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {
    //private List<Aluno> alunos = new ArrayList<>();


    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProgramaRepository programaRepository;


    public List<AlunoDTO> getAlunos() {
        //List<Aluno> alunos = alunoRepository.findAll();
        return alunoRepository.findAll()
                              .parallelStream()
                              .map(AlunoMapper::toAlunoDTO)
                              .collect(Collectors.toList());
    }

    public AlunoDTO criarAluno(AlunoDTO alunoDTO) {
        //System.out.println(alunoDTO);
        //System.out.println(programaRepository.findById(alunoDTO.getProgramaId()));
        Aluno aluno = AlunoMapper.toAluno(alunoDTO, programaRepository.findById(alunoDTO.getProgramaId()));
        //System.out.println(aluno);
        Aluno savedAluno = alunoRepository.save(aluno);
        //System.out.println(savedAluno);

        return AlunoMapper.toAlunoDTO(savedAluno);
    }
}
