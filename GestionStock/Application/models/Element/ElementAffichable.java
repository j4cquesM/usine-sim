package models.Element;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ElementAffichable extends Element {
	private DoubleProperty quantite;

	public ElementAffichable(ElementAffichable e) {
		super(e);
		this.quantite = new SimpleDoubleProperty(e.getQuantite());
		// TODO Auto-generated constructor stub
	}
	
	public ElementAffichable(String nom, String codeUnique, String uniteMesure,
			double prixAchat, double prixVente,double quantite) {
		
		super(nom,codeUnique,uniteMesure,prixAchat,prixVente);
		this.quantite = new SimpleDoubleProperty(quantite);
		
	}
	
	public Double getQuantite() {
		return this.quantite.get();
	}
	
	public DoubleProperty getQuantiteProperty() {
		return this.quantite;
	}
	
	public String toString() {
		String message = super.toString();
		message += "Quantite = "+this.getQuantite();
		return message;
	}
	

}
