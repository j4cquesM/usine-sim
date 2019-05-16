package serializer;

import models.Personnel.Personnel;

import java.util.ArrayList;

public class PersonnelTemplate {
    String nom;
    String prenom;
    String code;
    boolean qualification;
    double total_temps_de_travail;
    double temps_de_travail_effectue;


    public PersonnelTemplate(String nom, String prenom, String code, boolean qualification, double ttt, double tte) {
        this.nom = nom;
        this.prenom = prenom;
        this.code = code;
        this.qualification = qualification;
        this.total_temps_de_travail = ttt;
        this.temps_de_travail_effectue = tte;
    }
    /* Fonction permettant de transformer les types PropertyType de l'objet Personnel en
        type simple (String, double) pour être serialisable */
    public static ArrayList<PersonnelTemplate> toSimpleType(ArrayList<Personnel> lp){
        ArrayList<PersonnelTemplate> l = new ArrayList<PersonnelTemplate>();
        for(Personnel p : lp){
            PersonnelTemplate pt = new PersonnelTemplate(p.getNom(),p.getPrenom(), p.getCode(), p.getQualification(), p.getTotalTempsTravail(), p.getTempsTravaille());
            l.add(pt);
        }

        return l;

    }
}
