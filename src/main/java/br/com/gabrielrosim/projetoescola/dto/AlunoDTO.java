package br.com.gabrielrosim.projetoescola.dto;

import br.com.gabrielrosim.projetoescola.model.Programa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlunoDTO {
    private Long id;
    private String nome;
    private String cpf;
    private Long programaId;

}
