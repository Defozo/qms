package eu.qms.qms.api;

import eu.qms.qms.EmailSender;
import eu.qms.qms.model.Reservation;
import eu.qms.qms.model.ReservationEntity;
import eu.qms.qms.model.ReservationListObject;
import eu.qms.qms.services.ReservationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/reservations")
//@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST}, maxAge = 3600)
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    private MultiValueMap<String, String> getHeaderForCors() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
       // headers.put("access-control-allow-origin", Collections.singletonList("*"));
        return headers;
    }

    @GetMapping("/{reservationToken}/confirm")
    @ApiOperation(value = "Confirm reservation")
    public HttpEntity<Map<String, String>> confirmReservation(@ApiParam(value = "Reservation json", required = true) @PathVariable String reservationToken) {
        reservationService.confirmReservation(reservationToken);
        return new HttpEntity<>(getHeaderForCors());
    }

    @GetMapping(value = "/{studentId}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity<ReservationListObject> getStudentsReservation(@PathVariable Integer studentId) {
        ReservationListObject reservationListObject = reservationService.getReservation(studentId, true);
//        reservationListObject.setReservationToken(null);
        return new HttpEntity<>(reservationListObject, getHeaderForCors());
    }

    /*@GetMapping(value = "/{reservationToken}", produces = APPLICATION_JSON_VALUE)
    public HttpEntity<ReservationListObject> getStudentsReservation(@PathVariable String reservationToken) {
        ReservationListObject reservationListObject = reservationService.getReservation(reservationToken);
        return new HttpEntity<>(reservationListObject, getHeaderForCors());
    }*/

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public List<ReservationListObject> getReservations() {
        List<ReservationEntity> reservationEntityList = reservationService.getReservations();
        List<ReservationListObject> reservationListObjects = new ArrayList<>();
        for (int i = 0; i < reservationEntityList.size(); i++) {
            Integer numberOfReservationBeforeThisOne =  reservationService.getNumberOfReservationsBefore(reservationEntityList.get(i).getReservedOn());
            ReservationListObject reservationListObject = new ReservationListObject(reservationEntityList.get(i));
            reservationListObject.setPosition(numberOfReservationBeforeThisOne+1);
            ZonedDateTime nextMonday = ZonedDateTime.now();
            while (nextMonday.getDayOfWeek() != DayOfWeek.MONDAY) {
                nextMonday = nextMonday.plusDays(1);
            }
            nextMonday = nextMonday.withHour(9).withMinute(0).withSecond(0).withNano(0);
            reservationListObject.setEstimatedTime(DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy").format(nextMonday.plusMinutes(numberOfReservationBeforeThisOne*5)));
            reservationListObjects.add(reservationListObject);
        }
        //reservationEntityList.forEach(reservationEntity -> reservationListObjects.add(new ReservationListObject(reservationEntity)));
        reservationListObjects.sort(Comparator.comparingInt(ReservationListObject::getPosition));
        return reservationListObjects;
    }

    @DeleteMapping("/{reservationToken}")
    public HttpEntity deleteReservation(@PathVariable String reservationToken) {
        reservationService.deleteReservation(reservationToken, true);
        return new HttpEntity(getHeaderForCors());
    }

    @PostMapping("/delete/{reservationToken}")
    public HttpEntity deleteReservationViaPost(@PathVariable String reservationToken) {
        reservationService.deleteReservation(reservationToken, true);
        return new HttpEntity(getHeaderForCors());
    }

    @PostMapping("/emails/")
    public HttpStatus sendEmail(@ApiParam(value = "Reservation json", required = true) @RequestBody Reservation reservation) throws Exception {
        //TODO: Send e-mail with link with studentId and reservationToken
        String reservationToken = UUID.randomUUID().toString();
        reservationService.addReservation(new ReservationEntity(reservation, ZonedDateTime.now(), reservationToken, false));
        EmailSender.send(reservation.getEmail(), reservation.getStudentId(), reservationToken);
        return HttpStatus.OK;
    }

    //TODO: Niepotrzebne?
   /* @RequestMapping(value= "/{reservationToken}", method=RequestMethod.OPTIONS)
    public void corsHeaders(HttpServletResponse response, @PathVariable String reservationToken) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        response.addHeader("Access-Control-Max-Age", "3600");
    }*/

}
