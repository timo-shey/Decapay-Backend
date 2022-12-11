package com.example.decapay.configurations.mails;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}
