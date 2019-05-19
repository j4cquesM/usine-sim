package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import parser.CSVReader;
import parser.Parser ;




import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Chaine.Chaine;
import models.Element.*;
import models.Personnel.Personnel;
import models.Stock.ListeAchat;
import models.Stock.Stock;
import views.FichiersCtrl;
import views.SimulationCtrl;
import views.StockCtrl;

public class FactoryApp extends Application {
	
	 private Stage primaryStage;
	 private Stock stockData;
	 private ObservableList<Chaine> chaineData;
	 private ObservableList<Personnel> personnelData ;
	 
	
	public FactoryApp() {
		super();
		this.stockData = new Stock();
		this.chaineData = FXCollections.observableArrayList();
		this.personnelData = FXCollections.observableArrayList(); 
		
	 }

	public static void main(String[] args) {
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UsineApp");

        //Affichage de l'ecran 
        showIndex() ;
	}
	
	/**
	 * Affiche l'ecran permettant de charger les fichiers
	 */
	/*public void showFichier() {
        try {
        	//chargement du fichier fxml
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FactoryApp.class.getResource("/views/fichiersDialog.fxml"));
            loader.setClassLoader(this.getClass().getClassLoader());
			BorderPane  indexView = (BorderPane) loader.load();
			
			 //affichage de l'index
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("Chargement des fichiers");
	        Scene scene = new Scene(indexView);
	        dialogStage.setScene(scene);
	        dialogStage.show();
	        
	        //attribution du controller 
	        FichiersCtrl fichiersCtrl = loader.getController();
	        fichiersCtrl.setFactoryApp(this);
	        fichiersCtrl.setDialogStage(this.primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Affiche l'ecran permettant d'effectuer une simulation
	 */
	
	public void showIndex() {
		 
		try {
					
			//chargement du fichier fxml
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(FactoryApp.class.getResource("/views/index.fxml"));
	        loader.setClassLoader(this.getClass().getClassLoader());
	        AnchorPane  indexView = (AnchorPane) loader.load();
	       
	        
	        //affichage de l'index 
	     // cree la fenetre de dialogue
	        
	        Scene scene = new Scene(indexView);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        //attribution du controller 
	        StockCtrl stockCtrl = loader.getController();
	        stockCtrl.setFactoryApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        

	}
	
	
	/**
	 * Affiche l'ecran permettant de voir l'etat des stocks apres la simulation
	 * @param stock
	 * @param listePersonnel
	 */
	
	public void showSimulationDialog(Stock stock, ObservableList<Personnel> listePersonnel ) {
	    try {
	        // charge le fichier fxml
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(FactoryApp.class.getResource("/views/simulationDialog.fxml"));
	        loader.setClassLoader(this.getClass().getClassLoader());
	        AnchorPane page = (AnchorPane) loader.load();

	        // cree la fenetre de dialogue
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Stock après simulation");
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // On place le stock et la liste d'achat dans le controller
	        SimulationCtrl controller = loader.getController();
	        controller.setFactoryApp(this);
	        controller.setStockData(stock);
	        controller.setPersonnelData(listePersonnel);

	        // Show the dialog and wait until the user closes it
	        
	        dialogStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Met le stock dans une classe qui permet l'affichage
	 * @return  la liste observable de stock 
	 */
	public ObservableList<ElementAffichable> getStockAffichable() {
		
		ObservableList<ElementAffichable> stockAffichable = FXCollections.observableArrayList();
		
		for(Element key : this.getStockData().getStock().keySet()) {
			
			ElementAffichable nouv = new ElementAffichable(key.getNom(),key.getCodeUnique(),key.getUniteMesure(),
					key.getPrixAchat(),key.getPrixVente(),key.getDemande(),this.getStockData().getQuantite(key));
			
			stockAffichable.add(nouv);
		}
		return stockAffichable;
	}
	
	public ObservableList<ElementAffichable> getListeAchatAffichable(){
		ObservableList<ElementAffichable> achatAffichable = FXCollections.observableArrayList();
		
		for(Element key : this.stockData.getListeAchat().getListeAchat().keySet()) {
			
			ElementAffichable nouv = new ElementAffichable(key.getNom(),key.getCodeUnique(),key.getUniteMesure(),
					key.getPrixAchat(),key.getPrixVente(),key.getDemande(),this.stockData.getListeAchat().getQuantite(key));
			
			achatAffichable.add(nouv);
		}
		return achatAffichable;
	}
	
	public ObservableList<Personnel> getPersonnelData() {
		return this.personnelData;
	}
	
	public void setPersonnelData(ObservableList<Personnel> s) {
		this.personnelData = s;
	}
	
	public Stock getStockData() {
		return this.stockData;
	}
	
	
	public ObservableList<Chaine> getChaineData() {
		return chaineData;
	}
	
	
	public Stage getPrimaryStage() {
	     return primaryStage;
	}

	public void setStockDatta(Stock stock) {
		this.stockData = stock;
	}
	
	public void setChaineData(ObservableList<Chaine> chaineData) {
		this.chaineData = chaineData;
	}
	

}
