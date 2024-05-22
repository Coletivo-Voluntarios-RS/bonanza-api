package ong.bonanza.beneficiarioapi.adapter.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handle(Throwable e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(String.join(";", e.getAllErrors().stream().map(objectError -> toString(objectError)).toList()));
    }

    private static String toString(ObjectError objectError) {

        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            return String.format("campo=[%s] mensagem=[%s]", fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            return String.format(" mensagem=[%s]", objectError.getDefaultMessage());
        }

    }

}