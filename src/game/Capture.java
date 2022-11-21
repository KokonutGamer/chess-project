package game;

import game.components.Piece;

public class Capture extends Move {

	private Piece capturePiece;

	public Capture(int startSquare, int targetSquare, Piece capturePiece) {
		super(startSquare, targetSquare);
		this.capturePiece = capturePiece;
	}

	public Piece getCapturePiece() {
		return capturePiece;
	}

}
