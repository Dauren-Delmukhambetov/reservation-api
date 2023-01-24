package api.reservation.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class CreateReservationRequest {
    @NotBlank
    String serviceCode;
    @NotBlank
    String craftsmanUsername;
    @NotNull @Valid
    Customer customer;
    @NotNull @Future
    OffsetDateTime startTime;
}
