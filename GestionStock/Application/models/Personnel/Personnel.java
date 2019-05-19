package models.Personnel;

/**
 * @author claude
 */
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.Util;
import models.Chaine.Chaine;

public class Personnel {
	private StringProperty code;
	private StringProperty nom;
	private StringProperty prenom ;
	private boolean qualification ;
	private DoubleProperty totalTempsTravail ;
	private DoubleProperty tempsTravaille ;
	private HashMap<String,Double> repartitionQualifiee ;
	private HashMap<String,Double> repartitionNonQualifiee ;
	
	public Personnel(String code,String prenom, String nom, boolean qualification, double totalTempsTravail){
		this.code = new SimpleStringProperty(code)  ;
		this.prenom = new SimpleStringProperty(prenom) ;
		this.nom = new SimpleStringProperty(nom) ;
		this.qualification = qualification ;
		this.totalTempsTravail = new SimpleDoubleProperty(totalTempsTravail);
		this.tempsTravaille = new SimpleDoubleProperty(0.0) ;
		this.repartitionQualifiee = new HashMap<>();
		this.repartitionNonQualifiee = new HashMap<>();
	}
	
	public Personnel(Personnel p){
		this.code = p.code ;
		this.prenom = p.getPrenomProperty() ;
		this.nom = p.getNomProperty() ;
		this.qualification = p.getQualification() ;
		this.totalTempsTravail = p.getTotalTempsTravailProperty();
		this.tempsTravaille = p.getTempsTravailleProperty();
		this.repartitionQualifiee = new HashMap<>();
		for(String code:p.repartitionQualifiee.keySet())
		{
			this.repartitionQualifiee.put(code, p.repartitionQualifiee.get(code)) ;
		}
		this.repartitionNonQualifiee = new HashMap<>();
		for(String code:p.repartitionNonQualifiee.keySet())
		{
			this.repartitionNonQualifiee.put(code, p.repartitionNonQualifiee.get(code)) ;
		}
	}
	
	/**
	 * Verifie si le personnel peut travailler pdt le temps mentionné
	 * @param newTemps le nouveau temps
	 * @return oui ou non
	 */
	public boolean checkTempsTravail(double newTemps)
	{
		boolean result = true ;
		if (this.getTempsTravaille() + newTemps > this.getTotalTempsTravail() )
		{
			result =  false ;
		}
		return result ;
	} 
	
	/**
	 * Ajoute sur le planning du personnel une chaine
	 * @param 
	 * @param temps
	 */
	public void addTempsTravail(String nomChaine, double temps,boolean qualification)
	{
		Map map = null ;
		//ajout decompte du temps mis sur la nouvelle chaine
		this.setTempsTravaille(temps);
		//ajout de la chaine dans repartition
		if( this.isQualified() )
		{
			map = this.repartitionQualifiee ;
		}
		if( ! this.isQualified() || qualification)
		{
			map = this.repartitionNonQualifiee ;
		}
		Util.addInMap(map, nomChaine, temps);
		
	}
	
	public boolean isQualified()
	{
		boolean qualified = false ; 
		if( this.getQualification() )
		{
			qualified = true ;
		}
		
		return qualified; 
	}
	
	public String toString()
	{
		String qualified = "non qualifié" ;
		if(this.isQualified())
		{
			qualified = "qualifié" ;
		}
		String message = this.getPrenom() + " " + this.getNom() + ", personnel "+qualified+" .Heure travaillées : " + " "+this.getTempsTravaille() + "\n" ;
		
		
		return message ;
		
	}
	
	
	public String getNom() {
		return this.nom.get();
	}
	
	public StringProperty getNomProperty() {
		return this.nom;
	}
	
	public String getPrenom() {
		return this.prenom.get();
	}
	
	public StringProperty getPrenomProperty() {
		return this.prenom;
	}

	public HashMap<String,Double> getRepartitionQualifiee() { return this.repartitionQualifiee;}

	public HashMap<String,Double> getRepartitionNonQualifiee() { return this.repartitionNonQualifiee;}

	
	public boolean getQualification()
	{
		return this.qualification ;
	}
	
	public StringProperty getQualificationPropertyStr()
	{
		StringProperty result = new SimpleStringProperty() ;
		if(this.isQualified())
		{
			result.setValue("Qualifié"); 
		}
		else
		{
			result.setValue("Non qualifié"); 
		}
		return result ;
	}
	
	public double getTotalTempsTravail()
	{
		return this.totalTempsTravail.get();
	}
	
	public DoubleProperty getTotalTempsTravailProperty()
	{
		return this.totalTempsTravail ;
	}
	
	public double getTempsTravaille()
	{
		return this.tempsTravaille.get() ;
	}
	
	public DoubleProperty getTempsTravailleProperty()
	{
		return this.tempsTravaille ;
	}
	
	public String getCode() {
		return this.code.get() ;
	}
	
	public StringProperty getCodeProperty() {
		return this.code ;
	}
	
	/**
	 * 
	 * @param newTemps le temps passé sur une chaine est elevé 
	 */
	private void setTempsTravaille(double newTemps) {
		
		double old = this.getTempsTravaille() ;
		this.tempsTravaille = new SimpleDoubleProperty(old + newTemps);
	}
	
	@Override
	public boolean equals(Object e) {
		boolean result = false ; 
		Personnel p = (Personnel) e ;
		if(p== this )
		{
			result = true ;
		}
		
		if(p.getCode() == this.getCode()){
			result = true ;
		}
		
		if(p.hashCode() == this.hashCode()) {
			result = true ;
		}
		
		return result ; 
		
	}
	
	@Override
    public int hashCode() {
        final int prime = 30;
        int result = 1;
        result = prime * result + this.getCode().hashCode();
        return result;
    }
	
	
	
}
