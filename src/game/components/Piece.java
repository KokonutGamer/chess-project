package game.components;

import java.util.ArrayList;

import game.Board;
import game.Capture;
import game.Move;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {

	// Graphical components
	protected String name;
	protected ImageView image;

	// Board game components
	protected String color;
	protected Board board;
	protected ArrayList<Move> moves = new ArrayList<Move>();
	protected IntegerProperty position = new SimpleIntegerProperty();

	public Piece() {
		this.name = "";
		this.image = null;
		this.color = null;
	}

	public Piece(String name) {
		super("/images/pieces/" + name + ".png");
		this.color = name.substring(0, 5);
		if (!(color.equals("White") || color.equals("Black"))) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.setFitHeight(100);
		this.setFitWidth(100);
	}

	public Move moveTo(int targetSquare) {
		if (moves.contains(new Move(position.getValue(), targetSquare))) {
			int oldPos = position.getValue();
			position.setValue(targetSquare);
			;
			return new Move(oldPos, position.getValue());
		}
		throw new IllegalArgumentException();
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position.getValue();
	}

	public IntegerProperty getPositionProperty() {
		return position;
	}

	public String getColor() {
		return color;
	}

	public void setPosition(int index) {
		position.setValue(index);
		;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}

	public boolean canMoveTo(int targetSquare) {
		boolean isMovable = true;
		for (Piece piece : board.getPieces()) {
			if (piece.getPosition() == targetSquare && piece.getColor().equals(color)) {
				isMovable = false;
			}
		}
		return isMovable;
	}

	public boolean canCaptureOn(int targetSquare) {
		boolean canCapture = false;
		for (Piece piece : board.getPieces()) {
			if (piece.getPosition() == targetSquare && !piece.getColor().equals(color)) {
				canCapture = true;
			}
		}
		return canCapture;
	}

	public void choose(Move move) {
		if (moves.contains(move)) {
			move = moves.get(moves.indexOf(move));
			if (move instanceof Capture) {
				Piece capturedPiece = ((Capture) move).getCapturePiece();
				board.removePiece(capturedPiece);
			}
			position.setValue(move.getTargetSquare());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Piece) {
			Piece other = (Piece) obj;
			return position.getValue() == other.getPosition() && color.equals(other.getColor())
					&& name.equals(other.getName());
		}
		return false;
	}

	@Override
	public String toString() {
		// For debugging purposes
		return this.getClass().getSimpleName() + "\t[color=" + color + ", position=" + position + "]";
	}

	public abstract void generateMoves();
}
