package br.com.gabrielrosim.projetoescola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MentorDTO {
    private Long id;
    @Size(max = 50)
    @NotBlank(message = "Nome e obrigatorio")
    private String nome;
    @Size(min = 14, max = 14, message = "CPF invalido. Deve ser como XXX.XXX.XXX-XX")
    @NotBlank(message = "CPF e obrigatorio")
    private String cpf;
}
