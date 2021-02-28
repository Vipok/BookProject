package book.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CreateErrorException extends Exception {
    @Getter
    private HttpStatus httpStatus;

    public CreateErrorException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
