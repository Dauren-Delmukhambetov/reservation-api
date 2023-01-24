package api.reservation.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Customer {
    @NotEmpty
    String name;
    String phone;
    String email;
    String telegramUsername;
}
