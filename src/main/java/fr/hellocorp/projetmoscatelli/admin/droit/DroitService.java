package fr.hellocorp.projetmoscatelli.admin.droit;

import fr.hellocorp.projetmoscatelli.admin.utilisateur.IRepositoryUtilisateur;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.Utilisateur;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.UtilisateurNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class DroitService {

    @Autowired
    private IRepositoryDroit repo;

    public Droit get(String Nom){
        return repo.findByNom(Nom);
    }
    public List<Droit> liste() {
        return repo.findAll();
    }

}
