package ageria.nagefy.services;


import ageria.nagefy.exceptions.BadRequestException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    JavaMailSenderImpl javaMailSender;


    public void sendEmailClient(String toMail) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        if(toMail == null || !toMail.contains("@")){
            throw new BadRequestException("Indirizzo Email non valido");
        }

        helper.setTo(toMail);
        helper.setSubject("Password Reset");


        String resetLink = "http://localhost:5173/reset-password/" + toMail;


        String htmlBody = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "  .email-container { font-family: Arial, sans-serif; padding: 20px; }" +
                "  .header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; }" +
                "  .content { margin-top: 20px; font-size: 16px; line-height: 1.6; }" +
                "  .footer { margin-top: 20px; font-size: 14px; color: #777; text-align: center; }" +
                "  .btn { background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "  <div class='header'>" +
                "    <h2>Client reset password</h2>" +
                "  </div>" +
                "  <div class='content'>" +
                "    <p>Ciao,</p>" +
                "    <p>Clicca il pulsante qui sotto per resettare la tua password:</p>" +
                "    <a href='" + resetLink + "' class='btn'>Reset Password</a>" +
                "  </div>" +
                "  <div class='footer'>" +
                "    <p>© 2024 Nagefy. All rights reserved.</p>" +
                "  </div>" +
                "</div>" +
                "</body>" +
                "</html>";


        helper.setText(htmlBody, true);
        javaMailSender.send(mimeMessage);
    }
    public void sendEmailStaff(String toMail) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        if(toMail == null || !toMail.contains("@")){
            throw new BadRequestException("Indirizzo Email non valido");
        }

        helper.setTo(toMail);
        helper.setSubject("Password Reset");


        String resetLink = "http://localhost:5173/reset-password-staff/" + toMail;


        String htmlBody = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "  .email-container { font-family: Arial, sans-serif; padding: 20px; }" +
                "  .header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; }" +
                "  .content { margin-top: 20px; font-size: 16px; line-height: 1.6; }" +
                "  .footer { margin-top: 20px; font-size: 14px; color: #777; text-align: center; }" +
                "  .btn { background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "  <div class='header'>" +
                "    <h2>Staff reset password</h2>" +
                "  </div>" +
                "  <div class='content'>" +
                "    <p>Ciao,</p>" +
                "    <p>Clicca il pulsante qui sotto per resettare la tua password:</p>" +
                "    <a href='" + resetLink + "' class='btn'>Reset Password</a>" +
                "  </div>" +
                "  <div class='footer'>" +
                "    <p>© 2024 Nagefy. All rights reserved.</p>" +
                "  </div>" +
                "</div>" +
                "</body>" +
                "</html>";


        helper.setText(htmlBody, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendEmailVerificationClient(String toMail) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        if(toMail == null || !toMail.contains("@")){
            throw new BadRequestException("Indirizzo Email non valido");
        }

        helper.setTo(toMail);
        helper.setSubject("Password Reset");


        String verifyLink = "http://localhost:5173/verify-client/" + toMail;


        String htmlBody = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "  .email-container { font-family: Arial, sans-serif; padding: 20px; width: 100% }" +
                "  .header { background-color: blueviolet; color: white; padding: 10px; text-align: center; width: 50%}" +
                "  .content { margin-top: 20px; font-size: 16px; line-height: 1.6; }" +
                "  .footer { margin-top: 20px; font-size: 14px; color: #777; text-align: center; }" +
                "  .btn { background-color: blueviolet; color: white !important; padding: 10px 20px; text-decoration: none; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "  <div class='header'>" +
                "    <h2>Verifica Email</h2>" +
                "  </div>" +
                "  <div class='content'>" +
                "    <h3>Grazie per esserti registrato</h3>" +
                "    <p>Clicca per verificare la tua mail</p>" +
                "    <a href='" + verifyLink + "' class='btn'>Verifica Email</a>" +
                "  </div>" +
                "  <div class='footer'>" +
                "    <p>© 2024 Nagefy. All rights reserved.</p>" +
                "  </div>" +
                "</div>" +
                "</body>" +
                "</html>";


        helper.setText(htmlBody, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendEmailVerificationAdmin(String toMail) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        if(toMail == null || !toMail.contains("@")){
            throw new BadRequestException("Indirizzo Email non valido");
        }

        helper.setTo(toMail);
        helper.setSubject("Password Reset");


        String verifyLink = "http://localhost:5173/login/" + toMail ;


        String htmlBody = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "  .email-container { font-family: Arial, sans-serif; padding: 20px; }" +
                "  .header { background-color: blueviolet; color: white; padding: 10px; text-align: center; }" +
                "  .content { margin-top: 20px; font-size: 16px; line-height: 1.6; }" +
                "  .footer { margin-top: 20px; font-size: 14px; color: #777; text-align: center; }" +
                "  .btn { background-color: blueviolet; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "  <div class='header'>" +
                "    <h2>Verifica Email</h2>" +
                "  </div>" +
                "  <div class='content'>" +
                "    <p>Grazie per esserti registrato</p>" +
                "    <p>Clicca per verificare la tua mail</p>" +
                "    <a href='" + verifyLink + "' class='btn'>Verifica Email</a>" +
                "  </div>" +
                "  <div class='footer'>" +
                "    <p>© 2024 Nagefy. All rights reserved.</p>" +
                "  </div>" +
                "</div>" +
                "</body>" +
                "</html>";


        helper.setText(htmlBody, true);
        javaMailSender.send(mimeMessage);
    }
}
