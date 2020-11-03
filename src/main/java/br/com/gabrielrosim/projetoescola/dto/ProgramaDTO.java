package br.com.gabrielrosim.projetoescola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramaDTO {
    private Long id;
    @NotBlank(message = "Nome e obrigatorio")
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
}
