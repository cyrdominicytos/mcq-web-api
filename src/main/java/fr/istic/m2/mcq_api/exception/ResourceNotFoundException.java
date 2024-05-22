package fr.istic.m2.mcq_api.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Customize the response for different types of exceptions, such as setting HTTP
 */
@ControllerAdvice
public class ResourceNotFoundException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ressource recherchée n'existe pas !{"+ex.getMessage()+"}");
    }
}


//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseStatus;
//@ControllerAdvice
//@ResponseStatus(HttpStatus.NOT_FOUND)
//public class ResourceNotFoundException extends RuntimeException {
//    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
//        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
//    }
//
//    public ResourceNotFoundException(String message){
//        super(message);
//    }
//}




