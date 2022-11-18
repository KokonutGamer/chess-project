package game.components;

import java.util.ArrayList;

public class Rook extends SlidingPiece {

	public Rook(String color) {
		super(color + "Rook");
	}

	@Override
	public void generateMoves(ArrayList<Piece> board) {
		startDirIndex = 0;
		endDirIndex = 4;
		super.generateMoves(board);
	}

}
