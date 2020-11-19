package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.MentoriaDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.MentoriaMapper;
import br.com.gabrielrosim.projetoescola.exception.MentoriaCurrentlyInUseException;
import br.com.gabrielrosim.projetoescola.model.Mentoria;
import br.com.gabrielrosim.projetoescola.repository.MentoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentoriaService {
    @Autowired
    private MentoriaRepository mentoriaRepository;

    @Autowired
    private MentoriaMapper mentoriaMapper;

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private MentorService mentorService;
    @Autowired
    private DisciplinaService disciplinaService;

    public List<MentoriaDTO> getMentorias(Optional<Boolean> active) {
        if (active.isEmpty()) {
            return mentoriaRepository.findAll()
                    .parallelStream()
                    .map(mentoriaMapper::toMentoriaDTO)
                    .collect(Collectors.toList());
        }


        if (mentoriaRepository.findByActive(active.get()).isEmpty()) {
            return List.of();
        }

        return mentoriaRepository.findByActive(active.get()).get()
                .parallelStream()
                .map(mentoriaMapper::toMentoriaDTO)
                .collect(Collectors.toList());

    }


    public Optional<MentoriaDTO> criarMentoria(MentoriaDTO dto) {

        if(alunoService.getAlunoByIndex(dto.getIdAluno()).isEmpty()){
            return Optional.empty();
        }

        if(mentorService.getMentorByIndex(dto.getIdMentor()).isEmpty()){
            return Optional.empty();
        }

        if(disciplinaService.getDisciplinaByIndex(dto.getIdDisciplina()).isEmpty()){
            return Optional.empty();
        }

        Mentoria mentoria = mentoriaMapper.toMentoria(dto);

        if(mentoriaRepository.findByAlunoAndMentorAndDisciplina(mentoria.getAluno(), mentoria.getMentor(), mentoria.getDisciplina()).isPresent()){
            throw new MentoriaCurrentlyInUseException();
        }

        mentoria.setActive(true);

        Mentoria savedMentoria = mentoriaRepository.save(mentoria);
        return Optional.of(mentoriaMapper.toMentoriaDTO(savedMentoria));
    }

    public Optional<MentoriaDTO> getMentoriaByIndex(Long id) {
        Optional<Mentoria> mentoria = mentoriaRepository.findById(id);
        return mentoria.map(ment -> mentoriaMapper.toMentoriaDTO(ment));
    }

    @Transactional
    public Boolean atualizarMentoria(Long id, MentoriaDTO dto){
        Optional<Mentoria> mentoria = mentoriaRepository.findById(id);

        if(mentoria.isEmpty()){
            return Boolean.FALSE;
        }

        Mentoria mentoriaFromdto = mentoriaMapper.toMentoria(dto);

        Optional<Mentoria> mentoriaAMD = mentoriaRepository.findByAlunoAndMentorAndDisciplina(mentoriaFromdto.getAluno(), mentoriaFromdto.getMentor(), mentoriaFromdto.getDisciplina());
        if(mentoriaAMD.isPresent()){
            if(!mentoriaAMD.get().getId().equals(id)){
                throw new MentoriaCurrentlyInUseException();
            }
        }

        if(mentoriaFromdto.getAluno() != null){
            mentoria.get().setAluno(mentoriaFromdto.getAluno());
        }

        if(mentoriaFromdto.getMentor() != null){
            mentoria.get().setMentor(mentoriaFromdto.getMentor());
        }

        if(mentoriaFromdto.getDisciplina() != null){
            mentoria.get().setDisciplina(mentoriaFromdto.getDisciplina());
        }

        return Boolean.TRUE;
    }

    @Transactional
    public Boolean reativarMentoria(Long id) {
        Optional<Mentoria> mentoria = mentoriaRepository.findById(id);

        if(mentoria.isEmpty()){
            return Boolean.FALSE;
        }

        mentoria.get().setActive(true);
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean deletarMentoria(Long id) {
        Optional<Mentoria> mentoria = mentoriaRepository.findById(id);

        if(mentoria.isEmpty()){
            return Boolean.FALSE;
        }

        mentoria.get().setActive(false);
        return Boolean.TRUE;
    }


}
