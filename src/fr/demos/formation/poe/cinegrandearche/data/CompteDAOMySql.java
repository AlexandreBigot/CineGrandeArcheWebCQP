package fr.demos.formation.poe.cinegrandearche.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import fr.demos.formation.poe.cinegrandearche.metier.Compte;

public class CompteDAOMySql implements CompteDAO {
	
	//plus besoin context avec CDI
	//private Context context;
	@Resource(mappedName="java:comp/env/jdbc/CineGrandeArche") 
	private DataSource dataSource;
	
	// dans le constructeur je lance le context (annuaire) et le datasource (pool de connexion)
	public CompteDAOMySql() throws Exception {
		// plus besoin avec CDI
		//context = new InitialContext();
		//dataSource = (DataSource) context.lookup("java:comp/env/jdbc/CineGrandeArche");
	}
	
	@Override
	public void insert(Compte c) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Compte c) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Compte c) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Compte select(String email, String password) {
		
		// je cr�e un Compte � retourner
		Compte compte = null;
		
		// je me connecte
		try (Connection cx = dataSource.getConnection()){
			
			// je cr�e mon psmt qui me servira � �viter l'injection SQL r�ce aux "?"
			PreparedStatement contexteRequeteConnexionCompte = null;
			
			// je lance ma requ�te SQL
			contexteRequeteConnexionCompte = cx.prepareStatement(
				"SELECT * FROM Compte WHERE lower(Compte.email) LIKE ? AND Compte.password LIKE ?");
					
			// je renseigne les valeurs des "?"
			contexteRequeteConnexionCompte.setString(1, email);
			contexteRequeteConnexionCompte.setString(2, password);
			
			// stockage de mon compte pour le parcourrir
			ResultSet rs = contexteRequeteConnexionCompte.executeQuery();
			System.out.println("execute query lanc�");

			// si mon resultset est vide
			if (!rs.first()) {
				System.out.println("rs.next vide");
				// rien � faire compte (qu'on va retourner) est d�j� null par d�faut
				
			} else { // si pas vide
			
				// pas besoin de while next car un seul �l�ment
				// le rs.first s�lectionne le premier objet trouv�
				rs.first();
				System.out.println("rs.first effectu�");
				
				// je r�cup�re les donn�es dans des variables
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse_compte = rs.getString("adresse_compte");
				String adresse_livraison = rs.getString("adresse_livraison");
				String emailCompte = rs.getString("email");
				String telephone = rs.getString("telephone"); 
				String passwordCompte = rs.getString("password");

				// cr�ation ocmpte avec les infos r�cup�r�es
				compte = new Compte(nom, prenom, adresse_compte, adresse_livraison, emailCompte, telephone, passwordCompte);
			}// if else rs.first vide ou pas
			
						
		} // try de la connexion 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try catch
		
		return compte;
	}

	
	
}
