package exception;
import models.*;
import models.Element.Element;
/**
 * 
 * @author claude
 *
 */
public class NonExistantException extends Exception {

	public NonExistantException(Element e) {
		super("L'element " +e.getNom()+" n'existe pas en stock");
	}
}
