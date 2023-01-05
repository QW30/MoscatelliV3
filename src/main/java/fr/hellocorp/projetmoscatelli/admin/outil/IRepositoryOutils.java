package fr.hellocorp.projetmoscatelli.admin.outil;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IRepositoryOutils extends PagingAndSortingRepository<Outil, Long> {
    Long countById(Long id);

    @Query(
            value="SELECT o.*, IF(o.type_statut=0,'Disponible',IF(o.type_statut=1,'Rebut',IF(o.type_statut=2,'Manquant',IF(o.type_statut=3,'Maintenance',IF(o.type_statut=4,'Etalonnage',IF(o.type_statut=5,'Prêt','Statut inconnu')))))) AS statut FROM outils o WHERE (o.etalonnee=?2) AND ((IF(o.type_statut=0,'Disponible', IF(o.type_statut=1,'Rebut', IF(o.type_statut=2,'Manquant', IF(o.type_statut=3,'Maintenance', IF(o.type_statut=4,'Etalonnage', IF(o.type_statut=5,'Prêt','Statut inconnu')))))) LIKE %?1%) OR (o.designation LIKE %?1% ) OR (o.fournisseur LIKE %?1%) OR (o.periodicite LIKE %?1%) OR (o.marque LIKE %?1%) OR (o.puissance LIKE %?1%) OR (o.modele LIKE %?1%) OR (o.numero_de_serie LIKE %?1%) OR (o.repere LIKE %?1%) OR (o.etat LIKE %?1%) OR (o.capacite LIKE %?1%) OR (o.categorie LIKE  %?1%));",
            nativeQuery = true)
    List<Outil> search(String keyword, boolean etalonnee);

    @Query(
            value = "SELECT o.* FROM outils o WHERE (o.etalonnee=?1);",
            nativeQuery = true)
    List<Outil> findByEtalonnee(boolean etalonnee);



}

