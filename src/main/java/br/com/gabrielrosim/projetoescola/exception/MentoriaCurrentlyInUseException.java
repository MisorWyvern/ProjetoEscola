package br.com.gabrielrosim.projetoescola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "A mentoria ja esta em uso.", code = HttpStatus.BAD_REQUEST)
public class MentoriaCurrentlyInUseException extends RuntimeException {
    public MentoriaCurrentlyInUseException() {
    }
}
