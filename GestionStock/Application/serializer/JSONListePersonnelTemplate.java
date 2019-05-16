package serializer;

import java.util.*;
import serializer.PersonnelTemplate;

import models.Personnel.*;

public class JSONListePersonnelTemplate {
    public String filename;
    private ArrayList<PersonnelTemplate> liste_personnel;

    public JSONListePersonnelTemplate(String filename, ArrayList<Personnel> p){
        this.filename=filename;
        this.liste_personnel=new ArrayList<PersonnelTemplate>();
        this.liste_personnel=PersonnelTemplate.toSimpleType(p);


    }


}
