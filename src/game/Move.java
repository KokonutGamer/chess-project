package game;

import game.components.Piece;
import test.ANSI;

public class Move {
	
	protected int startSquare;
	protected int targetSquare;
	protected Piece movingPiece;

	public Move(int startSquare, int targetSquare) {
		this.startSquare = startSquare;
		this.targetSquare = targetSquare;
	}

	public int getStartSquare() {
		return startSquare;
	}

	public int getTargetSquare() {
		return targetSquare;
	}
	
	public void setMovingPiece(Piece piece) {
		movingPiece = piece;
	}
	
	public String toChessNotation() {
		String notation = "";
		switch(movingPiece.getClass().getSimpleName()) {
		case "King":
			notation += "K";
			break;
		case "Queen":
			notation += "Q";
			break;
		case "Rook":
			notation += "R";
			break;
		case "Bishop":
			notation += "B";
			break;
		case "Knight":
			notation += "N";
			break;
		default:
			break;
		}
		if(getClass().getSimpleName().equals("Capture")) {
			notation += "x";
		}
		int rank = targetSquare / 8 + 1;
		char file = (char)(targetSquare % 8 + 97);
		return notation + file + rank;
	}

	@Override
	public String toString() {
		// For debugging purposes
		String toString = "";
		if(movingPiece == null) {
			toString = "Move [startSquare=" + ANSI.WHITE_UNDERLINED + startSquare + ANSI.RESET + ", targetSquare="
					+ ANSI.WHITE_UNDERLINED + targetSquare + ANSI.RESET + "]";
		} else {
			toString = toChessNotation();
		}
		return toString;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		return startSquare == ((Move) obj).getStartSquare() && targetSquare == ((Move) obj).getTargetSquare();
	}
	
}
