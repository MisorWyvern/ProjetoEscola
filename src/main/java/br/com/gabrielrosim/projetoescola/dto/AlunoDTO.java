package br.com.gabrielrosim.projetoescola.dto;

import br.com.gabrielrosim.projetoescola.model.Programa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlunoDTO {
    private Long id;
    @NotBlank(message = "Nome e obrigatorio")
    private String nome;
    @NotBlank(message = "CPF e obrigatorio")
    private String cpf;
    @NotBlank(message = "Programa e obrigatorio")
    private Long programaId;

}
