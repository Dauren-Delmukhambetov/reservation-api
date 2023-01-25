package api.reservation.handler;

import api.reservation.exception.ClientException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.util.Map.of;

@RequiredArgsConstructor
public abstract class ApiGatewayEventHandler<T, R> extends BaseHandler<T, R, APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    protected final Class<T> inputType;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        try {
            log(context, "Starting processing request %s with input: %s", input.getRequestContext().getRequestId(), input.getBody());

            final var request = getRequestData(input);
            final var response = gson.toJson(doHandleRequest(request, context));

            log(context, "Completed processing request %s with output: %s", input.getRequestContext().getRequestId(), response);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(headers)
                    .withBody(response);
        } catch (ClientException e) {
            log(context, "Client exception occurred while processing request %s: %s", input.getRequestContext().getRequestId(), e.getMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(e.getStatusCode())
                    .withHeaders(headers)
                    .withBody(e.getMessage());
        } catch (Exception e) {
            log(context, "Error occurred while processing request %s: %s", input.getRequestContext().getRequestId(), e.getMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withHeaders(headers)
                    .withBody("Error occurred while processing request: " + e.getMessage());
        }
    }

    protected T getRequestData(final APIGatewayProxyRequestEvent input) {
        return gson.fromJson(input.getBody(), inputType);
    }

    protected static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(OffsetDateTime.class,
                    (JsonDeserializer<OffsetDateTime>) (json, type, context) -> OffsetDateTime.parse(json.getAsString()))
            .registerTypeAdapter(OffsetDateTime.class,
                    (JsonSerializer<OffsetDateTime>) (offsetDateTime, type, context) -> new JsonPrimitive(offsetDateTime.format(ISO_OFFSET_DATE_TIME)))
            .create();

    protected static final Map<String, String> headers = of(
            "Content-Type", "application/json",
            "Access-Control-Allow-Origin", "*",
            "Access-Control-Allow-Methods", "OPTIONS,POST,GET",
            "Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,X-Forwarded-For"
    );
}
