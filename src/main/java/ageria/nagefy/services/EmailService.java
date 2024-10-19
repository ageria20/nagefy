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


    public void sendEmail(String toMail, String resetToken) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        if(toMail == null || !toMail.contains("@")){
            throw new BadRequestException("Indirizzo Email non valido");
        }

        helper.setTo(toMail);
        helper.setSubject("Password Reset");


        String resetLink = "http://localhost:5173/clients/create?email=" + resetToken;


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
                "    <h2>Password Reset</h2>" +
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
}
