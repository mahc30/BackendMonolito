package backendJava.client.exception;

import backendJava.client.exception.Cliente.ClienteAlreadyExistsException;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
import backendJava.client.exception.Foto.FotoDeleteErrorException;
import backendJava.client.exception.Foto.FotoFileConversionErrorException;
import backendJava.client.exception.Foto.FotoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final String OCURRIO_UN_ERROR_FAVOR_CONTACTAR_AL_ADMINISTRADOR = "Ocurrió un error favor contactar al administrador.";
    private static final ConcurrentHashMap<String, Integer> STATUS_CODES = new ConcurrentHashMap<>();

    public ControllerAdvisor() {
        STATUS_CODES.put(
                ClienteNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND.value()
        );

        STATUS_CODES.put(
                ClienteAlreadyExistsException.class.getSimpleName(), HttpStatus.CONFLICT.value()
        );

        STATUS_CODES.put(
                FotoNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND.value()
        );
        STATUS_CODES.put(
                FotoFileConversionErrorException.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        STATUS_CODES.put(
                FotoDeleteErrorException.class.getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        STATUS_CODES.put(
                ConstraintViolationException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorObject> handleAllExceptions(Exception exception) {

        ResponseEntity<ErrorObject> result;

        String excepcionNombre = exception.getClass().getSimpleName();
        String mensaje = exception.getMessage();
        Integer codigo = STATUS_CODES.get(excepcionNombre);

        ErrorObject error = ErrorObject.builder()
                .exception_name(excepcionNombre)
                .status(codigo)
                .message(mensaje)
                .build();

        if (codigo != null) {
            result = new ResponseEntity<ErrorObject>(error, HttpStatus.valueOf(codigo));
        }
        else {
            result = new ResponseEntity<ErrorObject>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }
}