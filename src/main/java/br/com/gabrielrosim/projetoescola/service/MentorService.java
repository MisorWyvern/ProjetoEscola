package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.MentorMapper;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    MentorMapper mentorMapper;

    public List<MentorDTO> getMentores() {
        if (mentorRepository.findByActive(true).isPresent()) {
            return mentorRepository.findByActive(true).get()
                    .parallelStream()
                    .map(mentorMapper::toMentorDTO)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    public Optional<MentorDTO> getMentorByIndex(Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);
        if (mentor.isPresent()) {
            if (mentor.get().getActive()) {
                return mentor.map(mentorMapper::toMentorDTO);
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public MentorDTO criarMentor(MentorDTO dto) {
        Mentor mentor = mentorMapper.toMentor(dto);
        Mentor savedMentor = mentorRepository.save(mentor);
        System.out.println("Saved Mentor: " + savedMentor);
        return mentorMapper.toMentorDTO(savedMentor);
    }

    @Transactional
    public void atualizarMentor(Long id, MentorDTO dto) {
        Optional<Mentor> mentor = mentorRepository.findById(id);
        if (mentor.isPresent()) {
            mentor.get().setNome(dto.getNome());
            mentor.get().setCpf(dto.getCpf());
            mentor.get().setActive(true);
        } else {
            criarMentor(dto);
        }
    }

    @Transactional
    public void deletarMentor(Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);
        mentor.ifPresent(mtr -> mtr.setActive(false));
    }
}
