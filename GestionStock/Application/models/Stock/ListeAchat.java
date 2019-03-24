package models.Stock;

import java.util.*;

import models.Util;
import models.Element.Element;

/**
 * 
 * @author claude
 *
 */
public class ListeAchat {

	
	private Map<Element, Double> achat;

	public ListeAchat() {
		
		this.achat = new HashMap<Element, Double>();
	}
	
	/**
	 * Constructeur par copie
	 * @param l la liste d'achat
	 */
	
	public ListeAchat(ListeAchat l) {
		this();
		
		for(Element key: l.achat.keySet()) {
			
			Element nouv = new Element(key);
			double quantite = l.achat.get(key);
			
			this.acheter(nouv, quantite);
		}
	}

	/**
	 * @return la valeur de la liste d'achat
	 */
	public double getValeur() {
		double prix = 0;
		for (Element key : achat.keySet()) {

			// on prend le prix de l'element
			double prixElement = key.getPrixAchat();

			// on prend le prix de la quantite en stock
			double prixElementCumul = prixElement * (achat.get(key)).doubleValue();

			prix += prixElementCumul;
		}
		return prix;
	}

	/**
	 * 
	 * @param e        l'element à acheter
	 * @param quantite la quantite de l'element
	 */

	public void acheter(Element e, double quantite) {
		
		Util.addInMap(this.achat, e, quantite);
	}

	public void retirerListe(Element e) {
		this.achat.remove(e);
	}

	public String toString() {
		String message = "";

		for (Element key : achat.keySet()) {
			message += key.toString()+" Quantité "+this.achat.get(key)+"\n";
			
		}

		return message;
	}
	
	public Double getQuantite(Element e) {
		return (this.achat.get(e)).doubleValue();
	}
	
	public Map<Element, Double> getListeAchat(){
		return this.achat;
	}

}
