package game.components;

import java.util.List;

public class Bishop extends SlidingPiece {

	public Bishop(String color) {
		super(color + "Bishop");
	}

	@Override
	public void generateMoves(List<Piece> board) {
		startDirIndex = 4;
		endDirIndex = 8;
		super.generateMoves(board);
	}

}
