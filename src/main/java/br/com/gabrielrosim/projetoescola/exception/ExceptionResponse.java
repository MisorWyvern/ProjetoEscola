package br.com.gabrielrosim.projetoescola.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
public class ExceptionResponse {
    @Getter private Date timestamp;
    @Getter private Integer status;
    @Getter private String error;
    @Getter private String message;
    @Getter private String path;
    @Getter private String exception;
}
