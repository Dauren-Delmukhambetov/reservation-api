package api.reservation.util;

import java.time.OffsetDateTime;

public class JsonValidationUtils {

    public static String ofTodayDateInProperty(String property) {
        final var today = OffsetDateTime.now();
        final var datePattern = String.format("%d-%02d-%02dT\\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}((\\+\\d{2}:\\d{2})|(Z))", today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        return String.format("\"%s\": \"%s\"", property, datePattern);
    }

    public static String ofAnyCharactersInProperty(String property) {
        return String.format("\"%s\": \"%s\"", property, ".*");
    }
}
