package api.reservation.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ClientException extends RuntimeException {
    Integer statusCode;
    String message;
}
