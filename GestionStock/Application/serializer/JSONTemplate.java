package serializer;

import java.util.ArrayList;
import java.util.Map;

public class JSONTemplate {
	public String filename;
	private Map stock;
	private Map listeachat;

	public JSONTemplate(String filename, Map stock, Map listeachat) {
		this.filename=filename;
		this.stock=stock;
		this.listeachat=listeachat;
	}

}
