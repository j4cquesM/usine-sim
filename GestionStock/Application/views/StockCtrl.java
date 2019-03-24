package views;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import controllers.*;
import exception.ProductionImpossibleException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.Stock.*;
import models.Element.*;
import models.Chaine.*;
import parser.Parser;

/**
 * @author claude
 */

public class StockCtrl implements Initializable {

    private FactoryApp application;
    private boolean isElementCharge = false;


    @FXML
    private TableView<ElementAffichable> listeStock;

    @FXML
    private TableView<ElementAffichable> listeAchat;

    @FXML
    private ComboBox<Chaine> listeChaine;

    @FXML
    private Button btnSimuler;

    @FXML
    private Button btnChaine;

    @FXML
    private Button btnElement;
    @FXML
    private TextField fieldNiveauActivation;

    @FXML
    private TextArea areaBenefice;

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


    public StockCtrl() {
        application = new FactoryApp();
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


        this.afficheNiveauActivation();

        this.disableAreaBenefice();

    }


    /*****************************************PARTIE PRIVÉE*******************************************************************/

    /**
     * Affiche la liste des stocks
     */

    private void afficheStock() {
        // On ajoute le stock observable au tableau
        listeStock.setItems(this.application.getStockAffichable());

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
        valeurStock.setText("Valeur du stock : " + this.application.getStockData().getValeur() + " €");

    }
    

    /**
     * Affiche la liste d'achat
     */

    private void afficheListeAchat() {
        //on ajoute la liste d'achat au tableau
        listeAchat.setItems(this.application.getListeAchatAffichable());

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
        valeurListeAchat.setText("Valeur de la liste d'achat : " + this.application.getStockData().getListeAchat().getValeur() + " €");
    }
    

    /**
     * Affiche la liste des chaines
     */


    private void afficheListeChaine() {
        listeChaine.setPromptText("(Sélectionnez une chaine");
        listeChaine.setItems(this.application.getChaineData());
    }
    

    /**
     * Affiche le niveau d'activation
     */

    private void afficheNiveauActivation() {

        fieldNiveauActivation.setPromptText("niveau d'activation ..");

        //force le champ à etre numerique

        fieldNiveauActivation.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldNiveauActivation.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void disableAreaBenefice() {
        areaBenefice.setEditable(false);
        areaBenefice.setVisible(false);

        //valeurStock.setVisible(false);
        //valeurListeAchat.setVisible(false);
    }

    /**
     * Methode appelée pour effectuer une simulation
     * @throws ProductionImpossibleException production impossible
     */

    @FXML
    private void simulation() throws ProductionImpossibleException {

        if (this.dataNotNull()) {

            double niveauActivation = Double.parseDouble(fieldNiveauActivation.getText());

            Chaine chaine = listeChaine.getValue();
            String message = "";
            try {
                Stock stockSimulation = chaine.produire(niveauActivation, this.application.getStockData());

                double benefice = stockSimulation.getBenefice();

                message += "Vous avez " + benefice + " € de benefice !";

                this.application.showSimulationDialog(stockSimulation);


            } catch (ProductionImpossibleException e) {
                message += e.toString();
            }

            //on affiche les benefices
            areaBenefice.setVisible(true);
            areaBenefice.setText(message);

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.application.getPrimaryStage());
            alert.setTitle("Données absentes");
            alert.setHeaderText("Pas de chaine et/ou niveau d'activation");
            alert.setContentText("Veuillez selectionner une chaine et/ou entrer un niveau d'activation.");

            alert.showAndWait();
        }

    }

    private boolean dataNotNull() {
        boolean vide = false;
        if ((listeChaine.getValue() != null) && (fieldNiveauActivation.getText() != "")) {
            vide = true;

        }

        return vide;
    }

    /**
     * fonction appelée au chargement des chaines csv
     */
    @FXML
    private void chargerChaineCSV() {
        if (alertChargement()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir un fichier");
            File file = fileChooser.showOpenDialog(this.application.getPrimaryStage());
            if (file == null) {
                showIsEmpty();
            } else {
                this.isElementCharge = true;
                String filePath = file.getAbsolutePath();
                ObservableList chaineData = this.application.getChaineData();
                Parser.chaineParser(filePath, chaineData, this.application.getStockData());
                this.afficheListeChaine();

            }


        }
    }
    
    /**
     * fonction appelée au chargement des elements csv
     */

    @FXML
    private void chargerElementCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        File file = fileChooser.showOpenDialog(this.application.getPrimaryStage());
        if (file == null) {
            showIsEmpty();
        } else {
            this.isElementCharge = true;
            String filePath = file.getAbsolutePath();
            Stock stock = this.application.getStockData();
            Parser.elementParser(filePath, stock);

            this.afficheStock();

            this.afficheListeAchat();
        }


    }
    
    /**
     * fenetre de dialogue
     * @return l'autorisation de charger les chaines
     */

    private boolean alertChargement() {
        if (!this.isElementCharge) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.application.getPrimaryStage());
            alert.setTitle("Impossible");
            alert.setHeaderText("Vous devez charger les elements");
            alert.setContentText("Impossible de charger les chaines avant les elements !");

            alert.showAndWait();
            return false;
        }
        return true;

    }
    

    /**
     * fenetre de dialogue
     */

    private void showIsEmpty() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(this.application.getPrimaryStage());
        alert.setTitle("Impossible");
        alert.setHeaderText("Aucun fichier");
        alert.setContentText("Vous devez choisir un fichier !");

        alert.showAndWait();
    }


}
