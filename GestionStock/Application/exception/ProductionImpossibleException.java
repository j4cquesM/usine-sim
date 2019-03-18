package exception;

import models.*;
import models.Element.Element;

/**
 * 
 * @author claude
 *
 */

public class ProductionImpossibleException extends Exception{
	
	
	
	public ProductionImpossibleException(Element e)
	{
		super("Impossible de lancer la production car "+e.getNom()+" a un stock négatif" );
		
	}
	

}
