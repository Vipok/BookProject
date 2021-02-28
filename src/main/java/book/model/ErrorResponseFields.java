package book.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseFields {
    String message;
    Integer statusCode;
}
