package br.com.gabrielrosim.projetoescola.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlunoDTO {
    private Long id;
    @NotBlank(message = "Nome e obrigatorio")
    @Size(max = 50, message = "Nome do aluno nao pode ter mais que 50 caracteres.")
    private String nome;
    @NotBlank(message = "CPF e obrigatorio")
    @Size(min = 14, max = 14, message = "CPF invalido. Deve ser como XXX.XXX.XXX-XX")
    private String cpf;
    @NotNull(message = "Programa e obrigatorio")
    private Long idPrograma;

}
