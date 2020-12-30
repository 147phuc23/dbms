package vn.elca.training.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.elca.training.model.exception.ConcurrentUpdateException;
import vn.elca.training.model.exception.IllegalUserFormException;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.exception.VisasDoesNotExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Log logger = LogFactory.getLog(getClass());

    @ExceptionHandler(IllegalUserFormException.class)
    public final ResponseEntity<Object> handleIllegalUserFormException(
            IllegalUserFormException e, WebRequest request
    ){
        logger.error(e.getMessage(), e);
        String details = e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse("Illegal User Form", details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectNumberAlreadyExistsException.class)
    public final ResponseEntity<Object> handleProjectNumberAlreadyExistsException(
            ProjectNumberAlreadyExistsException e, WebRequest request
    ){
        logger.error(e.getMessage(), e);
        String details = e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse("Project Number Already Exists", details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConcurrentUpdateException.class)
    public final ResponseEntity<Object> handleConcurrentUpdateException(
            ConcurrentUpdateException e, WebRequest request
    ){
        logger.error(e.getMessage(), e);
        String details = e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse("Concurrently Updated", details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VisasDoesNotExistsException.class)
    public final ResponseEntity<Object> handleVisasDoesNotExistsException(
            VisasDoesNotExistsException e, WebRequest request
    ){
        logger.error(e.getMessage(), e);
        String visas = String.join(",", e.getVisas());
        ErrorResponse errorResponse = new ErrorResponse("Some Visas Does Not Exists", visas);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request){
        logger.error(e.getMessage(), e);
        String details = e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse("Server Error", details);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
