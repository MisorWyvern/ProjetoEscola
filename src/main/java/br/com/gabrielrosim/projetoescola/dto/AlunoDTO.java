package br.com.gabrielrosim.projetoescola.dto;

import br.com.gabrielrosim.projetoescola.model.Programa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlunoDTO {
    private Long id;
    @NotBlank(message = "Nome e obrigatorio")
    private String nome;
    @NotBlank(message = "CPF e obrigatorio")
    @Size(min = 14, max = 14, message = "CPF invalido. Deve ser como XXX.XXX.XXX-XX")
    private String cpf;
    @NotNull(message = "Programa e obrigatorio")
    private Long programaId;

}
