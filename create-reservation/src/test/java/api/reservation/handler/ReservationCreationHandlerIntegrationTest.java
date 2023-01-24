package api.reservation.handler;

import api.reservation.TestContext;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.params.ParameterizedTest;

import static api.reservation.util.JsonValidationUtils.ofAnyCharactersInProperty;
import static api.reservation.util.JsonValidationUtils.ofTodayDateInProperty;
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
                .containsPattern(ofTodayDateInProperty("creationTime"))
                .containsPattern(ofAnyCharactersInProperty("uuid"));
    }
}
