package application;


import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Node;


public class WebMain extends Application {
	private final Tab historyTab = new Tab("Hisoty");
	private final TabPane tabPane = new TabPane();
	private SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
	private final Button newTabbutton = new Button();
	private boolean isHistoryDisplay = false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		//set stage
		primaryStage.setTitle("website");
		primaryStage.setScene(layout());
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("google-drive.png")));
		primaryStage.show();

	}
	
	public Scene layout() throws NumberFormatException, FileNotFoundException {
		// Landing initialized tab
		Tab landingTab = new TabController("http://www.google.com");

		// add new tab button
		Tab plusTab = new Tab();
		ImageView view = new ImageView(getClass().getResource("plus.png").toExternalForm());
		view.setFitWidth(10);
		view.setFitHeight(10);
		newTabbutton.setStyle("-fx-background-color: transparent");
		newTabbutton.setGraphic(view);
		plusTab.setGraphic(newTabbutton);
		plusTab.setDisable(true);
		plusTab.setClosable(false);
		
		newTabbutton.setOnMouseClicked(action -> {
			Tab newTab = new TabController(null);
			tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
			selectionModel.select(newTab);

		});
		
		//set tab pane
		tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
		tabPane.getTabs().addAll(landingTab, plusTab);
		tabPane.setPrefWidth(1000);
		tabPane.setPrefHeight(600);
		

		// set scene
		Scene scene = new Scene(tabPane, getVisualScreenWidth() / 1.2, getVisualScreenHeight() / 1.2);
		return scene;
	}



	public static double getVisualScreenWidth() {
		return Screen.getPrimary().getVisualBounds().getWidth();
	}

	public static double getVisualScreenHeight() {
		return Screen.getPrimary().getVisualBounds().getHeight();
	}
	
	


	public static void main(String[] args) {
		Application.launch(args);

	}

}
