package api.reservation.repository;

import api.reservation.mapper.DynamoDbEntityMapper;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class BaseDynamoDbRepository<T> {

    protected abstract String getTableName();

    protected abstract DynamoDbClient getDynamoDbClient();

    protected abstract DynamoDbEntityMapper<T> getMapper();

    protected void persistAll(T... entities) {
        final var writeRequests = Stream.of(entities)
                .map(getMapper()::mapToRecord)
                .map(this::mapToWriteRequest)
                .collect(toList());
        final var requestItems = Map.of(getTableName(), writeRequests);
        getDynamoDbClient().batchWriteItem(br -> br.requestItems(requestItems));
    }

    private WriteRequest mapToWriteRequest(Map<String, AttributeValue> record) {
        return WriteRequest.builder()
                .putRequest(pr -> pr.item(record))
                .build();
    }
}
