package eu.qms.qms.repositories;

import eu.qms.qms.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    Optional<ReservationEntity> findByReservationTokenAndConfirmed(String reservationToken, Boolean isConfirmed);
    Optional<ReservationEntity> findTopByStudentIdAndConfirmed(Integer studentId, Boolean isConfirmed);
    Integer countAllByReservedOnBeforeAndConfirmed(ZonedDateTime estimatedTime, Boolean isConfirmed);

}
