package application;

import java.util.ArrayList;
import java.util.HashMap;

import game.Board;
import game.Move;
import game.MoveData;
import game.components.Piece;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import test.ANSI;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

	// private Scene intro; TODO finish core gameplay
	// private Scene titleScreen; TODO finish core gameplay
	private Scene menus;
	private HashMap<String, Region> menuContent;
	private String currentMenu;

	private Scene pvpScene;
	private Scene pvcpuScene;
	private Board currentGame;
	private boolean selectedSquare;
	private Piece selectedPiece;
	private ArrayList<BoardSquare> squares;
	private ArrayList<Move> moves;
	private int mouseClickIndex;

	private Scene endScreen; // NOTE: this may change into a component instead of a Scene

	public final int SQUARE_SIZE = 100;

	{
		currentGame = new Board();
		squares = new ArrayList<BoardSquare>();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
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
				currentGame = new Board();
				primaryStage.setScene(pvpScene);
			});
			pvcpu.setOnAction(click -> {
				currentGame = new Board();
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
			GridPane board = new GridPane();
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					int index = col + (7 - row) * 8;
					Color c = ((row + col) % 2 == 0) ? Color.BEIGE : Color.BROWN;
					BoardSquare square = new BoardSquare(SQUARE_SIZE, c, index);
					squares.add(square);
					board.add(square, col, row);
				}
			}
			pvpLayout.setLeft(board);
			setGameScene(board, currentGame);

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

	/*
	 * public void playButtonClick(Scene scene) {
	 * 
	 * }
	 * 
	 * public Button createPlayButton(String text) { Button button = new
	 * Button(text); button.setOnAction(click -> ); }
	 */

	public void setGameScene(GridPane board, Board currentGame) {
		ArrayList<Piece> pieces = currentGame.getPieces();
		pieces.forEach(piece -> {
			setPiece(board, piece);
		});
	}

	public void removePiece(GridPane board, Piece piece) {
		board.getChildren().remove(piece);
		currentGame.getPieces().remove(piece);
	}

	public void setPiece(GridPane board, Piece piece) {
		if(!currentGame.getPieces().contains(piece)) {			
			currentGame.getPieces().add(piece);
		}
		piece.setCursor(Cursor.HAND);
		piece.setOnMouseReleased(click -> {
			if (!selectedSquare) {
				selectedPiece = piece;
				selectedSquare = true;
				int index = squares.indexOf(new BoardSquare(selectedPiece.getPosition()));
				BoardSquare square = squares.get(index);
				square.highlight(true);
				piece.generateMoves(currentGame.getPieces());
				moves = piece.getMoves();
			} else {
				selectedSquare = false;
				squares.forEach(square -> square.highlight(false));
			}
		});
		piece.setFitWidth(SQUARE_SIZE);
		piece.setFitHeight(SQUARE_SIZE);
		int row = (63 - piece.getPosition()) / 8;
		int col = piece.getPosition() % 8;
		board.add(piece, col, row);
	}

	public static void main(String[] args) {
		MoveData.precomputeMoveData();
		launch(args);
	}

	class BoardSquare extends Rectangle {
		private int index;
		private Paint color;

		BoardSquare(int index) {
			this.index = index;
		}

		BoardSquare(double sideLength, Paint color, int index) {
			super(sideLength, sideLength, color);
			this.index = index;
			this.color = color;
			this.setOnMouseReleased(event -> {
				mouseClickIndex = index;
				if (selectedSquare) {
					if (moves.contains(new Move(selectedPiece.getPosition(), mouseClickIndex))) {
						System.out.println("Selected " + ANSI.YELLOW_BOLD + selectedPiece.getClass().getSimpleName() + ANSI.RESET + " has moved!");
						removePiece((GridPane) this.getParent(), selectedPiece);
						selectedPiece.moveTo(mouseClickIndex);
						setPiece((GridPane) this.getParent(), selectedPiece);
						selectedPiece = null;
					}
					squares.forEach(square -> square.highlight(false));
					selectedSquare = false;
				}
			});
		}

		public int getIndex() {
			return index;
		}

		public void highlight(boolean highlighted) {
			super.setFill(highlighted ? Color.GREEN : color);
		}

		@Override
		public boolean equals(Object obj) {
			return this.index == ((BoardSquare) obj).getIndex();
		}

		@Override
		public String toString() {
			return "Square at index " + index;
		}
	}
}
