package fr.demos.formation.poe.cinegrandearche.metier;

public class LignePanier {

	private Article article;
	private int quantite;
	
	public LignePanier(Article article, int quantite) {
		super();
		this.article = article;
		this.quantite = quantite;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LignePanier other = (LignePanier) obj;
		if (article == null) {
			if (other.article != null)
				return false;
		} else if (!article.equals(other.article))
			return false;
		return true;
	}



	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	

	@Override
	public String toString() {
		return "LignePanier [quantite=" + quantite + ", article=" + article + "]";
	}
		
}
