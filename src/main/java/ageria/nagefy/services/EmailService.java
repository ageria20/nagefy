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


        String resetLink = "https://nagefy.netlify.app/reset-password/" + toMail;


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


        String resetLink = "https://nagefy.netlify.app/reset-password-staff/" + toMail;


        String htmlBody = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "  .email-container { font-family: Arial, sans-serif; padding: 20px; }" +
                "  .header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; width: 50%}" +
                "  .content { margin-top: 20px; font-size: 16px; line-height: 1.6; }" +
                "  .footer { margin-top: 20px; font-size: 14px; color: #777; text-align: center; }" +
                "  .btn { background-color: #4CAF50; color: white !important; padding: 10px 20px; text-decoration: none; border-radius: 5px; }" +
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
        helper.setSubject("Verifica Mail");


        String verifyLink = "https://nagefy.netlify.app/verify-client/" + toMail;


        String htmlBody = "<!DOCTYPE html>" +
                "<html lang='it'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Benvenuto su Nagefy!</title>" +
                "</head>" +
                "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; text-align: center;'>" +
                "<div style='background-color: white; padding: 40px; max-width: 500px; margin: auto; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>" +
                "<h1 style='color: #5a2d82;'>Benvenuto su Nagefy!</h1>" +
                "<p style='color: #333;'>Grazie per esserti unito a Nagefy" + ".</p>" +
                "<p style='color: #333;'>Siamo lieti di averti con noi e di iniziare questa nuova esperienza insieme.</p>" +
                "<a href='" + verifyLink + "' style='background-color: #5a2d82; color: white; padding: 15px 25px; border-radius: 5px; font-size: 16px; text-decoration: none; display: inline-block; margin-top: 20px;'>Conferma Email</a>" +
                "<p style='color: #333; margin-top: 20px;'>Se hai domande o hai bisogno di assistenza, non esitare a contattarci.</p>" +
                "<small style='color: #666;'>Nagefy © " + java.time.Year.now() + "</small>" +
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
        helper.setSubject("Verifica Mail");


        String verifyLink = "https://nagefy.netlify.app/verify-admin/" + toMail ;


        String htmlBody = "<!DOCTYPE html>" +
                "<html lang='it'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>Benvenuto su Nagefy!</title>" +
                "</head>" +
                "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; text-align: center;'>" +
                "<div style='background-color: white; padding: 40px; max-width: 500px; margin: auto; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>" +
                "<h1 style='color: #5a2d82;'>Benvenuto su Nagefy!</h1>" +
                "<p style='color: #333;'>Grazie per esserti unito a Nagefy" + ".</p>" +
                "<p style='color: #333;'>Siamo lieti di averti con noi e di iniziare questa nuova esperienza insieme.</p>" +
                "<a href='" + verifyLink + "' style='background-color: #5a2d82; color: white; padding: 15px 25px; border-radius: 5px; font-size: 16px; text-decoration: none; display: inline-block; margin-top: 20px;'>Conferma Email</a>" +
                "<p style='color: #333; margin-top: 20px;'>Se hai domande o hai bisogno di assistenza, non esitare a contattarci.</p>" +
                "<small style='color: #666;'>Nagefy © " + java.time.Year.now() + "</small>" +
                "</div>" +
                "</body>" +
                "</html>";


        helper.setText(htmlBody, true);
        javaMailSender.send(mimeMessage);
    }
}
