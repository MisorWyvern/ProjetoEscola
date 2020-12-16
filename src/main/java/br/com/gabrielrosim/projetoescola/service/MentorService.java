package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.MentorMapper;
import br.com.gabrielrosim.projetoescola.exception.CpfCurrentlyInUseException;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private MentorMapper mentorMapper;

    public Page<MentorDTO> getMentores(Optional<Boolean> active, Pageable pageable) {
        if (active.isEmpty()) {
            return mentorRepository.findAll(pageable)
                    .map(mentorMapper::toMentorDTO);
        }

        if (mentorRepository.findByActive(active.get(), pageable).isEmpty()) {
            return Page.empty();
        }

        return mentorRepository.findByActive(active.get(), pageable).get()
                .map(mentorMapper::toMentorDTO);
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
