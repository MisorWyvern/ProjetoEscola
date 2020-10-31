package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MentorMapperDecorator implements MentorMapper {

    @Autowired
    @Qualifier("delegate")
    private MentorMapper delegate;

    @Autowired
    MentorRepository mentorRepository;

    @Override
    public Mentor toMentor(MentorDTO mentorDTO) {
        Mentor mentor = delegate.toMentor(mentorDTO);

        if (mentor.getId() != null) {
            Optional<Mentor> mentorRepo = mentorRepository.findById(mentor.getId());
            if (mentorRepo.isPresent()) {
                mentor.setProgramas(mentorRepo.get().getProgramas());
                mentor.setMentorias(mentorRepo.get().getMentorias());
                mentor.setActive(mentorRepo.get().getActive());
            }
            else {
                mentor.setProgramas(List.of());
                mentor.setMentorias(List.of());
                mentor.setActive(true);
            }
        }
        else {
            mentor.setProgramas(List.of());
            mentor.setMentorias(List.of());
            mentor.setActive(true);
        }
        return mentor;
    }

    @Override
    public MentorDTO toMentorDTO(Mentor mentor) {
        MentorDTO dto = delegate.toMentorDTO(mentor);
        return dto;
    }
}
