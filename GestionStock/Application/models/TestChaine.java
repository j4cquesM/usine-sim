package models;

import java.util.ArrayList;

import exception.NonExistantException;
import exception.ProductionImpossibleException;
import models.Chaine.Chaine;
import models.Element.Element;
import models.Personnel.Personnel;
import models.Stock.ListeAchat;
import models.Stock.Stock;

public class TestChaine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//test element
		
		Personnel p1 = new Personnel("001","Jane","Doe",false,12) ;
		Personnel p2 = new Personnel("002","Jana","Doa",true,15) ;
		Personnel p3 = new Personnel("003","Jani","Doi",true,18) ;
		Personnel p4 = new Personnel("004","Jano","Doo",false,10) ;
		
		ArrayList<Personnel> personnelData = new ArrayList<>() ;
		
		personnelData.add(p1) ;
		personnelData.add(p2) ;
		personnelData.add(p3) ;
		personnelData.add(p4) ;
		
		Element e1 = new Element("e1","e1","kg",0,10,5);	
		Element e2 = new Element("e2","e2","kg",1,1,5);
		
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
		
		Chaine c = new Chaine("chaine","code",1.0,1.0,0.0);
		Chaine c1 = new Chaine("chaine2","code2",1.0,2.0,1.0);
		
		
		c.addComposant(e2, 1);
		
		c.addSortie(e1, 2);
		
		/*for(Personnel p:personnelData) {
			System.out.println(p.toString());
		}*/
		
		ArrayList<Personnel> pp = new ArrayList<>() ;
		ArrayList<Chaine> a = new ArrayList<>() ;
		a.add(c) ;
		a.add(c1);
		
		try {
			ss = c.produire(1, s) ;
			System.out.println(ss.toString());
			
			for(Chaine ca:a)
			{
				pp = ca.gestionPersonnel(personnelData);
			}
			
		}
		catch(Exception e )
		{
			System.out.println(e.getMessage());
		}
		
			
	/*	System.out.println("--------------------------------------------------");
		System.out.println(s.toString());
		System.out.println("--------------------------------------------------");
		System.out.println(c.toString());
		System.out.println(s.getBenefice());
		System.out.println("--------------------------------------------------");

		System.out.println(ss.toString());
		System.out.println(ss.getBenefice());
		System.out.println(s.getBenefice());*/
		
		/*for(Personnel p:pp) {
			System.out.println(p.toString());
		}*/
		System.out.println(pp);
		
		
		
		

	}

}
