package api.reservation.generator;

import api.reservation.model.Customer;

import java.util.function.Consumer;

public class CustomerGenerator {

    public static Customer newCustomer() {
        return newCustomer(b -> {});
    }

    public static Customer newCustomer(Consumer<Customer.CustomerBuilder> updater) {
        final var builder = newBuilder();
        updater.accept(builder);
        return builder.build();
    }

    private static Customer.CustomerBuilder newBuilder() {
        return Customer.builder()
                .name("client")
                .email("client@example.com")
                .phone("+1 (23) 456-78-90")
                .telegramUsername("@client");
    }
}
