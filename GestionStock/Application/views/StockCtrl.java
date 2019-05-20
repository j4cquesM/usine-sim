package views;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.*;
import exception.ProductionImpossibleException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import models.Stock.*;
import models.Element.*;
import models.Personnel.Personnel;
import models.Util;
import models.Chaine.*;
import parser.Parser;

/**
 * @author claude
 */

public class StockCtrl implements Initializable {

    private FactoryApp application;
   
    @FXML
    private TableView<ElementAffichable> listeStock;

    @FXML
    private TableView<ElementAffichable> listeAchat;
    
    @FXML
    private TableView<Personnel> listePersonnel;
    
    @FXML
    private TableView<Chaine> listeChaine ;
   
    @FXML
    private ListView<String> listViewSimalation ;
   
    @FXML
    private Button btnSimulation;
    
    @FXML
    private Button btnMemorisation;
    
    @FXML
    private Button btnEnregistrer ;
    
    @FXML
    private TextField nomSimulation ;
    
    @FXML
    private Text areaBenefice ;
    
    @FXML
    private Label labelHistorique ;
   
    @FXML
    private Text valeurStock;
    
    @FXML
    private Text valeurListeAchat;
    
    @FXML
    private Label labelSimulation ;
    
    private String fileChaine ;
    
    private String filePersonnel ;
    
    private String fileElement ;
    
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
    @FXML
    private TableColumn<ElementAffichable, Double> colDemandeStock;

    
  //table view liste chaine
    @FXML
    private TableColumn<Chaine, String> colNomC;
    @FXML
    private TableColumn<Chaine, String> colCodeC;
    @FXML
    private TableColumn<Chaine, TextField> colNiveauActivationC;
    @FXML
    private TableColumn<Chaine, Double> colTempsC ;
    @FXML
    private TableColumn<Chaine, Double> colPersoQualifieC ;
    @FXML
    private TableColumn<Chaine, Double> colPersoNonQualifie ;
   
   // table view liste personne 
    @FXML
    private TableColumn<Personnel, String> colCodeP;
    @FXML
    private TableColumn<Personnel, String> colNomP;
    @FXML
    private TableColumn<Personnel, String> colPrenomP;
    @FXML
    private TableColumn<Personnel, String> colQualificationP ;
    @FXML
    private TableColumn<Personnel, Double> colTempsTravailP  ;
    @FXML
    private TableColumn<Personnel, Double> colTempsTravailRealiseP  ;
    
    private ObservableList<Chaine> chaineData ;
    
    private ObservableList<Personnel> personnelData ;
    
    private Stock stockData ;
    
    private HashMap<String,ArrayList<String>> listeSimulation ;
  

    public StockCtrl() {
        application = new FactoryApp();
        this.listeSimulation = new HashMap<>() ;
        this.fileChaine = "" ;
		this.fileElement = "" ;
		this.filePersonnel = "" ;
        
    }

    public void setFactoryApp(FactoryApp applicationPrincipale) {

        this.application = applicationPrincipale;
    }
    
    

    public void setFileChaine(String fileChaine) {
		this.fileChaine = fileChaine;
		this.chargerChaineCSV();
	}

	public void setFilePersonnel(String filePersonnel) {
		this.filePersonnel = filePersonnel;
		this.chargerPersonnelCSV();
	}

	public void setFileElement(String fileElement) {
		this.fileElement = fileElement;
		this.chargerElementCSV();
	}

	/**
     * methode appelée au chargement du fichier fxml
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    	
    	//chemin d'acces des fichiers
    	this.alertFichier();
    	
    	//desactive le champ benefice
       	this.disableAreas();
       	
       	//desactive les elements relatifs à l'enregistrement
       	this.disableEnregistrement();
       	
    	
    	if(!this.fileElement.isEmpty() && !this.fileChaine.isEmpty() && !this.filePersonnel.isEmpty()) {
    		//affiche la liste des stocks
       	 this.chargerElementCSV();
       	      
          //affiche la liste des chaines
            this.chargerChaineCSV();
       	 
          //affiche la liste des personnels
            this.chargerPersonnelCSV();
       	
       
       	//remplissage des niveaux d'activation
       	this.listViewSimalation.getSelectionModel().selectedItemProperty().addListener(
       			 (observable, oldValue, newValue) ->  remplirNiveauActivation(newValue)
       	);
       	
       	//S'assure que les niveaux d'activation soient bien des entiers numériques
       	for(Chaine chaine :this.chaineData) {
       		this.filtreNiveauActivation(chaine.getNiveauActivation());
       	}
       	
       	nomSimulation.textProperty().addListener(
       			(observable, oldValue, newValue) ->  this.btnEnregistrer.setDisable(newValue.trim().isEmpty())
       	);
    	}
    	
    	//test
    	//this.test();
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
        
        colDemandeStock = new TableColumn<>("Demande");
        colDemandeStock.setCellValueFactory(cellData -> cellData.getValue().getDemandeProperty().asObject());

        //on les ajoute au tableau
        listeStock.getColumns().setAll(colNomStock, colCodeStock, colUniteMesureStock, colPrixAchatStock, colPrixVenteStock, colQuantiteStock,colDemandeStock);

        //on affiche la valeur du stock
        valeurStock.setText("Valeur du stock : " + this.application.getStockData().getValeur() + " €");

    }
        
    /**
     * Affiche les chaines de production
     */
    private void afficheListeChaine() {
        //on ajoute la liste d'achat au tableau
    	chaineData = this.application.getChaineData() ;
        listeChaine.setItems(chaineData);

        //on initialise chaque colonne
        colNomC = new TableColumn<>("Nom");
        colNomC.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
        

        colCodeC = new TableColumn<>("Code");
        colCodeC.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());

        colNiveauActivationC = new TableColumn<>("Niveau d'activation");
        colNiveauActivationC.setCellValueFactory( new PropertyValueFactory<>("niveauActivation") );
        
        colTempsC = new TableColumn<>("Temps");
        colTempsC.setCellValueFactory(cellData -> cellData.getValue().getTempsProperty().asObject());
        
        colPersoQualifieC = new TableColumn<>("Personnel qualifié");
        colPersoQualifieC.setCellValueFactory(cellData -> cellData.getValue().getPersonnelQualifieProperty().asObject());
        
        colPersoNonQualifie = new TableColumn<>("Personnel non qualifié");
        colPersoNonQualifie.setCellValueFactory(cellData -> cellData.getValue().getPersonnelNonQualifieProperty().asObject()) ;
        
        //on les ajoute au tableau
        listeChaine.getColumns().setAll(colNomC,colCodeC,colNiveauActivationC,colTempsC,colPersoQualifieC,colPersoNonQualifie);
        
    }
    
    /**
     * Affiche la liste des personnel
     */
    
    private void afficheListePersonnel() {
        //on ajoute la liste d'achat au tableau
    	personnelData = this.application.getPersonnelData()  ;
        listePersonnel.setItems(personnelData);

        //on initialise chaque colonne
        colCodeP = new TableColumn<>("Code");
        colCodeP.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
        
        colNomP = new TableColumn<>("Nom");
        colNomP.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
        

        colPrenomP = new TableColumn<>("Prenom");
        colPrenomP.setCellValueFactory(cellData -> cellData.getValue().getPrenomProperty());

        colQualificationP = new TableColumn<>("Qualification");
        colQualificationP.setCellValueFactory( cellData -> cellData.getValue().getQualificationPropertyStr());
        
        colTempsTravailP = new TableColumn<>("Heures prévues");
        colTempsTravailP.setCellValueFactory( cellData -> cellData.getValue().getTotalTempsTravailProperty().asObject() );
        
        colTempsTravailRealiseP = new TableColumn<>("Heures réalisés");
        colTempsTravailRealiseP.setCellValueFactory(cellData -> cellData.getValue().getTempsTravailleProperty().asObject() );
        
        
        
        //on les ajoute au tableau
        listePersonnel.getColumns().setAll(colCodeP,colPrenomP,colNomP,colQualificationP,colTempsTravailP,colTempsTravailRealiseP);
    }
    
    /**
     * Methode appelée pour effectuer une simulation
     * Pour chaque niveau d'activation la chaine appelée produit puis on affiche le stock
     */
    
    @FXML 
    private void simulation() {
    	Stock stock = this.stockData ;
    	Stock stockSimulation = new Stock(stock) ;
    	ObservableList<Personnel> personnelSimulation = this.personnelData ;
    	String messageErreur = "";
		double benefice = 0 ;
		boolean afficheSimulation = false ;
    	for(Chaine chaineAffichable :this.chaineData) {
    		
    		String niveauActivation = chaineAffichable.getNiveauActivation().getText() ;
    		if( ! niveauActivation.isEmpty() &&  ( Double.parseDouble(niveauActivation) > 0 ) ) {
    			afficheSimulation = true ;
    			try {
					stockSimulation = chaineAffichable.produire( Double.parseDouble(niveauActivation),stockSimulation);
					personnelSimulation = chaineAffichable.gestionPersonnel(personnelSimulation,Double.parseDouble(niveauActivation)) ;
					benefice += stockSimulation.getBenefice();
				} catch (NumberFormatException | ProductionImpossibleException e) {
					messageErreur += e.getMessage();
				}
    		}
    	}
    	
        if(!afficheSimulation){
        	 // Nothing selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(this.application.getPrimaryStage());
            alert.setTitle("Données absentes");
            alert.setHeaderText("Niveau d'activation");
            alert.setContentText("Veuillez entrer un niveau d'activation.");

            alert.showAndWait();
        }
        if( benefice != 0 ) {
        	this.application.showSimulationDialog(stockSimulation,personnelSimulation );
        	String message = "Vous avez " + benefice +" de bénéfices. " ;
        	showAreaBenefice(message) ;
        }
        
        if( ! messageErreur.isEmpty() )
        {
        	 showAreaErreur( messageErreur ) ;
        } 
    }
    
    
    /**
     * Permet l'affichage des elements relatif à l'enregistrement
     */
    @FXML
    private void getNomsimulation()
    {
    	this.enableEnregistrement();
    }
    
    /**
     * Enregistrer les niveaux d'activation en fonction du nom rentré
     */
    @FXML
    private void enregistrerSimulation()
    {
    	String nom = nomSimulation.getText() ;
    	this.nomSimulation.setText("");
    	ArrayList<String> listeChaine = new ArrayList<>() ;
    	if( ! nom.isEmpty() )
    	{
    		if( ! this.listeSimulation.containsKey(nom)) {
    			for(Chaine chaine :this.chaineData) {
            		listeChaine.add(chaine.getNiveauActivation().getText()) ;
            	}
    			this.listeSimulation.put(nom, listeChaine) ;
    		}else {
    			this.showSimulationExist() ;
    		}   
        	this.remplirSimulation();
        	this.disableEnregistrement();
    	}
    	else
    	{
    		this.showSimulationEmpty();
    	}
		
    }
    
    /**
     * 
     */
    private void remplirSimulation(){
    	 ObservableList<String> nom = FXCollections.observableArrayList(this.listeSimulation.keySet());
    	 listViewSimalation.setItems(nom);
    	 this.enableHistorique() ;
    	 this.remiseAZero() ;
    }
    
    
    /**
     * Mets à jour les niveaux d'activation des chaines 
     * @param nom de la simulation
     */
    private void remplirNiveauActivation(String nom) {
    	if(this.listeSimulation.containsKey(nom))
    	{
    		ArrayList<String> newValue = this.listeSimulation.get(nom) ;
    		
    		int i = 0 ;
    		while(i < this.chaineData.size())
    		{
    			this.chaineData.get(i).getNiveauActivation().setText(newValue.get(i));
    			
    			i++ ;
    		}
    	}
    	
    }
    
    /**
     * Desactive le textArea 
     */
    private void disableAreas() {
    	this.disableHistorique() ;
    	 areaBenefice.setVisible(false) ;
        //valeurStock.setVisible(false);
        //valeurListeAchat.setVisible(false);
    }
    
    private void disableHistorique() {
    	this.labelHistorique.setVisible(false);
    	this.listViewSimalation.setVisible(false);
    }
    
    private void enableHistorique() {
    	this.labelHistorique.setVisible(true);
    	this.listViewSimalation.setVisible(true);
    }
    
    /**
     * Active le text area benefice
     * @param message messaqge à afficher
     */
    private void showAreaBenefice( String message ) {
        areaBenefice.setVisible(true) ;
        areaBenefice.setText(message) ;
        btnMemorisation.setVisible(true) ;
    }
    
    /**
     * Affiche l'alert d'erreur 
     * @param message message à afficher
     */
    private void showAreaErreur( String message ) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Production impossible");
    	alert.setHeaderText("Erreur dans la production de certaines chaines");
    	
    	Label label = new Label("Details des erreurs :");
    	
    	TextArea textArea = new TextArea(message);
    	textArea.setEditable(false);
    	textArea.setWrapText(true);

    	textArea.setMaxWidth(Double.MAX_VALUE);
    	textArea.setMaxHeight(Double.MAX_VALUE);
    	GridPane.setVgrow(textArea, Priority.ALWAYS);
    	GridPane.setHgrow(textArea, Priority.ALWAYS);

    	GridPane expContent = new GridPane();
    	expContent.setMaxWidth(Double.MAX_VALUE);
    	expContent.add(label, 0, 0);
    	expContent.add(textArea, 0, 1);

    	// Set expandable Exception into the dialog pane.
    	alert.getDialogPane().setExpandableContent(expContent);

    	alert.showAndWait();
    }
    
    /**
     * Regex sur les text field des niveau d'activation
     * @param fieldNiveauActivation le champ à verifier
     */
    private void filtreNiveauActivation(TextField fieldNiveauActivation)
    {
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
    
    
    /**
     * Desactive les elements en rapport avec l'enregistrement
     */
    private void disableEnregistrement()
    {
    	btnMemorisation.setVisible(false) ;
    	btnEnregistrer.setVisible(false);
    	nomSimulation.setVisible(false);
        btnMemorisation.setVisible(false);  
        labelSimulation.setVisible(false);
    }
    
    /**
     * Active les elements en rapport avec l'enregistrement
     */
    private void enableEnregistrement()
    {
    	btnEnregistrer.setVisible(true);
    	nomSimulation.setVisible(true);
        btnMemorisation.setVisible(true);  
        labelSimulation.setVisible(true);
    } 
    /**
     * alert verification simulation
     */
    private void showSimulationEmpty() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(this.application.getPrimaryStage());
        alert.setTitle("Impossible");
        alert.setHeaderText("Aucun nom");
        alert.setContentText("Vous devez entrer un nom !");

        alert.showAndWait();
    }
    
    /**
     * alert non duplication simulation
     */
    private void showSimulationExist() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(this.application.getPrimaryStage());
        alert.setTitle("Impossible");
        alert.setHeaderText("Duplication");
        alert.setContentText("Une simulation porte déjà ce nom !");

        alert.showAndWait();
    }
    
    /**
     * alert chargement des fichiers
     */
    private void alertFichier() {
    	Dialog<ArrayList<String>> dialog = new Dialog<>();
    	dialog.initOwner(this.application.getPrimaryStage());
    	dialog.setTitle("Chargement des fichiers");
    	dialog.setHeaderText("Chemin vers les différents fichiers");
    	
    	// button type
    	ButtonType loginButtonType = new ButtonType("Charger", ButtonData.OK_DONE);
    	ButtonType annulerButtonType = new ButtonType("Annuler" , ButtonData.CANCEL_CLOSE );
    	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType,annulerButtonType);
    	
    	// les champs input
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));

    	TextField elements = new TextField();
    	elements.setPromptText("/home/xxxx/documents/");
    	//elements.setText("/home/claude/Téléchargements/FichiersV2__78__0/elements.csv");
    	
    	TextField chaines = new TextField();
    	chaines.setPromptText("/home/xxxx/documents/");
    	//chaines.setText("/home/claude/Téléchargements/FichiersV2__78__0/chaines.csv");
    	
    	TextField personnel = new TextField();
    	personnel.setPromptText("/home/xxxx/documents/");
    	//personnel.setText("/home/claude/Téléchargements/FichiersV2__78__0/personnel.csv");
    	
    	// ajout au grid pane
    	grid.add(new Label("Fichiers Elements:"), 0, 0);
    	grid.add(elements, 1, 0);
    	
    	grid.add(new Label("Fichiers Chaines:"), 0, 1);
    	grid.add(chaines, 1, 1);
    	
    	grid.add(new Label("Fichiers Personnel:"), 0, 2);
    	grid.add(personnel, 1, 2);
    	
    	//descativation du bouton 
    	Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    	//loginButton.setDisable(true);
    	
    	personnel.textProperty().addListener((observable, oldValue, newValue) -> {
    	    loginButton.setDisable(newValue.trim().isEmpty());
    	});
    	
    	//ajout de la grid pane dans le dialog
    	dialog.getDialogPane().setContent(grid);
    	
    	//focus sur element
    	Platform.runLater(() -> elements.requestFocus());
    	
    	// convertit le resultat en array list quand on a cliqué
    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == loginButtonType) {
    	    	ArrayList<String> result = new ArrayList<>() ;
    	    	result.add(elements.getText()) ;
    	    	result.add(chaines.getText()) ;
    	    	result.add(personnel.getText()) ;
    	        return result;
    	    }
    	    	 return null;
    	   
    	});

    
    	Optional<ArrayList<String>> result = dialog.showAndWait();
    	
    	result.ifPresent(nomFichiers -> this.cheminAcces(nomFichiers));
    	
    }
    
 /**
  * chargement des chaines csv
  */
 private void chargerChaineCSV() {
         ObservableList<Chaine> chaineData = this.application.getChaineData();
         Parser.chaineParser(this.fileChaine, chaineData, this.application.getStockData());
         this.afficheListeChaine();    
 }
 
 /**
  * Remise à 0 des champs d'activation
  */
 
 private void remiseAZero(){
	 for(Chaine chaine :this.chaineData) {
		 chaine.getNiveauActivation().setText(""); 
 	}
	  btnMemorisation.setVisible(false);
 }
 /**
  *  chargement des elements csv
  */

 private void chargerElementCSV() {
      Stock stock = this.application.getStockData();
      Parser.elementParser(this.fileElement, stock);
      this.afficheStock();

 }
 
 /**
  * chargement des personeels csv
  */
 private void chargerPersonnelCSV() {
 	
 	//generer la arrayList de personnel
 	//set les personnels  dans le factory app
	 this.application.getPersonnelData().addAll(Parser.personnelParser(this.filePersonnel)) ;
 	this.afficheListePersonnel() ;
 }
 
 private void cheminAcces(ArrayList<String> fichiers)
 {
	 this.fileElement = fichiers.get(0) ;
	 this.fileChaine = fichiers.get(1) ;
	 this.filePersonnel = fichiers.get(2) ;
 }
 
 private void test() {
	 ArrayList <String> l1 = new ArrayList<>() ;
	 ArrayList <String> l2 = new ArrayList<>() ;
	 ArrayList <String> l3 = new ArrayList<>() ;
	
	 for(Chaine chaine :this.chaineData) {
		 String ad1 = "" ;
		 String ad2= "" ;
		 String ad3 = "" ;
		 if(chaine.getCode().equals("C001"))
		 {
			 ad1 = "10" ;
			 ad3 = "5" ;
		 }
		
		 
		 if(chaine.getCode().equals("C006"))
		 {
			 ad2 = "80" ;
			 ad3 = "5" ;
		 }
		 
		 if(chaine.getCode().equals("C004"))
		 {
			 ad2 = "80" ;
			 ad3 = "5" ;
		 }
		 
		 if(chaine.getCode().equals("C007"))
		 {
			 ad1 = "5" ;
		 }
		 
		 if(chaine.getCode().equals("C005"))
		 {
			 ad1 = "10" ;
			 ad3 = "5" ;
		 }
		 l1.add(ad1) ;
		 l2.add(ad2) ;
		 l3.add(ad3);
 	}
	this.listeSimulation.put("erreur produit fini", l1) ;
	this.listeSimulation.put("erreur personnel", l2) ;
	this.listeSimulation.put("production normale", l3) ;
	
 }
    
}
