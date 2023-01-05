package fr.hellocorp.projetmoscatelli.admin.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {

    private final static Logger LOGGER =  LoggerFactory
            .getLogger(EmailService.class);

    @Autowired
    JavaMailSender javaMailSender;

    @Async
    @Override
    public void envoiEmail(String destinataire, String sujet, String texte) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(texte, true);
            helper.setTo(destinataire);
            helper.setSubject(sujet);
            helper.setFrom("hello.corp@javenir84.com");

            javaMailSender.send(mimeMessage);



        }
        catch
            (MessagingException e){
            LOGGER.error("Echec de l'envoi de mail", e);
            throw new IllegalStateException("Echec de l'envoi de mail");
        }



    }
}
