package models.Chaine;
import java.util.*;

import exception.NonExistantException;
import exception.ProductionImpossibleException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.Util;
import models.Element.Element;
import models.Stock.ListeAchat;
import models.Stock.Stock;
/**
 * 
 * @author claude
 *
 */
public class Chaine {
	private StringProperty nom;
	private StringProperty code;
	private Map<Element, Double> composants;
	private Map<Element,Double> sortie;
	
	
	public Chaine(String nom,String code)
	{
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.composants = new HashMap<Element,Double>();
		this.sortie = new HashMap<Element,Double>();
		
	}
	

	
	/**
	 * 
	 * @param niveauActivation le niveau d'activation de la chaine
	 * @param stock le stock 
	 * @return le benefice produit
	 *  @throws ProductionImpossibleException production impossible
	 */
	public Stock  produire(double niveauActivation, Stock stock) throws ProductionImpossibleException
	{
		//duplication du stock pour la simulation
		Stock stockSimulation = new Stock(stock);
		
		for(Element elem : this.composants.keySet())
		{
			
				double quantite = niveauActivation * this.composants.get(elem);
				
				try {
					stockSimulation.destocker(elem , quantite );
					
					if( stockSimulation.getQuantite(elem) < 0  ) {
						
						if( elem.isMatierePremiere() ) {
							
							//ajout à la liste d'achat 
							double quantiteAchat = stockSimulation.getQuantiteMinAchat(elem);
							
							stock.acheter(elem, quantiteAchat);
							
						}
						else {
							throw new ProductionImpossibleException(elem);
						}
					}
					
				} catch (NonExistantException e) {
					// TODO Auto-generated catch block
					throw new ProductionImpossibleException(elem);
				}
						
				
		}
		//ajout des elements en sortie de le stock
		
		for(Element key: sortie.keySet()) {			
			double quantiteAjout= niveauActivation * this.sortie.get(key);
			stockSimulation.stocker(key, quantiteAjout);
		}
		return stockSimulation ;
	}
	
	/**
	 * Ajoute un element en entrée
	 * @param e element 
	 * @param quantite quantite de l'element
	 */
	
	public void addComposant(Element e, double quantite)
	{
		Util.addInMap(this.composants, e, quantite);
	}
	
	/**
	 * Ajoute un element en sortie
	 * @param e element
	 * @param quantite quantite de l'element
	 */
	
	public void addSortie(Element e,double quantite)
	{
		Util.addInMap(this.sortie, e, quantite);
	}
	
	/**
	 * Supprime un element en entrée
	 * @param e element à supprimer
	 */
	
	public void removeComposant(Element e)
	{
		Util.removeInMap(this.composants, e);
	}
	
	/**
	 * Supprime un element en sortie
	 * @param e elemnent à supprimer
	 */
	
	
	public void removeSortie(Element e) {
		
		Util.removeInMap(this.sortie, e);
	}
	
	public String toString() {
		String message = ""+this.getNom()+"\n"+this.getCode()+"\n"+"Composants : \n";
		
		for (Element key : this.composants.keySet()) {
			message += key.getNom()+" , "+key.getCodeUnique()+"\n";
		}

		message += "Produits : \n ";

		for (Element key : this.sortie.keySet()) {
			message += key.getNom()+" "+key.getCodeUnique()+"\n";
		}

		
		return message;
	}
	
	public String getNom() {
		return this.nom.get();
	}
	
	public StringProperty getNomProperty() {
		return this.nom;
	}
	
	public String getCode() {
		return this.code.get();
	}
	
	public StringProperty getCodeProperty() {
		return this.code;
	}
	
	
	
	
	
}