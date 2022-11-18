package game.components;

import java.util.ArrayList;

public class Queen extends SlidingPiece {

	public Queen(String color) {
		super(color + "Queen");
	}

	@Override
	public void generateMoves(ArrayList<Piece> board) {
		startDirIndex = 0;
		endDirIndex = 8;
		super.generateMoves(board);
	}

}
