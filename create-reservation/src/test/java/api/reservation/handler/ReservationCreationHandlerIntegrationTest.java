package api.reservation.handler;

import api.reservation.TestContext;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.params.ParameterizedTest;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class ReservationCreationHandlerIntegrationTest {

    private final ReservationCreationHandler handler = new ReservationCreationHandler();

    @ParameterizedTest
    @Event(value = "events/api-gateway-event.json", type = APIGatewayProxyRequestEvent.class)
    public void shouldHandleApiGatewayEvent(APIGatewayProxyRequestEvent event) {
        assertThat(handler.handleRequest(event, new TestContext()))
                .isNotNull()
                .extracting("body", as(InstanceOfAssertFactories.STRING))
                .isNotEmpty()
                .containsPattern("\"creationTime\": \"" + todayDatePattern() + "\"")
                .containsPattern("\"uuid\": \".*\"");
    }

    protected static String todayDatePattern() {
        final var today = OffsetDateTime.now();
        return String.format("%d-%02d-%dT\\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}\\+\\d{2}:\\d{2}", today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }
}
