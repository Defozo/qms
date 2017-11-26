package eu.qms.qms.model;

public class ReservationListObject {

    private String email;
    private Integer studentId;
    private String reason;
    private String estimatedTime;
    private String reservationToken;
    private Integer position;

    public ReservationListObject() {
    }

    public ReservationListObject(ReservationEntity reservationEntity) {
        this.email = reservationEntity.getEmail();
        this.studentId = reservationEntity.getStudentId();
        this.reason = reservationEntity.getReason();
        this.estimatedTime = reservationEntity.getReservedOn().toString();
        this.reservationToken = reservationEntity.getReservationToken();
    }

    public ReservationListObject(String email, Integer studentId, String reason, String estimatedTime, String reservationToken, Integer position) {
        this.email = email;
        this.studentId = studentId;
        this.reason = reason;
        this.estimatedTime = estimatedTime;
        this.reservationToken = reservationToken;
        this.position = position;
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
                ", estimatedTime='" + estimatedTime + '\'' +
                ", reservationToken='" + reservationToken + '\'' +
                '}';
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
