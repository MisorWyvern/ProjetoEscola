package br.com.gabrielrosim.projetoescola.dto.mapper;

import br.com.gabrielrosim.projetoescola.dto.MentoriaDTO;
import br.com.gabrielrosim.projetoescola.model.Mentoria;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(MentoriaMapperDecorator.class)
public interface MentoriaMapper {
    public Mentoria toMentoria(MentoriaDTO mentoriaDTO);
    public MentoriaDTO toMentoriaDTO(Mentoria mentoria);
}
