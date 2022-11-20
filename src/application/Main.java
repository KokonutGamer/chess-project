package application;

import java.util.HashMap;

import game.Board;
import game.MoveData;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Main extends Application {

	// private Scene intro; TODO finish core gameplay
	// private Scene titleScreen; TODO finish core gameplay
	private Scene menus;
	private HashMap<String, Region> menuContent;
	private String currentMenu;

	private Scene pvpScene;
	private Scene pvcpuScene;
	private Board game;

//	private Scene endScreen; // NOTE: this may change into a component instead of a Scene

	public final int SQUARE_SIZE = 100;

	{
		game = new Board();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Play Chess!");
			
			// Menus Layout
			BorderPane menusLayout = new BorderPane();

			// Menus
			BorderPane menusPane = new BorderPane();
			menus = new Scene(menusLayout, 400, 400);
			menuContent = new HashMap<String, Region>();

			// Main
			VBox mainContent = new VBox();
			mainContent.setAlignment(Pos.CENTER);
			Button play = createMenuButton("Play", "Play", menusPane);
			Button settings = createMenuButton("Settings", "Settings", menusPane);
			Button credits = createMenuButton("Credits", "Credits", menusPane);
			mainContent.getChildren().addAll(play, settings, credits);
			menuContent.put("Main", mainContent);

			// Play
			VBox playContent = new VBox();
			playContent.setAlignment(Pos.CENTER);
			Button pvp = new Button("Human vs Human");
			Button pvcpu = new Button("Human vs Computer");
			pvp.setOnAction(click -> {
				game = new Board();
				primaryStage.setScene(pvpScene);
			});
			pvcpu.setOnAction(click -> {
				game = new Board();
				primaryStage.setScene(pvcpuScene);
			});
			playContent.getChildren().addAll(pvp, pvcpu);
			menuContent.put("Play", playContent);

			// Settings
			VBox settingsContent = new VBox();
			settingsContent.setAlignment(Pos.CENTER);
			Button boardColors = new Button("Change board colors");
			Button pieceColors = new Button("Change piece colors");
			Button timer = new Button("Timer preferences");
			settingsContent.getChildren().addAll(boardColors, pieceColors, timer);
			menuContent.put("Settings", settingsContent);

			// Credits
			Region creditsContent = new Region();
			menuContent.put("Credits", creditsContent);

			setMenuScene(menusPane, "Main");
			menusLayout.setCenter(menusPane);

			// PvP Layout
			BorderPane pvpLayout = new BorderPane();
			pvpLayout.setLeft(game);
			
			// PvP
			pvpScene = new Scene(pvpLayout, SQUARE_SIZE * 8, SQUARE_SIZE * 8);

			primaryStage.setScene(menus);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeBorderPaneItems(BorderPane pane, String currentMenu) {
		pane.getChildren().remove(menuContent.get(currentMenu));
	}

	public void setMenuScene(BorderPane root, String currentMenu) {
		root.setTop(new Label("Chess Project"));
		if (!currentMenu.equals("Main")) {
			root.setBottom(createMenuButton("Return to Main Menu", "Main", root));
		} else {
			root.setBottom(new Label(""));
		}
		BorderPane.setAlignment(root.getTop(), Pos.CENTER);
		BorderPane.setAlignment(root.getBottom(), Pos.CENTER);
		root.setCenter(menuContent.get(currentMenu));
	}

	public void menuButtonClick(String nextMenu, BorderPane root) {
		removeBorderPaneItems(root, currentMenu);
		setMenuScene(root, nextMenu);
	}

	public Button createMenuButton(String text, String nextMenu, BorderPane pane) {
		Button button = new Button(text);
		button.setOnAction(click -> menuButtonClick(nextMenu, pane));
		return button;
	}

	public static void main(String[] args) {
		MoveData.precomputeMoveData();
		launch(args);
	}
}
