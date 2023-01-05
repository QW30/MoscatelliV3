package fr.hellocorp.projetmoscatelli.admin.securite;

import fr.hellocorp.projetmoscatelli.admin.droit.Droit;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UtilisateurDetails implements UserDetails {

    Utilisateur utilisateur;

    public UtilisateurDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


    @Override
    public String getPassword() {
        return utilisateur.getMdp();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String fullName(){
        return utilisateur.getPrenom()+" " +utilisateur.getNom();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Droit> droits = utilisateur.getDroits();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Droit droit: droits)
            authorities.add(new SimpleGrantedAuthority(droit.getNom()));

        return authorities;
    }
}
