package ariefsyaifu.gymmem.exception;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest req, Exception e)
            throws Exception {

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            Map<String, Object> m = Map.of(
                    "message", ex.getFieldError().getDefaultMessage());
            return ResponseEntity.status(ex.getStatusCode()).body(m);
        }

        if (e instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) e;
            // Map<String, Object> m = Map.of("message", ex.getMessage());
            Map<String, Object> m = Map.of("message", ex.getReason());
            if (ex.getBody() != null) {
                m = Map.of("message", ex.getBody().getDetail());
            }
            return ResponseEntity.status(ex.getStatusCode()).body(m);
        }
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}