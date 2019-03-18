package models;

import exception.NonExistantException;
import exception.ProductionImpossibleException;
import models.Chaine.Chaine;
import models.Element.Element;
import models.Stock.ListeAchat;
import models.Stock.Stock;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//test element
		
		Element e1 = new Element("e1","e1","kg",0,10);	
		Element e2 = new Element("e2","e2","kg",1,1);
		
		Stock s = new Stock();
		
		s.stocker(e1, 3);
		s.stocker(e2, 3);
//		
//		System.out.println(e1.toString());
//		
//		System.out.println(e2.toString());
//		
//		System.out.println(e1.isMatierePremiere());
//		
//		System.out.println(e1.isProduitFini());
		
		Stock ss = new Stock();
//		
//		s.stocker(e1,2);
//		
//		s.stocker(e2, 3);
//		
//		System.out.println(s.toString());
//		
		
//			
//		ss.stocker(e2, 2);
//		
//		System.out.println(ss.toString());
//		
//		try {
//			ss.destocker(e2, 1);
//		} catch (NonExistantException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(ss.toString());
		
		ListeAchat l = new ListeAchat();
//		
		l.acheter(e1, 2);
		l.acheter(e2, 30);
//		
//		System.out.println(l.toString());
//		System.out.println(l.getValeur());
//		
//		l.retirerListe(e2);
//		
//		System.out.println(l.toString());
		
		Chaine c = new Chaine("chaine","code");
		
		c.addComposant(e2, 1);
		
		c.addSortie(e1, 2);
		
		
				
		try {
			ss=c.produire(3,s);
		} catch (ProductionImpossibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("--------------------------------------------------");
		System.out.println(s.toString());
		System.out.println("--------------------------------------------------");
		System.out.println(c.toString());
		System.out.println(s.getBenefice());
		System.out.println("--------------------------------------------------");

		System.out.println(ss.toString());
		System.out.println(ss.getBenefice());
		System.out.println(s.getBenefice());
		
		
		
		

	}

}
