package br.com.gabrielrosim.projetoescola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProgramaCurrentlyInUseException extends RuntimeException {
    public ProgramaCurrentlyInUseException(String message) {
        super(message);
    }
}
