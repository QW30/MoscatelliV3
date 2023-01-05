package fr.hellocorp.projetmoscatelli.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ConfigurationController {


    @Autowired
    private ConfigurationService service;

    @GetMapping("/configuration")
    public String showConfigurations(Model model) {
        Configuration config = service.getConfig();
        //List<Configuration> configurations = service.listeConfigurations();
        model.addAttribute("configuration", config);

        return "configuration";
    }

//    @GetMapping("/configuration/nouveau")
//    public String showNouvelleConfiguration(Model model) {
//        Configuration configuration = new Configuration();
//        model.addAttribute("configuration", configuration);
//
//        return "configuration_saisie";
//    }

    @PostMapping("/configuration/enregistrer")
    public String enregistrer(Configuration configuration) {
        service.enregistrer(configuration);

        return "redirect:/configuration";
    }
}
