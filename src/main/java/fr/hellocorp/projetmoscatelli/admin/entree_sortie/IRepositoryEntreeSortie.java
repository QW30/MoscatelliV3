package fr.hellocorp.projetmoscatelli.admin.entree_sortie;

import fr.hellocorp.projetmoscatelli.admin.pojo.EtalonnageES;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRepositoryEntreeSortie extends CrudRepository <EntreeSortie, Long> {
    Long countById(Long id);

    @Query("SELECT p FROM EntreeSortie p WHERE p.utilisateur.nom LIKE %?1%"
            + " OR p.utilisateur.prenom LIKE %?1%"
            + " OR p.outil.designation LIKE %?1%"
            + " OR p.outil.repere LIKE %?1%"
            + " OR CONCAT(p.date_sortie, '') LIKE %?1%"
            + " OR CONCAT(p.date_de_retour_prevue, '') LIKE %?1%"
            + "OR p.probleme LIKE %?1%"
            + "OR p.EXE LIKE %?1%"
            + "OR p.referencePV LIKE %?1%" )
    List<EntreeSortie> search(String keyword);

    @Query("SELECT es FROM EntreeSortie es WHERE (es.outil.id = ?1) AND es.date_retour IS NULL")
    EntreeSortie findLastByOutil(Long id_outil);

    @Query(value="select entrees_sorties.*, outils.designation, utilisateurs.nom, utilisateurs.prenom from entrees_sorties left join outils on outils.id = entrees_sorties.id_outil left join utilisateurs on utilisateurs.id = entrees_sorties.id_utilisateur where entrees_sorties.date_de_retour_prevue <= DATE(NOW()) AND entrees_sorties.date_retour is NULL;",nativeQuery = true)
    List<EntreeSortie> retardOutils();


    @Query(value="select entrees_sorties.*, outils.designation, utilisateurs.nom, utilisateurs.prenom from entrees_sorties left join outils on outils.id = entrees_sorties.id_outil left join utilisateurs on utilisateurs.id = entrees_sorties.id_utilisateur where entrees_sorties.date_de_retour_prevue = ADDDATE(DATE(NOW()),+7) AND entrees_sorties.date_retour is NULL;",nativeQuery = true)
    List<EntreeSortie> notificationsOutils();


    @Query(value="SELECT o.id AS id, o.designation AS designation, o.periodicite AS periodicite, es.date_etalonnage AS dateEtalonnage, IF(o.periodicite IS NULL, NULL, IF(date_etalonnage IS NULL, NOW(), DATE_ADD(date_etalonnage, INTERVAL o.periodicite MONTH))) AS dateProchainEtalonnage, es.id_utilisateur AS idUtilisateur, u.nom AS nom, u.prenom AS prenom FROM outils o LEFT OUTER JOIN (SELECT id_outil, MAX(date_etalonnage) AS date_etalonnage, id_utilisateur FROM entrees_sorties GROUP BY id_outil) es ON o.id = es.id_outil LEFT OUTER JOIN Utilisateurs u on u.id = es.id_utilisateur WHERE (IF(o.periodicite IS NULL, NULL, IF(date_etalonnage IS NULL, NOW(), DATE_ADD(date_etalonnage, INTERVAL o.periodicite MONTH)))<=DATE_ADD(NOW(),INTERVAL 10 DAY));",nativeQuery = true)
    List<EtalonnageES> notificationsEtalonnage(@Param("periodicite") int periodicite);

}
