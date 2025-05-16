package lk.student.SMS.Exception;


import lk.student.SMS.Dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<MessageResponse> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage(), 0), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage(), 0), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGeneric(Exception ex) {
        return new ResponseEntity<>(new MessageResponse("Internal server error: " + ex.getMessage(), 0), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
