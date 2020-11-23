package br.com.gabrielrosim.projetoescola.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AvaliacaoCurrentlyInUseException.class,
                               CodigoAlreadyExistsException.class,
                               CpfCurrentlyInUseException.class,
                               MentoriaCurrentlyInUseException.class,
                               MentorIsPresentInProgramaException.class,
                               ProgramaContainsMentoresException.class,
                               ProgramaCurrentlyInUseException.class,
                               })
    public final ResponseEntity<ExceptionResponse> handleCodigoAlreadyExists(RuntimeException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getServletPath(),
                ex.getClass().getName()
        );
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, httpStatus);
        }
    }
