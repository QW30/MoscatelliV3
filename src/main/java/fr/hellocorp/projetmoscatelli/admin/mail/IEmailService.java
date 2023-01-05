package fr.hellocorp.projetmoscatelli.admin.mail;

public interface IEmailService {
    void envoiEmail(String destinataire, String sujet, String texte);
}
