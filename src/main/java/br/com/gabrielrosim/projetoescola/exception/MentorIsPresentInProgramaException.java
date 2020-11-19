package br.com.gabrielrosim.projetoescola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MentorIsPresentInProgramaException extends RuntimeException {
    public MentorIsPresentInProgramaException() {
        super("O Mentor já faz parte deste Programa.");
    }
}
