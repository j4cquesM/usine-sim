package models;
import models.Personnel.*;
public class TestPersonnel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Personnel p = new Personnel("001", "Jane", "Doe", false, 5) ;
		System.out.println(p);
		
		p.addTempsTravail("c1", 2,false);
		
		if( p.checkTempsTravail(1) )
		{
			p.addTempsTravail("c2", 1,false);
		}
		
		System.out.println(p);
		
	}

}
