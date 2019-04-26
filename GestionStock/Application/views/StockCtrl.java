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
    private TableView<ChaineAffichable> listeChaine ;

    @FXML
    private Button btnSimulation;
   
    @FXML
    private Text valeurStock;
    
    @FXML
    private TextArea areaBenefice;

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
    
  //table view liste chaine
    @FXML
    private TableColumn<ChaineAffichable, String> colNomC;
    @FXML
    private TableColumn<ChaineAffichable, String> colCodeC;
    @FXML
    private TableColumn<ChaineAffichable, TextField> colNiveauActivationC;
    
    private ObservableList<ChaineAffichable> chaineAffichableData;
    private ObservableList<Chaine> chaineData;
    private Stock stockData ;
  

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
    	
    	//chargement des element csv
    	this.chargerElementCSV() ;
    	
    	//charger chaine csv
    	this.chargerChaineCSV();
    	
    	//desactive le champ benefice
    	this.disableAreaBenefice();

    }


    /*****************************************PARTIE PRIVÉE*******************************************************************/

    /**
     * Affiche la liste des stocks
     */

    private void afficheStock() {
        // On ajoute le stock observable au tableau
    	stockData = this.application.getStockData() ;
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
    
    private void afficheListeChaine() {
        //on ajoute la liste d'achat au tableau
    	chaineData = this.application.getChaineData() ;
    	chaineAffichableData = this.application.getChaineAffichable() ;
        listeChaine.setItems(chaineAffichableData);

        //on initialise chaque colonne
        colNomC = new TableColumn<>("Nom");
        colNomC.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
        

        colCodeC = new TableColumn<>("Code");
        colCodeC.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());

        colNiveauActivationC = new TableColumn<>("Niveau d'activation");
        colNiveauActivationC.setCellValueFactory( new PropertyValueFactory<>("niveauActivation") );
        
        //on les ajoute au tableau
        listeChaine.getColumns().setAll(colNomC,colCodeC,colNiveauActivationC);
    }
    
    /**
     * Methode appelée pour effectuer une simulation
     * Pour chaque niveau d'activation la chaine appelée produit puis on affiche le stock
     */
    
    @FXML 
    private void simulation() {
    	//recupère le stock et la liste de chaine de production
    	Stock stock = this.stockData ;
    	Stock stockSimulation = stock ;
    	String message = "";
		double benefice = 0 ;
		boolean afficheSimulation = false ;
		System.out.println(this.chaineData);
    	for(ChaineAffichable chaineAffichable :this.chaineAffichableData) {
    		
    		String niveauActivation = chaineAffichable.getNiveauActivation().getText() ;
    		if( ! niveauActivation.isEmpty() &&  ( Integer.parseInt(niveauActivation) >0 ) ) {
    			Chaine chaineChoisie = getChaine(this.chaineData,chaineAffichable.getCode()) ;
    			try {
					stockSimulation = chaineChoisie.produire( Double.parseDouble(niveauActivation),stockSimulation);
					benefice += stockSimulation.getBenefice();
					afficheSimulation = true ;
				} catch (NumberFormatException | ProductionImpossibleException e) {
					message += e.getMessage();
				}
    		}
    	}
    	
    	System.out.println(stockSimulation);
        if(!afficheSimulation){
        	 // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.application.getPrimaryStage());
            alert.setTitle("Données absentes");
            alert.setHeaderText("Niveau d'activation");
            alert.setContentText("Veuillez entrer un niveau d'activation.");

            alert.showAndWait();
        }
        
        if( benefice > 0 ) {
        	this.application.showSimulationDialog(stockSimulation);
        }
        
        
        if( ! message.isEmpty() )
        {
        	 showAreaBenefice( message ) ;
        }
       
    }
    
    
    
    private Chaine getChaine(ObservableList<Chaine> list,String code ) {  
    	Chaine result = null ;
    	for(Chaine chaine : list) {
    		if( chaine.getCode().equals(code) ) {
    			System.out.println(code);
    			System.out.println(chaine.getCode());
    			result = chaine ;
    		}
    	}
    	return result ;
    }

  
    

   /* @FXML
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
*/
    /**
     * fonction appelée au chargement des chaines csv
     */
    private void chargerChaineCSV() {
       /* if (alertChargement()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir un fichier");
            File file = fileChooser.showOpenDialog(this.application.getPrimaryStage());
            if (file == null) {
                showIsEmpty();
            } else {
                this.isElementCharge = true;
                String filePath = file.getAbsolutePath();
                ObservableList chaineData = this.application.getChaineData();
                System.out.println(filePath);
                Parser.chaineParser(filePath, chaineData, this.application.getStockData());
                this.afficheListeChaine();

            }
            
            }*/
            
            String filePath = "/home/claude/Téléchargements/FichiersV1__78__0/chaines.csv";
            ObservableList<Chaine> chaineData = this.application.getChaineData();
            ObservableList<Chaine> newChaine = Parser.chaineParser(filePath, chaineData, this.application.getStockData());   
            this.application.setChaineData(newChaine);
            this.afficheListeChaine();    
    }
    
    /**
     * fonction appelée au chargement des elements csv
     */

    private void chargerElementCSV() {
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        File file = fileChooser.showOpenDialog(this.application.getPrimaryStage());
        if (file == null) {
            showIsEmpty();
        } else {
            this.isElementCharge = true;
            String filePath = file.getAbsolutePath();
            System.out.println(filePath);
            Stock stock = this.application.getStockData();
            Parser.elementParser(filePath, stock);

            this.afficheStock();

            this.afficheListeAchat();
        }*/
         String filePath = "/home/claude/Téléchargements/FichiersV1__78__0/elements.csv";
         Stock stock = this.application.getStockData();
         Stock newStock= Parser.elementParser(filePath, stock);
         this.application.setStockDatta(newStock);

         this.afficheStock();

         this.afficheListeAchat();
         


    }
    
    
    /**
     * Desactive le textArea 
     */
    private void disableAreaBenefice() {
        areaBenefice.setEditable(false);
        areaBenefice.setVisible(false);

        //valeurStock.setVisible(false);
        //valeurListeAchat.setVisible(false);
    }
    
    /**
     * Active  le textArea 
     */
    private void showAreaBenefice( String message ) {
        areaBenefice.setVisible(true) ;
        areaBenefice.setText(message) ;
        
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
