package fr.demos.formation.poe.cinegrandearche.metier;

import fr.demos.formation.poe.cinegrandearche.data.CompteDAOMySql;

public class CompteLogin {
	
	public Compte getCompteSiConnexionReussie(String identifiantEmail, String password) throws Exception {
		
		Compte compteRetourne = null;
		//je cr�e une instance du DAO qui va me retourner un Compte
		CompteDAOMySql compteDAOMySql;
		
			compteDAOMySql = new CompteDAOMySql();
			compteRetourne = compteDAOMySql.select(identifiantEmail, password);

		return compteRetourne;
		// si retourne null erreur password ou email
	}	
}