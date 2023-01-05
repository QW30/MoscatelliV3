package fr.hellocorp.projetmoscatelli.admin.entree_sortie;

import fr.hellocorp.projetmoscatelli.admin.pojo.EtalonnageES;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRepositoryEtalonnageES extends JpaRepository<EtalonnageES, Long> {

    @Query(value="SELECT o.id AS id, o.designation AS designation, o.periodicite AS periodicite, o.modele as modele, o.repere as repere, es.date_etalonnage AS date_etalonnage, IF(o.periodicite IS NULL, NULL, IF(date_etalonnage IS NULL, NOW(), DATE_ADD(date_etalonnage, INTERVAL o.periodicite MONTH))) AS date_prochain_etalonnage, es.id_utilisateur AS id_utilisateur, u.nom AS nom, u.prenom AS prenom FROM outils o LEFT OUTER JOIN (SELECT id_outil, MAX(date_etalonnage) AS date_etalonnage, id_utilisateur FROM entrees_sorties GROUP BY id_outil) es ON o.id = es.id_outil LEFT OUTER JOIN Utilisateurs u on u.id = es.id_utilisateur WHERE (IF(o.periodicite IS NULL, NULL, IF(date_etalonnage IS NULL, NOW(), DATE_ADD(date_etalonnage, INTERVAL o.periodicite MONTH)))<=DATE_ADD(NOW(),INTERVAL 60 DAY));",nativeQuery = true)
    List<EtalonnageES> notificationsEtalonnage();

}
