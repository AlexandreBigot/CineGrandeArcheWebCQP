package fr.demos.formation.poe.cinegrandearche.exceptions;

public class ExceptionRetirerArticlePanier extends Exception {

	private int quantiteDepassement;

	public ExceptionRetirerArticlePanier(String message, int quantiteDepassement) {
		super(message);
		this.quantiteDepassement = quantiteDepassement;
	}
	
	public int getQuantiteDepassement(){
		return quantiteDepassement;
	}
	
}
