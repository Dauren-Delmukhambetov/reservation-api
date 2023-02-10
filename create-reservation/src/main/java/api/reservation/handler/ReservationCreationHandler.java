package api.reservation.handler;

import api.reservation.exception.ClientException;
import api.reservation.mapper.ReservationMapper;
import api.reservation.model.CreateReservationRequest;
import api.reservation.model.Reservation;
import api.reservation.util.ValidationUtils;
import com.amazonaws.services.lambda.runtime.Context;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class ReservationCreationHandler extends ApiGatewayEventHandler<CreateReservationRequest, Reservation> {
    public ReservationCreationHandler() { super(CreateReservationRequest.class); }

    @Override
    protected Reservation doHandleRequest(CreateReservationRequest input, Context context) {
        validate(input);
        return mapper.toReservation(input);
    }

    private void validate(CreateReservationRequest request) {
        final var validator = factory.getValidator();
        final var violations = validator.validate(request);
        if (violations.isEmpty()) { return; }
        final var reasons = violations.stream().map(ValidationUtils::toErrorMessage).collect(joining(";"));
        throw new ClientException(400, format("Validation failed with the next violations: %s", reasons));
    }

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final ReservationMapper mapper = ReservationMapper.getInstance();
}
