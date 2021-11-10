package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

public class TabController extends Tab {
		private VBox vbox = new VBox();
		private TextField textField = new TextField();
		private WebView tabView = new WebView();
		private WebEngine engine = tabView.getEngine();
		private double webZoom = 1;
		private WebHistory history;
		private final Tab historyTab = new Tab("Hisoty");
		private boolean isHistoryDisplay = false;
		private HBox root;


		public  TabController(String homeURL) {
			//constructor to set the tab by combining controller and webview
			this.setContent(vbox); 
			this.root = controller();
			engine.load(homeURL);
			vbox.getChildren().addAll(root, tabView);
			vbox.setPadding(new Insets(5, 5, 5, 5));
			vbox.setAlignment(Pos.CENTER);
			VBox.setVgrow(vbox, Priority.ALWAYS);
			}

				//buttons\menu\textfiles
				public HBox controller() {
					//// set home button
					Button homePage = new Button();
					homePage.setText("Home");
					homePage.setPrefSize(80, 20);
					homePage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> homePage());
					homePage.setCursor(Cursor.HAND);
					//load page
					Button launch = new Button("Launch");
					launch.setPrefSize(80, 20);
					launch.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> loadPage());
					launch.setCursor(Cursor.HAND);
					//add textfiled
					HBox load = new HBox();
					textField.setFont(Font.font(15));
					textField.setAlignment(Pos.CENTER);// Align text to center
					textField.setPrefWidth(750);// Set width
					load.getChildren().addAll(homePage,textField, launch);
					load.setAlignment(Pos.CENTER);
					HBox.setHgrow(textField, Priority.ALWAYS);

					// set back/forward/reload togglebutton
					ToggleButton toggleButton1 = new ToggleButton();
					ImageView backimage = new ImageView(getClass().getResource("left.png").toExternalForm());
					toggleButton1.setGraphic(backimage);
					backimage.setFitHeight(20);
					backimage.setFitWidth(20);
					toggleButton1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> back());

					ToggleButton toggleButton2 = new ToggleButton();
					ImageView forwardimage = new ImageView(getClass().getResource("right.png").toExternalForm());
					toggleButton2.setGraphic(forwardimage);
					forwardimage.setFitHeight(20);
					forwardimage.setFitWidth(20);

					toggleButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> forward());

					ToggleButton toggleButton3 = new ToggleButton();
					ImageView reloadimage = new ImageView(getClass().getResource("refresh.png").toExternalForm());
					toggleButton3.setGraphic(reloadimage);
					reloadimage.setFitHeight(20);
					reloadimage.setFitWidth(20);
					toggleButton3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> refresh());

					ToggleGroup toggleGroup = new ToggleGroup();

					toggleButton1.setToggleGroup(toggleGroup);
					toggleButton2.setToggleGroup(toggleGroup);
					toggleButton3.setToggleGroup(toggleGroup);
					
					HBox controller = new HBox(toggleButton1, toggleButton2, toggleButton3);
					controller.setAlignment(Pos.CENTER);

			        MenuItem displayHistory = new MenuItem("History");
			        MenuItem zoomIn = new MenuItem("ZoomIn+");
			        MenuItem zoomOut = new MenuItem("ZoomOut-");

			        MenuButton menuButton = new MenuButton("Options", null, displayHistory, zoomIn, zoomOut);
					
			        displayHistory.setOnAction(event -> {displayHistory();});
			        zoomIn.setOnAction(event -> {zoomIn();});
			        zoomOut.setOnAction(event -> {zoomOut();});

					
					ImageView settingimage = new ImageView(getClass().getResource("Settings-32.png").toExternalForm());
					settingimage.setFitHeight(20);
					settingimage.setFitWidth(20);
			        menuButton.setGraphic(settingimage);
			        
					HBox menusetting = new HBox(menuButton);
					menusetting.setPrefWidth(10);
					menusetting.setPadding(new Insets(5, 5, 5, 5));
					menusetting.setAlignment(Pos.TOP_RIGHT);

					// tools
					// Node is the parent of all GUI objects in JavaFX
					HBox root = new HBox(10);
					root.setPadding(new Insets(5, 5, 5, 5));
					root.setStyle("-fx-background-color:white");
					root.getChildren().addAll(controller, load, menusetting);
					HBox.setHgrow(root, Priority.ALWAYS);
					return root;
					
				}

			
				//homepage loading method
				public void homePage() {
					engine.load("http://www.google.com");
					if (!vbox.getChildren().contains(tabView)) {
						vbox.getChildren().add(tabView);
					} else {
						engine.load("http://www.google.com");
					}

				}

				//load new page
				public void loadPage() {
					//if the user tab URL into the textfild,update webview 
					String validURL =  "(([\\w]+:)?//)?(([\\d\\w]|%[a-fA-f\\d]{2,2})+(:([\\d\\w]|%[a-fA-f\\d]{2,2})+)?@)?([\\d\\w][-\\d\\w]{0,253}[\\d\\w]\\.)+[\\w]{2,63}(:[\\d]+)?(/([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})*)*(\\?(&?([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})=?)*)?(#([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})*)?";
					String enteredText = textField.getText();
					if (enteredText.matches(validURL)) {
						String hashttp = "^(http|http)://";
						if (enteredText.matches(hashttp)) {
							tabView.getEngine().load(enteredText);
						} else {
							tabView.getEngine().load("https://" + enteredText);
						}
					} else {
						tabView.getEngine().load("http://google.com/search?q=" + enteredText);
					}

				}

				//refresh web
				public void refresh() {
					engine.reload();
				}

				//zoom in web
				public void zoomIn() {
					webZoom += 0.25;
					tabView.setZoom(webZoom);
				}

				//zoom out web
				public void zoomOut() {
					webZoom -= 0.25;
					tabView.setZoom(webZoom);
				}

				
				//go back to previous web
				public void back() {
					history = engine.getHistory();
					ObservableList<WebHistory.Entry> entries = history.getEntries();
					if (history.getCurrentIndex() >= 1) {
						textField.setText(entries.get(history.getCurrentIndex() - 1).getUrl());
						history.go(-1);
					}
				}
				
				//go forward to +1 web
				public void forward() {
					history = engine.getHistory();
					ObservableList<WebHistory.Entry> entries = history.getEntries();

					if (history.getCurrentIndex() < entries.size() - 1) {
						textField.setText(entries.get(history.getCurrentIndex() + 1).getUrl());
						history.go(1);
					}
				}
				
				//display history
				public void displayHistory() {
					if (!isHistoryDisplay) {
						isHistoryDisplay = true;
						history = engine.getHistory();
						ObservableList<WebHistory.Entry> entries = history.getEntries();
						TextFlow tf = new TextFlow();
						for (WebHistory.Entry entry : entries) {

							Text tx = new Text(entry.getUrl() + "" + entry.getLastVisitedDate());
							tf.getChildren().add(tx);
							System.out.println(entry.getUrl() + "" + entry.getLastVisitedDate());
						}
						this.setContent(tf);

						this.setOnCloseRequest(new EventHandler<Event>() {
							@Override
							public void handle(Event arg0) {
								isHistoryDisplay = false;
							}
						});
					} 
				}
			}
