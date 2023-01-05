package fr.hellocorp.projetmoscatelli.admin.droit;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "droits")
public class Droit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public Droit(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Droit() {
    }

    @Override
    public String toString() {
        return getNom();
    }
}

