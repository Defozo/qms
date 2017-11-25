package eu.qms.qms.services;

import eu.qms.qms.model.Reservation;
import eu.qms.qms.model.ReservationEntity;
import eu.qms.qms.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void addReservation(ReservationEntity reservation) {
        reservationRepository.save(reservation);
    }

    public List<ReservationEntity> getReservations() {
        return reservationRepository.findAll();
    }

    public ReservationEntity getReservation(String reservationToken) {
        return reservationRepository.findByReservationToken(reservationToken).get();
    }

    public Integer getNumberOfReservationsBefore(ZonedDateTime time) {
        return reservationRepository.countAllByReservedOnBefore(time);
    }

    public ReservationEntity getReservation(Integer studentId) {
        Optional<ReservationEntity> reservationEntityOptional = reservationRepository.findTopByStudentId(studentId);
        if (reservationEntityOptional.isPresent()) {
            System.out.println("jest :)");
        } else {
            System.out.println("nie ma :(");
        }
        return reservationEntityOptional.get();
    }

    public void deleteReservation(String reservationToken) {
        reservationRepository.delete(reservationRepository.findByReservationToken(reservationToken).get());
    }
}
