package fr.demos.formation.poe.cinegrandearche.metier;

import java.util.ArrayList;
import java.util.Iterator;

import fr.demos.formation.poe.cinegrandearche.exceptions.ExceptionQuantiteDemandeeSuperieureAuStock;
import fr.demos.formation.poe.cinegrandearche.exceptions.ExceptionRetirerArticleAbsentDuPanier;
import fr.demos.formation.poe.cinegrandearche.exceptions.ExceptionRetirerArticlePanier;

public class Panier implements Iterable<LignePanier> {
	
	private Compte compte;
//	relier un panier � une session ou un compte
	private ArrayList<LignePanier> lignesPanier = new ArrayList<LignePanier>();
	
	
	@Override
	public String toString() {
		return "Panier [lignesPanier=" + lignesPanier + ", getPrixTotal()=" + getPrixTotal()
				+ ", getSizeContenuPanier()=" + getSizeContenuPanier() + ", getArticlesCumulesPanier()="
				+ getArticlesCumulesPanier() + "]";
	}

	public double getPrixTotal(){
		double prixTotal=0;
		for (LignePanier ligneDuPanier : lignesPanier){
			prixTotal = prixTotal + (ligneDuPanier.getQuantite() * ligneDuPanier.getArticle().getPrixHt());
		}
		return prixTotal;
	}
	
	//sera appel�e par ajouterUnArticle
	private void ajouterLignePanier(Article a, int quantite) throws ExceptionQuantiteDemandeeSuperieureAuStock{
		//si quantit� demand�e inf�rieure ou �gale au stock
		if (quantite <= a.getStock()){
			lignesPanier.add(new LignePanier(a, quantite));
		} else { // si quantite demand�e sup�rieure au stock
			if (a.getMateriel() == null){ // si d�materialise on s'en fiche c'est illimit�
				lignesPanier.add(new LignePanier(a, quantite));
			} else { // si mat�rialis� stock insuffisant
				throw new ExceptionQuantiteDemandeeSuperieureAuStock("Le stock n'est pas suffisant, nombre d'articles en stock : ", a.getStock());
				}//else
		}//if
	}// ajouterLignePanier
	
	public void ajouterUnArticle(Article a, int quantiteAjout�e) throws ExceptionQuantiteDemandeeSuperieureAuStock{
		LignePanier lp1 = new LignePanier(a, quantiteAjout�e);
		int indexDeMaLigne = lignesPanier.indexOf(lp1);
		
		// modif importante !!!
		// si la ligne a d�j� des articles il faut les comptabiliser pour comparer au stock
		// test sur quantit�LignePlusQuantiteAjout�e, pas uniquement sur la quantit� ajout�e !!!
		int quantit�LignePlusQuantiteAjout�e = lp1.getQuantite() + quantiteAjout�e;
		
		// si la ligne article existe d�j�
		if (indexDeMaLigne != -1){
			// si la quantit� ajout�e <= stock de l'article
			if (quantit�LignePlusQuantiteAjout�e <= a.getStock()){
				lignesPanier.get(indexDeMaLigne).setQuantite(
					lignesPanier.get(indexDeMaLigne).getQuantite() + quantiteAjout�e);
			} else { // si quantite demand�e sup�rieure au stock
				 // si d�materialise on s'en fiche c'est illimit�
				if (a.getMateriel() == null){
					lignesPanier.get(indexDeMaLigne).setQuantite(
							lignesPanier.get(indexDeMaLigne).getQuantite() + quantiteAjout�e);
				} else { // si mat�rialis� et stock insuffisant
					throw new ExceptionQuantiteDemandeeSuperieureAuStock(
							"Le stock n'est pas suffisant, nombre d'articles en stock : ", a.getStock());
					}//else
			}//if
		}//if
		// si la ligne article n'existe pas
		else { 
			this.ajouterLignePanier(a, quantiteAjout�e);
		}//else
	}//ajouterUnArticle
	

	// supprimer ligne � partir de la r�f�rence trouv�e dans la ligne (c'est ma cl� primaire)
	public void supprimerLignePanier(String refArticleLigne){
			
		// j'utilise l'outil iterator
		Iterator<LignePanier> iter = lignesPanier.iterator();
		// tant qu'il y en a
		while(iter.hasNext()){
			// va au suivant
			LignePanier lp= iter.next();
			// si la r�f est la m�me, c'est ma ligne panier
			if(lp.getArticle().getRef().equals(refArticleLigne)){
				iter.remove();
			} // if
		} // while
	} //supprimer ligne
	
	

	// TODO d�clencher mise � jour panier avec quantit� disponible en stock si return false
	// v�rifications si la commande peut �tre valid�e
	//    /!\   d�clencher le plus tard possible (� validation du paiement)    /!\
	public boolean validerStockCommande(Panier p){
		
		boolean stockArticlesOk = true;
		
		// parcourir le panier et sortir la quantit�
		for(LignePanier ligne : lignesPanier){
			
			int nbreArticlesLigne = ligne.getQuantite();			
			// si au moins une lignePanier > stock article
			if(nbreArticlesLigne <= ligne.getArticle().getStock()){
				stockArticlesOk = false;
			}
		} // for each
		return stockArticlesOk;
	} // validerCommande
	
	
	//  valider paiement puis modifier stock et d�clencher livraison, facture et enregistrement commande en BDD
	public void procederCommande(Panier p, Compte c){
		
	}
	
	// nombre de lignes dans panier
	public int getSizeContenuPanier(){
		int quantite = lignesPanier.size();
		return quantite;
	}
	
	//nombre d'articles dans panier (plusieurs articles par ligne)
	public int getArticlesCumulesPanier(){
		int i = 0;
		for(LignePanier lignePanier : lignesPanier){
			i = i + lignePanier.getQuantite();		
		}
		return i;
	}

	
	// m�thode pour modifier la quantit� de la ligne panier
	public void modifierQuantiteLignePanier (String refArticleLigne, int nouvelleQuantiteLigne) throws ExceptionQuantiteDemandeeSuperieureAuStock{
		for(LignePanier lignePanier : lignesPanier){
			// si c'est bien mon article
			if (lignePanier.getArticle().getRef().equals(refArticleLigne)){
				// si article d�mat�rialis�
				if(lignePanier.getArticle().getMateriel() == null){
					lignePanier.setQuantite(nouvelleQuantiteLigne);
				} else { // si article mat�riel 
					// test quantit� demand�e et stock
					if(nouvelleQuantiteLigne <= lignePanier.getArticle().getStock()) {
						lignePanier.setQuantite(nouvelleQuantiteLigne);
					} else {
						throw new ExceptionQuantiteDemandeeSuperieureAuStock(
								"Le stock n'est pas suffisant, nombre d'articles en stock : ", lignePanier.getArticle().getStock());
					}// if else test stock
				}// if else mat�riel ou pas
			} // if article
		} //for
	} // modifierQuantiteLignePanier
			
	@Override
	public Iterator<LignePanier> iterator() {
		// TODO Auto-generated method stub
		return lignesPanier.iterator();
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	
	
}// class
