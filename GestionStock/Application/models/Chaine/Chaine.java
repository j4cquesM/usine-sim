package models.Chaine;
import java.util.*;

import exception.NonExistantException;
import exception.ProductionImpossibleException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import models.Util;
import models.Element.Element;
import models.Personnel.Personnel;
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
	private TextField niveauActivation;
	private DoubleProperty temps ;
	private DoubleProperty nbPersonnelQualifie ;
	private DoubleProperty nbPersonnelNonQualifie ;
	private Map<Element, Double> composants;
	private Map<Element,Double> sortie;
	
	
	public Chaine(String nom,String code)
	{
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.composants = new HashMap<Element,Double>();
		this.sortie = new HashMap<Element,Double>();
		this.niveauActivation = new TextField("") ;
		
	}
	
	public Chaine(String nom,String code,Double temps , Double nbPersonnelQualifie,  Double nbPersonnelNonQualifie)
	{
		this.code = new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.composants = new HashMap<Element,Double>();
		this.sortie = new HashMap<Element,Double>();
		this.niveauActivation = new TextField("") ;
		this.temps = new SimpleDoubleProperty(temps) ;
		this.nbPersonnelNonQualifie = new SimpleDoubleProperty(nbPersonnelNonQualifie) ;
		this.nbPersonnelQualifie = new SimpleDoubleProperty(nbPersonnelQualifie) ;	
	}
	
	
	/**
	 * 
	 * @param niveauActivation le niveau d'activation de la chaine
	 * @param stock le stock 
	 * @param listePersonnel liste des personnels qualifiés et non qualfiés 
	 * @return le benefice produit
	 *  @throws ProductionImpossibleException production impossible
	 */
	public Stock  produire(double niveauActivation, Stock stock ) throws ProductionImpossibleException
	{
		//duplication du stock pour la simulation
		Stock stockSimulation = new Stock(stock);
		
		if(niveauActivation != 0)
		{
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
								throw new ProductionImpossibleException("Impossible de lancer la production de la chaine "+this.getNom()+" car "+elem.getNom()+" a un stock negatif" );
							}
						}
						
					} catch (NonExistantException e) {
						// TODO Auto-generated catch block
						throw new ProductionImpossibleException("Impossible de lancer la production de la chaine "+this.getNom()+"\n"+e.getMessage());
					}
							
					
			}
			//ajout des elements en sortie de le stock
			
			for(Element key: sortie.keySet()) {			
				double quantiteAjout= niveauActivation * this.sortie.get(key);
				stockSimulation.stocker(key, quantiteAjout);
			}
		}
		
		return stockSimulation ;
	}
	
	/**
	 * 
	 * @param niveauActivation le niveau d'activation de la chaine
	 * @param stock le stock 
	 * @param listePersonnel liste des personnels qualifiés et non qualfiés 
	 * @return le benefice produit
	 *  @throws ProductionImpossibleException production impossible
	 */
	public ArrayList<Personnel> gestionPersonnel( ArrayList<Personnel> listePersonnel) throws ProductionImpossibleException
	{
		ArrayList<Personnel> listeResultat = new ArrayList<>() ;
		ArrayList<Personnel> listeResultatFinal = new ArrayList<>() ;
		//copie de la liste de personnel de base
		for(Personnel p:listePersonnel) {
			listeResultat.add(p) ;
		}
		
		//personnel qualifiés 
		ArrayList<Personnel> personnelQualifie = this.getListePersonnelQualifie(listeResultat, this.getPersonnelQualifie()) ;
		if( personnelQualifie.size() == this.getPersonnelQualifie() )
		{
			//on ajout à la liste de resultat
			listeResultatFinal.addAll(personnelQualifie) ;
			
			//personnel non qualifié
			ArrayList<Personnel> personnelNonQualifie = this.getListePersonnelNonQualifie(listeResultat, this.getPersonnelNonQualifie()) ;
			listeResultatFinal.addAll(personnelNonQualifie) ;
			if( personnelNonQualifie.size() != this.getPersonnelNonQualifie() ) {
				//si on a pas assez de personnel qualifié on fait appel au personnel qualifié 
				listeResultat.removeAll(personnelQualifie) ;
				double personnelRestant = this.getPersonnelNonQualifie() - personnelNonQualifie.size() ;
				ArrayList<Personnel> personnelNonQualifieRestant = this.getListePersonnelQualifie(listeResultat, personnelRestant) ;
				
				if(personnelNonQualifie.size() + personnelNonQualifieRestant.size() == this.getPersonnelNonQualifie())
				{
					listeResultatFinal.addAll(personnelNonQualifieRestant) ; 
				}
				else
				{
					throw new ProductionImpossibleException("Impossible de lancer la production de la chaine "+this.getNom()+" car "
							+ "il n'y a pas assez de personnel non qualifié ");
				}
			}
			
			listeResultatFinal = this.addTempsTravail(listeResultatFinal,true) ;
			listeResultat = Util.addSansDoublons(listeResultat, listeResultatFinal) ;
		}
		else
		{
			throw new ProductionImpossibleException("Impossible de lancer la production de la chaine "+this.getNom()+" car "
					+ "il n'y a pas assez de personnel qualifié ");
		}
		
		//ObservableList<Personnel> listeResultatObservable = FXCollections.observableArrayList(listeResultat); 
		
		return listeResultat  ;
	}
	
	/**
	 * 
	 * @param list liste des personnes à faire travailler 
	 * @param forced indique si un qualifié fait le travail d'un non qualifié
	 */
	
	private ArrayList<Personnel> addTempsTravail ( ArrayList<Personnel> list , boolean forced ) {
		for(Personnel p : list) {
			p.addTempsTravail(this.getCode(), this.getTemps(), forced);
		}
		return list ;
	}
	
	/**
	 * @param listePersonnel
	 * @param nbRequis nombre de personnel qualifiés requis 
	 * @return Renvoie les personnes qualifiés
	 */
	
	private ArrayList<Personnel> getListePersonnelQualifie( ArrayList<Personnel> listePersonnel , double nbRequis){
		ArrayList<Personnel> listeResultat = new ArrayList<>() ;
		if( nbRequis > 0 )
		{
			Iterator<Personnel> iter = listePersonnel.iterator() ;
			
			double nb = 0.0 ;
			
			while ( iter.hasNext() && (nb< nbRequis) ) {
				Personnel p = iter.next() ;
				if( p.isQualified() )
				{
					if(p.checkTempsTravail(this.getTemps()))
					{
						listeResultat.add(p);
						nb ++ ;
					}
				}
			}
		}
		
		return listeResultat ;
	}
	
	private ArrayList<Personnel> getListePersonnelNonQualifie( ArrayList<Personnel> listePersonnel , double nbRequis ){
			
		ArrayList<Personnel> listeResultat = new ArrayList<>() ;

		if( nbRequis > 0 )
		{
			Iterator<Personnel> iter = listePersonnel.iterator() ;
			
			double nb = 0.0 ;
			
			while ( iter.hasNext() && (nb<nbRequis) ) {
				Personnel p = iter.next() ;
				if( ! p.isQualified() )
				{
					if(p.checkTempsTravail(this.getTemps()))
					{
						listeResultat.add(p);
						nb ++ ;
					}
				}
			}
		}
		
		return listeResultat ;
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
	

	public void setNiveauActivation(TextField na) {
		this.niveauActivation= na ;
	}
	
	public TextField getNiveauActivation() {
		return this.niveauActivation;
	}
	
	public double getTemps()
	{
		return this.temps.get() ;
	}
	
	public DoubleProperty getTempsProperty()
	{
		return this.temps ;
	}
	
	public double getPersonnelQualifie()
	{
		return this.nbPersonnelQualifie.get() ;
	}
	
	public DoubleProperty getPersonnelQualifieProperty()
	{
		return this.nbPersonnelQualifie ;
	}
	
	public double getPersonnelNonQualifie()
	{
		return this.nbPersonnelNonQualifie.get() ;
	}
	
	public DoubleProperty getPersonnelNonQualifieProperty()
	{
		return this.nbPersonnelNonQualifie ;
	}
	

}