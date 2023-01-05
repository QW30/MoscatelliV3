package fr.hellocorp.projetmoscatelli.admin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private IRepositoryConfiguration repo;

    public List<Configuration> listeConfigurations() {
        return (List<Configuration>) repo.findAll();
    }

    public Configuration getConfig() {
        Optional<Configuration> config = repo.findById(1L);
        if (config.isPresent())
            return config.get();
        else
            return null;
    }
    public void enregistrer(Configuration configuration) {

        repo.save(configuration);
    }

}
