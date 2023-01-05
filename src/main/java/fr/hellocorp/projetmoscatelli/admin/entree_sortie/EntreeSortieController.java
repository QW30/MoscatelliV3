package fr.hellocorp.projetmoscatelli.admin.entree_sortie;

import fr.hellocorp.projetmoscatelli.admin.outil.Outil;
import fr.hellocorp.projetmoscatelli.admin.outil.OutilService;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.Utilisateur;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/entreesortie")
public class EntreeSortieController {

    @Autowired
    private EntreeSortieService service;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private OutilService outilService;

    @GetMapping(value = {"/recherche",""})
    public String ShowUsers(Model model,
                            @Param("keyword") String keyword,
                            @Param("etalonnee)") boolean etalonnee)
    {
        List<EntreeSortie> entreesSorties = service.findAll(keyword);
        model.addAttribute("entreesSorties", entreesSorties);
        EntreeSortie entreesortie = new EntreeSortie();
        model.addAttribute("entreeSortie", entreesortie);
        List<Utilisateur> utilisateurs = utilisateurService.listAll();
        model.addAttribute("utilisateurs", utilisateurs);
        List<Outil> outils = outilService.findAll();
        model.addAttribute("Outils",outils);
        model.addAttribute("url","/entreesortie");
        model.addAttribute("etalonnee", etalonnee);
        model.addAttribute("keyword", keyword);
        return "entreesortie";
    }


    @PostMapping("/enregistrer")
    public String enregistrer(EntreeSortie entreeSortie) {
        service.enregistrer(entreeSortie);
        return "redirect:/entreesortie";
    }


    @PostMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id,
                            @Param("etalonnee") boolean etalonnee,
                            @Param("keyword")String keyword) throws EntreeSortieNotFoundException {
        service.supprimer(id);
        return "redirect:/entreesortie?keyword="+(Objects.equals(keyword, "null") ? "":keyword) +"&etalonnee="+etalonnee;
        //return "redirect:/outils?keyword="+(Objects.equals(keyword, "null") ? "":keyword) +"&etalonnee="+etalonnee;
    }


    @PostMapping ("/modifier/{id}")
    public String modifier(Model model,
                           @Param("keyword)") String keyword,
                           @Param("etalonnee)") boolean etalonnee,
                           @PathVariable Long id,
                           @RequestParam Utilisateur utilisateur,
                           @RequestParam Outil outil,
                           @RequestParam EntreeSortie.MotifEntreeSortie motif,
                           @RequestParam String date_sortie,
                           @RequestParam String date_de_retour_prevue,
                           @RequestParam String probleme,
                           @RequestParam String referencePV,
                           @RequestParam String date_etalonnage,
                           @RequestParam String date_retour)
                            //@RequestParam EntreeSortie.MotifEntreeSortie motifEntreeSortie)


    {
        EntreeSortie entreeSortie = new EntreeSortie();
        entreeSortie.setId(id);
        entreeSortie.setUtilisateur(utilisateur);
        entreeSortie.setOutil(outil);
        entreeSortie.setMotif(motif);
        entreeSortie.setDate_sortie(LocalDate.parse(date_sortie));
        entreeSortie.setDate_de_retour_prevue(LocalDate.parse(date_de_retour_prevue));
        entreeSortie.setProbleme(probleme);
        entreeSortie.setReferencePV(referencePV);
        if(date_retour.length()!=0)
            entreeSortie.setDate_retour(LocalDate.parse(date_retour));

        if(date_etalonnage.length()!=0)
            entreeSortie.setDate_etalonnage(LocalDate.parse(date_etalonnage));

//        entreeSortie.setDate_retour(LocalDate.parse(date_retour));
//        entreeSortie.setDate_etalonnage(LocalDate.parse(date_etalonnage));



        List<EntreeSortie> entreesSorties = service.findAll(keyword);
        model.addAttribute("entreesSorties", entreesSorties);

        List<Utilisateur> utilisateurs = utilisateurService.listAll();
        model.addAttribute("utilisateurs", utilisateurs);
        List<Outil> outils = outilService.findAll();
        model.addAttribute("Outils",outils);
        model.addAttribute("etalonnee", etalonnee);
        model.addAttribute("keyword", keyword);
        service.enregistrer(entreeSortie);
        return "redirect:/entreesortie?keyword="+(Objects.equals(keyword, "null") ? "":keyword);
    }

}