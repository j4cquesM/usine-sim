package models.Element;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author claude
 *
 */
public class Element {
	private StringProperty nom;
	private StringProperty codeUnique;
	private StringProperty uniteMesure;
	private DoubleProperty prixAchat;
	private DoubleProperty prixVente;
	
	


	public Element(String nom, String codeUnique, String uniteMesure, double prixAchat, double prixVente) {
		this.nom = new SimpleStringProperty(nom);
		this.codeUnique = new SimpleStringProperty(codeUnique);
		this.uniteMesure = new SimpleStringProperty(uniteMesure);
		this.prixAchat = new SimpleDoubleProperty(prixAchat);
		this.prixVente = new SimpleDoubleProperty(prixVente);
	}
	
	
	public Element(Element e)
	{
		this(e.getNom(),e.getCodeUnique(),e.getUniteMesure(),e.getPrixAchat(),e.getPrixVente());
	}


	public String getNom() {
		return this.nom.get();
	}
	
	public StringProperty getNomProperty() {
		return this.nom;
	}

	public String getCodeUnique() {
		return this.codeUnique.get();
	}
	
	public StringProperty getCodeUniqueProperty() {
		return this.codeUnique;
	}

	public StringProperty getUniteMesureProperty() {
		return this.uniteMesure;
	}
	
	public String getUniteMesure() {
		return this.uniteMesure.get();
	}

	public DoubleProperty getPrixAchatProperty() {
		return this.prixAchat;
	}
	
	public double getPrixAchat() {
		return this.prixAchat.get();
	}


	public DoubleProperty getPrixVenteProperty() {
		return this.prixVente;
	}
	
	public double getPrixVente() {
		return this.prixVente.get();
	}
	
	
	

	public boolean isMatierePremiere() {
		
		if( this.getPrixAchat() != 0 )
		{
			return true;
		}
		return false;
	}
	
	public boolean isProduitFini() {
		if( this.getPrixVente() != 0 ) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		
		String message = this.getNom()+" "+this.getCodeUnique()+" : "+this.getPrixAchat()+" "+this.getPrixVente()+" \n";
		
		return message;
	}
	
	@Override 
	
	public boolean equals(Object e) {
		boolean result = false ; 
		
		if((Element) e == this ) {
			result = true; 
		}
		
		if( ! ( e instanceof Element ) ) {
			result = false ;
			
		}
		
		if ( ((Element) e).getCodeUnique() == this.getCodeUnique() )
		{
			result = true;
		}
		
		return result ;
	} 
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getCodeUnique().hashCode();
        return result;
    }





	
	
	
	

}
