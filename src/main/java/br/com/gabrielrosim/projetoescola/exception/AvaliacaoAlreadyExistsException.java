package br.com.gabrielrosim.projetoescola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "A avaliacao ja existe.", code = HttpStatus.BAD_REQUEST)
public class AvaliacaoAlreadyExistsException extends RuntimeException {
    public AvaliacaoAlreadyExistsException() {
    }
}
