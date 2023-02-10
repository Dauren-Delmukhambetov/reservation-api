package api.reservation.model;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class Reservation {
    String uuid;
    String serviceCode;
    String craftsmanUsername;
    Customer customer;
    OffsetDateTime startTime;
    OffsetDateTime creationTime;
    OffsetDateTime lastUpdateTime;
}
