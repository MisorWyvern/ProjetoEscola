package br.com.gabrielrosim.projetoescola.dto;

import br.com.gabrielrosim.projetoescola.model.Mentoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

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
