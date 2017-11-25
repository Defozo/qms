package eu.qms.qms.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Reservation {


    String email;
    Integer studentId;
    String reason;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ApiModelProperty(required = true, value = "Student's album number")
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    @ApiModelProperty(required = true, value = "Student's e-mail address")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "email='" + email + '\'' +
                ", studentId=" + studentId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
