package api.reservation.mapper;

import api.reservation.model.CreateReservationRequest;
import api.reservation.model.Reservation;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ReservationMapper {

    private ReservationMapper() {}

    public Reservation toReservation(CreateReservationRequest request) {
        return Reservation.builder()
                .uuid(UUID.randomUUID().toString())
                .serviceCode(request.getServiceCode())
                .craftsmanUsername(request.getCraftsmanUsername())
                .customer(request.getCustomer())
                .startTime(request.getStartTime())
                .creationTime(OffsetDateTime.now())
                .lastUpdateTime(OffsetDateTime.now())
                .build();
    }

    public static ReservationMapper getInstance() {
        return new ReservationMapper();
    }
}
