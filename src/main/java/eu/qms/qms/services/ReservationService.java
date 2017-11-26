package eu.qms.qms.services;

import eu.qms.qms.model.ReservationEntity;
import eu.qms.qms.model.ReservationListObject;
import eu.qms.qms.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public ReservationListObject getReservation(Integer studentId) {
        ReservationEntity reservationEntity = reservationRepository.findTopByStudentId(studentId).get();
        Integer numberOfReservationBeforeThisOne = getNumberOfReservationsBefore(reservationEntity.getReservedOn());
        ReservationListObject reservationListObject = new ReservationListObject(reservationEntity);
        reservationListObject.setPosition(numberOfReservationBeforeThisOne+1);
        ZonedDateTime nextMonday = getNextMonday();
        reservationListObject.setEstimatedTime(DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy").format(nextMonday.plusMinutes(numberOfReservationBeforeThisOne*5)));
        return reservationListObject;
    }

    private ZonedDateTime getNextMonday() {
        ZonedDateTime nextMonday = ZonedDateTime.now();
        while (nextMonday.getDayOfWeek() != DayOfWeek.MONDAY) {
            nextMonday = nextMonday.plusDays(1);
        }
        nextMonday = nextMonday.withHour(9).withMinute(0).withSecond(0).withNano(0);
        return nextMonday;
    }

    public void deleteReservation(String reservationToken) {
        reservationRepository.delete(reservationRepository.findByReservationToken(reservationToken).get());
    }
}
