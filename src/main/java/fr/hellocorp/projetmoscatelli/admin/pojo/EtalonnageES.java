package fr.hellocorp.projetmoscatelli.admin.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class EtalonnageES {

    @Id
    private Long id;
    private String designation;
    private Integer periodicite;

    private String repere;

    private String modele;

    private LocalDate date_etalonnage;
    private LocalDate date_prochain_etalonnage;
    private Long id_utilisateur;
    private String nom;
    private String prenom;

    public EtalonnageES() {
    }

    public EtalonnageES(Long id, String designation, String repere, String modele, Integer periodicite, LocalDate date_etalonnage, LocalDate date_prochain_etalonnage, Long id_utilisateur, String nom, String prenom) {
        this.id = id;
        this.designation = designation;
        this.repere = repere;
        this.modele = modele;
        this.periodicite = periodicite;
        this.date_etalonnage = date_etalonnage;
        this.date_prochain_etalonnage = date_prochain_etalonnage;
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
    }

    public EtalonnageES(String designation, String repere, String modele, Integer periodicite, LocalDate date_etalonnage, LocalDate date_prochain_etalonnage, Long id_utilisateur, String nom, String prenom) {
        this.designation = designation;
        this.repere = repere;
        this.modele = modele;
        this.periodicite = periodicite;
        this.date_etalonnage = date_etalonnage;
        this.date_prochain_etalonnage = date_prochain_etalonnage;
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRepere() {return repere;}

    public void setRepere(String repere){this.repere = repere;}

    public Integer getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(Integer periodicite) {
        this.periodicite = periodicite;
    }

    public LocalDate getDate_etalonnage() {
        return date_etalonnage;
    }

    public void setDate_etalonnage(LocalDate date_etalonnage) {
        this.date_etalonnage = date_etalonnage;
    }

    public LocalDate getDate_prochain_etalonnage() {
        return date_prochain_etalonnage;
    }

    public void setDate_prochain_etalonnage(LocalDate date_prochain_etalonnage) {
        this.date_prochain_etalonnage = date_prochain_etalonnage;
    }

    public Long getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(Long id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "EtalonnageES{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", repere='" + repere +
                ", periodicite=" + periodicite +
                ", modele=" + modele +
                ", dateEtalonnage=" + date_etalonnage +
                ", dateProchainEtalonnage=" + date_prochain_etalonnage +
                ", idUtilisateur=" + id_utilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}