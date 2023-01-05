package fr.hellocorp.projetmoscatelli.admin.outil;

import fr.hellocorp.projetmoscatelli.admin.entree_sortie.EntreeSortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static fr.hellocorp.projetmoscatelli.admin.outil.Outil.TypeStatut.Disponible;

@Service
public class OutilService {

    @Autowired
    private IRepositoryOutils repo;

    public List<Outil> findAll(String keyword, boolean etalonnee) {
        if (keyword != null) {
            return repo.search(keyword, etalonnee);
        }
        return repo.findByEtalonnee(etalonnee);
    }

    public List<Outil> findAll( boolean etalonnee) {
        return repo.findByEtalonnee(etalonnee);
    }

    public List<Outil> findAll() {
        return (List<Outil>) repo.findAll();
    }

    public void enregistrer(Outil outil) {
        if (outil.getId() == null){
           outil.setTypeStatut(Disponible);
        }
        repo.save(outil);
    }

    public void supprimer(Long id) throws OutilNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count ==0) {
            throw new OutilNotFoundException("l'outil " + id + " n'existe pas.");
        }
        repo.deleteById(id);
    }

    public Outil getOutil(Long id) throws OutilNotFoundException {
        Optional<Outil> outil = repo.findById(id);
        if (outil.isPresent())
            return outil.get();
        throw new OutilNotFoundException("l'outil " + id + " n'existe pas.");
    }
}
