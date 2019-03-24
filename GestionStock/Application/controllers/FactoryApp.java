package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import parser.CSVReader;




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
import models.Stock.ListeAchat;
import models.Stock.Stock;
import views.SimulationCtrl;
import views.StockCtrl;

public class FactoryApp extends Application {
	
	 private Stage primaryStage;
	 private Stock stockData;
	 private ObservableList<Chaine> chaineData;
	 
	
	public FactoryApp() {
		super();
		this.stockData = new Stock();
		this.chaineData = FXCollections.observableArrayList();
/*
		Element e1 = new Element("element 1","element 1","kg",0,10);
		Element e2 = new Element("element 2","element 2","kg",1,1);

		Chaine c1 = new Chaine("chaine1","chaine1");
		c1.addComposant(e2, 1);
		c1.addSortie(e1, 2);

		Chaine c2 = new Chaine("chaine2","chaine2");
		c2.addComposant(e1,0.5 );
		c2.addSortie(e2, 1);

		chaineData.add(c1);
		chaineData.add(c2);

		this.stockData.stocker(e1, 3);
		this.stockData.stocker(e2, 3);
		this.stockData.acheter(e2, (double) 1);*/
	 }





	public static void main(String[] args) {
		launch(args);
	}
		
	

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UsineAPP");

        //Affichage de l'ecran 
        showIndex();		
	}
	
	public void showIndex() {
		 
		try {
			
			//chargement du fichier fxml
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(FactoryApp.class.getResource("/views/index.fxml"));
	        loader.setClassLoader(this.getClass().getClassLoader());
	        AnchorPane  indexView = (AnchorPane) loader.load();
	       
	        
	        //affichage de l'index 
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
	
	public void showSimulationDialog(Stock stock) {
	    try {
	        // charge le fichier fxml
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(FactoryApp.class.getResource("/views/simulationDialog.fxml"));
	        loader.setClassLoader(this.getClass().getClassLoader());
	        AnchorPane page = (AnchorPane) loader.load();

	        // cree la fenetre de dialogue
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Stock après simulation");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(this.primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // On place le stock et la liste d'achat dans le controller
	        SimulationCtrl controller = loader.getController();
	        controller.setFactoryApp(this);
	        controller.setStockData(stock);

	        // Show the dialog and wait until the user closes it
	        //dialogStage.showAndWait();
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
					key.getPrixAchat(),key.getPrixVente(),this.getStockData().getQuantite(key));
			
			stockAffichable.add(nouv);
		}
		return stockAffichable;
	}
	
	public ObservableList<ElementAffichable> getListeAchatAffichable(){
		ObservableList<ElementAffichable> achatAffichable = FXCollections.observableArrayList();
		
		for(Element key : this.stockData.getListeAchat().getListeAchat().keySet()) {
			
			ElementAffichable nouv = new ElementAffichable(key.getNom(),key.getCodeUnique(),key.getUniteMesure(),
					key.getPrixAchat(),key.getPrixVente(),this.stockData.getListeAchat().getQuantite(key));
			
			achatAffichable.add(nouv);
		}
		return achatAffichable;
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


}
