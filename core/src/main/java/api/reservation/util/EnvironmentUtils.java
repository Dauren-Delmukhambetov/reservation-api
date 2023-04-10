package api.reservation.util;

import software.amazon.awssdk.regions.Region;

public class EnvironmentUtils {

    public static Region getRegion() {
        return Region.of(System.getenv("AWS_REGION"));
    }

    public static String getEnvVar(String varName) {
        return System.getenv(varName);
    }
}
