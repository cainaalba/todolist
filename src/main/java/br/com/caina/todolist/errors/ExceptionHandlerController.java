package br.com.caina.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

///CLASSE PARA EXIBIR EXCEÇOES CUSTOMIZADAS

@ControllerAdvice //DEFINE CLASSES GLOBAIS NO MOMENTO DE TRATAMENTO DE EXCEÇÕES
//SE FOR DO TIPO QUE ESTAMOS TRATANDO, USA NOSSO THROW NEW
public class ExceptionHandlerController {

    //HttpMessageNotReadableException ESTÁ NO CONSOLE, DURANTE A EXCEÇÃO
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpNotRead(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
    }
    
}
