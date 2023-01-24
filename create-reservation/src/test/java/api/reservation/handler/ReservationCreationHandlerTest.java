package api.reservation.handler;

import api.reservation.exception.ClientException;
import api.reservation.model.CreateReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static api.reservation.generator.CustomerGenerator.newCustomer;
import static api.reservation.generator.ReservationGenerator.newCreateReservationRequest;
import static api.reservation.generator.ReservationGenerator.newReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationCreationHandlerTest {

    private final ReservationCreationHandler handler = new ReservationCreationHandler();

    @ParameterizedTest
    @MethodSource("generateInvalidCreateReservationRequests")
    @DisplayName("should fail on invalid input")
    public void shouldFailOnInvalidInput(CreateReservationRequest request) {
        assertThrows(ClientException.class, () -> handler.doHandleRequest(request, null));
    }

    @Test
    @DisplayName("should create and return a new reservation")
    public void shouldReturnReservation() {
        final var actual = handler.doHandleRequest(newCreateReservationRequest(), null);
        final var expected = newReservation();

        assertThat(actual)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .ignoringFields("uuid", "creationTime", "lastUpdateTime")
                .isEqualTo(expected);
    }

    private static Stream<Arguments> generateInvalidCreateReservationRequests() {
        return Stream.of(
                Arguments.of(Named.of("with empty serviceCode", newCreateReservationRequest(b -> b.serviceCode("")))),
                Arguments.of(Named.of("with null serviceCode", newCreateReservationRequest(b -> b.serviceCode(null)))),
                Arguments.of(Named.of("with null craftsmanUsername", newCreateReservationRequest(b -> b.craftsmanUsername(null)))),
                Arguments.of(Named.of("with empty craftsmanUsername", newCreateReservationRequest(b -> b.craftsmanUsername("")))),
                Arguments.of(Named.of("with null startTime", newCreateReservationRequest(b -> b.startTime(null)))),
                Arguments.of(Named.of("with startTime in the past", newCreateReservationRequest(b -> b.startTime(OffsetDateTime.now().minusMinutes(30))))),
                Arguments.of(Named.of("with startTime in present", newCreateReservationRequest(b -> b.startTime(OffsetDateTime.now())))),
                Arguments.of(Named.of("with null customer", newCreateReservationRequest(b -> b.customer(null)))),
                Arguments.of(Named.of("with customer with empty name", newCreateReservationRequest(b -> b.customer(newCustomer(c -> c.name("")))))),
                Arguments.of(Named.of("with customer with null name", newCreateReservationRequest(b -> b.customer(newCustomer(c -> c.name(null))))))
        );
    }
}
