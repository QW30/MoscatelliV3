package fr.hellocorp.projetmoscatelli.admin.mail;

public class Email {
    private String destinataire;
    private String sujet;
    private String texte;

    public Email() {
    }

    public Email(String destinataire, String sujet, String texte) {
        this.destinataire = destinataire;
        this.sujet = sujet;
        this.texte = texte;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    @Override
    public String toString() {
        return "Email{" +
                "destinataire='" + destinataire + '\'' +
                ", sujet='" + sujet + '\'' +
                ", texte='" + texte + '\'' +
                '}';
    }
}