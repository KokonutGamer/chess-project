package game.components;

import java.util.ArrayList;
import java.util.List;

import game.Move;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {

	// Graphical components
	protected String name;
	protected ImageView image;

	// Board game components
	protected IntegerProperty position;
//	protected int position;
	protected String color;
	protected ArrayList<Move> moves;

	{
		moves = new ArrayList<Move>();
		position = new SimpleIntegerProperty();
	}

	public Piece() {
		this.name = null;
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
			position.setValue(targetSquare);;
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
		position.setValue(index);;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}

	public Generic onSquare(int targetSquare) {
		Generic toReturn = new Generic(targetSquare);
		return toReturn;
	}

	public boolean isOccupied(List<Piece> board, int targetSquare) {
		return board.contains(onSquare(targetSquare));
	}

	public boolean isCapture(List<Piece> board, int targetSquare) {
		boolean isCapture = true;
		if (board.contains(onSquare(targetSquare))) {
			Piece other = board.get(board.indexOf(onSquare(targetSquare)));
			isCapture = !other.getColor().equals(color);
		}
		return isCapture;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Piece) {
			return this.position.getValue() == ((Piece) obj).getPosition();
		}
		return false;
	}

	@Override
	public String toString() {
		// For debugging purposes
		return this.getClass().getSimpleName() + "\t[color=" + color + ", position=" + position + "]";
	}

	public abstract void generateMoves(List<Piece> board);

	class Generic extends Piece {

		public Generic(int position) {
			this.position.setValue(position);;
		}

		@Override
		public void generateMoves(List<Piece> board) {
		}

	}
}
