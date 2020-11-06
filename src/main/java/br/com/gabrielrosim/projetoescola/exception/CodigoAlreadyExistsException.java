package br.com.gabrielrosim.projetoescola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "O Codigo da avaliacao ja esta em uso.", code = HttpStatus.BAD_GATEWAY)
public class CodigoAlreadyExistsException extends RuntimeException{
    public CodigoAlreadyExistsException() {
    }
}
