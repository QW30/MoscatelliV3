package fr.hellocorp.projetmoscatelli.admin.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    EmailService emailService;

    @PostMapping("/envoi-mail")
    public ResponseEntity envoiMail(@RequestBody Email email) {
        emailService.envoiEmail(email.getDestinataire(), email.getSujet(), email.getTexte());
        return ResponseEntity.ok("L'email a bien été envoyé !");
    }
}

