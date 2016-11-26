package com.edu.utn.infoba;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class ConcreteMailSender implements MailSender
{
    private String host;
    private String port;
    private String auth;
    private String startTls;

    public ConcreteMailSender(String host, String port, String auth, String startTls)
    {
       this.host = host;
       this.port = port;
       this.auth = auth;
       this.startTls = startTls;
    }

    public void send(final String fromEmail , String password, String toEmail, String subject, String body)
    {

        System.out.println("Se comienza el envio");

        Properties props = new Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.starttls.enable", this.startTls);

        Authenticator auth = new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("fromEmail", "Sistema de POI"));
            msg.setReplyTo(InternetAddress.parse(toEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);

            System.out.println("EMail enviado");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
