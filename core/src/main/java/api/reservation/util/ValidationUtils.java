package api.reservation.util;

import jakarta.validation.ConstraintViolation;

public class ValidationUtils {

    public static String toErrorMessage(ConstraintViolation<?> violation) {
        return String.format("%s %s, actual is >%s<", violation.getPropertyPath().toString(), violation.getMessage(), violation.getInvalidValue());
    }
}
