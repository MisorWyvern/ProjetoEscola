package br.com.gabrielrosim.projetoescola.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AvaliacaoDTO {
    private Long id;
    @NotBlank(message = "Id da Disciplina e obrigatorio.")
    private Long idDisciplina;
    @NotBlank(message = "Id do Aluno e obrigatorio.")
    private Long idAluno;
    @NotBlank(message = "Id do Tipo de Avaliacao e obrigatorio.")
    private Long idTipoAvaliacao;
    private Double nota;
    private LocalDate dataAplicacao;
}
