package serializer;

import java.util.*;
import serializer.PersonnelTemplate;

import models.Personnel.*;

public class JSONTemplate {
	public String filename;
	private Map stock;
	private Map listeachat;
	private ArrayList<PersonnelTemplate> liste_personnel;

	public JSONTemplate(String filename, Map stock, Map listeachat) {
		this.filename=filename;
		this.stock=stock;
		this.listeachat=listeachat;
	}


}
