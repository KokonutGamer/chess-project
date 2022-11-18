package game.components;

import java.util.ArrayList;

import game.Move;
import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {

	// Graphical components
	protected String name;
	protected ImageView image;

	// Board game components
	protected int position;
	protected String color;
	protected ArrayList<Move> moves;

	{
		moves = new ArrayList<Move>();
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
	}

	public Move moveTo(int targetSquare) {
		if (moves.contains(new Move(position, targetSquare))) {
			int oldPos = position;
			position = targetSquare;
			return new Move(oldPos, position);
		}
		throw new IllegalArgumentException();
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public String getColor() {
		return color;
	}

	public void setPosition(int index) {
		position = index;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Piece) {
			return this.position == ((Piece) obj).getPosition();
		}
		return false;
	}

	@Override
	public String toString() {
		// For debugging purposes
		return this.getClass().getSimpleName() + "\t[color=" + color + ", position=" + position + "]";
	}

	public Generic onSquare(int targetSquare) {
		Generic toReturn = new Generic(targetSquare);
		return toReturn;
	}
	
	public boolean isOccupied(ArrayList<Piece> board, int targetSquare) {
		return board.contains(onSquare(targetSquare));
	}

	public boolean isCapture(ArrayList<Piece> board, int targetSquare) {
		boolean isCapture = true;
		if (board.contains(onSquare(targetSquare))) {
			Piece other = board.get(board.indexOf(onSquare(targetSquare)));
			isCapture = !other.getColor().equals(color);
		}
		return isCapture;
	}

	public abstract void generateMoves(ArrayList<Piece> board);

	class Generic extends Piece {

		public Generic(int position) {
			this.position = position;
		}

		@Override
		public void generateMoves(ArrayList<Piece> board) {
		}

	}
}
