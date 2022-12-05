package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import game.components.Piece;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import test.ANSI;
import util.ObservableStack;

public class Board extends GridPane {

	// Constants
	private final int SQUARE_SIZE = 100;
	private final String[] colors = { "White", "Black" };

	// Primatives
	private int numberOfMoves;
	private boolean isPieceSelected;
//	private boolean isKingInCheck;
	
	// Objects
	private Piece selectedPiece;
	private StringProperty colorToMove = new SimpleStringProperty();
	
	// Data Structures
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private ArrayList<BoardSquare> squares = new ArrayList<BoardSquare>();
	private ObservableStack<Move> moveList = new ObservableStack<Move>();

	public Board() {
		this("/files/PieceStartingPositions.csv");
	}

	public Board(String positionPath) {

		numberOfMoves = 0;
		colorToMove.setValue(colors[0]);
		isPieceSelected = false;

		// Set column and row constraints
		final int sides = 8;
		for (int i = 0; i < sides; i++) {
			ColumnConstraints colConst = new ColumnConstraints(SQUARE_SIZE);
			RowConstraints rowConst = new RowConstraints(SQUARE_SIZE);
			getColumnConstraints().add(colConst);
			getRowConstraints().add(rowConst);
		}

		// Create all board squares and add them to the squares ArrayList
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				int index = col + (7 - row) * 8;
				Color c = ((row + col) % 2 == 0) ? Color.BEIGE : Color.BROWN;
				BoardSquare square = new BoardSquare(SQUARE_SIZE, c, index);
				squares.add(square);
			}
		}

		// Load inital starting position
		loadPosition(positionPath);

		// Display all pieces and squares on the scene
		setGameScene();
	}

	// Load position parses the given file specified by the file path and generates
	// a piece off of each line
	// The Class and Constructor classes help to construct the correct subclass of
	// Piece for each given piece
	// Each instance of Piece is then stored into the pieces ArrayList
	private void loadPosition(String positionPath) {
		String line;
		pieces = new ArrayList<Piece>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Board.class.getResourceAsStream(positionPath)))) {
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				String pieceType = tokens[1].substring(5);

				// Load the appropriate Class of the given pieceType
				Class<?> piece = Class.forName("game.components." + pieceType);
				Constructor<?> constructor = piece.getConstructor(String.class);
				Piece currentPiece = (Piece) constructor.newInstance(tokens[1].substring(0, 5));

				// Set the position of the piece
				// NOTE: ASCII value of 'a' = 97, ASCII value of 'h' = 104
				String position = tokens[0].toLowerCase();
				int fileAsInt = position.charAt(0) - 97;
				int rank = Integer.parseInt(position.substring(1)) - 1;
				currentPiece.setPosition(fileAsInt + rank * 8);

				pieces.add(currentPiece);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Initializes each Piece and BoardSquare onto the GridPane
	public void setGameScene() {

		// Add each instance of BoardSquare in squares onto this GridPane
		squares.forEach(square -> {
			int index = square.getIndex();
			add(square, index % 8, (63 - index) / 8);
		});

		// Add each instance of Piece in pieces onto this GridPane
		pieces.forEach(piece -> {

			piece.setBoard(this);

			int index = piece.getPosition();

			// Bind the mouseTransparent property to whether its color is equal to the
			// current color
			piece.mouseTransparentProperty().bind(Bindings.notEqual(colorToMove, piece.getColor()));

			piece.setCursor(Cursor.HAND);

			// When a MouseEvent of type MOUSE_RELEASED hits this piece, call generateMoves
			piece.setOnMouseReleased(release -> {
				squares.forEach(square -> square.highlight(false));
				selectedPiece = piece;
				isPieceSelected = true;
				piece.generateMoves();
				selectedPiece.getMoves().forEach(move -> {
					int boardSquareIndex = move.getTargetSquare();
					for(BoardSquare square : squares) {
						if(square.getIndex() == boardSquareIndex) {
							square.highlight(true);
						}
					}
				});
			});

			// Whenever a piece is moved, update its position on the board
			piece.getPositionProperty().addListener((property, oldVal, newVal) -> {
				this.getChildren().remove(piece);
				int target = newVal.intValue();
				add(piece, target % 8, (63 - target) / 8);
				updateBoardState();
			});

			add(piece, index % 8, (63 - index) / 8);

		});

	}

	public void removePiece(Piece piece) {
		pieces.remove(piece);
		this.getChildren().remove(piece);
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	public ObservableStack<Move> getMoveList() {
		return moveList;
	}

	public void updateBoardState() {
		isPieceSelected = false;
		selectedPiece = null;
		numberOfMoves++;
		colorToMove.setValue(colors[numberOfMoves % 2]);
		
		// Check whether the king is in check
//		if(true) {
//			isKingInCheck = true;
//		}
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
				if (isPieceSelected) {
					Move move = new Move(selectedPiece.getPosition(), index);
					if (selectedPiece.getMoves().contains(move)) {
						System.out.println("Selected " + ANSI.YELLOW_BOLD + selectedPiece.getClass().getSimpleName()
								+ ANSI.RESET + " has moved!");
						moveList.push(selectedPiece.choose(move));
					}
					squares.forEach(square -> {
						square.highlight(false);
					});
					isPieceSelected = false;
				}
			});
		}

		public int getIndex() {
			return index;
		}

		public void highlight(boolean highlighted) {
			Color highlight = (index / 8 % 2 == 0) ? Color.LIME : Color.LIMEGREEN;
			super.setFill(highlighted ? highlight : color);
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
