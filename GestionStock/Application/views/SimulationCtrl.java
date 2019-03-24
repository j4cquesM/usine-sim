package views;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import controllers.FactoryApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Chaine.Chaine;
import models.Element.Element;
import models.Element.ElementAffichable;
import models.Stock.ListeAchat;
import models.Stock.Stock;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import serializer.JSONSerializer;
import serializer.JSONTemplate;

public class SimulationCtrl implements Initializable {

    private FactoryApp application;
    private Stock stockData;

    @FXML
    private TableView<ElementAffichable> listeStock;
    @FXML
    private TableView<ElementAffichable> listeAchat;
    @FXML
    private Text valeurStock;
    @FXML
    private Text valeurListeAchat;

    //table view stock
    @FXML
    private TableColumn<ElementAffichable, String> colNomStock;
    @FXML
    private TableColumn<ElementAffichable, Double> colQuantiteStock;
    @FXML
    private TableColumn<ElementAffichable, String> colCodeStock;
    @FXML
    private TableColumn<ElementAffichable, Double> colPrixAchatStock;
    @FXML
    private TableColumn<ElementAffichable, Double> colPrixVenteStock;
    @FXML
    private TableColumn<ElementAffichable, String> colUniteMesureStock;

    //table view liste achat
    @FXML
    private TableColumn<ElementAffichable, String> colNomLA;
    @FXML
    private TableColumn<ElementAffichable, Double> colQuantiteLA;
    @FXML
    private TableColumn<ElementAffichable, String> colCodeLA;
    @FXML
    private TableColumn<ElementAffichable, Double> colPrixAchatLA;
    @FXML
    private TableColumn<ElementAffichable, Double> colPrixVenteLA;
    @FXML
    private TableColumn<ElementAffichable, String> colUniteMesureLA;
    @FXML
    private Button btnExport;

    public SimulationCtrl() {
        this.application = new FactoryApp();
        this.stockData = new Stock();
    }

    public void setFactoryApp(FactoryApp applicationPrincipale) {

        this.application = applicationPrincipale;
    }
    
    /**
     * methode appelée au chargement du fichier fxml
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    public void setStockData(Stock stock) {
        this.stockData = stock;
        this.afficheListeAchat();
        this.afficheStock();
    }


    public Stock getStockData() {
        return stockData;
    }

    /*****************************************PARTIE PRIVÉE*******************************************************************/
    
    /**
     * Affiche la liste des stocks
     */

    private void afficheStock() {
        // On ajoute le stock observable au tableau
        listeStock.setItems(this.stockAffichable());

        //on initialise chaque colonne
        colNomStock = new TableColumn<>("Nom");
        colNomStock.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());

        colCodeStock = new TableColumn<>("Code");
        colCodeStock.setCellValueFactory(cellData -> cellData.getValue().getCodeUniqueProperty());

        colUniteMesureStock = new TableColumn<>("Unité de mesure");
        colUniteMesureStock.setCellValueFactory(cellData -> cellData.getValue().getUniteMesureProperty());

        colPrixAchatStock = new TableColumn<>("Prix achat");
        colPrixAchatStock.setCellValueFactory(cellData -> cellData.getValue().getPrixAchatProperty().asObject());

        colPrixVenteStock = new TableColumn<>("Prix Vente");
        colPrixVenteStock.setCellValueFactory(cellData -> cellData.getValue().getPrixVenteProperty().asObject());

        colQuantiteStock = new TableColumn<>("Quantite");
        colQuantiteStock.setCellValueFactory(cellData -> cellData.getValue().getQuantiteProperty().asObject());

        //on les ajoute au tableau
        listeStock.getColumns().setAll(colNomStock, colCodeStock, colUniteMesureStock, colPrixAchatStock, colPrixVenteStock, colQuantiteStock);

        //on affiche la valeur du stock
        valeurStock.setText("Valeur du stock : " + this.getStockData().getValeur() + " €");

    }
    
    /**
     * Affiche la liste d'achat
     */

    private void afficheListeAchat() {
        //on ajoute la liste d'achat au tableau
        listeAchat.setItems(this.listeAchatAffichable());

        //on initialise chaque colonne
        colNomLA = new TableColumn<>("Nom");
        colNomLA.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());

        colCodeLA = new TableColumn<>("Code");
        colCodeLA.setCellValueFactory(cellData -> cellData.getValue().getCodeUniqueProperty());

        colUniteMesureLA = new TableColumn<>("Unité de mesure");
        colUniteMesureLA.setCellValueFactory(cellData -> cellData.getValue().getUniteMesureProperty());

        colPrixAchatLA = new TableColumn<>("Prix achat");
        colPrixAchatLA.setCellValueFactory(cellData -> cellData.getValue().getPrixAchatProperty().asObject());

        colPrixVenteLA = new TableColumn<>("Prix Vente");
        colPrixVenteLA.setCellValueFactory(cellData -> cellData.getValue().getPrixVenteProperty().asObject());

        colQuantiteLA = new TableColumn<>("Quantite");
        colQuantiteLA.setCellValueFactory(cellData -> cellData.getValue().getQuantiteProperty().asObject());

        //on les ajoute au tableau
        listeAchat.getColumns().setAll(colNomLA, colCodeLA, colUniteMesureLA, colPrixAchatLA, colPrixVenteLA, colQuantiteLA);

        //on affiche la valeur de la liste de la liste d'achat
        valeurListeAchat.setText("Valeur de la liste d'achat : " + this.getStockData().getListeAchat().getValeur() + " €");
    }
    
    
    /**
     * Methode pour 
     */

   

    @FXML
    private void exporter() {
        //pour avoir le stock tu fais this.getStockData()
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showSaveDialog(this.application.getPrimaryStage());
        if (selectedFile != null) {
            String cheminDuFichier = selectedFile.getAbsolutePath();
            if (  ! cheminDuFichier.isEmpty() ){
            	
            	Stock s = this.getStockData();
                Map listeachat = s.getListeAchat().getListeAchat();
                Map stock = s.getStock();

                JSONTemplate template = new JSONTemplate(cheminDuFichier, stock, listeachat);
                JSONSerializer serializer = new JSONSerializer();
                try {
                    serializer.serializeToFile(template);
                } catch (IOException e) {
                    System.out.println("Impossible d'écrire dans le fichier - "+e.getMessage());
                }
            }
            else {
            	 this.showIsEmpty();
            }
        }
        
            

        
    }


    private void showIsEmpty() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(this.application.getPrimaryStage());
        alert.setTitle("Impossible");
        alert.setHeaderText("Aucun fichier");
        alert.setContentText("Vous devez entrer le nom d'un fichiers !");

        alert.showAndWait();
    }
    
    private ObservableList<ElementAffichable> stockAffichable() {

        ObservableList<ElementAffichable> stockAffichable = FXCollections.observableArrayList();

        for (Element key : this.stockData.getStock().keySet()) {

            ElementAffichable nouv = new ElementAffichable(key.getNom(), key.getCodeUnique(), key.getUniteMesure(),
                    key.getPrixAchat(), key.getPrixVente(), this.getStockData().getQuantite(key));

            stockAffichable.add(nouv);
        }
        return stockAffichable;

    }

    private ObservableList<ElementAffichable> listeAchatAffichable() {
        ObservableList<ElementAffichable> achatAffichable = FXCollections.observableArrayList();

        for (Element key : this.stockData.getListeAchat().getListeAchat().keySet()) {

            ElementAffichable nouv = new ElementAffichable(key.getNom(), key.getCodeUnique(), key.getUniteMesure(),
                    key.getPrixAchat(), key.getPrixVente(), this.getStockData().getListeAchat().getQuantite(key));

            achatAffichable.add(nouv);
        }
        return achatAffichable;

    }


}



