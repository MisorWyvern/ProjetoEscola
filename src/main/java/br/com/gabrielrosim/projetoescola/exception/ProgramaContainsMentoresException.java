package br.com.gabrielrosim.projetoescola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProgramaContainsMentoresException extends RuntimeException{
    public ProgramaContainsMentoresException() {
        super("O Programa contem Mentores vinculados a ele.");
    }
}
