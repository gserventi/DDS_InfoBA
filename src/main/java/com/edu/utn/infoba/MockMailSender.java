package com.edu.utn.infoba;

public class MockMailSender  implements MailSender
{
    public void send(String fromEmail , String password, String toEmail, String subject, String body)
    {
        System.out.println("Envíando mail con los siguientes parámetros:");

        System.out.println("---------------------------");
        System.out.println("Remitente:" + fromEmail);
        System.out.println("Destinatario:" + toEmail);
        System.out.println("Remitente: " + fromEmail);
        System.out.println("Asunto: " + subject);
        System.out.println("Mensaje: " + body);
        System.out.println("---------------------------");

        System.out.println("Email enviado con éxito");
    }

}
