package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.MentorMapper;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorService {

    @Autowired
    MentorRepository mentorRepository;

    public List<MentorDTO> getMentores() {
        if(mentorRepository.findByActive(true).isPresent()) {
            return mentorRepository.findByActive(true).get()
                    .parallelStream()
                    .map(MentorMapper::toMentorDTO)
                    .collect(Collectors.toList());
        }
        else{
            return List.of();
        }
    }

    public Optional<MentorDTO> getMentorByIndex(Long id) {
        return mentorRepository.findById(id)
                               .map(MentorMapper::toMentorDTO);
    }

    public MentorDTO criarMentor(MentorDTO dto) {
        Mentor mentor = MentorMapper.toMentor(dto, Optional.of(List.of()), Optional.of(List.of()));
        Mentor savedMentor = mentorRepository.save(mentor);
        return MentorMapper.toMentorDTO(savedMentor);
    }
}
