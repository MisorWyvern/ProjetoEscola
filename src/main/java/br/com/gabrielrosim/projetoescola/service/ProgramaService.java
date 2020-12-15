package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.dto.ProgramaDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.MentorMapper;
import br.com.gabrielrosim.projetoescola.dto.mapper.ProgramaMapper;
import br.com.gabrielrosim.projetoescola.exception.MentorIsPresentInProgramaException;
import br.com.gabrielrosim.projetoescola.exception.ProgramaContainsMentoresException;
import br.com.gabrielrosim.projetoescola.exception.ProgramaCurrentlyInUseException;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    protected ProgramaMapper programaMapper;

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private MentorService mentorService;
    @Autowired
    private MentorMapper mentorMapper;

    public Page<ProgramaDTO> getProgramas(Pageable pageable) {
        return programaRepository.findAll(pageable)
                .map(programaMapper::toProgramaDTO);
    }

    public Optional<ProgramaDTO> getProgramaByIndex(Long id) {
        Optional<Programa> programa = programaRepository.findById(id);

        if (programa.isEmpty()) {
            return Optional.empty();
        }

        return programa.map(programaMapper::toProgramaDTO);
    }


    public ProgramaDTO criarPrograma(ProgramaDTO dto) {
        Programa programa = programaMapper.toPrograma(dto);
        if (programa.getDataInicio() == null) {
            programa.setDataInicio(LocalDate.now());
        }
        if (programa.getDataTermino() == null) {
            programa.setDataTermino(programa.getDataInicio().plusDays(90));
        }
        programa.setMentores(List.of());
        Programa savedPrograma = programaRepository.save(programa);
        return programaMapper.toProgramaDTO(savedPrograma);
    }

    @Transactional
    public Boolean atualizarPrograma(Long id, ProgramaDTO dto) {
        Optional<Programa> programa = programaRepository.findById(id);

        if (programa.isEmpty()) {
            return Boolean.FALSE;
        }

        programa.get().setNome(dto.getNome());
        if (dto.getDataInicio() != null) {
            programa.get().setDataInicio(dto.getDataInicio());
        }
        if (dto.getDataTermino() != null) {
            programa.get().setDataTermino(dto.getDataTermino());
        }
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean deletarPrograma(Long id) {
        Optional<Programa> programa = programaRepository.findById(id);

        if (programa.isEmpty()) {
            return Boolean.FALSE;
        }

        List<AlunoDTO> alunosByPrograma = alunoService.getAlunosByPrograma(programa.get());
        if (!alunosByPrograma.isEmpty()) {
            throw new ProgramaCurrentlyInUseException("Program em uso. Existem alunos vinculados a esse programa.");
        }

        if (!programa.get().getMentores().isEmpty()) {
            throw new ProgramaContainsMentoresException();
        }

        programaRepository.delete(programa.get());
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean addMentorToPrograma(Long idPrograma, Long idMentor) {
        Optional<Programa> programa = programaRepository.findById(idPrograma);
        Optional<Mentor> mentor = mentorService.getMentorByIndex(idMentor).map(mentorMapper::toMentor);


        if (programa.isEmpty() || mentor.isEmpty()) {
            return Boolean.FALSE;
        }

        if (programa.get().getMentores().contains(mentor.get())) {
            throw new MentorIsPresentInProgramaException();
        }

        programa.get().getMentores().add(mentor.get());
        mentor.get().getProgramas().add(programa.get());
        programaRepository.save(programa.get());
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean removeMentorFromPrograma(Long idPrograma, Long idMentor) {
        Optional<Programa> programa = programaRepository.findById(idPrograma);
        Optional<Mentor> mentor = mentorService.getMentorByIndex(idMentor).map(mentorMapper::toMentor);


        if (programa.isEmpty() || mentor.isEmpty()) {
            return Boolean.FALSE;
        }

        if (!programa.get().getMentores().contains(mentor.get())) {
            return Boolean.FALSE;
        }

        programa.get().getMentores().remove(mentor.get());
        mentor.get().getProgramas().remove(programa.get());
        return Boolean.TRUE;
    }

    public Page<MentorDTO> getMentoresFromProgramaByIndex(Long id, Pageable pageable) {
        return programaRepository.findAllMentoresFromProgramaById(id, pageable)
                .map(mentorMapper::toMentorDTO);
    }
}
