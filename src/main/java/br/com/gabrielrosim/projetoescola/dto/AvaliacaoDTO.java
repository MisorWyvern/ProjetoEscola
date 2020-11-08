package br.com.gabrielrosim.projetoescola.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AvaliacaoDTO {
    private Long id;
    @Min(value = 0)
    @NotNull(message = "Id da Disciplina e obrigatorio.")
    private Long idDisciplina;
    @Min(value = 0)
    @NotNull(message = "Id do Tipo de Avaliacao e obrigatorio.")
    private Long idTipoAvaliacao;
    @NotNull(message = "Id do Aluno e obrigatorio.")
    private Long idAluno;
    @Min(value = 0, message = "Valor da nota deve ser superior ou igual a 0.")
    @Max(value = 10, message = "Valor da nota deve ser inferior ou igual a 10.")
    private Double nota;
    private LocalDate dataAplicacao;
}
