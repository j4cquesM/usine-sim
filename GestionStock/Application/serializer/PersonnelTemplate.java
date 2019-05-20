package serializer;

import models.Personnel.Personnel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Template permettant de transformer les objets Java du modèle en objet serialisable pour le module externe Gson.
 */
public class PersonnelTemplate {
    String nom;
    String prenom;
    String code;
    boolean qualification;
    double total_temps_de_travail;
    double temps_de_travail_effectue;
    HashMap<String,Double> repartitionQualifie;
    HashMap<String,Double> repartitionNonQualifie;


    /**
     * @param nom nom du personnel
     * @param prenom prénom du personnel
     * @param code code du personnel
     * @param qualification vrai si le personnel est qualifié, faux sinon
     * @param ttt temps de travail total du salarié en une semaine
     * @param tte temps de travail effectué
     * @param repartitionQual    Répartion du travail de travail qualifié par chaine
     * @param repartitionNonQual Repartition du temps de travail non qualifié par chaine
     */
    public PersonnelTemplate(String nom, String prenom, String code, boolean qualification, double ttt, double tte, HashMap<String,Double> repartitionQual, HashMap<String,Double> repartitionNonQual) {
        this.nom = nom;
        this.prenom = prenom;
        this.code = code;
        this.qualification = qualification;
        this.total_temps_de_travail = ttt;
        this.temps_de_travail_effectue = tte;
        this.repartitionQualifie = repartitionQual;
        this.repartitionNonQualifie = repartitionNonQual;

    }

    /**
     * Fonction permettant de transformer les types PropertyType de l'objet Personnel en
     * type simple (String, double) pour être serialisable
     * @param lp liste d'objets Personnel
     * @return liste d'objets PersonnelTemplate (adaptés à la serialisation en json
     */
    public static ArrayList<PersonnelTemplate> toSimpleType(ArrayList<Personnel> lp){
        ArrayList<PersonnelTemplate> l = new ArrayList<PersonnelTemplate>();
        for(Personnel p : lp){
            PersonnelTemplate pt = new PersonnelTemplate(p.getNom(),p.getPrenom(), p.getCode(), p.getQualification(), p.getTotalTempsTravail(), p.getTempsTravaille(), p.getRepartitionQualifiee(), p.getRepartitionNonQualifiee());
            l.add(pt);
        }

        return l;

    }
}