package fr.hellocorp.projetmoscatelli.admin.droit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRepositoryDroit extends JpaRepository<Droit ,Long> {
    public Droit findByNom(String nom);
}
