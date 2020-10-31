package br.com.gabrielrosim.projetoescola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MentorDTO {
    private Long id;
    @NotBlank(message = "Nome e obrigatorio")
    private String nome;
    @NotBlank(message = "CPF e obrigatorio")
    private String cpf;
}
