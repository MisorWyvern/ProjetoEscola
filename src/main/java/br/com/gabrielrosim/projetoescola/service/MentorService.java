package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.MentorMapper;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
