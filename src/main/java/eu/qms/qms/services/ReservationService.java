package eu.qms.qms.services;

import eu.qms.qms.model.ReservationEntity;
import eu.qms.qms.model.ReservationListObject;
import eu.qms.qms.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void addReservation(ReservationEntity reservation) {
        reservationRepository.save(reservation);
    }

    public void confirmReservation(String reservationToken) {
        Optional<ReservationEntity> reservationEntity = reservationRepository.findByReservationTokenAndConfirmed(reservationToken, false);
        reservationEntity.get().setConfirmed(true);
        reservationRepository.delete(reservationEntity.get().getId());
        reservationRepository.save(reservationEntity.get());
    }

    public List<ReservationEntity> getReservations() {
        List<ReservationEntity> reservationEntityList = reservationRepository.findAll();
        return reservationEntityList.stream().filter(ReservationEntity::getConfirmed).collect(Collectors.toList());
    }

    /*public ReservationEntity getReservation(String reservationToken) {
        return reservationRepository.findTopByReservationToken(reservationToken).get();
    }*/

    public Integer getNumberOfReservationsBefore(ZonedDateTime time) {
        return reservationRepository.countAllByReservedOnBeforeAndConfirmed(time, true);
    }



    public ReservationListObject getReservation(Integer studentId, Boolean isConfirmed) {
        ReservationEntity reservationEntity = reservationRepository.findTopByStudentIdAndConfirmed(studentId, isConfirmed).get();
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

    public void deleteReservation(String reservationToken, Boolean isConfirmed) {
        reservationRepository.delete(reservationRepository.findByReservationTokenAndConfirmed(reservationToken, isConfirmed).get());
    }
}
