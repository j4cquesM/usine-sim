package models.Stock;

import java.util.*;

import exception.NonExistantException;
import exception.ProductionImpossibleException;
import models.Util;
import models.Element.Element;

/**
 * @author claude
 */

public class Stock {


    private Map<Element, Double> stock;

    private ListeAchat listeAchat;

    public Stock() {
        this.stock = new HashMap<Element, Double>();
        this.listeAchat = new ListeAchat();
    }

    public Stock(Stock s) {
        this();
        for (Element key : s.stock.keySet()) {

            Element nouv = new Element(key);

            double quantite = (s.stock.get(key)).doubleValue();

            this.stocker(nouv, quantite);
        }

        this.listeAchat = new ListeAchat(s.listeAchat);

    }


    /**
     * Rajoute ou met à jour un element dans le stock
     *
     * @param e        l'element du stock
     * @param quantite la quantite de l'element
     */

    public void stocker(Element e, double quantite) {

        Util.addInMap(this.stock, e, quantite);
    }

    /**
     * Fait sortir un element du stock
     *
     * @param e        l'element à destocker
     * @param quantite la quantite de l'element
     * @throws NonExistantException element non existant en stock
     */

    public void destocker(Element e, double quantite) throws NonExistantException {

        Util.removeInMap(this.stock, e, quantite);

    }

    public String toString() {
        String message = "";

        for (Element key : this.stock.keySet()) {
            message += key.toString() + " Quantité " + this.stock.get(key) + "\n";
        }

        return message;
    }


    /**
     * benefice du stock
     *
     * @return valeur d ela production
     */
    public double getBenefice() {
        double benefice = this.getValeurPrixVente() - this.getListeAchat().getValeur();
        return benefice;
    }

    /**
     * @return la valeur du stock avec le prixAchat
     */
    public double getValeurPrixAchat() {
        double prix = 0;
        for (Element key : stock.keySet()) {

            // on prend le prix de l'element
            double prixElement = key.getPrixAchat();

            // on prend le prix de la quantite en stock
            double prixElementCumul = prixElement * (stock.get(key)).doubleValue();

            prix += prixElementCumul;
        }
        return prix;
    }

    /**
     * @return la valeur du stock avec la prixVente
     */
    public double getValeurPrixVente() {
        double prix = 0;
        for (Element key : stock.keySet()) {

            // on prend le prix de l'element
            double prixElement = key.getPrixVente();

            // on prend le prix de la quantite en stock
            double prixElementCumul = prixElement * (stock.get(key)).doubleValue();

            prix += prixElementCumul;
        }
        return prix;
    }

    public double getValeur() {
        return this.getValeurPrixVente() ;
    }

    public double getQuantiteMinAchat(Element e) {
        double quantiteActuelle = this.getQuantite(e);
        double i = 0;
        while (quantiteActuelle <= 0) {
            i++;
            quantiteActuelle++;
        }
        return i;
    }

    public Map<Element, Double> getStock() {
        return stock;
    }


    public ListeAchat getListeAchat() {
        return listeAchat;
    }

    /**
     * @param e element en stock
     * @return la quanité d'une element en stock
     */

    public double getQuantite(Element e) {

        double quantite = (this.stock.get(e)).doubleValue();

        return quantite;

    }

    public void acheter(Element e, Double quantite) {
        this.getListeAchat().acheter(e, quantite);
    }
    
    /**
     * 
     * @param code  code d'un element
     * @return un element en stock
     */

    public Element getElementByCode(String code) {


        for (Element key : this.stock.keySet()) {

            if (key.getCodeUnique().equals(code)) {
                return key;
            }
        }
        return new Element("Na", "Na", "Na", 0, 0);
    }


}
