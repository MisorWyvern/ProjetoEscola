package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.MentorMapper;
import br.com.gabrielrosim.projetoescola.exception.CpfCurrentlyInUseException;
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
    private MentorRepository mentorRepository;

    @Autowired
    private MentorMapper mentorMapper;

    public List<MentorDTO> getMentores(Optional<Boolean> active) {
        if (active.isEmpty()) {
            return mentorRepository.findAll()
                    .parallelStream()
                    .map(mentorMapper::toMentorDTO)
                    .collect(Collectors.toList());
        }

        if (mentorRepository.findByActive(active.get()).isEmpty()) {
            return List.of();
        }

        return mentorRepository.findByActive(active.get()).get()
                .parallelStream()
                .map(mentorMapper::toMentorDTO)
                .collect(Collectors.toList());
    }

    public Optional<MentorDTO> getMentorByIndex(Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);

        if (mentor.isEmpty()) {
            return Optional.empty();
        }

        return mentor.map(mentorMapper::toMentorDTO);
    }

    public Optional<MentorDTO> criarMentor(MentorDTO dto) {
        Mentor mentor = mentorMapper.toMentor(dto);

        if (mentorRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new CpfCurrentlyInUseException();
        }

        Mentor savedMentor = mentorRepository.save(mentor);
        return Optional.of(mentorMapper.toMentorDTO(savedMentor));
    }

    @Transactional
    public Boolean atualizarMentor(Long id, MentorDTO dto) {
        Optional<Mentor> mentor = mentorRepository.findById(id);

        if (mentor.isEmpty()) {
            return Boolean.FALSE;
        }

        Optional<Mentor> mentorByCpf = mentorRepository.findByCpf(dto.getCpf());
        if (mentorByCpf.isPresent()) {
            if (!mentorByCpf.get().getId().equals(mentor.get().getId())) {
                throw new CpfCurrentlyInUseException();
            }
        }

        mentor.get().setNome(dto.getNome());
        mentor.get().setCpf(dto.getCpf());
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean deletarMentor(Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);

        if (mentor.isEmpty()) {
            return Boolean.FALSE;
        }

        mentor.get().setActive(false);
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean activateMentor(Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);

        if (mentor.isEmpty()) {
            return Boolean.FALSE;
        }

        mentor.get().setActive(Boolean.TRUE);
        return Boolean.TRUE;
    }
}
