package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.MentorDTO;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import br.com.gabrielrosim.projetoescola.model.Mentoria;
import br.com.gabrielrosim.projetoescola.model.Programa;

import java.util.List;
import java.util.Optional;

public class MentorMapper {
    public static Mentor toMentor(MentorDTO dto, Optional<List<Programa>> programas, Optional<List<Mentoria>> mentorias){
        Mentor mentor = new Mentor(dto.getId(), dto.getNome(), dto.getCpf());
        programas.ifPresent(programaList -> mentor.setProgramas(List.copyOf(programaList)));
        mentorias.ifPresent(mentoriasList -> mentor.setMentorias(List.copyOf(mentoriasList)));
        mentor.setActive(true);
        return mentor;
    }

    public static MentorDTO toMentorDTO(Mentor mentor){
        return new MentorDTO(mentor.getId(), mentor.getNome(), mentor.getCpf());
    }
}
