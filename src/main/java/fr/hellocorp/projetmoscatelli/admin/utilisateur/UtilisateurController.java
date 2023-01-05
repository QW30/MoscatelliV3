package fr.hellocorp.projetmoscatelli.admin.utilisateur;

import fr.hellocorp.projetmoscatelli.admin.droit.Droit;
import fr.hellocorp.projetmoscatelli.admin.droit.DroitService;
import fr.hellocorp.projetmoscatelli.admin.droit.IRepositoryDroit;
import net.bytebuddy.description.annotation.AnnotationValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private DroitService droitService;


    @GetMapping(value = {"/recherche",""})
    public String index(Model model, @Param("keyword") String keyword,
                        @RequestParam(value = "page" , defaultValue = "1")  int page,
                        @RequestParam(value = "tri" , defaultValue = "id") String tri){

        //Pageable sortedById =
                //PageRequest.of(0,20, Sort.by(tri));

        List<Utilisateur> utilisateurs = utilisateurService.findAll(keyword);
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("currentPage",page);
        model.addAttribute("tri",tri);
        //model.addAttribute("totalPages", page.getTotalPages);
        //model.addAttribute("totalItems", page.getTotalElements);

        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("url","/utilisateur");
        model.addAttribute("keyword", keyword);
        List<Droit> roles = droitService.liste();
        model.addAttribute("roles", roles);

        return "/utilisateur";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(Utilisateur utilisateur,
                              @Param("keyword") String keyword) {

        // Nouvel utilisateur
        if (utilisateur.getId() == null) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            utilisateur.setMdp(encoder.encode(utilisateur.getMdp()));

            // Rôle par défaut
            Droit droit = droitService.get("ROLE_VISITEUR");
            utilisateur.ajouterDroit(droit);
        }

        utilisateurService.enregistrer(utilisateur);
        return "redirect:/utilisateur?keyword="+(Objects.equals(keyword, "null") ? "":keyword);
    }

    @PostMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id,
                            @Param("keyword") String keyword) throws UtilisateurNotFoundException {
        utilisateurService.supprimer(id);
        return "redirect:/utilisateur?keyword="+(Objects.equals(keyword, "null") ? "":keyword);
    }

    @PostMapping ("/modifier/{id}")
    public String modifier(Model model,
                           @PathVariable Long id,
                           @Param("keyword)") String keyword,
                           @RequestParam String nom1,
                           @RequestParam String prenom1,
                           @RequestParam String email1,
                           @RequestParam String mdp1,
                           @RequestParam String telephone1,
                           @RequestParam(value="droits[]") String[] paramDroits) {

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        utilisateur.setNom(nom1);
        utilisateur.setPrenom(prenom1);
        utilisateur.setEmail(email1);
        utilisateur.setMdp(mdp1);
        utilisateur.setTelephone(telephone1);

        // Supprimer tous les droits
        utilisateur.resetDroits();

        // Remettre les droits cochés
        for (String s : paramDroits) {
            Droit d = droitService.get(s);
            utilisateur.ajouterDroit(d);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        utilisateur.setMdp(encoder.encode(utilisateur.getMdp()));


        utilisateurService.enregistrer(utilisateur);

        List<Droit> roles = droitService.liste();
        model.addAttribute("roles", roles);

        return "redirect:/utilisateur?keyword="+(Objects.equals(keyword, "null") ? "":keyword);
    }

    //@PostMapping(/importerliste/)



}
