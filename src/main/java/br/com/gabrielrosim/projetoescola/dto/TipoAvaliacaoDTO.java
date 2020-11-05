package br.com.gabrielrosim.projetoescola.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class TipoAvaliacaoDTO {
    private Long id;
    @NotBlank(message = "Codigo do tipo da avaliacao e obrigatorio.")
    @Size(max = 5, message = "Codigo da avaliacao deve conter no maximo 5 caracteres.")
    private String codigo;
}
