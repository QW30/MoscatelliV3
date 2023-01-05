package fr.hellocorp.projetmoscatelli.admin.entree_sortie;

import fr.hellocorp.projetmoscatelli.admin.outil.Outil;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="entrees_sorties",
        indexes = {
                @Index(columnList = "id_utilisateur"),
                @Index(columnList = "id_outil"),
                @Index(columnList = "date_sortie"),
                @Index(columnList = "date_de_retour_prevue")})
public class EntreeSortie {
    //////////////////////////////////////// DECLARATIONS DES VARIABLES ////////////////////////////////////////
    public enum MotifEntreeSortie{
        Prêt, Rebut, Maintenance, Etalonnage, Manquant
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="motif")
    @Enumerated
    private MotifEntreeSortie motif;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_outil")
    private Outil outil;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date_sortie;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date_de_retour_prevue;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date_retour;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date_etalonnage;

    @Column(length = 255)
    private String probleme;

    @Column(length = 255)
    private String referencePV;



    @Column
    private String EXE;
    @Column(name = "utilisateur_creation", length = 70, columnDefinition = "VARCHAR(20) DEFAULT 'root'")
    private String utilisateurcreation;

    @Column(name="date_creation", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDateTime dateCreation;

    @Column(name = "utilisateur_maj", length = 70, columnDefinition = "VARCHAR(20) DEFAULT 'root'")
    private String utilisateurMAJ;

    @Column(name = "date_maj", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime dateMaj;

    //////////////////////////////////////// DECLARATIONS DES CONSTRUCTEURS ////////////////////////////////////////


    public EntreeSortie(Long id, MotifEntreeSortie motif, Utilisateur utilisateur, Outil outil, LocalDate date_sortie, LocalDate date_de_retour_prevue, LocalDate date_retour, LocalDate date_etalonnage, String probleme, String EXE, String referencePV, String utilisateurcreation, LocalDateTime dateCreation, String utilisateurMAJ, LocalDateTime dateMaj) {
        this.id = id;
        this.motif = motif;
        this.utilisateur = utilisateur;
        this.outil = outil;
        this.date_sortie = date_sortie;
        this.date_de_retour_prevue = date_de_retour_prevue;
        this.date_retour = date_retour;
        this.date_etalonnage = date_etalonnage;
        this.probleme = probleme;
        this.EXE = EXE;
        this.referencePV = referencePV;
        this.utilisateurcreation = utilisateurcreation;
        this.dateCreation = dateCreation;
        this.utilisateurMAJ = utilisateurMAJ;
        this.dateMaj = dateMaj;
    }

    public EntreeSortie(MotifEntreeSortie motif, Utilisateur utilisateur, Outil outil, LocalDate date_sortie, LocalDate date_de_retour_prevue, LocalDate date_retour, LocalDate date_etalonnage, String probleme, String EXE, String referencePV, String utilisateurcreation, LocalDateTime dateCreation, String utilisateurMAJ, LocalDateTime dateMaj) {
        this.motif = motif;
        this.utilisateur = utilisateur;
        this.outil = outil;
        this.date_sortie = date_sortie;
        this.date_de_retour_prevue = date_de_retour_prevue;
        this.date_retour = date_retour;
        this.date_etalonnage = date_etalonnage;
        this.probleme = probleme;
        this.EXE = EXE;
        this.referencePV = referencePV;
        this.utilisateurcreation = utilisateurcreation;
        this.dateCreation = dateCreation;
        this.utilisateurMAJ = utilisateurMAJ;
        this.dateMaj = dateMaj;
    }

    public EntreeSortie() {
    }
    //////////////////////////////////////// DECLARATIONS DES GETTERS AND SETTERS ////////////////////////////////////////



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MotifEntreeSortie getMotif() {
        return motif;
    }

    public void setMotif(MotifEntreeSortie motif) {
        this.motif = motif;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur id_utilisateur) {
        this.utilisateur = id_utilisateur;
    }

    public Outil getOutil() {
        return outil;
    }

    public void setOutil(Outil id_outil) {
        this.outil = id_outil;
    }

    public LocalDate getDate_sortie() {
        return date_sortie;
    }

    public void setDate_sortie(LocalDate date_sortie) {
        this.date_sortie = date_sortie;
    }

    public LocalDate getDate_de_retour_prevue() {
        return date_de_retour_prevue;
    }

    public void setDate_de_retour_prevue(LocalDate date_de_retour_prevue) {
        this.date_de_retour_prevue = date_de_retour_prevue;
    }

    public LocalDate getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(LocalDate date_retour) {
        this.date_retour = date_retour;
    }

    public LocalDate getDate_etalonnage() {
        return date_etalonnage;
    }

    public void setDate_etalonnage(LocalDate date_etalonnage) {
        this.date_etalonnage = date_etalonnage;
    }

    public String getProbleme() {
        return probleme;
    }

    public void setProbleme(String probleme) {
        this.probleme = probleme;
    }

    public String getEXE() {
        return EXE;
    }

    public void setEXE(String EXE) {
        this.EXE = EXE;
    }

    public String getReferencePV() {
        return referencePV;
    }

    public void setReferencePV(String referencePV) {
        this.referencePV = referencePV;
    }

    public String getUtilisateurcreation() {
        return utilisateurcreation;
    }

    public void setUtilisateurcreation(String utilisateurcreation) {
        this.utilisateurcreation = utilisateurcreation;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getUtilisateurMAJ() {
        return utilisateurMAJ;
    }

    public void setUtilisateurMAJ(String utilisateurMAJ) {
        this.utilisateurMAJ = utilisateurMAJ;
    }

    public LocalDateTime getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(LocalDateTime dateMaj) {
        this.dateMaj = dateMaj;
    }

}




