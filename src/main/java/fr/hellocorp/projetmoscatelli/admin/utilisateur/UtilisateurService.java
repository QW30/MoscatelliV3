package fr.hellocorp.projetmoscatelli.admin.utilisateur;

import fr.hellocorp.projetmoscatelli.admin.droit.Droit;
import fr.hellocorp.projetmoscatelli.admin.droit.IRepositoryDroit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("utilisateurService")
public class UtilisateurService {
    @Autowired
    private IRepositoryUtilisateur repo;
    @Autowired
    private IRepositoryDroit repositoryDroit;



    public List<Utilisateur> findAll(String keyword) {
       if (keyword != null) {
            return repo.search(keyword);}

        return (List<Utilisateur>) repo.findAll();
    }

    public List<Utilisateur> listAll(){
        return (List<Utilisateur>) repo.listAll();
    }

    public List<Utilisateur> findAll(){
        return (List<Utilisateur>) repo.findAll();
    }

    public Utilisateur enregistrer(Utilisateur utilisateur) {
        if (utilisateur.getId() == null) {

            //Cryptage du mot de passe
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            utilisateur.setMdp(encoder.encode(utilisateur.getMdp()));

            //role
            Droit droit = repositoryDroit.findByNom("Visiteur");
            utilisateur.ajouterDroit(droit);
        }
        return(repo.save(utilisateur));
    }

    public void supprimer(Long id) throws UtilisateurNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count ==0) {
            throw new UtilisateurNotFoundException("l'utilisateur " + id + " n'existe pas.");
        }
        repo.deleteById(id);
    }

    //public Page<Utilisateur> findAll(String keyword, Pageable sortedById) {
        //return repo.findAll(keyword, sortedById);
    //}
}
