package backendJava.client.exception;

import backendJava.client.exception.Cliente.ClienteAlreadyExistsException;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
import backendJava.client.exception.Foto.FotoDeleteErrorException;
import backendJava.client.exception.Foto.FotoFileConversionErrorException;
import backendJava.client.exception.Foto.FotoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorObject> handleClienteNotFoundException(ClienteNotFoundException ex) {
        ErrorObject error = ErrorObject.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<ErrorObject>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleClienteAlreadyExistsException(ClienteAlreadyExistsException ex) {
        ErrorObject error = ErrorObject.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<ErrorObject>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FotoNotFoundException.class)
    public ResponseEntity<ErrorObject> handleFotoNotFoundException(FotoNotFoundException ex){
        ErrorObject error = ErrorObject.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<ErrorObject>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FotoFileConversionErrorException.class)
    public ResponseEntity<ErrorObject> handleFotoFileConversionErrorException(FotoFileConversionErrorException ex){
        ErrorObject error = ErrorObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<ErrorObject>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FotoDeleteErrorException.class)
    public ResponseEntity<ErrorObject> handleFotoDeleteErrorException(FotoDeleteErrorException ex){
        ErrorObject error = ErrorObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<ErrorObject>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorObject> handleConstraintViolationException(ConstraintViolationException ex){
        String errorMessage = new ArrayList<>(ex.getConstraintViolations()).get(0).getMessage();

        ErrorObject error = ErrorObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .build();

        return new ResponseEntity<ErrorObject>(error, HttpStatus.BAD_REQUEST);
    }
}