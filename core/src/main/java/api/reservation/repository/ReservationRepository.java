package api.reservation.repository;


import api.reservation.mapper.ReservationMapper;
import api.reservation.model.Reservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static api.reservation.util.EnvironmentUtils.getEnvVar;
import static api.reservation.util.EnvironmentUtils.getRegion;

@Getter
@RequiredArgsConstructor
public class ReservationRepository extends BaseDynamoDbRepository<Reservation>{
    private final DynamoDbClient dynamoDbClient;
    private final ReservationMapper mapper;
    private final String tableName;

    public void save(Reservation reservation) {
        persistAll(reservation);
    }

    public static ReservationRepository getInstance() {
        if (instance != null) return instance;
        instance = new ReservationRepository(
                DynamoDbClient.builder().region(getRegion())
                        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                        .build(),
                new ReservationMapper(),
                getEnvVar("RESERVATION_TABLE_NAME")
        );
        return instance;
    }

    private static ReservationRepository instance;
}
