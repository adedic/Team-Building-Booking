package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.Mail;

public interface EmailService {

    boolean sendSimpleMessage(Mail mail);

}

