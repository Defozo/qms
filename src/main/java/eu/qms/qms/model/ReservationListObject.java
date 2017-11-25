package eu.qms.qms.model;

import java.time.ZonedDateTime;

public class ReservationListObject {

    private String email;
    private Integer studentId;
    private String reason;

    public void setEstimatedTime(ZonedDateTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    private ZonedDateTime estimatedTime;
    private String estimatedTimeString;
    private String reservationToken;

    public ReservationListObject() {
    }

    public ReservationListObject(ReservationEntity reservationEntity) {
        this.email = reservationEntity.getEmail();
        this.studentId = reservationEntity.getStudentId();
        this.reason = reservationEntity.getReason();
        this.estimatedTimeString = reservationEntity.getReservedOn().toString();
        this.reservationToken = reservationEntity.getReservationToken();
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

    public String getReservationToken() {
        return reservationToken;
    }

    public void setReservationToken(String reservationToken) {
        this.reservationToken = reservationToken;
    }

    @Override
    public String toString() {
        return "ReservationListObject{" +
                ", email='" + email + '\'' +
                ", studentId=" + studentId +
                ", reason='" + reason + '\'' +
                ", estimatedTimeString='" + estimatedTimeString + '\'' +
                ", reservationToken='" + reservationToken + '\'' +
                '}';
    }

    public String getEstimatedTimeString() {
        return estimatedTime.toString();
    }

    public void setEstimatedTimeString(String estimatedTimeString) {
        this.estimatedTimeString = estimatedTimeString;
    }
}
