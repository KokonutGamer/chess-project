package game.components;

import java.util.ArrayList;

public class Bishop extends SlidingPiece {

	public Bishop(String color) {
		super(color + "Bishop");
	}

	@Override
	public void generateMoves(ArrayList<Piece> board) {
		startDirIndex = 4;
		endDirIndex = 8;
		super.generateMoves(board);
	}

}
