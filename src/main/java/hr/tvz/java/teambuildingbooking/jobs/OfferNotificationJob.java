package hr.tvz.java.teambuildingbooking.jobs;

import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class OfferNotificationJob extends QuartzJobBean {

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        Iterable<Reservation> reservations = reservationRepository.findAll();

        StreamSupport.stream(reservations.spliterator(), false)
                .filter(OfferNotificationJob::isDayBeforeReservation)
                .collect(Collectors.toList())
                .forEach(OfferNotificationJob::sendNotification);

        ((List<Reservation>) reservations).stream().forEach(reservation -> reservationRepository.save(reservation));
    }

    private static boolean isDayBeforeReservation(Reservation reservation) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date input = reservation.getDateOfReservation();
        LocalDate dateOfReservation = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return dateOfReservation.equals(tomorrow);
    }

    private static void sendNotification(Reservation reservation) {
        if(!reservation.getNotificationSent()){
            log.info("Tommorow is reservation date of this offer!"
                    + "\nDate: " + reservation.getDateOfReservation()
                    + " \n,Offer: " + reservation.getOffer().getName()
                    + "\n,User: " + reservation.getUser().getUsername());
            //sendEmail(reservation);
            reservation.setNotificationSent(true);
        }
    }
}
