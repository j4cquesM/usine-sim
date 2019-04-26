package models.Chaine;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ChaineAffichable extends Chaine {
	private TextField niveauActivation;

	public ChaineAffichable(String nom, String code,String niveauActivation) {
		super(nom, code);
		// TODO Auto-generated constructor stub
		this.niveauActivation = new TextField(niveauActivation) ;
	}
	
	public void setNiveauActivation(TextField na) {
		this.niveauActivation= na ;
	}
	
	public TextField getNiveauActivation() {
		return this.niveauActivation;
	}

}
