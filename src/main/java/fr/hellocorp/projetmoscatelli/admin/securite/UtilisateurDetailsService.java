package fr.hellocorp.projetmoscatelli.admin.securite;

import fr.hellocorp.projetmoscatelli.admin.utilisateur.IRepositoryUtilisateur;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UtilisateurDetailsService implements UserDetailsService {

    @Autowired
    IRepositoryUtilisateur repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Utilisateur utilisateur = repo.findByEmail(email);
        if (utilisateur == null)
            throw new UsernameNotFoundException("Cet utilisateur n'existe pas");

        return new UtilisateurDetails(utilisateur);
    }
}
