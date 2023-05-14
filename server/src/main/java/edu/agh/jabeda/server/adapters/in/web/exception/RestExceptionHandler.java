package edu.agh.jabeda.server.adapters.in.web.exception;

import edu.agh.jabeda.server.domain.exception.CategoryNotFoundException;
import edu.agh.jabeda.server.domain.exception.JabedaException;
import edu.agh.jabeda.server.domain.exception.RoleNotFoundException;
import edu.agh.jabeda.server.domain.exception.SubscriberAlreadyExistsException;
import edu.agh.jabeda.server.domain.exception.SubscriberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SubscriberAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<JabedaRestError> handleSubscriberAlreadyExistsException(
            SubscriberAlreadyExistsException e) {
        return new ResponseEntity<>(buildRestError(e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({SubscriberNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JabedaRestError> handleSubscriberNotFoundException(
            SubscriberNotFoundException e) {
        return new ResponseEntity<>(buildRestError(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JabedaRestError> handleRoleNotFoundException(
            RoleNotFoundException e) {
        return new ResponseEntity<>(buildRestError(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<JabedaRestError> handleCategoryNotFoundException(
            CategoryNotFoundException e) {
        return new ResponseEntity<>(buildRestError(e), HttpStatus.NOT_FOUND);
    }

    private JabedaRestError buildRestError(JabedaException e) {
        return JabedaRestError.builder()
                .message(e.getMessage())
                .errorCode(e.getErrorCode().getErrorCode())
                .build();
    }
}
