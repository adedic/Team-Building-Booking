package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.Mail;
import hr.tvz.java.teambuildingbooking.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceImplTest {

    @MockBean
    EmailService emailService;

    @Autowired
    JavaMailSender emailSender;

    @Test
    public void EmailServiceAutowired() {
        assertNotNull(emailService);
    };

    @Test
    public void JavaMailSenderAutowired() {
        assertNotNull(emailSender);
    };

    @Test
    public void sendSimpleMessageException() {
        Mail mail = new Mail();
        mail.setFrom("unknown@email.no");

        mail.setSubject("Naslov");
        mail.setContent("Poruka");

       Mockito.when(emailService.sendSimpleMessage(mail)).thenThrow(MailAuthenticationException.class);
    }

    @Test
    public void sendSimpleMessage() {
        Mail mail = new Mail();
        mail.setFrom("anaaadedic@gmail.com");
        mail.setTo("anaaadedic@gmail.com");
        mail.setSubject("Naslov");
        mail.setContent("Poruka");

        Mockito.when(emailService.sendSimpleMessage(mail)).thenReturn(true);


    }
}