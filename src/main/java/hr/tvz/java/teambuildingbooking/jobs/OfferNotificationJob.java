package hr.tvz.java.teambuildingbooking.jobs;

import hr.tvz.java.teambuildingbooking.model.Reservation;
import hr.tvz.java.teambuildingbooking.model.rest.Mail;
import hr.tvz.java.teambuildingbooking.repository.ReservationRepository;
import hr.tvz.java.teambuildingbooking.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    EmailService emailService;


    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        Iterable<Reservation> reservations = reservationRepository.findAll();

        StreamSupport.stream(reservations.spliterator(), false)
                .filter(OfferNotificationJob::isDayBeforeReservation)
                .collect(Collectors.toList())
                .forEach(
                        reservation -> {
                            if(!reservation.getNotificationSent()){

                                emailService.sendSimpleMessage(createMail(reservation));
                                reservation.setNotificationSent(true);
                            }
                        }
                );

        ((List<Reservation>) reservations).stream().forEach(reservation -> reservationRepository.save(reservation));
    }

    private static boolean isDayBeforeReservation(Reservation reservation) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Date input = reservation.getDateOfReservation();
        LocalDate dateOfReservation = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return dateOfReservation.equals(tomorrow);
    }

    private static Mail createMail(Reservation reservation) {
        Mail mail = new Mail();
        mail.setFrom("no-reply@teambulidingbooking.com");
        //mail.setTo(reservation.getUser().getEmail()); ignored for test purposes
        mail.setTo("anaaadedic@gmail.com");
        mail.setSubject("Podsjetnik na rezervaciju");

        mail.setContent(
                "Poštovani " + reservation.getUser().getName() +
                        " " + reservation.getUser().getSurname() +
                        ",\n\n" +
                        "Podsjećamo Vas da je sutra, " +
                        LocalDate.now().plusDays(1) +
                        ", datum na koji ste rezervirali ponudu: "
                        + reservation.getOffer().getName() + "\n" + "za " + reservation.getNumberOfUsers() + " sudionika.\n\n" +
                        "Želimo Vam ugodnu zabavu i druženje!\n\n" +
                        "Lijep pozdrav," +
                        "\nTeam tulipani"
        );
        return mail;
    }

}
