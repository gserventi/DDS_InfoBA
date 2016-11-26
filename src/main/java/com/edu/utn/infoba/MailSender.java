package com.edu.utn.infoba;


public interface MailSender
{
    void send(String fromEmail , String password, String toEmail, String subject, String body);
}
