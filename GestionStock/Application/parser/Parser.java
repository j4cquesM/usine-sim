package parser;

import controllers.FactoryApp;
import javafx.collections.ObservableList;
import models.Element.Element;
import models.Stock.Stock;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.Double;

import models.Chaine.Chaine;
import models.Personnel.Personnel;

/**
 * Classe permettant de parser les données du fichier (entrée).
 */
public class Parser {
    /**
     * @param token correspond à une ligne du tableau
     * @param s objet stock du modèle
     */
    public static void elementSerializer(LinkedHashMap token, Stock s) {
        String nom = (String) token.get("Nom");
        String code = (String) token.get("Code");
        String unite = (String) token.get("unite");
        double achat;
        double vente;
        if (((String) token.get("achat")).equals("NA")) {
            achat = 0;
        } else {
            achat = Double.parseDouble((String) token.get("achat"));
        }

        if (((String) token.get("vente")).equals("NA")) {
            vente = 0;
        } else {
            vente = Double.parseDouble((String) token.get("vente"));
        }
        double demande = Double.parseDouble((String) token.get("Demande"));
        // ------
        Element e = new Element(nom,code, unite, achat, vente, demande);
        s.stocker(e, Double.parseDouble((String) token.get("Quantite")));
    }

    /**
     * @param filePath chemin du fichier à parser
     * @param s objet stock du modèle (mise à jour du stock à partir des données parsées)
     */
    public static void elementParser(String filePath, Stock s) {
        CSVReader element = new CSVReader(filePath);
        element.read();
        ArrayList data = element.getData();

        for (int i = 0; i < data.size(); i++) {
            Parser.elementSerializer((LinkedHashMap) data.get(i), s);
        }


    }

    /**
     * @param token correspond à une ligne du tableau de données (chaines.csv)
     * @param chaineData liste d'objets chaines du modèle
     * @param s objet stock mis à jour à partir des données parsées
     */
    public static void chaineSerializer(LinkedHashMap token, ObservableList chaineData, Stock s) {
        String code = (String) token.get("Code");
        String nom = (String) token.get("Nom");
        String entree = (String) token.get("Entree (code,qte)");
        String sortie = (String) token.get("Sortie (code,qte)");

        double temps = Double.parseDouble((String) token.get("Temps"));
        double qual = Double.parseDouble((String) token.get("Personnels qualifies"));
        double nonqual = Double.parseDouble((String) token.get("Personnels non qualifies"));
        // --------------
        Chaine c1 = new Chaine(nom,code, temps, qual, nonqual);
        String[] token_entree = entree.split(",");
        String[] token_sortie = sortie.split(",");
        //("TOKENS ENTREE --- \n");

        for (int n = 0; n < (token_entree.length - 1); n=n+2) {
            String entree_code = token_entree[n].replace("(", "").replace(")", "");
            //System.out.println("Code = " + entree_code);

            String entree_quantite = token_entree[n + 1].replace("(", "").replace(")", "");
            //System.out.println("Quantite = " + entree_quantite);
            Element e = s.getElementByCode(entree_code);
            c1.addComposant(e,Double.parseDouble(entree_quantite));

        }

        //System.out.println("TOKENS SORTIE --- \n");
        /*for (int n = 0; n < (token_sortie.length - 1); n=n+2) {
            String sortie_code = token_sortie[n].replace("(", "").replace(")", "");
            System.out.println("Code = " + sortie_code);
            String sortie_quantite = token_sortie[n + 1].replace("(", "").replace(")", "");
            System.out.println("Quantite = " + sortie_quantite);
            Element e = s.getElementByCode(sortie_code);
            //System.out.println(e.toString());
            c1.addSortie(e,Double.parseDouble(sortie_quantite));
        }*/
        chaineData.add(c1);
    }

    /**
     * @param filePath chemin du fichier à parser
     * @param chaineData liste d'objets chaines du modèle
     * @param s objet stock mis à jour à partir des données parsées
     */
    public static void chaineParser (String filePath, ObservableList chaineData, Stock s){
        CSVReader chaines = new CSVReader(filePath);
        chaines.read();
        ArrayList data = chaines.getData();
        for (int i = 0; i < data.size(); i++) {
            Parser.chaineSerializer((LinkedHashMap) data.get(i), chaineData, s);
        }
    }

    /**
     * @param filePath chemin d'acces au fichier
     * surcharge de la méthode chaineParser à des fins de tests.
     */
    public static void chaineParser (String filePath){
        CSVReader chaines = new CSVReader(filePath);
        chaines.read();
        ArrayList data = chaines.getData();
        /*for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }*/
    }

    /**
     * @param token tuples de données d'un fichier personnel.csv
     * @return retourne l'objet personnel crée à partir des données lues
     */
    public static Personnel personnelSerializer(LinkedHashMap token){
        ArrayList<Personnel> listp = new ArrayList<Personnel>();
        String code = (String) token.get("code");
        String prenom = (String) token.get("prenom");
        String nom = (String) token.get("nom");
        double heures = Double.parseDouble((String)token.get("heures"));
        boolean qualification;
        if ( ((String) token.get("qualification")).equals("1") ){
            qualification =  true;
        }
        else {
            qualification =  false;
        }

        Personnel p = new Personnel(code,prenom,nom,qualification,heures);
        return p;


    }

    /**
     * @param filePath nom du fichier à parser
     * @return retourne une liste de personnel correspondant au nombre de lignes du fichier personnel.csv parsé
     */
    public static ArrayList<Personnel> personnelParser (String filePath){
        ArrayList<Personnel> listp = new ArrayList<Personnel>();
        CSVReader personnel = new CSVReader(filePath);
        personnel.read();
        ArrayList data = personnel.getData();
        for (int i = 0; i < data.size(); i++) {
            Personnel p = Parser.personnelSerializer((LinkedHashMap) data.get(i));
            listp.add(p);

        }
        return listp;
    }
}