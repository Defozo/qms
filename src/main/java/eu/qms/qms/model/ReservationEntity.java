package eu.qms.qms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Integer studentId;
    private String reason;
    private ZonedDateTime reservedOn;
    private String reservationToken;
    private Boolean confirmed;

    public ReservationEntity(String email, Integer studentId, String reason, ZonedDateTime reservedOn, String reservationToken) {
        this.email = email;
        this.studentId = studentId;
        this.reason = reason;
        this.reservedOn = reservedOn;
        this.reservationToken = reservationToken;
    }
    public ReservationEntity(Reservation reservation, ZonedDateTime reservedOn, String reservationToken) {
        this.email = reservation.getEmail();
        this.studentId = reservation.getStudentId();
        this.reason = reservation.getReason();
        this.reservedOn = reservedOn;
        this.reservationToken = reservationToken;
    }

    public ReservationEntity() {
    }

    public ReservationEntity(Reservation reservation, ZonedDateTime reservedOn, String reservationToken, Boolean confirmed) {
        this.email = reservation.getEmail();
        this.studentId = reservation.getStudentId();
        this.reason = reservation.getReason();
        this.reservedOn = reservedOn;
        this.reservationToken = reservationToken;
        this.confirmed = confirmed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getReservedOn() {
        return reservedOn;
    }

    public void setReservedOn(ZonedDateTime reservedOn) {
        this.reservedOn = reservedOn;
    }

    public String getReservationToken() {
        return reservationToken;
    }

    public void setReservationToken(String reservationToken) {
        this.reservationToken = reservationToken;
    }

    @Override
    public String toString() {
        return "ReservationEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", studentId=" + studentId +
                ", reason='" + reason + '\'' +
                ", reservedOn=" + reservedOn.toLocalDateTime() +
                ", reservationToken='" + reservationToken + '\'' +
                '}';
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
