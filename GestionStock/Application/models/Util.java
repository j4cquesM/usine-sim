package models;

import java.util.Map;

import exception.*;
import models.Element.Element;

/**
 * 
 * @author claude
 *
 */

public class Util {
	
	/**
	 * Methode pour ajouter ou metre à jour un element dans une map
	 * @param map où on doit ajouter l'element
	 * @param e l'element à ajouter dans la map
	 * @param quantite la quantite de l'element à ajouter
	 */
	public static void addInMap(Map<Element, Double> map, Element e, double quantite)
	{
		//si l'element existe dans la map
		if(map.containsKey(e))
		{		
			// on prend la quantite actuelle
			double oldQuantite = (map.get(e)).doubleValue();

			// on ajoute la nouvelle quantite
			double newQuantite = oldQuantite + quantite;

			// on met à jour la quantite dans la map
			map.replace(e, newQuantite);
		}
		else {
			//si l'element n'existe pas 
			
			//on l'ajoute dans la map
			Double nouvQuantite = new Double(quantite);
			map.put(e, nouvQuantite);
		}
	}
	
	/**
	 * Methode pour destocker
	 * @param map  la map ù on doit supprimer l'element
	 * @param e l'element à destocker
	 * @param quantite la quantite à destocker
	 * @throws NonExistantException au cas ou l'elem n'existe pas dans le stock
	 */
	
	public static void removeInMap(Map<Element, Double> map, Element e, double quantite) throws NonExistantException
	{
		//si l'element existe dans la map
		if(map.containsKey(e))
			
		{	//on met à jour la quantite	
			double old = map.get(e);
			
			double nouv = old -quantite; 
			
			map.replace(e, nouv);
		}
		else {
			throw  new NonExistantException(e);
		}
	}
	
	public static void removeInMap(Map<Element,Double> map, Element e)
	{
		map.remove(e);
	}

}
