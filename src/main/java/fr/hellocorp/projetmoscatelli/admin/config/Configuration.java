package fr.hellocorp.projetmoscatelli.admin.config;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;


@Entity
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="destinataires_notification", nullable = false, length = 255)
    private String destinatairesNotification;

    @Column(name="heure_envoi", columnDefinition = "TIME")
//    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime heureEnvoi;

    @Column(name = "alerte_on_off", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean alerteOnOff;

    @Column(name="duree_pret", nullable = false)
    private int dureePret;

    public Configuration() {
    }

    public Configuration(Long id, String destinatairesNotification, LocalTime heureEnvoi, Boolean alerteOnOff, int dureePret) {
        this.id = id;
        this.destinatairesNotification = destinatairesNotification;
        this.heureEnvoi = heureEnvoi;
        this.alerteOnOff = alerteOnOff;
        this.dureePret = dureePret;
    }

    public Configuration(String destinatairesNotification, LocalTime heureEnvoi, Boolean alerteOnOff, int dureePret) {
        this.destinatairesNotification = destinatairesNotification;
        this.heureEnvoi = heureEnvoi;
        this.alerteOnOff = alerteOnOff;
        this.dureePret = dureePret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinatairesNotification() {
        return destinatairesNotification;
    }

    public void setDestinatairesNotification(String destinatairesNotification) {
        this.destinatairesNotification = destinatairesNotification;
    }

    public LocalTime getHeureEnvoi() {
        return heureEnvoi;
    }

    public void setHeureEnvoi(LocalTime heureEnvoi) {
        this.heureEnvoi = heureEnvoi;
    }

    public Boolean getAlerteOnOff() {
        return alerteOnOff;
    }

    public void setAlerteOnOff(Boolean alerteOnOff) {
        this.alerteOnOff = alerteOnOff;
    }

    public int getDureePret() {
        return dureePret;
    }

    public void setDureePret(int dureePret) {
        this.dureePret = dureePret;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", destinatairesNotification='" + destinatairesNotification + '\'' +
                ", heureEnvoi=" + heureEnvoi +
                ", alerteOnOff=" + alerteOnOff +
                ", dureePret=" + dureePret +
                '}';
    }
}
