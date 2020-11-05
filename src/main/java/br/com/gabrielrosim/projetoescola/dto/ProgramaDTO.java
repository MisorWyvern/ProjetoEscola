package br.com.gabrielrosim.projetoescola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramaDTO {
    private Long id;
    @NotBlank(message = "Nome e obrigatorio")
    @Size(max = 50, message = "Nome do Programa nao pode ter mais que 50 caracteres.")
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
}
