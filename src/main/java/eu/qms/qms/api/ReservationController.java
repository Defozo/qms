package eu.qms.qms.api;

import eu.qms.qms.model.Reservation;
import eu.qms.qms.model.ReservationEntity;
import eu.qms.qms.model.ReservationListObject;
import eu.qms.qms.services.ReservationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

//@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    private MultiValueMap<String, String> getHeaderForCors() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("access-control-allow-origin", Collections.singletonList("*"));
        return headers;
    }

    @PostMapping("/")
    @ApiOperation(value = "Make reservation")
    public HttpEntity<Map<String, String>> makeReservation(@ApiParam(value = "Reservation json", required = true) @RequestBody Reservation reservation) {
        //TODO: Send e-mail

        reservationService.addReservation(new ReservationEntity(reservation, ZonedDateTime.now(), UUID.randomUUID().toString()));
        return new HttpEntity<>(getHeaderForCors());
    }

    @GetMapping(value = "/{studentId}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity<ReservationListObject> getStudentsReservation(@PathVariable Integer studentId) {
        return new HttpEntity<>(new ReservationListObject(reservationService.getReservation(studentId)), getHeaderForCors());
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public List<ReservationListObject> getReservations() {
        List<ReservationEntity> reservationEntityList = reservationService.getReservations();
        List<ReservationListObject> reservationListObjects = new ArrayList<>();
        for (int i = 0; i < reservationEntityList.size(); i++) {
            Integer numberOfReservationBeforeThisOne =  reservationService.getNumberOfReservationsBefore(reservationEntityList.get(i).getReservedOn());
            System.out.println("Number of reservations before now: " + numberOfReservationBeforeThisOne);
            ReservationListObject reservationListObject = new ReservationListObject(reservationEntityList.get(i));
            reservationListObject.setEstimatedTime(reservationEntityList.get(i).getReservedOn().plusMinutes(numberOfReservationBeforeThisOne*5));
            reservationListObjects.add(reservationListObject);
        }
        reservationEntityList.forEach(reservationEntity -> reservationListObjects.add(new ReservationListObject(reservationEntity)));
        return reservationListObjects;
    }

    @DeleteMapping("/{reservationToken}")
    public void deleteReservation(@PathVariable String reservationToken) {
        reservationService.deleteReservation(reservationToken);
    }

}
