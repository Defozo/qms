package eu.qms.qms.repositories;

import eu.qms.qms.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    Optional<ReservationEntity> findByReservationToken(String reservationToken);
    Optional<ReservationEntity> findTopByStudentId(Integer studentId);
    Integer countAllByReservedOnBefore(ZonedDateTime estimatedTime);

}
