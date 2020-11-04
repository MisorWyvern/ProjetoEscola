package br.com.gabrielrosim.projetoescola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisciplinaDTO {
    private Long id;
    @Size(max = 50, message = "O Nome deve ter no maximo 50 caracteres.")
    @NotBlank(message = "Nome da Disciplina e obrigatorio.")
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    @Min(value = 0, message = "O valor da nota nao pode ser inferior a zero.")
    @Max(value = 10, message = "O valor da nota nao pode ser superior a dez.")
    private Double nota;
}
