package book.exceptions;

import book.model.ErrorResponseFields;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//костыль для отображения ошибок в ответе сервиса
public class RestExceptionHandler {
    @ExceptionHandler(value = CreateErrorException.class)
    public ResponseEntity<ErrorResponseFields> exception(CreateErrorException exception) {
        return new ResponseEntity<>(new ErrorResponseFields(exception.getMessage(), exception.getHttpStatus().value()), exception.getHttpStatus());
    }
}
