package api.reservation.mapper;

import api.reservation.model.Reservation;
import com.google.gson.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.function.Function;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static java.util.stream.Collectors.toMap;
import static software.amazon.awssdk.utils.StringUtils.isNotBlank;

public class ReservationMapper implements DynamoDbEntityMapper<Reservation> {

    public static final String RESERVATION_ID = "ReservationId";
    public static final String SERVICE_CODE = "UserEmail";
    public static final String CRAFTSMAN_USERNAME = "CraftsmanUsername";
    public static final String CUSTOMER = "Customer";
    public static final String START_TIME = "StartTime";
    public static final String CREATION_TIME = "CreatedAt";
    public static final String LAST_UPDATE_TIME = "LastUpdatedAt";
    public static final String TENANT_ID = "TenantId";
    public static final String USER_ID = "UserId";

    public Map<String, AttributeValue> mapToRecord(Reservation reservation) {
        return reservationGetters.entrySet()
                .stream()
                .filter(e -> isNotBlank(e.getValue().apply(reservation)))
                .collect(
                        toMap(
                                Map.Entry::getKey,
                                e -> stringAttribute(e.getValue().apply(reservation))
                        )
                );
    }

    private AttributeValue stringAttribute(String value) {
        return AttributeValue.builder().s(value).build();
    }

    private final Map<String, Function<Reservation, String>> reservationGetters = Map.of(
            RESERVATION_ID, Reservation::getUuid,
            SERVICE_CODE, Reservation::getServiceCode,
            CRAFTSMAN_USERNAME, Reservation::getCraftsmanUsername,
            CUSTOMER, reservation -> gson.toJson(reservation.getCustomer()),
            START_TIME, reservation -> reservation.getStartTime().format(ISO_OFFSET_DATE_TIME),
            CREATION_TIME, reservation -> reservation.getCreationTime().format(ISO_OFFSET_DATE_TIME),
            LAST_UPDATE_TIME, reservation -> reservation.getLastUpdateTime().format(ISO_OFFSET_DATE_TIME),
            TENANT_ID, reservation -> "tenantId#public",
            USER_ID, reservation -> "userId#anonymous"
    );

    protected static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(OffsetDateTime.class,
                    (JsonDeserializer<OffsetDateTime>) (json, type, context) -> OffsetDateTime.parse(json.getAsString()))
            .registerTypeAdapter(OffsetDateTime.class,
                    (JsonSerializer<OffsetDateTime>) (offsetDateTime, type, context) -> new JsonPrimitive(offsetDateTime.format(ISO_OFFSET_DATE_TIME)))
            .create();

}
