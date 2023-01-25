package api.reservation.generator;

import api.reservation.model.CreateReservationRequest;
import api.reservation.model.Reservation;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.function.Consumer;

import static api.reservation.generator.CustomerGenerator.newCustomer;

public class ReservationGenerator {

    public static final OffsetDateTime RESERVATION_START_TIME = OffsetDateTime.now().plusMinutes(30);
    public static final OffsetDateTime RESERVATION_CREATION_TIME = OffsetDateTime.now();

    public static CreateReservationRequest newCreateReservationRequest() {
        return newCreateReservationRequest(b -> {});
    }
    public static CreateReservationRequest newCreateReservationRequest(Consumer<CreateReservationRequest.CreateReservationRequestBuilder> updater) {
        final var builder = defaultCreateReservationRequestBuilder();
        updater.accept(builder);
        return builder.build();
    }

    public static Reservation newReservation() {
        return defaultReservationBuilder().build();
    }

    private static Reservation.ReservationBuilder defaultReservationBuilder() {
        return Reservation.builder()
                .uuid(UUID.randomUUID().toString())
                .serviceCode("haircut")
                .startTime(RESERVATION_START_TIME)
                .customer(newCustomer())
                .craftsmanUsername("barber")
                .creationTime(RESERVATION_CREATION_TIME);
    }

    private static CreateReservationRequest.CreateReservationRequestBuilder defaultCreateReservationRequestBuilder() {
        return CreateReservationRequest.builder()
                .serviceCode("haircut")
                .craftsmanUsername("barber")
                .customer(newCustomer())
                .startTime(RESERVATION_START_TIME);
    }
}
