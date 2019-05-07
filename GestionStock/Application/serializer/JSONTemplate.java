package serializer;

import java.util.ArrayList;
import java.util.Map;
import models.Personnel.*;

public class JSONTemplate {
	public String filename;
	private Map stock;
	private Map listeachat;
	private ArrayList<Personnel> p;

	public JSONTemplate(String filename, Map stock, Map listeachat) {
		this.filename=filename;
		this.stock=stock;
		this.listeachat=listeachat;
	}
	public JSONTemplate(String filename, ArrayList<Personnel> p){
		this.filename=filename;
		this.p=p;
	}

}