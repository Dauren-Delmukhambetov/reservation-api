package api.reservation.mapper;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

public interface DynamoDbEntityMapper<T> {
    Map<String, AttributeValue> mapToRecord(T object);
}
