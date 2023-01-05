package fr.hellocorp.projetmoscatelli.admin.outil;

import fr.hellocorp.projetmoscatelli.admin.entree_sortie.EntreeSortie;
import fr.hellocorp.projetmoscatelli.admin.entree_sortie.EntreeSortieService;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.Utilisateur;
import fr.hellocorp.projetmoscatelli.admin.utilisateur.UtilisateurService;
import fr.hellocorp.projetmoscatelli.admin.outil.Outil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/outils")
public class OutilsController {

    @Autowired
    private OutilService service;
    @Autowired
    private EntreeSortieService entreeSortieService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private OutilService outilService;


    @GetMapping(value = {"/recherche",""})
    public String ShowTools(Model model, @Param("keyword") String keyword,@Param("etalonnee") boolean etalonnee /*@Param("categorie") Outil.Categorie categorie*/) {

        List<Outil> outils = service.findAll(keyword, etalonnee);
        System.out.println(outils);
        model.addAttribute("Outils",outils);

        Outil outil = new Outil();
        model.addAttribute("Outil",outil);


        List<Utilisateur> utilisateurs = utilisateurService.listAll();
        model.addAttribute("utilisateurs", utilisateurs);

        model.addAttribute("url","/outils");
        model.addAttribute("etalonnee", etalonnee);
        model.addAttribute("keyword", keyword);
        /*model.addAttribute("categorie", categorie);*/

        model.addAttribute("currentDate", LocalDate.now());

        return "outils";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(Outil outil,
                              @Param("etalonnee") boolean etalonnee,
                              @Param("keyword") String keyword ){
        outil.setEtalonnee(etalonnee);
        service.enregistrer(outil);

        return "redirect:/outils?keyword="+(Objects.equals(keyword, "null") ? "":keyword ) +"&etalonnee="+etalonnee;
    }

    @PostMapping("/sortie")
    public String sortie(
            @Param("keyword)") String keyword,
            @Param("etalonnee)") boolean etalonnee,
            @RequestParam Utilisateur utilisateur,
            @RequestParam Outil outil,

            @RequestParam String motif,
            @RequestParam String date_sortie,
            @RequestParam String date_de_retour_prevue,

            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String telephone )
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        EntreeSortie entreeSortie = new EntreeSortie();
        entreeSortie.setOutil(outil);
        entreeSortie.setMotif(EntreeSortie.MotifEntreeSortie.valueOf(motif));
        entreeSortie.setDate_sortie(LocalDate.parse(date_sortie));
        entreeSortie.setDate_de_retour_prevue(LocalDate.parse(date_de_retour_prevue));

        if (nom.length()!=0) {
            Utilisateur u = new Utilisateur();
            u.setNom(nom);
            u.setPrenom(prenom);
            u.setTelephone(telephone);
            u.setMdp("MotDePasse");
            u.setDate_creation(LocalDateTime.now());
            u.setDate_maj(LocalDateTime.now());
            u.setUtilisateur_creation(currentPrincipalName);
            u.setUtilisateur_maj(currentPrincipalName);
            Utilisateur nouvelUtilisateur = utilisateurService.enregistrer(u);
            entreeSortie.setUtilisateur(nouvelUtilisateur);

        }
        else
            entreeSortie.setUtilisateur(utilisateur);

        entreeSortie.setDateCreation(LocalDateTime.now());
        entreeSortie.setDateMaj(LocalDateTime.now());

        entreeSortie.setUtilisateurcreation(currentPrincipalName);
        entreeSortie.setUtilisateurMAJ(currentPrincipalName);

        entreeSortieService.enregistrer(entreeSortie);


        switch (EntreeSortie.MotifEntreeSortie.valueOf(motif)) {
            case Maintenance -> outil.setTypeStatut(Outil.TypeStatut.Maintenance);
            case Prêt -> outil.setTypeStatut(Outil.TypeStatut.Pret);
            case Rebut -> outil.setTypeStatut(Outil.TypeStatut.Rebut);
            case Etalonnage -> outil.setTypeStatut(Outil.TypeStatut.Etalonnage);
            case Manquant -> outil.setTypeStatut(Outil.TypeStatut.Manquant);
        }

        outilService.enregistrer(outil);


        return "redirect:/outils?keyword="+(Objects.equals(keyword, "null") ? "":keyword) +"&etalonnee="+etalonnee;
    }

    @PostMapping("/retour")
    public String retour(
            @Param("keyword") String keyword,
            @Param("etalonnee)") boolean etalonnee,
            @RequestParam Outil outil,
            @RequestParam String idES,
            @RequestParam  String date_retour,
            @RequestParam (required = false) String date_etalonnage,
            @RequestParam String probleme,
            @RequestParam (required = false) MultipartFile file)
    {
        final String WORKING_DIR = "C:\\DossierPVMagasin";
        EntreeSortie entreeSortie = entreeSortieService.get(Long.parseLong(idES));

        if (entreeSortie != null) {
            entreeSortie.setDate_retour(LocalDate.parse(date_retour));
            if (date_etalonnage != null) entreeSortie.setDate_etalonnage(LocalDate.parse(date_etalonnage));

            if (date_retour != null){
                outil.setTypeStatut(Outil.TypeStatut.Disponible);
            }

            entreeSortie.setProbleme(probleme);
            if (null!=file) {
//                entreeSortie.setReferencePV(Paths.get(WORKING_DIR + "/" + StringUtils.cleanPath(file.getOriginalFilename())).toString());
                entreeSortie.setReferencePV(StringUtils.cleanPath(file.getOriginalFilename()));
            }

            entreeSortieService.enregistrer(entreeSortie);
        }

        if (date_etalonnage != null) {
            outil.setDate_prochain_etalonnage(LocalDate.parse(date_etalonnage).plusMonths(outil.getPeriodicite()));
            outilService.enregistrer(outil);
        }

        if (file!=null) {

            // normalize the file path
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path path = Paths.get(WORKING_DIR + "/" + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return "redirect:/outils?keyword="+(Objects.equals(keyword, "null") ? "":keyword) +"&etalonnee="+etalonnee;
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadTextFileExample1(@PathVariable("fileName") String fileName) throws FileNotFoundException {
        //String path = System.getProperty("user.home");

        final File file = new File("C:\\DossierPVMagasin" + "/" + fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(inputStreamResource);
    }

    @PostMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id,
                            @Param("etalonnee") boolean etalonnee,
                            @Param("keyword") String keyword) throws OutilNotFoundException {
        service.supprimer(id);

        return "redirect:/outils?keyword="+(Objects.equals(keyword, "null") ? "":keyword) +"&etalonnee="+etalonnee;
    }



    @PostMapping ("/modifier/{id}")
    public String modifier(@PathVariable Long id,
                           @Param("keyword") String keyword,
                           @Param("etalonnee)") boolean etalonnee,
                           @RequestParam String designation,
                           @RequestParam String fournisseur,
                           @RequestParam String marque,
                           @RequestParam String modele,
                           @RequestParam String numero_de_serie,
                           @RequestParam String capacite,
                           @RequestParam String puissance,
                           @RequestParam String repere,
                           @RequestParam String etat,
                           @RequestParam Outil.Categorie categorie,
                           @RequestParam Outil.TypeStatut typeStatut,
                           @RequestParam (required = false) String date_prochain_etalonnage,
                           @RequestParam String periodicite) {

        Outil outil = new Outil();
        outil.setId(id);
        outil.setDesignation(designation);
        outil.setFournisseur(fournisseur);
        outil.setMarque(marque);
        outil.setModele(modele);
        outil.setNumero_de_serie(numero_de_serie);
        outil.setCapacite(capacite);
        outil.setCategorie(categorie);
        outil.setPuissance(puissance);
        outil.setRepere(repere);
        outil.setEtat(Outil.Etat.valueOf(String.valueOf(etat)));
        outil.setTypeStatut(Outil.TypeStatut.valueOf(String.valueOf(typeStatut)));
        if (periodicite.length()!=0)
            outil.setPeriodicite(Integer.parseInt(periodicite));
        if (date_prochain_etalonnage!=null)
            if (date_prochain_etalonnage.length()!=0)
                outil.setDate_prochain_etalonnage(LocalDate.parse(date_prochain_etalonnage));
        outil.setEtalonnee(etalonnee);

        service.enregistrer(outil);

        return "redirect:/outils?keyword="+(Objects.equals(keyword, "null") ? "":keyword) +"&etalonnee="+etalonnee;
    }
}