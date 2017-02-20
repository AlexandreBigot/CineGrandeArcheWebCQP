package fr.demos.formation.poe.cinegrandearche.metier;

import javax.inject.Inject;

import fr.demos.formation.poe.cinegrandearche.data.CompteDAO;
import fr.demos.formation.poe.cinegrandearche.data.CompteDAOMySql;

public class CompteLogin {
	
	// appel de l'interface on pourra choisir son dao si plusieurs
	@Inject CompteDAO compteDaoCDI;
	
	public Compte getCompteSiConnexionReussie(String identifiantEmail, String password) throws Exception {
		
		Compte compteRetourne = null;
		//je crée une instance du DAO qui va me retourner un Compte
		CompteDAOMySql compteDAOMySql;
		
			// plus besoin car on le récupère en injection
			//compteDAOMySql = new CompteDAOMySql();
			//compteRetourne = compteDAOMySql.select(identifiantEmail, password);

			// version avec injection
			compteRetourne = compteDaoCDI.select(identifiantEmail, password);
		
		return compteRetourne;
		// si retourne null erreur password ou email
	}	
}