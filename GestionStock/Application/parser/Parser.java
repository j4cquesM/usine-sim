package parser;

import controllers.FactoryApp;
import javafx.collections.ObservableList;
import models.Element.Element;
import models.Stock.Stock;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.Double;

import models.Chaine.Chaine;

public class Parser {
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


        Element e = new Element(nom, code, unite, achat, vente);
        s.stocker(e, Double.parseDouble((String) token.get("Quantite")));
        //System.out.println(s.getElementByCode("E002"));
    }

    public static Stock elementParser(String filePath, Stock s) {
        CSVReader element = new CSVReader(filePath);
        element.read();
        ArrayList data = element.getData();

        for (int i = 0; i < data.size(); i++) {
            Parser.elementSerializer((LinkedHashMap) data.get(i), s);
        }
        
        return s;
    }

    public static void chaineSerializer(LinkedHashMap token, ObservableList chaineData, Stock s) {
        String code = (String) token.get("Code");
        String nom = (String) token.get("Nom");
        String entree = (String) token.get("Entree (code,qte)");
        String sortie = (String) token.get("Sortie (code,qte)");
        Chaine c1 = new Chaine(nom, code);
        String[] token_entree = entree.split(",");
        String[] token_sortie = sortie.split(",");
       // System.out.println(token_entree[0]+" "+token_entree[1]+" "+token_entree[2]+" "+token_entree[3]);
        for (int n = 0; n < (token_entree.length - 1); n=n+2) {
            String entree_code = token_entree[n].replace("(", "").replace(")", "");
            //System.out.println("Code = " + entree_code);

            String entree_quantite = token_entree[n + 1].replace("(", "").replace(")", "");
            //System.out.println("Quantite = " + entree_quantite);
            Element e = s.getElementByCode(entree_code);
           // System.out.println(e.toString());
            c1.addComposant(e,Double.parseDouble(entree_quantite));

        }


        for (int n = 0; n < (token_sortie.length - 1); n=n+2) {
            String sortie_code = token_sortie[n].replace("(", "").replace(")", "");
            //System.out.println("Code = " + sortie_code);

            String sortie_quantite = token_sortie[n + 1].replace("(", "").replace(")", "");
            //System.out.println("Quantite = " + sortie_quantite);

            Element e = s.getElementByCode(sortie_code);
            // System.out.println(e.toString());
            c1.addSortie(e,Double.parseDouble(sortie_quantite));

        }
        chaineData.add(c1);
    }

        public static ObservableList<Chaine> chaineParser (String filePath, ObservableList<Chaine>  chaineData, Stock s){
            CSVReader chaines = new CSVReader(filePath);
            chaines.read();
            ArrayList data = chaines.getData();
            for (int i = 0; i < data.size(); i++) {
                Parser.chaineSerializer((LinkedHashMap) data.get(i), chaineData, s);
            }
            return chaineData ;
        }
    }