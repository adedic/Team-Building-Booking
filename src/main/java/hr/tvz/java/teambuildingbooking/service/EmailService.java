package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.rest.Mail;

public interface EmailService {

    void sendSimpleMessage(Mail mail);

}

