package fr.demos.formation.poe.cinegrandearche.metier;

public class Commande {

	private Panier panier;
	private Compte compte;
	private double totalTTC;
	private boolean paiementConfirme; // get pour v�rifier si pay�
	
	
	public Commande(Panier panier, Compte compte) {
		super();
		this.panier = panier;
		this.compte = compte;
		// TVA 20% sur tous les articles...
		this.totalTTC = this.panier.getPrixTotal()*1.2;
	} // constructeur
	
	
	public void validationCommande () {
		if (paiementConfirme == true) {
			// this.commande.creationFacture
			// this.commande.declencherLivraison
			
		} else {
		//	return exception paiement non valid�
		}
	}
	
	
	
	//TODO
	// ### m�thode pour payer
	// ### m�thode pour enregistrer et stocker la commande
	// ### m�thode pour �mettre la facture
	// ### m�thode pour d�clencher la livraison
	
} // class
