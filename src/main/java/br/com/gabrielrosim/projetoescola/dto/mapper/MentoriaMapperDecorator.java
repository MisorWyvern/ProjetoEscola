package br.com.gabrielrosim.projetoescola.dto.mapper;


import br.com.gabrielrosim.projetoescola.dto.MentoriaDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.model.Mentoria;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import br.com.gabrielrosim.projetoescola.repository.DisciplinaRepository;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
import br.com.gabrielrosim.projetoescola.repository.MentoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MentoriaMapperDecorator implements MentoriaMapper {

    @Autowired
    @Qualifier("delegate")
    private MentoriaMapper delegate;

    @Autowired
    private MentoriaRepository mentoriaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Override
    public Mentoria toMentoria(MentoriaDTO mentoriaDTO) {
        Mentoria mentoria = delegate.toMentoria(mentoriaDTO);

        if(mentoriaDTO.getIdAluno() != null){
            Optional<Aluno> aluno = alunoRepository.findById(mentoriaDTO.getIdAluno());
            aluno.ifPresent(mentoria::setAluno);
        }

        if(mentoriaDTO.getIdMentor() != null){
            Optional<Mentor> mentor = mentorRepository.findById(mentoriaDTO.getIdMentor());
            mentor.ifPresent(mentoria::setMentor);
        }

        if(mentoriaDTO.getIdDisciplina() != null){
            Optional<Disciplina> disciplina = disciplinaRepository.findById(mentoriaDTO.getIdDisciplina());
            disciplina.ifPresent(mentoria::setDisciplina);
        }

        if (mentoriaDTO.getId() != null){
            Optional<Mentoria> mentoriaFromRepo = mentoriaRepository.findById(mentoria.getId());
            mentoriaFromRepo.ifPresent(mfr -> mentoria.setActive(mfr.getActive()));
        }

        return mentoria;
    }

    @Override
    public MentoriaDTO toMentoriaDTO(Mentoria mentoria) {
        MentoriaDTO mentoriaDTO = delegate.toMentoriaDTO(mentoria);

        if(mentoria.getAluno() != null){
            if(mentoria.getAluno().getId() != null){
                mentoriaDTO.setIdAluno(mentoria.getAluno().getId());
            }
        }

        if(mentoria.getMentor() != null){
            if(mentoria.getMentor().getId() != null){
                mentoriaDTO.setIdMentor(mentoria.getMentor().getId());
            }
        }

        if(mentoria.getDisciplina() != null){
            if(mentoria.getDisciplina().getId() != null){
                mentoriaDTO.setIdDisciplina(mentoria.getDisciplina().getId());
            }
        }

        return mentoriaDTO;
    }
}
