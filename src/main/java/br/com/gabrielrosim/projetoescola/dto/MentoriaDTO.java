package br.com.gabrielrosim.projetoescola.dto;

import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Disciplina;
import br.com.gabrielrosim.projetoescola.model.Mentor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MentoriaDTO {
    private Long id;

    @NotNull(message = "Id do aluno e obrigatorio.")
    private Long idAluno;

    @NotNull(message = "Id do mentor e obrigatorio")
    private Long idMentor;

    @NotNull(message = "Id da disciplina e obrigatorio")
    private Long idDisciplina;
}
